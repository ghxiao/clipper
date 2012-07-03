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


public class RequiemTestSuiteAdolena {
	@Test
	public void query0() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setNamingStrategy(NamingStrategy.IntEncoding);
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/AdolenaQuery0.dl");
		//qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/adolena.owl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/miniAdolena.owl");
		// Q(?0) <- Device(?0), assistsWith(?0,?1)
		String sparql = "PREFIX ub: <http://ksg.meraka.co.za/adolena.owl#>\n" + //
			"PREFIX y: <file:/home/aurona/0AlleWerk/Navorsing/Ontologies/NAP/NAP#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	a y:Device ;\n" + //
				"       ub:asistsWith ?x1 . \n" + //
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
	public void query01() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	    QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		// qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDataLogName("TestData/requiem/EvalDL09/AdolenaQuery01.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/adolena.owl");
		// Q(?0) <- Device(?0), assistsWith(?0,?1), UpperLimbMobility(?1)
		String sparql = "PREFIX ub: <http://ksg.meraka.co.za/adolena.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Device ;\n" + //
				"       ub:asistsWith ?x1 . \n" + //
				"	?x1 a ub:UpperLimbMobility . \n" + "}";
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
	public void query02() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ
				.setDataLogName("TestData/requiem/EvalDL09/AdolenaQuery02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		// Q(?0) <- Device(?0), assistsWith(?0,?1), Hear(?1), affects(?2,?1),
		// Autism(?2)
		String sparql = "PREFIX ub: <http://ksg.meraka.co.za/adolena.owl#>\n"
				+ //
				"SELECT ?x0 \n"
				+ //
				"WHERE {\n"
				+ //
				"	?x0	a ub:Device ;\n"
				+ //
				"       ub:asistsWith ?x1 . \n"
				+ //
				"	?x1 a ub:Hear . \n" + "	?x2 a ub:Autism ; \n"
				+ "		  ub:affects ?x1 . \n" + "}";
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
	public void query03() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ
				.setDataLogName("TestData/requiem/EvalDL09/AdolenaQuery03.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/university.owl");
		// Q(?0) <- Device(?0), assistsWith(?0,?1), PhysicalAbility(?1)
		String sparql = "PREFIX ub: <http://ksg.meraka.co.za/adolena.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Device ;\n" + //
				"       ub:asistsWith ?x1 . \n" + //
				"	?x1 a ub:PhysicalAbility . \n" + "}";
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
	public void query04() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ
				.setDataLogName("TestData/requiem/EvalDL09/AdolenaQuery04.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/adolena.owl");
		ClipperManager.getInstance().setVerboseLevel(1);
		// Q(?0) <- Device(?0), assistsWith(?0,?1), PhysicalAbility(?1),
		// affects(?2,?1), Quadriplegia(?2)
		String sparql = "PREFIX ub: <http://ksg.meraka.co.za/adolena.owl#>\n"
				+ //
				"SELECT ?x0  \n"
				+ //
				"WHERE {\n"
				+ //
				"  ?x0 a ub:Device ;\n"
				+ //
				"      ub:assistWith ?x1 .\n"
				+ "  ?x1  a ub:PhysicalAbility .\n" + //
				"  ?x2   ub:affects ?x1 ;" + //
				"       a ub:Quadriplegia . " + "}";
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
}
