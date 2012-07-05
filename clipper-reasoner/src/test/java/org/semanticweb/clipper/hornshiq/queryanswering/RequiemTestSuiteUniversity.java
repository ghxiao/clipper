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


public class RequiemTestSuiteUniversity {
	@Test
	public void query0() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(2);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query1.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n"
				+ //
				"SELECT ?x \n" + //
				"WHERE {\n" + //
				"  ?x ub:worksFor ?y.\n" + //
				"   ?y     ub:affiliatedOrganizationOf ?z .\n" + //
				"}";
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
		System.out.println("reasoning time " + qaHornSHIQ.getClipperReport().getReasoningTime() + "milisecond");
		System.out.println("reasoning time " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "milisecond");
		// Set predictedAnswser = new HashSet<String>();
		// List<String> ans1= new List<String>();

		// // System.out.println(qaHornSHIQ.getDecodedAnswers());
		// Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		// List<List<String>> actualAnswersList =
		// qaHornSHIQ.getDecodedAnswers();
		// for (List<String> listAnswer : actualAnswersList) {
		// Set<String> setAnswer = new HashSet<String>(listAnswer);
		// actualAnswers.add(setAnswer);
		// }
		// GetLUBMAnswers lubmAnswers = new GetLUBMAnswers();
		// lubmAnswers.readAnswers("TestData/lubm/answers_query1.txt");
		// Set<Set<String>> correctAnswers= lubmAnswers.getAnswers();
		// System.out.println("Actual answers:"+ setActualAnswers);
		// System.out.println("Correct answers:"+ correctAnswers);
		// System.out.println(setActualAnswers.equals(correctAnswers));

		// assertEquals(lubmAnswers.getAnswers(), actualAnswers);
	}

	@Test
	public void query1() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query1.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		// Q(?0,?1) <- Person(?0), teacherOf(?0,?1), Course(?1)
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n"
				+ //
				"SELECT ?x0 ?x1 \n" + //
				"WHERE {\n" + //
				"  ?x0 a ub:Professor ; \n" + //
				"      ub:teacherOf ?x1 .\n" + //
			"  ?x1  a ub:GraduateCourse .\n" + //
				"}";
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
		qaHornSHIQ.getDataLog();
		
		//qaHornSHIQ.getAnswers();
		// Check if our answers is the same as correct answers.
		System.out.println("NUmber of rewritten quries: " + qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueries());

	}

	@Test
	public void query02() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0,?1,?2) <- Student(?0), advisor(?0,?1), FacultyStaff(?1),
		// takesCourse(?0,?2), teacherOf(?1,?2), Course(?2)
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n" + //
				"SELECT ?x0 ?x1  \n" + //
				"WHERE {\n" + //
				"  ?x0 a ub:Student ;\n" + //
				"      ub:advisor ?x1 ;\n" + //
				"	   ub:takesCourse ?x2 ." + "  ?x1  a ub:PostDoc ;\n" + //
				"       ub:teacherOf ?x2 . \n" + //
				"  ?x2  a ub:GraduateCourse ." + "}";
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
		//System.out.println(qaHornSHIQ.get
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
      
	}

	@Test
	public void query03() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query03.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0,?1) <- Person(?0), worksFor(?0,?1), Organization(?1)
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n" + //
				"SELECT ?x0 ?x1  \n" + //
				"WHERE {\n" + //
				"  ?x0 a ub:Dean ;\n" + //
				"      ub:worksFor ?x1 .\n" + //
				"  ?x1  a ub:College .\n" + "}";
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
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
	}

	@Test
	public void query04() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query04.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// /Q(?0) <- Person(?0), worksFor(?0,?1), University(?1),
		// hasAlumnus(?1,?0)
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n" + //
				"SELECT ?x0   \n" + //
				"WHERE {\n" + //
				"  ?x0 a ub:PostDoc ;\n" + //
				"      ub:worksFor ?x1 .\n" + //
				"  ?x1  a ub:University ;\n" + //
				"		ub:hasAlumnus ?x0 ." + "}";
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
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
	}
	@Test
	public void query07() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0,?1,?2) <- Student(?0), advisor(?0,?1), FacultyStaff(?1),
		// takesCourse(?0,?2), teacherOf(?1,?2), Course(?2)
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>\n" + //
				"SELECT ?x0 ?x1  \n" + //
				"WHERE {\n" + //
				"  ?x0 a ub:GraduateStudent ;\n" + //
				"      ub:advisor ?x1 ;\n" + //
				"	   ub:takesCourse ?x2 ." + "  ?x1  a ub:Professor ;\n" + //
				"       ub:teacherOf ?x2 . \n" + //
				"  ?x2  a ub:GraduateCourse ." + "}";
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
		//System.out.println(qaHornSHIQ.get
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
    
	}
	@Test
	public void query08() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0,?1,?2) <- Student(?0), advisor(?0,?1), FacultyStaff(?1),
		// takesCourse(?0,?2), teacherOf(?1,?2), Course(?2)
		String sparql =  "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" +
		                 "SELECT ?X ?Z \n" + 
							"WHERE { \n" +
						"?X a ub:Student . \n" + 
						"?Y a ub:Course . \n" +
						"?X ub:takesCourse ?Y . \n" +
						"?Z a ub:Professor ; \n" +
						"   ub:teacherOf  ?Y . }\n ";
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
		//System.out.println(qaHornSHIQ.get
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
    
	}
	@Test
	public void query09() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/query02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0,?1,?2) <- Student(?0), advisor(?0,?1), FacultyStaff(?1),
		// takesCourse(?0,?2), teacherOf(?1,?2), Course(?2)
		String sparql =  "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n" +
		                 "SELECT ?X ?Z \n" + 
							"WHERE { \n" +
						"?X a ub:Student . \n" + 
						"?Y a ub:Department . \n" +
						"?X ub:memberOf ?Y . \n" +
						"?Z a ub:Professor ; \n" +
						"   ub:worksFor  ?Y . }\n ";
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
		//System.out.println(qaHornSHIQ.get
		// Check if our answers is the same as correct answers.
		Set<Set<String>> actualAnswers = new HashSet<Set<String>>();
		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();
		for (List<String> listAnswer : actualAnswersList) {
			Set<String> setAnswer = new HashSet<String>(listAnswer);
			actualAnswers.add(setAnswer);
		}
    
	}
	
}
