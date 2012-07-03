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


public class RequiemTestSuiteStockExchange {
	@Test
	public void query0() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/StockExQuery0.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/stockexchange.owl");
		//Q(?0) <- StockExchangeMember(?0)
		String sparql = "PREFIX ub: <http://www.owl-ontologies.com/Ontology1207768242.owl#>\n" + //
				"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:StockExchangeMember .\n" + //
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
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/StockExQuery01.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/stockexchange.owl");
		//Q(?0,?1) <- Person(?0), hasStock(?0,?1), Stock(?1)
		String sparql = "PREFIX ub: <http://www.owl-ontologies.com/Ontology1207768242.owl#>\n" + //
				"SELECT ?x0 ?x1 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Person ,\n" + //
				"		  ub:hasStock ?x1 .\n" + //
				" 	?x1	a ub:Stock . \n"+
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
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/StockExQuery01.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/stockexchange.owl");
		//Q(?0,?1,?2) <- FinantialInstrument(?0), belongsToCompany(?0,?1), Company(?1), hasStock(?1,?2), Stock(?2)
		String sparql = "PREFIX ub: <http://www.owl-ontologies.com/Ontology1207768242.owl#>\n" + //
				"SELECT ?x0 ?x1 ?x2 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:FinantialInstrument ;\n" + //
				"		  ub:belongsToCompany ?x1 .\n" + //
				" 	?x1	a ub:Company ; \n"+
				" 		 ub:hasStock ?x2 . \n"+
				" 	?x2	a ub:Stock . \n"+
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
	public void query03() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/StockExQuery03.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/stockexchange.owl");
		//Q(?0,?1,?2) <- Person(?0), hasStock(?0,?1), Stock(?1), isListedIn(?1,?2), StockExchangeList(?2)
		String sparql = "PREFIX ub: <http://www.owl-ontologies.com/Ontology1207768242.owl#>\n" + //
				"SELECT ?x0 ?x1 ?x2 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Person ;\n" + //
				"		  ub:hasStock ?x1 .\n" + //
				" 	?x1	a ub:Stock ; \n"+
				" 		 ub:isListedIn ?x2 . \n"+
				" 	?x2	a ub:StockExchangeList . \n"+
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
	public void query04() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDataLogName("TestData/requiem/EvalDL09/StockExQuery03.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/stockexchange.owl");
		//Q(?0,?1,?2,?3) <- FinantialInstrument(?0), belongsToCompany(?0,?1), Company(?1), hasStock(?1,?2), Stock(?2), isListedIn(?1,?3), StockExchangeList(?3)
		String sparql = "PREFIX ub: <http://www.owl-ontologies.com/Ontology1207768242.owl#>\n" + //
				"SELECT ?x0 ?x1 ?x2 ?x3 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:FinantialInstrument ;\n" + //
				"		  ub:belongsToCompany ?x1 .\n" + //
				" 	?x1	a ub:Company ; \n"+
				" 		 ub:hasStock ?x2 ; \n"+
				" 		 ub:isListedIn ?x3 . \n"+
				" 	?x2	a ub:Stock . \n"+
				" 	?x3	a ub:StockExchangeList . \n"+
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
	
}
