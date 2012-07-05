package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.clipper.util.GetLUBMAnswers;

import java.util.List;

public class LUBMQueryTest {
	@Test
	public void query1() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query1.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>" + "SELECT ?x "
				+ "		WHERE {" + "		  ?x a ub:GraduateStudent;"
				+ "		       ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> ." + "		}";

		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getAboxDataLog();
		// qaHornSHIQ.getDataLog();
		qaHornSHIQ.runDatalogEngine();

		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  millisecond");
		System.out
				.println("reasoning time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query1.txt");

		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void testQuery2() throws RecognitionException {
		// query2("old");
		query2("new");
	}

	/**
	 * @throws RecognitionException
	 */
	private void query2(String rewriter) throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query2.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");
		String sparql = " PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>  \n"
				+ "SELECT ?x ?y ?z \n" + "WHERE {" + "	 ?x a ub:GraduateStudent . \n" + "	 ?y a ub:University . \n"
				+ "	 ?z a ub:Department . \n" + "	 ?x ub:memberOf ?z . \n " + "	 ?z ub:subOrganizationOf ?y . \n"
				+ "	?x ub:undergraduateDegreeFrom ?y . \n}	";

		//
		// String sparql =
		// "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n"
		// + //
		// "SELECT ?x ?y ?z  \n" + //
		// "WHERE {\n" + //
		// "  ?x a ub:GraduateStudent ;\n" + //
		// "  ?y a ub:University . \n" +
		// "	?z a ub:Department ."
		// "       ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> .\n"
		// + //
		// "}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setQueryRewriter(rewriter);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  millisecond");
		System.out
				.println("reasoning time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query2.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void testQuery3() throws RecognitionException {
		// query3("old");
		query3("new");
	}

	public void query3(String rewriter) throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query3.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" //
				+ "SELECT ?X 	" //
				+ "WHERE { \n"//
				+ "?X a ub:Publication . \n " //
				+ "?X ub:publicationAuthor <http://www.Department0.University0.edu/AssistantProfessor0>.} \n";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setQueryRewriter(rewriter);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query3.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	// @Test
	// public void query4() throws RecognitionException {
	// System.setProperty("entityExpansionLimit", "512000");
	// QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	// qaHornSHIQ.setDataLogName("TestData/lubm/query4.dl");
	// qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");
	//
	// String sparql =
	// "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
	// + "SELECT ?X ?Y1 ?Y2 ?Y3 	"
	// + "WHERE { \n"
	// + "?X a ub:Professor . \n "
	// + "?X ub:worksFor <http://www.Department0.University0.edu> . \n"
	// + "?X ub:name ?Y1 . \n "
	// + "?X ub:emailAddress ?Y2 .\n"
	// + "?X ub:telephone ?Y3 . \n" + "} \n";
	//
	// System.out.println(sparql);
	//
	// CharStream stream = new ANTLRStringStream(sparql);
	// SparqlLexer lexer = new SparqlLexer(stream);
	// TokenStream tokenStream = new CommonTokenStream(lexer);
	// SparqlParser parser = new SparqlParser(tokenStream);
	// CQ cq = parser.query();
	//
	// String queryString = cq.toString();
	// System.out.println(queryString);
	// // qaHornSHIQ.setQueryString(queryString);
	// qaHornSHIQ.setCq(cq);
	// qaHornSHIQ.setDlvPath("lib/dlv");
	// // qaHornSHIQ.getModel();
	// qaHornSHIQ.getAnswers();
	// System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
	// + "  milliseconds");
	// System.out.println("query rewriting time: "
	// + qaHornSHIQ.getQueryRewritingTime() + "  milliseconds");
	// System.out.println("Total of reasoning + rewriting time: "
	// + qaHornSHIQ.getReasoningTime()
	// + qaHornSHIQ.getQueryRewritingTime() + "  milliseconds");
	// // Check if our answers is the same as correct answers.
	// Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
	// List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
	// for (List<String> listAnswer : actualAnswersList) {
	// Set<String> setAnswer = new HashSet<String>(listAnswer);
	// actualAnswers.add(setAnswer);
	// }
	// GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
	// lubmAnswers.readAnswers("TestData/lubm/answers_query4.txt");
	// assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	// }

