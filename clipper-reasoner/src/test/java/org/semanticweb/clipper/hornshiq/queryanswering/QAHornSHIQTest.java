package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;


public class QAHornSHIQTest {

	@Test
	public void testBasicRewriting() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	KaosManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LOWER_CASE_FRAGMENT);
		qaHornSHIQ.setDatalogFileName("AllTestCases/testRoleInclusion.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testRoleInclusion.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at.testRoleInclusion.owl#> \n"
				+ "SELECT ?x1 \n"
				+ "WHERE { \n"
				+ "  ?x1    uri:R2 ?x2  . \n"
				+ "} \n";
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

		qaHornSHIQ.execQuery();
		// expect answer: a1
		List<String> a1 = new ArrayList<String>();
		a1.add("q0(\"a1\")");
		assertEquals(a1, qaHornSHIQ.getAnswers());
		for(CQ i : qaHornSHIQ.getRewrittenQueries()){
			System.out.println(i);
		}
	}
	
	@Test
	public void testTransitivity01() throws OWLOntologyCreationException, FileNotFoundException, IOException{
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new File("TestCaseOntologies/trans1.owl"));
		CQParser cqParser = new CQParser(new File("TestCaseOntologies/trans1.cq"), ImmutableSet.of(ontology));
		CQ cq = cqParser.parse();
		qaHornSHIQ.addOntology(ontology);
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setQueryRewriter("new");
		ClipperManager.getInstance().setVerboseLevel(1);
		qaHornSHIQ.setDatalogFileName("TestCaseOntologies/tmp.dlv");
		List<List<String>> results = qaHornSHIQ.execQuery();
		Joiner.on("\n").appendTo(System.out, results);
		assertEquals(1, results.size());
	}
	
	@Test
	public void testTransitivity02() throws OWLOntologyCreationException, FileNotFoundException, IOException{
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new File("TestCaseOntologies/trans2.owl"));
		CQParser cqParser = new CQParser(new File("TestCaseOntologies/trans2.cq"), ImmutableSet.of(ontology));
		CQ cq = cqParser.parse();
		qaHornSHIQ.addOntology(ontology);
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setQueryRewriter("new");
		ClipperManager.getInstance().setVerboseLevel(1);
		qaHornSHIQ.setDatalogFileName("TestCaseOntologies/tmp.dlv");
		List<List<String>> results = qaHornSHIQ.execQuery();
		Joiner.on("\n").appendTo(System.out, results);
		assertEquals(3, results.size());
	}
	
	public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException, IOException{
		new QAHornSHIQTest().testTransitivity01();
	}
	
}
