package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.RecognitionException;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperReport;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;

import com.beust.jcommander.JCommander;
import com.google.common.io.Files;

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

		ClipperManager.getInstance().setVerboseLevel(co.getVerbose());

		String cmd = jc.getParsedCommand();

		if (cmd == null) {
			help(jc);
		} else if (cmd.equals("query")) {
			query(co, commandQuery);
		} else if (cmd.equals("rewrite")) {
			rewrite(co, commandRewrite);
		} else if (cmd.equals("help")) {
			help(jc);
		}
	}

	/**
	 * @param jc
	 */
	private void help(JCommander jc) {
		System.out.println("Clipper reasoner [v0.2]");
		System.out.println();
		jc.usage();
	}

	private void rewrite(CommandLineArgs co, CommandRewrite cmd) {
		System.setProperty("entityExpansionLimit", "512000");

		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// note that naming strategy shoud be set after create new QAHornSHIQ
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);

		String ontologyFileName = cmd.getFiles().get(0);
		qaHornSHIQ.setOntologyName(ontologyFileName);

		qaHornSHIQ.setDataLogName(ontologyFileName + ".dl");

		if (cmd.isRewritingOntologyOnly()) {
			qaHornSHIQ.getCompletionRulesDataLog();
		} else if (cmd.isRewritingABoxOnly()) {
			qaHornSHIQ.getAboxDataLog();
		} else if (cmd.isRewritingOntologyAndQuery()) {
			qaHornSHIQ.getDataLog();
		} else if (cmd.isRewritingTBoxOnly()) {
			// TODO
		} else if (cmd.isRewritingTBoxAndQuery()) {
			// TODO
		}

		long totalTime = qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime();
		System.out.println(qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueries() + " "
				+ qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueriesAndRules() + " " + totalTime);

	}

	private void query(CommandLineArgs cla, CommandQuery cmd) {
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
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

//		File ontologyFile = new File(ontologyFileName);
//		String ontologyDir = ontologyFile.getParent();
//		String name = ontologyFile.getName();
//		String string = Files.getFileExtension(ontologyFileName);
//		
		String sparqlName = new File(sparqlFileName).getName();
		
		qaHornSHIQ.setOntologyName(ontologyFileName);
		qaHornSHIQ.setDataLogName(ontologyFileName + "-" + sparqlName + ".dl");
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setQueryRewriter(cla.getRewriter());

		qaHornSHIQ.setDlvPath(cmd.getDlvPath());

		long startTime = System.currentTimeMillis();
		List<List<String>> answers = qaHornSHIQ.runDatalogEngine();
		long endTime = System.currentTimeMillis();

		QueryResultPrinter printer = createQueryResultPrinter(cmd.getOutputFormat());

		printer.print(cq.getHead(), answers);

		if (ClipperManager.getInstance().getVerboseLevel() > 0) {
			statistics(qaHornSHIQ.getClipperReport(), startTime, endTime);
		}

	}

	/**
	 * @param cmd
	 * @return
	 */
	private QueryResultPrinter createQueryResultPrinter(String format) {
		QueryResultPrinter printer = null;
		// String outputFormat = cmd.getOutputFormat();
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
	 * @param qaHornSHIQ
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
		System.out.println("Total time for query rewriting (reasoning + rewriting time):  " + totalTime
				+ "  milliseconds");
		System.out.println("Total rules/rewritten queries: " + clipperReport.getNumberOfRewrittenQueriesAndRules());
		System.out.println("Time of running datalog program:                              "
				+ clipperReport.getDatalogRunTime() + "  milliseconds");
		System.out.println("Time for output answer  :                                     "
				+ clipperReport.getOutputAnswerTime() + "  milliseconds");
		System.out.println("Time for counting queries realted rules (just for benchmark): "
				+ clipperReport.getCoutingRealtedRulesTime() + "  milliseconds");
		long runningTime = endTime - startTime - clipperReport.getCoutingRealtedRulesTime();
		System.out.println("Total running time of the whole system:                       " + runningTime
				+ "  milliseconds");
	}

}
