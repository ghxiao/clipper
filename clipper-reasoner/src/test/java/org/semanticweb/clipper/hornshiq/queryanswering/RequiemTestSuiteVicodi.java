package org.semanticweb.clipper.hornshiq.queryanswering;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.clipper.util.DecodeUtility;


public class RequiemTestSuiteVicodi {
	@Test
	public void query1() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery0.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/vicodi.owl");
		//Q(?0) <- Location(?0)
		String sparql = "PREFIX ub:<http://vicodi.org/ontology#> \n" + //
						"SELECT ?x0 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Location .\n" + //
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
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		// qaHornSHIQ.getModel();
		qaHornSHIQ.generateDatalog();
	//	qaHornSHIQ.getAnswers();

		DecodeUtility decodeUtility = new DecodeUtility(NamingStrategy.INT_ENCODING);
		System.out.println("Decoded query : " +decodeUtility.decodeQuery(cq));
		
	}

	@Test
	public void query2() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	//	KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery01.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/vicodi.owl");
		//Q(?0,?1) <- Military-Person(?0), hasRole(?1,?0), related(?0,?2)
		String sparql = "PREFIX ub: <http://vicodi.org/ontology#>\n" + //
				"SELECT ?x0 ?x1 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Military-Person ;\n" + //
				"       ub:hasRole ?x1 ; \n" + //
				"	    ub:related ?x2 . \n" + "}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		
		// qaHornSHIQ.getModel();
		qaHornSHIQ.generateDatalog();

	}
	//Q(?0) <- Location(?0)

	
	//Q(?0,?1) <- Object(?0), hasRole(?0,?1), Symbol(?1)
	//Q(?0) <- Individual(?0), hasRole(?0,?1), Scientist(?1), hasRole(?0,?2), Discoverer(?2), hasRole(?0,?3), Inventor(?3)
	@Test
	public void query3() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	//	KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery02.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/vicodi.owl");
		//Q(?0,?1) <- Time-Dependant-Relation(?0), hasRelationMember(?0,?1), Event(?1)
		String sparql = "PREFIX ub: <http://vicodi.org/ontology#>\n" + //
				"SELECT ?x0 ?x1 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Time-Dependant-Relation ;\n" + //
				"       ub:hasRelationMember ?x1 . \n" + //
				"	?x1 a ub:Event . \n" + "}";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		
		// qaHornSHIQ.getModel();
		qaHornSHIQ.generateDatalog();

	}
	
	@Test
	public void query4() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	//	KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery4.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/vicodi.owl");
		//Q(?0,?1) <- Object(?0), hasRole(?0,?1), Symbol(?1)
		String sparql = "PREFIX ub: <http://vicodi.org/ontology#>\n" + //
				"SELECT ?x0 ?x1 \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Object ;\n" + //
				"       ub:hasRole ?x1 . \n" + //
				"	?x1	a ub:Symbol . \n" + //
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
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		
		// qaHornSHIQ.getModel();
		qaHornSHIQ.generateDatalog();

	}
	@Test
	public void query5() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	//	KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery04.dl");
		qaHornSHIQ.setOntologyName("TestData/requiem/EvalDL09/vicodi.owl");

		String sparql = "PREFIX ub: <http://vicodi.org/ontology#>\n" + //
				"SELECT ?x0  \n" + //
				"WHERE {\n" + //
				"	?x0	a ub:Individual ;\n" + //
				"       ub:hasRole ?x1 ; \n" + //
				"       ub:hasRole ?x2 ; \n" + //
				"	    ub:hasRole ?x3 . \n" + //
				"	?x1	a ub:Scientist . \n" + //
				"	?x2	a ub:Discoverer . \n" + //
				"	?x3	a ub:Inventor . \n" + //
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
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		
		// qaHornSHIQ.getModel();
		qaHornSHIQ.execQuery();

	}
	
	@Test
	public void query5WithData() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		ClipperManager.getInstance().setVerboseLevel(1);
	//	KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	qaHornSHIQ.setNamingStrategy(NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDatalogFileName("TestData/requiem/EvalDL09/VicodiQuery04.dl");
		qaHornSHIQ.setOntologyName("/home/kien/Downloads/ontologies/galen/galen.owl");
	//	Q1(x):- horn(x),hasstate(x,y), cellmorphologystate(y).
		String sparql = "PREFIX ub: <http://www.galen.com/ontology#>\n" + //
				"SELECT ?x  \n" + //
				"WHERE {\n" + //
				"	?x	a ub:horn ;\n" + //
				"       ub:hasstate ?y . \n" + //
				"	?y	a ub:cellmorphologystate . \n" + //
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
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		
		// qaHornSHIQ.getModel();
		qaHornSHIQ.execQuery();

	}
}
