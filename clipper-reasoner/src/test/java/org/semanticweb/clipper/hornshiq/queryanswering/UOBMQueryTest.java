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
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.clipper.util.GetLUBMAnswers;

import java.util.List;


public class UOBMQueryTest {
	@Test
	public void query1() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query1.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n"
				+ //
				"  ?x a ub:UndergraduateStudent ;\n"
				+ //
				"       ub:takesCourse <http://www.Department0.University0.edu/Course0> . \n"
				+ "}";
		System.out.println(sparql);

//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	
	@Test
	public void query2() throws RecognitionException {
		// Query 6 in LiMa's paper
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query2.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n"
				+ //
				"  ?x a ub:Person . \n"
				+ //
				"  <http://www.University0.edu>     ub:hasAlumnus ?x . \n"
				+ "}";
		System.out.println(sparql);

//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	
	@Test
	public void query2Prime() throws RecognitionException {
		// Query 6 in LiMa's paper, but using inverserole of hasAlumnus
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query2Prime.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n"
				+ //
				"  ?x a ub:Person ; \n"
				+ //
				"     ub:hasDegreeFrom <http://www.University0.edu>  . \n"
				+ "}";
		System.out.println(sparql);
//
//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	@Test
	public void query3() throws RecognitionException {
		// Modify Query 9 in LiMa's paper
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query3.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
//		WHERE { ?x rdf:type benchmark:GraduateCourse . ?x benchmark:isTaughtBy ?y .
//				?y benchmark:isMemberOf ?z .?z benchmark:subOrganizationOf http://www.University0.edu }
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n" +
				"  ?x a ub:GraduateCourse ; \n" +
				"       ub:isTaughtBy ?y . \n"+
				"  ?y ub:isMemberOf <http://www.Department0.University0.edu> ."+
				 "}";
		System.out.println(sparql);

//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	
	@Test
	public void query4() throws RecognitionException {
		// Query 10 in LiMa's paper, but using inverserole of hasAlumnus
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query4.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
//		WHERE { ?x rdf:type benchmark:GraduateCourse . ?x benchmark:isTaughtBy ?y .
//				?y benchmark:isMemberOf ?z .?z benchmark:subOrganizationOf http://www.University0.edu }
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n" +
				"  ?x  ub:isFriendOf <http://www.Department0.University0.edu/FullProfessor0> ."+
				 "}";
		System.out.println(sparql);
//
//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	
	@Test
	public void query5() throws RecognitionException {
		// Modify Query 8 in LiMa's paper
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/uobm/query5.dl");
		qaHornSHIQ
				.setOntologyName("TestData/uobm/univ-bench-dl-hornshiq-1.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
//		WHERE { ?x rdf:type benchmark:GraduateCourse . ?x benchmark:isTaughtBy ?y .
//				?y benchmark:isMemberOf ?z .?z benchmark:subOrganizationOf http://www.University0.edu }
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n" +
				"  ?x a ub:SportLovers . \n" +
				"      <http://www.Department0.University0.edu>    ub:hasMembers ?x ."+
				 "}";
		System.out.println(sparql);

//		CharStream stream = new ANTLRStringStream(sparql);
//		SparqlLexer lexer = new SparqlLexer(stream);
//		TokenStream tokenStream = new CommonTokenStream(lexer);
//		SparqlParser parser = new SparqlParser(tokenStream);
//		CQ cq = parser.query();
//
//		String queryString = cq.toString();
//		System.out.println(queryString);
//		// qaHornSHIQ.setQueryString(queryString);
//		qaHornSHIQ.setCq(cq);
//		qaHornSHIQ.setDlvPath("lib/dlv");
//		// qaHornSHIQ.getModel();
//		qaHornSHIQ.getAnswers();
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
	@Test
	public void query6() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "51200000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("/home/kien/dataset/5/query1.dl");
		qaHornSHIQ
				.setOntologyName("/home/kien/dataset/5/univ-bench-hornshiq.owl");
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		String sparql = "PREFIX ub: <http://semantics.crl.ibm.com/univ-bench-dl.owl#>"
				+ //
				"SELECT ?x \n"
				+ //
				"WHERE {\n"
				+ //
				"  ?x a ub:UndergraduateStudent ;\n"
				+ //
				"       ub:takesCourse <http://www.Department0.University0.edu/Course0> . \n"
				+ "}";
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
//		// Set predictedAnswser = new HashSet<String>();
//		// List<String> ans1= new List<String>();
//
//		// System.out.println(qaHornSHIQ.getDecodedAnswers());
//
//		System.out.println("reasoning time: " + qaHornSHIQ.getReasoningTime()
//				+ "  millisecond");
//		System.out.println("query rewriting time: "
//				+ qaHornSHIQ.getQueryRewritingTime() + "  millisecond");
	}
}
