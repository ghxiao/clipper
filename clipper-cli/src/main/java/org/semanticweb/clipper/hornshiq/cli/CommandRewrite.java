package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import lombok.Getter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Getter
@Parameters(commandNames = { "rewrite" }, separators = "=", commandDescription = "rewrite the query w.r.t. the ontology, and generate a datalog program")
class CommandRewrite extends CommandBase {

	public CommandRewrite(JCommander jc) {
		super(jc);
	}

	@Parameter(description = "<ontology1.owl> ... <ontologyk.owl>")
	private List<String> ontologyFiles;

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
	@Parameter(names = { "--output-directory", "-d" }, description = "output directory")
	private String outputDirectory = ".";

	@Parameter(names = "-cq", description = "<query.cq> query file in CQ format")
	private String cqFile;

	@Parameter(names = "-sparql", description = "<query.sparql> query file in SPARQL format")
	private String sparqlFile;

	public boolean validate() {
		return true;
	}

	@Override
	String getErrorMessage() {
		return null;
	}

	@Override
	void exec() {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy should be set after create new QAHornSHIQ
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);

		CommandRewrite cmd = this;
		for (String ontologyFile : cmd.getOntologyFiles()) {
			try {
				OWLOntology ontology = OWLManager.createOWLOntologyManager()
						.loadOntologyFromOntologyDocument(
								new File(ontologyFile));
				qaHornSHIQ.addOntology(ontology);
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
			}
		}

		CQ cq = null;

		String sparqlFileName = cmd.getSparqlFile();

		if (sparqlFileName != null) {

			try {
				SparqlParser sparqlParser = new SparqlParser(sparqlFileName);
				cq = sparqlParser.query();
				qaHornSHIQ.setCq(cq);
			} catch (RecognitionException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// TODO: only consider related rules

		qaHornSHIQ.setDatalogFileName("tmp.dlv");

		if (cmd.isRewritingOntologyOnly()) {
			// -o
			qaHornSHIQ.generateOntologyDatalog();

		} else if (cmd.isRewritingABoxOnly()) {
			// -a
			qaHornSHIQ.generateABoxDatalog();

		} else if (cmd.isRewritingOntologyAndQuery()) {
			// -oq
			qaHornSHIQ.generateDatalog();

		} else if (cmd.isRewritingTBoxOnly()) {
			// -t
			qaHornSHIQ.generateTBoxRulesDataLog();

		} else if (cmd.isRewritingTBoxAndQuery()) {
			// -tq
			qaHornSHIQ.generateQueriesAndCompletionRulesDataLog();
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

}