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


public class RequiemTestSuitePaths {
	@Test
	public void query0() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(2);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/PathsQuery0.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/path5.owl");
		//Q(?0) <- edge(?0,?1)
		String sparql = "PREFIX ub: <http://www.semanticweb.org/ontologies/2008/8/24/Ontology1222256119496.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	ub:edge ?x1 .\n" + //
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

	}

	@Test
	public void query01() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/PathsQuery01.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/path5.owl");
		//Q(?0) <- edge(?0,?1), edge(?1,?2)
		String sparql = "PREFIX ub: <http://www.semanticweb.org/ontologies/2008/8/24/Ontology1222256119496.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	ub:edge ?x1 .\n" + //
				"	?x1 ub:edge ?x2 ." +
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

	}
	
	@Test
	public void query02() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/PathsQuery02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/path5.owl");
		//Q(?0) <- edge(?0,?1), edge(?1,?2), edge(?2,?3)
		String sparql = "PREFIX ub: <http://www.semanticweb.org/ontologies/2008/8/24/Ontology1222256119496.owl#>\n" + //
				"SELECT ?x1 \n" + //
				"WHERE {\n" + //
				"	?x0	ub:edge ?x1 .\n" + //
				"	?x2 ub:edge ?x1 ." +
				"	?x3 ub:edge ?x1 ." +
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
		System.out.println("NUmber of rewritten quries: " + qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueries());
	}
	
	@Test
	public void query03() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/PathsQuery03.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/path5.owl");
		//Q(?0) <- edge(?0,?1), edge(?1,?2), edge(?2,?3)
		String sparql = "PREFIX ub: <http://www.semanticweb.org/ontologies/2008/8/24/Ontology1222256119496.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	ub:edge ?x1 .\n" + //
				"	?x1 ub:edge ?x2 ." +
				"	?x2 ub:edge ?x3 ." +
				"	?x3 ub:edge ?x4 ." +
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
		System.out.println("reasoning time " + qaHornSHIQ.getClipperReport().getReasoningTime() + "millisecond");
		System.out.println("reasoning time " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "millisecond");
		System.out.println("NUmber of rewritten quries: " + qaHornSHIQ.getClipperReport().getNumberOfRewrittenQueries());
	}
	
	@Test
	public void query04() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/PathsQuery04.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/path5.owl");
		//Q(?0) <- edge(?0,?1), edge(?1,?2), edge(?2,?3)
		String sparql = "PREFIX ub: <http://www.semanticweb.org/ontologies/2008/8/24/Ontology1222256119496.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	ub:edge ?x1 .\n" + //
				"	?x1 ub:edge ?x2 ." +
				"	?x2 ub:edge ?x3 ." +
				"	?x3 ub:edge ?x4 ." +
				"	?x4 ub:edge ?x5 ." +
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
		System.out.println("Reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime() + "  millisecond");
		System.out.println("Query rewriting time: " + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
		System.out.println("Time of running datalog program : " + qaHornSHIQ.getClipperReport().getDatalogRunTime() + "  milliseconds");
		

	}
}
