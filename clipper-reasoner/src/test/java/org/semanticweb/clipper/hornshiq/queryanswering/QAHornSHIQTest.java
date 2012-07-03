package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;


public class QAHornSHIQTest {

	@Test
	public void testBasicRewriting() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
	//	KaosManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testRoleInclusion.dl");
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
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		qaHornSHIQ.runDatalogEngine();
		// expect answer: a1
		List<String> a1 = new ArrayList<String>();
		a1.add("q0(\"a1\")");
		assertEquals(a1, qaHornSHIQ.getAnswers());
		for(CQ i : qaHornSHIQ.getRewrittenQueries()){
			System.out.println(i);
		}
	}
}
