package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperReport;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.List;
import java.util.Set;

@Parameters(commandNames = { "query" }, separators = "=", commandDescription = "answering conjunctive query")
class CommandQuery extends ReasoningCommandBase {

	public CommandQuery(JCommander jc) {
		super(jc);
	}

	@Parameter(names = "-dlv", description = "the path to dlv")
	private String dlvPath;

	@Parameter(names = { "-f", "-output-format" }, description = "output format, possible values: { table | csv | atoms | html }")
	private String outputFormat = "table";

	// TODO: will be supported in the future
	@Parameter(names = "-clingo", description = "the path to clingo", hidden = true)
	private String clingoPath;

	@Parameter(names = { "-output-datalog", "-d" }, description = "output datalog file (if not specified, the output will be stdout)")
	private String datalog = "tmp.dlv";

	@Override
	public boolean validate() {
		if (ontologyFiles.size() == 0) {
			errorMessage = "At least one ontology file should be specified";
			return false;
		}

		if ((sparqlFile != null && cqFile == null)
				|| (sparqlFile == null && cqFile != null)) {
			errorMessage = "One and only one query file (either in CQ or in SPARQL format) must be provided";
		}

		return true;
	}

	@Override
	public void exec() {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy should be set after create new QAHornSHIQ
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LOWER_CASE_FRAGMENT);

		Set<OWLOntology> ontologies = loadOntologies();

		qaHornSHIQ.setOntologies(ontologies);

		CQ cq = parseCQ(ontologies);

		qaHornSHIQ.setDatalogFileName(this.datalog);
		qaHornSHIQ.setQueryRewriter(this.getRewriter());
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath(this.getDlvPath());

		long startTime = System.currentTimeMillis();
		List<List<String>> answers = qaHornSHIQ.execQuery();
		long endTime = System.currentTimeMillis();

		QueryResultPrinter printer = createQueryResultPrinter(this.getOutputFormat());

		printer.print(cq.getHead(), answers);

		if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
			statistics(qaHornSHIQ.getClipperReport(), startTime, endTime);
		}

	}

	/**
	 * @param
	 * @return
	 */
	private QueryResultPrinter createQueryResultPrinter(String format) {
		QueryResultPrinter printer = null;
		// String outputFormat = this.getOutputFormat();
		if (format.equals("csv")) {
			printer = new CsvQueryResultPrinter();
		} else if (format.equals("table")) {
			printer = new TableQueryResultPrinter();
		} else if (format.equals("html")) {
			printer = new HtmlQueryResultPrinter();
		} else if (format.equals("terms")) {
			// TODO
		}
		return printer;
	}

	/**
	 * @param clipperReport
	 * @param startTime
	 * @param endTime
	 */
	private void statistics(ClipperReport clipperReport, long startTime, long endTime) {
		System.out.println("Ontology parsing and normalization time:                      "
				+ clipperReport.getNormalizationTime() + "  milliseconds");
		System.out.println("Reasoning time:                                               "
				+ clipperReport.getReasoningTime() + "  milliseconds");
		System.out.println("Query rewriting time:                                         "
				+ clipperReport.getQueryRewritingTime() + "  milliseconds");
		long totalTime = clipperReport.getReasoningTime() + clipperReport.getQueryRewritingTime();
		System.out.println("Total time for query rewriting (reasoning + rewriting time):  "
                + totalTime + "  milliseconds");
		System.out.println("Total rules/rewritten queries:                                "
				+ clipperReport.getNumberOfRewrittenQueriesAndRules());
		System.out.println("Time of running datalog program:                              "
				+ clipperReport.getDatalogRunTime() + "  milliseconds");
		System.out.println("Time for output answer  :                                     "
				+ clipperReport.getOutputAnswerTime() + "  milliseconds");
		System.out.println("Time for counting queries realted rules (just for benchmark): "
				+ clipperReport.getCoutingRealtedRulesTime() + "  milliseconds");
		long runningTime = endTime - startTime; // - clipperReport.getCoutingRealtedRulesTime();
		System.out.println("Total running time of the whole system:                       "
                + runningTime + "  milliseconds");
	}

    public String getDlvPath() {
        return this.dlvPath;
    }

    public String getOutputFormat() {
        return this.outputFormat;
    }

    public String getClingoPath() {
        return this.clingoPath;
    }

    public String getDatalog() {
        return this.datalog;
    }
}