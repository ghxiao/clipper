package org.semanticweb.clipper.hornshiq.cli;

import java.util.Set;

import lombok.Getter;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

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
	@Parameter(names = { "-output-datalog", "-d" }, description = "output datalog file (if not specified, the output will be stdout)")
	private String datalog = "output.dlv";

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	void exec() {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy should be set after create new QAHornSHIQ
		// ClipperManager.getInstance().setNamingStrategy(
		// NamingStrategy.LOWER_CASE_FRAGMENT);

		qaHornSHIQ.setNamingStrategy(this.namingStrategy);

		//ClipperManager.getInstance().setNamingStrategy(this.namingStrategy);

		Set<OWLOntology> ontologies = loadOntologies();

		qaHornSHIQ.setOntologies(ontologies);

		if (rewritingOntologyAndQuery || rewritingTBoxAndQuery || this.cqFile != null || this.sparqlFile != null || this.sparqlString != null) {

			CQ cq = parseCQ(ontologies);

			qaHornSHIQ.setQuery(cq);
		}
		// TODO: only consider related rules

		qaHornSHIQ.setDatalogFileName(datalog);

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

		if (ClipperManager.getInstance().getVerboseLevel() > 1) {
			System.err.println("rewritten queries : "
					+ qaHornSHIQ.getClipperReport()
							.getNumberOfRewrittenQueries());
			System.err.println("rewritten queries + rules : "
					+ qaHornSHIQ.getClipperReport()
							.getNumberOfRewrittenQueriesAndRules());
			System.err.println("total time : " + totalTime + "ms");
		}
	}

}