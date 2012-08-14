package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import it.unical.mat.wrapper.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.semanticweb.clipper.QueryAnswersingSystem;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.profile.ClipperHornSHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.InternalCQParser;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.util.AnswerParser;
import org.semanticweb.clipper.util.QueriesRelatedRules;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

import com.google.common.collect.Lists;

@Getter
@Setter
public class QAHornSHIQ implements QueryAnswersingSystem {

	private String dataLogName;
	private String ontologyName;
	private String queryFileName;
	private String queryString;
	private String queryPrefix;
	private String datalogEngine = "dlv";

	private String queryRewriter = "old";

	private String headPredicate;
	private List<String> answers;
	private List<List<String>> decodedAnswers;
	private CQ cq;

	private ClipperReport clipperReport = new ClipperReport();

	private Collection<CQ> rewrittenQueries;

	// String dlvPath = "lib/dlv";
	String dlvPath;

	private Collection<OWLOntology> ontologies;
	private ClipperHornSHIQOntology clipperOntology;

	public QAHornSHIQ() {
		answers = new ArrayList<String>();
		decodedAnswers = new ArrayList<List<String>>();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);// default
		this.ontologies = new ArrayList<OWLOntology>();
	}

	public void setNamingStrategy(NamingStrategy namingStrategy) {
		ClipperManager.getInstance().setNamingStrategy(namingStrategy);

	}

	/**
	 * @return Datalog program that contains: rewritten queries, completion
	 *         rules, and ABox assertions
	 */
	public void generateDataLog() {

		// if (this.ontologyName == null || this.dataLogName == null) {
		// System.out.println("ontologyName and datalogName should be specified!");
		// } else {

		// File file = new File(ontologyName);
		if (cq == null) {
			InternalCQParser cqParser = new InternalCQParser();
			cqParser.setQueryString(queryString);
			cqParser.setPrefix(queryPrefix);
			cq = cqParser.getCq();
		}

		// may be useful in the future
		// if (cq.getHead().getPredicate().getEncoding() != -1)
		// this.headPredicate = "q" +
		// cq.getHead().getPredicate().getEncoding();
		// else
		String ans = "ans";
		cq.getHead().setPredicate(new NonDLPredicate("ans") );
		
		this.headPredicate = ans;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("% Encoded Input query:" + cq);
		}

		try {

			preprocessOntologies();

			TBoxReasoning tb = saturateTbox();

			reduceOntologyToDatalog(tb);

			queryRewriting(tb);

			reduceRewrittenQueriesToDatalog(tb);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param tb
	 * @throws IOException
	 */
	private void reduceRewrittenQueriesToDatalog(TBoxReasoning tb) throws IOException {
		long starCoutingRelatedRule = System.currentTimeMillis();
		// this.rewrittenQueries = qr.getUcq();
		// Set<CQ> ucq = qr.getUcq();
		Set<CQ> ucq = new HashSet<CQ>(rewrittenQueries);
		QueriesRelatedRules relatedRules = new QueriesRelatedRules(clipperOntology, ucq);
		relatedRules.setCoreImps(tb.getImpContainer().getImps());
		relatedRules.setCoreEnfs(tb.getEnfContainer().getEnfs());
		relatedRules.countUCQRelatedRules();
		long endCoutingRelatedRule = System.currentTimeMillis();
		this.clipperReport.setCoutingRealtedRulesTime(endCoutingRelatedRule - starCoutingRelatedRule);

		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("==============================================");
			System.out.println("Numbers of rewritten queries is: " + ucq.size());
			System.out.println("==============================================");
			System.out.println("Rewritten queries: ");
			for (CQ query : ucq)
				System.out.println(formatQuery(query));
			System.out.println("==============================================");
			System.out.println("Datalog related to rewritten queries: ");
			for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
				System.out.println(rule);
			}
		}
		clipperReport.setNumberOfRewrittenQueries(ucq.size());
		clipperReport.setNumberOfRewrittenQueriesAndRules(relatedRules.getUcqRelatedDatalogRules().size() + ucq.size());

		FileWriter fstream = new FileWriter(this.dataLogName, true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write("% rewritten queries:\n");
		for (CQ query : ucq)
			out.write(formatQuery(query) + "\n");
		// for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
		// out.write(rule + "\n");
		// }

		out.close();
	}

	/**
	 * @param tb
	 */
	private void queryRewriting(TBoxReasoning tb) {
		QueryRewriter qr = createQueryRewriter(clipperOntology, tb);

		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%Enforces relations:");
			for (EnforcedRelation e : tb.getEnfContainer().getEnfs()) {
				printClassNamesFromEncodedNamesSet(e.getType1());
				printRoleNamesFromEncodedNamesSet(e.getRoles());
				printClassNamesFromEncodedNamesSet(e.getType2());
				System.out.println();
			}

		}
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%rewritten queries:");
		}
		// /////////////////////////////////////////////////////////
		// Evaluate query rewriting time
		long rewritingBegin = System.currentTimeMillis();
		this.rewrittenQueries = qr.rewrite(cq);
		long rewritingEnd = System.currentTimeMillis();
		clipperReport.setQueryRewritingTime(rewritingEnd - rewritingBegin);
		// End of evaluating query rewriting time
		// /////////////////////////////////////////////////////////
	}

	/**
	 * @param tb
	 */
	private void reduceOntologyToDatalog(TBoxReasoning tb) {
		ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(clipperOntology);
		// reduction.setNamingStrategy(this.namingStrategy);
		reduction.setCoreImps(tb.getImpContainer().getImps());
		reduction.setCoreEnfs(tb.getEnfContainer().getEnfs());
		reduction.saveEncodedDataLogProgram(this.dataLogName);
	}

	/**
	 * @return
	 */
	private TBoxReasoning saturateTbox() {
		TBoxReasoning tb = new TBoxReasoning(clipperOntology);
		// ///////////////////////////////////////////////
		// Evaluate reasoning time
		long reasoningBegin = System.currentTimeMillis();
		tb.reasoning();
		long reasoningEnd = System.currentTimeMillis();
		clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
		// end of evaluating reasoning time
		return tb;
	}

	/**
	 * @param onto_bs
	 * @param tb
	 * @return
	 */
	private QueryRewriter createQueryRewriter(ClipperHornSHIQOntology onto_bs, TBoxReasoning tb) {
		QueryRewriter qr;
		if (queryRewriter.equals("old")) {
			qr = new QueryRewriting(tb.getEnfContainer(), tb.getInverseRoleAxioms(), tb.getAllValuesFromAxioms());
		} else {
			qr = new CQGraphRewriter(onto_bs, tb.getEnfContainer());
		}
		return qr;
	}

	/**
	 * @param ontology
	 * @return
	 */
	private OWLOntology normalize(OWLOntology ontology) {
		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println(report);
		}

		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
		return normalizedOnt3;
	}

	/**
	 * @return Datalog program contains: rewritten queries, completion rules
	 * */
	public void getQueriesAndCompletionRulesDataLog() {

		if (this.ontologyName == null || this.dataLogName == null) {
			System.out.println("ontologyName and datalogName should be specified!");
		} else {
			File file = new File(ontologyName);
			if (cq == null) {
				InternalCQParser cqParser = new InternalCQParser();
				cqParser.setQueryString(queryString);
				cqParser.setPrefix(queryPrefix);
				cq = cqParser.getCq();
			}
			if (cq.getHead().getPredicate().getEncoding() != -1)
				this.headPredicate = "q" + cq.getHead().getPredicate().getEncoding();
			else
				this.headPredicate = "q";
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println("% Encoded Input query:" + cq);
			}

			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			try {
				long startNormalizatoinTime = System.currentTimeMillis();
				ontology = man.loadOntologyFromOntologyDocument(file);

				OWLOntology normalizedOnt3 = normalize(ontology);

				ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
				ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);

				long endNormalizationTime = System.currentTimeMillis();
				this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizatoinTime);

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					for (ClipperAxiom ax : onto_bs.getAxioms()) {
						System.out.println(ax);
					}
				}

				TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
				// ///////////////////////////////////////////////
				// Evaluate reasoning time
				long reasoningBegin = System.currentTimeMillis();
				tb.reasoning();
				long reasoningEnd = System.currentTimeMillis();
				clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
				// end of evaluating reasoning time
				// //////////////////////////////////////////////
				ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
				// reduction.setNamingStrategy(this.namingStrategy);
				reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
				reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				reduction.getCompletionRulesDatalogProgram(this.dataLogName);

				QueryRewriting qr = new QueryRewriting(tb.getIndexedEnfContainer(), tb.getInverseRoleAxioms(),
						tb.getAllValuesFromAxioms());

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					System.out.println("%Enforces relations:");
					for (EnforcedRelation e : qr.getEnfContainer().getEnfs()) {
						printClassNamesFromEncodedNamesSet(e.getType1());
						printRoleNamesFromEncodedNamesSet(e.getRoles());
						printClassNamesFromEncodedNamesSet(e.getType2());
						System.out.println();
					}

				}
				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					System.out.println("%rewritten queries:");
				}
				// /////////////////////////////////////////////////////////
				// Evaluate query rewriting time
				long rewritingBegin = System.currentTimeMillis();
				qr.rewrite(cq);
				long rewritingEnd = System.currentTimeMillis();
				clipperReport.setQueryRewritingTime(rewritingEnd - rewritingBegin);
				// End of evaluating query rewriting time
				// /////////////////////////////////////////////////////////

				long starCoutingRelatedRule = System.currentTimeMillis();
				this.rewrittenQueries = qr.getUcq();
				Set<CQ> ucq = qr.getUcq();
				QueriesRelatedRules relatedRules = new QueriesRelatedRules(onto_bs, ucq);
				relatedRules.setCoreImps(tb.getIndexedHornImpContainer().getImps());
				relatedRules.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				relatedRules.countUCQRelatedRules();
				long endCoutingRelatedRule = System.currentTimeMillis();
				this.clipperReport.setCoutingRealtedRulesTime(endCoutingRelatedRule - starCoutingRelatedRule);

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					System.out.println("==============================================");
					System.out.println("Numbers of rewritten queries is: " + ucq.size());
					System.out.println("==============================================");
					System.out.println("Rewritten queries: ");
					for (CQ query : ucq)
						System.out.println(query);
					System.out.println("==============================================");
					System.out.println("Datalog related to rewritten queries: ");
					for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
						System.out.println(rule);
					}
				}
				clipperReport.setNumberOfRewrittenQueries(ucq.size());
				clipperReport.setNumberOfRewrittenQueriesAndRules(relatedRules.getUcqRelatedDatalogRules().size()
						+ ucq.size());
				// System.out
				// .println("==============================================");
				// System.out
				// .println("Total number of Conjunctive queries and related rules: "
				// + numberOfRewrittenQueriesAndRules);
				FileWriter fstream = new FileWriter(this.dataLogName, true);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write("% rewritten queries:\n");
				for (CQ query : ucq)
					out.write(formatQuery(query) + "\n");
				out.close();
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * @return Datalog program contains: rewritten queries, completion rules
	 * */
	public void getCompletionRulesDataLog() {

		if (this.ontologyName == null || this.dataLogName == null) {
			System.out.println("ontologyName and datalogName should be specified!");
		} else {
			File file = new File(ontologyName);

			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			try {
				long startNormalizatoinTime = System.currentTimeMillis();
				ontology = man.loadOntologyFromOntologyDocument(file);

				OWLOntology normalizedOnt3 = normalize(ontology);

				ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
				ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);

				long endNormalizationTime = System.currentTimeMillis();
				this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizatoinTime);

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					for (ClipperAxiom ax : onto_bs.getAxioms()) {
						System.out.println(ax);
					}
				}

				TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
				// ///////////////////////////////////////////////
				// Evaluate reasoning time
				long reasoningBegin = System.currentTimeMillis();
				tb.reasoning();
				long reasoningEnd = System.currentTimeMillis();
				clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
				// end of evaluating reasoning time
				// //////////////////////////////////////////////
				ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
				// reduction.setNamingStrategy(this.namingStrategy);
				reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
				reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				reduction.getCompletionRulesDatalogProgram(this.dataLogName);
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * @return Datalog program contains: rewritten queries, related rules
	 * */
	public void getQueriesAndRelatedRulesDataLog() {

		if (this.ontologyName == null || this.dataLogName == null) {
			System.out.println("ontologyName and datalogName should be specified!");
		} else {
			File file = new File(ontologyName);
			if (cq == null) {
				InternalCQParser cqParser = new InternalCQParser();
				cqParser.setQueryString(queryString);
				cqParser.setPrefix(queryPrefix);
				cq = cqParser.getCq();
			}
			if (cq.getHead().getPredicate().getEncoding() != -1)
				this.headPredicate = "q" + cq.getHead().getPredicate().getEncoding();
			else
				this.headPredicate = "q";
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println("% Encoded Input query:" + cq);
			}

			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			try {
				long startNormalizatoinTime = System.currentTimeMillis();
				ontology = man.loadOntologyFromOntologyDocument(file);

				OWLOntology normalizedOnt3 = normalize(ontology);

				ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
				ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);

				long endNormalizationTime = System.currentTimeMillis();
				this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizatoinTime);

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					for (ClipperAxiom ax : onto_bs.getAxioms()) {
						System.out.println(ax);
					}
				}

				TBoxReasoning tb = new TBoxReasoning(onto_bs);
				// ///////////////////////////////////////////////
				// Evaluate reasoning time
				long reasoningBegin = System.currentTimeMillis();
				tb.reasoning();
				long reasoningEnd = System.currentTimeMillis();
				clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
				// end of evaluating reasoning time
				// //////////////////////////////////////////////
				// ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(
				// onto_bs);
				// // reduction.setNamingStrategy(this.namingStrategy);
				// reduction
				// .setCoreImps(tb.getIndexedHornImpContainer().getImps());
				// reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				// reduction.getCompletionRulesDatalogProgram(this.dataLogName);

				QueryRewriting qr = new QueryRewriting(tb.getEnfContainer(), tb.getInverseRoleAxioms(),
						tb.getAllValuesFromAxioms());
				// QueryRewriting qr = new QueryRewriting(tb.getEnfContainer(),
				// tb.getInverseRoleAxioms(), tb.getAllValuesFromAxioms());

				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("%Enforces relations:");
					for (EnforcedRelation e : qr.getEnfContainer().getEnfs()) {
						printClassNamesFromEncodedNamesSet(e.getType1());
						printRoleNamesFromEncodedNamesSet(e.getRoles());
						printClassNamesFromEncodedNamesSet(e.getType2());
						System.out.println();
					}

				}
				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("%rewritten queries:");
				}
				// /////////////////////////////////////////////////////////
				// Evaluate query rewriting time
				long rewritingBegin = System.currentTimeMillis();
				qr.rewrite(cq);
				long rewritingEnd = System.currentTimeMillis();
				clipperReport.setQueryRewritingTime(rewritingEnd - rewritingBegin);
				// End of evaluating query rewriting time
				// /////////////////////////////////////////////////////////
				this.rewrittenQueries = qr.getUcq();
				long starCoutingRelatedRule = System.currentTimeMillis();
				Set<CQ> ucq = qr.getUcq();
				QueriesRelatedRules relatedRules = new QueriesRelatedRules(onto_bs, ucq);
				relatedRules.setCoreImps(tb.getImpContainer().getImps());
				relatedRules.setCoreEnfs(tb.getEnfContainer().getEnfs());
				relatedRules.countUCQRelatedRules();
				long endCoutingRelatedRule = System.currentTimeMillis();
				this.clipperReport.setCoutingRealtedRulesTime(endCoutingRelatedRule - starCoutingRelatedRule);

				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("==============================================");
					System.out.println("Numbers of rewritten queries is: " + ucq.size());
					System.out.println("==============================================");
					System.out.println("Rewritten queries: ");
					for (CQ query : ucq)
						System.out.println(query);
					System.out.println("==============================================");
					System.out.println("Datalog related to rewritten queries: ");
					for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
						System.out.println(rule);
					}
				}
				clipperReport.setNumberOfRewrittenQueries(ucq.size());
				clipperReport.setNumberOfRewrittenQueriesAndRules(relatedRules.getUcqRelatedDatalogRules().size()
						+ ucq.size());
				// System.out
				// .println("==============================================");
				// System.out
				// .println("Total number of Conjunctive queries and related rules: "
				// + numberOfRewrittenQueriesAndRules);
				try {
					PrintStream program = new PrintStream(new FileOutputStream(this.dataLogName));
					// System.out.println("======================================== ");
					// System.out.println("Facts from Role assertions: ");
					for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
						program.println(rule);
					}
					program.println("% rewritten queries ");
					for (CQ query : ucq)
						program.println(formatQuery(query));

					program.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}

		}
	}

	/**
	 * @return Datalog program contains only Abox assersions
	 * */
	public void getAboxDataLog() {

		if (this.ontologyName == null || this.dataLogName == null) {
			System.out.println("ontologyName and datalogName should be specified!");
		} else {
			File file = new File(ontologyName);
			if (cq == null) {
				InternalCQParser cqParser = new InternalCQParser();
				cqParser.setQueryString(queryString);
				cqParser.setPrefix(queryPrefix);
				cq = cqParser.getCq();
			}
			if (cq.getHead().getPredicate().getEncoding() != -1)
				this.headPredicate = "q" + cq.getHead().getPredicate().getEncoding();
			else
				this.headPredicate = "q";
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println("% Encoded Input query:" + cq);
			}

			OWLOntologyManager man = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			try {
				long startNormalizatoinTime = System.currentTimeMillis();
				ontology = man.loadOntologyFromOntologyDocument(file);

				OWLOntology normalizedOnt3 = normalize(ontology);

				ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
				ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);

				long endNormalizationTime = System.currentTimeMillis();
				this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizatoinTime);

				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					for (ClipperAxiom ax : onto_bs.getAxioms()) {
						System.out.println(ax);
					}
				}

				TBoxReasoningOpt tb = new TBoxReasoningOpt(onto_bs);
				// ///////////////////////////////////////////////
				// Evaluate reasoning time
				long reasoningBegin = System.currentTimeMillis();
				tb.reasoning();
				long reasoningEnd = System.currentTimeMillis();
				clipperReport.setReasoningTime(reasoningEnd - reasoningBegin);
				// end of evaluating reasoning time
				// //////////////////////////////////////////////
				ReductionToDatalogOpt reduction = new ReductionToDatalogOpt(onto_bs);
				// reduction.setNamingStrategy(this.namingStrategy);
				reduction.setCoreImps(tb.getIndexedHornImpContainer().getImps());
				reduction.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				reduction.getABoxAssertionsDatalogProgram(this.dataLogName);

				QueryRewriting qr = new QueryRewriting(tb.getIndexedEnfContainer(), tb.getInverseRoleAxioms(),
						tb.getAllValuesFromAxioms());

				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("%Enforces relations:");
					for (EnforcedRelation e : qr.getEnfContainer().getEnfs()) {
						printClassNamesFromEncodedNamesSet(e.getType1());
						printRoleNamesFromEncodedNamesSet(e.getRoles());
						printClassNamesFromEncodedNamesSet(e.getType2());
						System.out.println();
					}

				}
				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("%rewritten queries:");
				}
				// /////////////////////////////////////////////////////////
				// Evaluate query rewriting time
				long rewritingBegin = System.currentTimeMillis();
				qr.rewrite(cq);
				long rewritingEnd = System.currentTimeMillis();
				clipperReport.setQueryRewritingTime(rewritingEnd - rewritingBegin);
				// End of evaluating query rewriting time
				// /////////////////////////////////////////////////////////

				long starCoutingRelatedRule = System.currentTimeMillis();
				Set<CQ> ucq = qr.getUcq();
				QueriesRelatedRules relatedRules = new QueriesRelatedRules(onto_bs, ucq);
				relatedRules.setCoreImps(tb.getIndexedHornImpContainer().getImps());
				relatedRules.setCoreEnfs(tb.getIndexedEnfContainer().getEnfs());
				relatedRules.countUCQRelatedRules();
				long endCoutingRelatedRule = System.currentTimeMillis();
				this.clipperReport.setCoutingRealtedRulesTime(endCoutingRelatedRule - starCoutingRelatedRule);

				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("==============================================");
					System.out.println("Numbers of rewritten queries is: " + ucq.size());
					System.out.println("==============================================");
					System.out.println("Rewritten queries: ");
					for (CQ query : ucq)
						System.out.println(query);
					System.out.println("==============================================");
					System.out.println("Datalog related to rewritten queries: ");
					for (Rule rule : relatedRules.getUcqRelatedDatalogRules()) {
						System.out.println(rule);
					}
				}
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}
	}

	// =================================
	@Override
	public List<List<String>> query() {
		generateDataLog();
		
		this.answers = Lists.newArrayList();
		
		DLVInputProgram inputProgram = new DLVInputProgramImpl();
		String outPutNotification = "";

		/* I can add some file to the DLVInputProgram */

		inputProgram.addFile(this.dataLogName);

		ensureDlvPath();

		DLVInvocation invocation = DLVWrapper.getInstance().createInvocation(dlvPath);
		/* I can specify a part of DLV program using simple strings */

		// Creates an instance of DLVInvocation

		// Creates an instance of DLVInputProgram
		if (ClipperManager.getInstance().getVerboseLevel() > 0)
			System.out.println("===========Answers for the query ========");

		try {
			invocation.setInputProgram(inputProgram);
			invocation.setNumberOfModels(1);
			List<String> filters = new ArrayList<String>();
			filters.add(this.headPredicate);
			invocation.setFilter(filters, true);
			ModelBufferedHandler modelBufferedHandler = new ModelBufferedHandler(invocation);

			/* In this moment I can start the DLV execution */
			FactHandler factHandler = new FactHandler() {
				@Override
				public void handleResult(DLVInvocation obsd, FactResult res) {
					String answerString = res.toString();
					if (ClipperManager.getInstance().getVerboseLevel() > 0)
						System.out.println(answerString);
					answers.add(answerString);
				}
			};

			invocation.subscribe(factHandler);
			// Roughly datalog program evalutaion
			long dlvBegin = System.currentTimeMillis();
			invocation.run();
			long dlvEng = System.currentTimeMillis();
			clipperReport.setDatalogRunTime(dlvEng - dlvBegin);
			// Roughly datalog program evalutaion
			if (!modelBufferedHandler.hasMoreModels()) {
				outPutNotification = "No model";
				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("No model");
				}
			}
			invocation.waitUntilExecutionFinishes();
			List<DLVError> k = invocation.getErrors();
			if (k.size() > 0) {
				if (ClipperManager.getInstance().getVerboseLevel() >= 0) {
					System.out.println(k);
				}
				outPutNotification = k.toString();
			}

		} catch (DLVInvocationException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		long starOutputAnswer = System.currentTimeMillis();
		AnswerParser answerParser = new AnswerParser();
		if (ClipperManager.getInstance().getNamingStrategy() == NamingStrategy.IntEncoding) {
			answerParser.setAnswers(this.answers);
			answerParser.parse();
			this.decodedAnswers = answerParser.getDecodedAnswers();
		} else {
			this.decodedAnswers.add(this.answers);
		}
		if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
			System.out.println("=============Decoded answers ==============");
		}

		BufferedWriter bufferedWriter = null;

		try {

			// Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(dataLogName + "-answer.txt"));
			bufferedWriter.write(outPutNotification);
			// Start writing to the output stream
			for (List<String> ans : decodedAnswers) {
				bufferedWriter.write(ans.toString());
				bufferedWriter.newLine();
			}

			long endOutputAnswer = System.currentTimeMillis();
			this.clipperReport.setOutputAnswerTime(endOutputAnswer - starOutputAnswer);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return decodedAnswers;

	}

	/**
	 * if dlvPath is not set, then we use ~/bin/dlv
	 */
	private void ensureDlvPath() {
		if (dlvPath == null || dlvPath.length() == 0) {
			dlvPath = System.getenv("HOME") + "/bin/dlv";
		}
	}

	private String getBinaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			OWLObjectPropertyExpression owlExpression = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder().getSymbolByValue(value);
			if (owlExpression.isAnonymous())
				return "INVERSEOF(" + normalizeIRI(owlExpression.getNamedProperty().getIRI()) + ")";
			else {
				IRI iri = ClipperManager.getInstance().getOwlObjectPropertyExpressionEncoder().getSymbolByValue(value)
						.asOWLObjectProperty().getIRI();
				return normalizeIRI(iri);
			}

		case IntEncoding:
			return "r" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	private String getUnaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(value).getIRI();

			return normalizeIRI(iri);
		case IntEncoding:
			return "c" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	private void printClassNamesFromEncodedNamesSet(TIntHashSet encodedNames) {
		TIntIterator iterator = encodedNames.iterator();
		System.out.print("{ ");
		while (iterator.hasNext()) {
			int index = iterator.next();
			System.out.print(getUnaryPredicate(index) + ",");
		}
		System.out.print(" } ");
	}

	private void printRoleNamesFromEncodedNamesSet(TIntHashSet encodedNames) {
		TIntIterator iterator = encodedNames.iterator();
		System.out.print("{ ");
		while (iterator.hasNext()) {
			int index = iterator.next();
			System.out.print(getBinaryPredicate(index) + ",");
		}
		System.out.print(" } ");
	}

	private String normalizeIRI(IRI iri) {
		String fragment = iri.getFragment();
		if (fragment != null) {
			return fragment.replaceAll("[_-]", "").toLowerCase();
		} else {
			final String iriString = iri.toString();
			int i = iriString.lastIndexOf('/') + 1;
			return iriString.substring(i).replace("_-", "").toLowerCase();

		}

	}

	/**
	 * Print return a conjunctive query in lowercase, not in form of encoded
	 * number.
	 * */
	private String formatQuery(CQ cq) {
		StringBuilder sb = new StringBuilder();
		if (ClipperManager.getInstance().getNamingStrategy().equals(NamingStrategy.IntEncoding)) {
			sb.append(cq.getHead());
			sb.append(" :- ");
			boolean first = true;
			for (Atom b : cq.getBody()) {
				if (b.getPredicate().getEncoding() != ClipperManager.getInstance().getThing()) {
					if (!first) {
						sb.append(", ");
					}
					first = false;
					sb.append(intFormOfAtom(b));
				}
			}
			sb.append(".");
			return sb.toString();

		} else {
			sb.append(cq.getHead());
			sb.append(" :- ");
			boolean first = true;
			for (Atom b : cq.getBody()) {
				if (b.getPredicate().getEncoding() != ClipperManager.getInstance().getThing()) {
					if (!first) {
						sb.append(", ");
					}
					first = false;
					sb.append(lowerCaseFormOfAtom(b));
				}
			}
			sb.append(".");
			return sb.toString();

		}

	}

	// ============================================
		private String intFormOfAtom(Atom atom) {
			StringBuilder sb = new StringBuilder();
			if (atom.getPredicate().getArity() == 1) {
				String predicateStr = getUnaryPredicate(atom.getPredicate().getEncoding());
				
				sb.append(predicateStr);
			} else if (atom.getPredicate().getArity() == 2) {
				String predicateStr = getBinaryPredicate(atom.getPredicate().getEncoding());
				
				sb.append(predicateStr);
			} else
				sb.append(atom.getPredicate());
			sb.append("(");
			boolean first = true;
			for (Term t : atom.getTerms()) {
				if (!first) {
					sb.append(",");
				}
				first = false;
				if (!t.isVariable()) {
					sb.append(getConstant(t.getIndex()));
				} else
					sb.append(t);
			}
			sb.append(")");
			return sb.toString();
		}
	
	// ============================================
	private String lowerCaseFormOfAtom(Atom atom) {
		StringBuilder sb = new StringBuilder();
		if (atom.getPredicate().getArity() == 1) {
			String predicateStr = getUnaryPredicate(atom.getPredicate().getEncoding());
			sb.append(predicateStr);
		} else if (atom.getPredicate().getArity() == 2) {
			String predicateStr = getBinaryPredicate(atom.getPredicate().getEncoding());
			sb.append(predicateStr);
		} else
			sb.append(atom.getPredicate());
		sb.append("(");
		boolean first = true;
		for (Term t : atom.getTerms()) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			if (!t.isVariable()) {
				sb.append(getConstant(t.getIndex()));
			} else
				sb.append(t);
		}
		sb.append(")");
		return sb.toString();
	}

	// convert term to lower case format
	private String getConstant(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlIndividualEncoder().getSymbolByValue(value)
					.asOWLNamedIndividual().getIRI();
			return "\"" + normalizeIRI(iri) + "\"";
		case IntEncoding:
			return "d" + value;
		}
		throw new IllegalStateException("Not possible");
	}

//	public QAHornSHIQ(String dlv) {
//		this.ontologies = Lists.newArrayList();
//	}

	@Override
	public void addOntology(OWLOntology ontology) {
		this.ontologies.add(ontology);
	}

	@Override
	public void setOntologies(Collection<OWLOntology> ontologies) {
		this.ontologies = ontologies;
	}

	@Override
	public void setQuery(CQ cq) {
		this.cq = cq;
	}

	// @Override
	public void preprocessOntologies() {
		long startNormalizatoinTime = System.currentTimeMillis();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology combinedOntology = null;
		try {
			combinedOntology = manager.createOntology();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		for (OWLOntology ontology : ontologies) {
			ontology = normalize(ontology);
			manager.addAxioms(combinedOntology, ontology.getAxioms());
		}

		ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
		ClipperHornSHIQOntology onto_bs = converter.convert(combinedOntology);

		this.clipperOntology = onto_bs;

		long endNormalizationTime = System.currentTimeMillis();
		this.clipperReport.setNormalizationTime(endNormalizationTime - startNormalizatoinTime);

	}
}
