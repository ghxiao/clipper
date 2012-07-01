package org.semanticweb.clipper.hornshiq.sparql;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;


public class SparqlParserTest {

	@Test
	public void test000() throws RecognitionException {
		String sparql = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" + //
				"SELECT ?name ?email\n" + //
				"WHERE {\n" + //
				"  ?person a foaf:Person .\n" + //
				"  ?person foaf:name ?name .\n" + //
				"  ?person foaf:mbox ?email .\n" + //
				"}";

		doTest(sparql);
	}

	@Test
	public void test001() throws RecognitionException {
		String sparql = "" + //
				"PREFIX abc: <http://example.com/exampleOntology#>\n" + //
				"SELECT ?capital ?country\n" + //
				"WHERE {\n" + //
				"  ?x abc:cityname ?capital ;\n" + //
				"     abc:isCapitalOf ?y .\n" + //
				"  ?y abc:countryname ?country ;\n" + //
				"     abc:isInContinent abc:Africa .\n" + //
				"}";
		doTest(sparql);
		// System.out.println("ok - result is " + result);
	}

	@Test
	public void test002() throws RecognitionException {
		String sparql = "" + //
				"PREFIX abc: <http://example.com/exampleOntology#>\n" + //
				"SELECT ?capital ?country\n" + //
				"WHERE {\n" + //
				"  ?x a abc:City ;\n " + "     abc:cityname ?capital ;\n" + //
				"     abc:isCapitalOf ?y .\n" + //
				"  ?y abc:countryname ?country ;\n" + //
				"     abc:isInContinent abc:Africa .\n" + //
				"}";
		doTest(sparql);
	}

	protected void doTest(String sparql) throws RecognitionException {
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ query = parser.query();
		
		System.out.println(query);
		
		
		System.out.println("-----------------------------------------");
		System.out.println();
	}
}
