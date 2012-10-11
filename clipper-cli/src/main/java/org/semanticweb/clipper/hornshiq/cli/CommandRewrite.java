package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import org.antlr.runtime.RecognitionException;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.collect.ImmutableSet;

@Getter
@Parameters(commandNames = { "rewrite" }, separators = "=", commandDescription = "rewrite the query w.r.t. the ontology, and generate a datalog program")
class CommandRewrite extends ReasoningCommandBase {

	public CommandRewrite(JCommander jc) {
		super(jc);
	}

	@Parameter(names = { "-tbox-only", "-t" }, description = "only rewrite TBox")
	private boolean rewritingTBoxOnly;

	@Parameter(names = { "-abox-only", "-a" }, description = "only rewrite ABox")
	private boolean rewritingABoxOnly;

	@Parameter(names = { "-ontology-only", "-o" }, description = "only rewrite ontology (= TBox + ABox)")
	private boolean rewritingOntologyOnly;

	@Parameter(names = { "-tbox-and-query", "-tq" }, description = "only rewrite TBox and query")
	private boolean rewritingTBoxAndQuery;

	@Parameter(names = { "-ontology-and-query", "-oq" }, description = "rewrite ontology (= TBox + ABox) and query")
	private boolean rewritingOntologyAndQuery;

	// @Parameter(names = { "--remove-redundancy", "-r" }, description =
	// "remove redundancy rules w.r.t the query")
	// private boolean removingRedundancyRules;
	@Parameter(names = { "-datalog", "-d" }, description = "output datalog file (if not specified, the output will be stdout)")
	private String datalog = "";

	public boolean validate() {
		return true;
	}

	@Override
	void exec() {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy should be set after create new QAHornSHIQ
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);

		Set<OWLOntology> ontologies = new HashSet<OWLOntology>();

		for (String ontologyFile : this.getOntologyFiles()) {
			try {
				OWLOntology ontology = OWLManager.createOWLOntologyManager()
						.loadOntologyFromOntologyDocument(
								new File(ontologyFile));

				ontologies.add(ontology);
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
		}

		qaHornSHIQ.setOntologies(ontologies);

		// qaHornSHIQ.addOntology(ontology);

		CQ cq = parseCQ(ontologies);

		qaHornSHIQ.setCq(cq);
		// TODO: only consider related rules

		qaHornSHIQ.setDatalogFileName("tmp.dlv");

		if (this.isRewritingOntologyOnly()) { // -o
			qaHornSHIQ.generateOntologyDatalog();
		} else if (this.isRewritingABoxOnly()) { // -a
			qaHornSHIQ.generateABoxDatalog();
		} else if (this.isRewritingTBoxOnly()) { // -t
			qaHornSHIQ.generateTBoxRulesDatalog();
		} else if (this.isRewritingTBoxAndQuery()) { // -tq
			qaHornSHIQ.generateQueriesAndCompletionRulesDatalog();
		} else if (this.isRewritingOntologyAndQuery()) { // -oq
			qaHornSHIQ.generateDatalog();
		} else { // rewrite everything by default
			qaHornSHIQ.generateDatalog();
		}

		long totalTime = qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime();
		System.out.println(qaHornSHIQ.getClipperReport()
				.getNumberOfRewrittenQueries()
				+ " "
				+ qaHornSHIQ.getClipperReport()
						.getNumberOfRewrittenQueriesAndRules()
				+ " "
				+ totalTime);

	}

	private CQ parseCQ(Set<OWLOntology> ontologies) {
		CQ cq = null;

		if (sparqlFile != null) {

			try {
				SparqlParser sparqlParser = new SparqlParser(sparqlFile);
				cq = sparqlParser.query();
			} catch (RecognitionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (cqFile != null) {
			CQParser parser;
			try {
				parser = new CQParser(new File(cqFile), ontologies);
				cq = parser.parse();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cq;
	}

}