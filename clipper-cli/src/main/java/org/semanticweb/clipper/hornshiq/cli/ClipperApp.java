package org.semanticweb.clipper.hornshiq.cli;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.semanticweb.clipper.hornshiq.queryanswering.KaosManager;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;

import com.beust.jcommander.JCommander;

public class ClipperApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ClipperApp(args);
	}

	public ClipperApp(String[] args) {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery();
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite();
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp();
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");

		jc.parse(args);

		KaosManager.getInstance().setVerboseLevel(co.getVerbose());

		String cmd = jc.getParsedCommand();

		if (cmd == null) {
			help(jc);
		} else if (cmd.equals("query")) {
			query(commandQuery);
		} else if (cmd.equals("rewrite")) {
			rewrite(commandRewrite);
		} else if (cmd.equals("help")) {
			help(jc);
		}
	}

	/**
	 * @param jc
	 */
	private void help(JCommander jc) {
		System.out.println("Clipper reasoner [0.2]");
		System.out.println();
		jc.usage();
	}

	private void rewrite(CommandRewrite cmd) {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy shoud be set after create new QAHornSHIQ
		KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);

		if (cmd.isRewritingOntologyOnly()) {

			String ontologyFileName = cmd.getFiles().get(0);
			qaHornSHIQ.setOntologyName(ontologyFileName);

			qaHornSHIQ.setDataLogName(ontologyFileName + ".dl");

			qaHornSHIQ.getCompletionRulesDataLog();

			long totalTime = qaHornSHIQ.getReasoningTime() + qaHornSHIQ.getQueryRewritingTime();
			System.out.println(qaHornSHIQ.getNumberOfRewrittenQueries() + " "
					+ qaHornSHIQ.getNumberOfRewrittenQueriesAndRules() + " " + totalTime);
		}
	}

	private void query(CommandQuery cmd) {
		KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		System.setProperty("entityExpansionLimit", "512000");
		String ontologyFileName = cmd.getFiles().get(0);
		String sparqlFileName = cmd.getFiles().get(1);
		SparqlParser sparqlParser = null;
		try {
			sparqlParser = new SparqlParser(sparqlFileName);
		} catch (IOException e) {

			e.printStackTrace();
		}

		CQ cq = null;
		try {
			cq = sparqlParser.query();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setOntologyName(ontologyFileName);
		qaHornSHIQ.setDataLogName(ontologyFileName + "-" + ontologyFileName + ".dl");
		qaHornSHIQ.setCq(cq);

		qaHornSHIQ.setDlvPath(cmd.getDlvPath());

		long startTime = System.currentTimeMillis();
		List<List<String>> answers = qaHornSHIQ.runDatalogEngine();
		long endTime = System.currentTimeMillis();

		QueryResultPrinter printer = null;
		if (cmd.getOutputFormat().equals("csv")) {
			printer = new CsvQueryResultPrinter();
		} else if (cmd.getOutputFormat().equals("table")) {
			printer = new TableQueryResultPrinter();
		} else if (cmd.getOutputFormat().equals("terms")) {
			// TODO
		}

		printer.print(cq.getHead(), answers);

		if (KaosManager.getInstance().getVerboseLevel() > 0) {
			statistics(qaHornSHIQ, startTime, endTime);
		}

	}

	/**
	 * @param qaHornSHIQ
	 * @param startTime
	 * @param endTime
	 */
	private void statistics(QAHornSHIQ qaHornSHIQ, long startTime, long endTime) {
		System.out.println("Ontology parsing and normalization time:                      "
				+ qaHornSHIQ.getNormalizationTime() + "  milliseconds");
		System.out.println("Reasoning time:                                               "
				+ qaHornSHIQ.getReasoningTime() + "  milliseconds");
		System.out.println("Query rewriting time:                                         "
				+ qaHornSHIQ.getQueryRewritingTime() + "  milliseconds");
		long totalTime = qaHornSHIQ.getReasoningTime() + qaHornSHIQ.getQueryRewritingTime();
		System.out.println("Total time for query rewriting (reasoning + rewriting time):  " + totalTime
				+ "  milliseconds");
		System.out.println("Total rules/rewritten queries: " + qaHornSHIQ.getNumberOfRewrittenQueriesAndRules());
		System.out.println("Time of running datalog program:                              "
				+ qaHornSHIQ.getDatalogRunTime() + "  milliseconds");
		System.out.println("Time for output answer  :                                     "
				+ qaHornSHIQ.getOutputAnswerTime() + "  milliseconds");
		System.out.println("Time for counting queries realted rules (just for benchmark): "
				+ qaHornSHIQ.getCoutingRealtedRulesTime() + "  milliseconds");
		long runningTime = endTime - startTime - qaHornSHIQ.getCoutingRealtedRulesTime();
		System.out.println("Total running time of the whole system:                       " + runningTime
				+ "  milliseconds");
	}

}