	@Test
	public void query5() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query5.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X 	"
				+ "WHERE { \n" + "?X a ub:Person . \n "
				+ "?X ub:memberOf <http://www.Department0.University0.edu> . \n" + "} \n";

		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query5.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query6() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query6.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X 	"
				+ "WHERE { \n" + "?X a ub:Student . \n " + "} \n";

		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query6.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query7() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query7.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X ?Y	\n"
				+ "WHERE { \n" + "	?X a ub:Student . \n" + "	?Y a ub:Course . \n" + "	?X ub:takesCourse ?Y . \n"
				+ "	<http://www.Department0.University0.edu/AssociateProfessor0> " + "	  	ub:teacherOf  ?Y . \n}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query7.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query8() throws RecognitionException {
		// WITH DIFFERENT RESULTS
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query8.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X ?Y ?Z \n"
				+ "WHERE {\n" + "	?X a ub:Student . \n" + "	?Y a ub:Department . \n" + "	?X ub:memberOf ?Y .\n"
				+ "	?Y ub:subOrganizationOf <http://www.University0.edu> .\n" + "	?X ub:emailAddress ?Z .\n}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query8.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query9() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query9.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X ?Y ?Z \n"
				+ "WHERE {" + "?X a ub:Student ." + "?Y a ub:Faculty ." + "?Z a ub:Course ." + "?X ub:advisor ?Y ."
				+ "?Y ub:teacherOf ?Z ." + "?X ub:takesCourse ?Z .}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		long totalTime = qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime();
		System.out.println("Total of reasoning + rewriting time: " + totalTime + "  milliseconds");
		System.out.println("Time of running datalog program : " + qaHornSHIQ.getClipperReport().getDatalogRunTime()
				+ "  milliseconds");
		// Check if our answers is the same as correct answers.
		// Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		// List<List<String>> actualAnswersList =
		// qaHornSHIQ.getDecodedAnswers();
		// for (List<String> listAnswer : actualAnswersList) {
		// Set<String> setAnswer = new HashSet<String>(listAnswer);
		// actualAnswers.add(setAnswer);
		// }
		// GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		// lubmAnswers.readAnswers("TestData/lubm/answers_query9.txt");
		// assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query10() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/lubm/query10.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X "
				+ "WHERE{" + "?X a ub:Student ."
				+ "?X ub:takesCourse 	<http://www.Department0.University0.edu/GraduateCourse0> .} ";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query10.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query11() throws RecognitionException {
		// Different results
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/lubm/query11.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X \n"
				+ "WHERE{ \n" + "?X a ub:ResearchGroup . \n"
				+ "?X ub:subOrganizationOf <http://www.University0.edu> .\n" + "}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query11.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query12() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/lubm/query12.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X ?Y \n"
				+ "WHERE{ \n" + "?X a ub:Chair .\n" + "?Y a ub:Department .\n" + "?X ub:worksFor ?Y .\n"
				+ "?Y ub:subOrganizationOf <http://www.University0.edu> .\n}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query12.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query13() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/lubm/query13.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X \n"
				+ "WHERE 	{ \n" + "?X a ub:Person .\n" + "<http://www.University0.edu> ub:hasAlumnus ?X .\n}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		System.out.println("Total of reasoning + rewriting time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query13.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query14() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/lubm/query14.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/full-lubm-wo-dt.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" + "SELECT ?X \n"
				+ "WHERE {?X a ub:UndergraduateStudent .}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		// qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.runDatalogEngine();
		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  milliseconds");
		System.out.println("query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime()
				+ "  milliseconds");
		long totalTime = qaHornSHIQ.getClipperReport().getReasoningTime()
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime();
		System.out.println("Total of reasoning + rewriting time: " + totalTime + "  milliseconds");
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
		GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		lubmAnswers.readAnswers("TestData/lubm/answers_query14.txt");
		assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void queryTest() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("TestData/lubm/queryTest.dl");
		qaHornSHIQ.setOntologyName("TestData/lubm/lubm1.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>" + " SELECT ?X ?Z "
				+ "WHERE {" + "?X a ub:Student ." + "?Y a ub:Department ." + "?X ub:memberOf ?Y ."
				+ "?Z a ub:Professor ;" + "ub:worksFor  ?Y ." + "}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		qaHornSHIQ.getAboxDataLog();
		// qaHornSHIQ.getAnswers();

		// Set predictedAnswser = new HashSet<String>();
		// List<String> ans1= new List<String>();

		// System.out.println(qaHornSHIQ.getDecodedAnswers());

		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  millisecond");
		System.out
				.println("reasoning time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
	}

}
