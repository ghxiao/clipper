package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import org.semanticweb.clipper.util.DecodeUtility;


/**
 * @author kien
 * 
 */
public class TestCase {

	// /////////////////////////////////////////////////////////
	// Really test the whole system from here.
	// See the master thesis for more detail of the test cases.
	// /////////////////////////////////////////////////////////

	// ///////////////////////////////////////
	// TEST CASE FOR SATURATION
	// ///////////////////////////////////////
	/**
	 * Test case 1
	 */
	@Test
	public void testRoleInclusion() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
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
		a1.add("q0(\"a\")");
		assertEquals(a1, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 2: Test Inverse Role Inclusion
	 * 
	 * @throws RecognitionException
	 */
	@Test
	public void testRoleInclusionWithInverse() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testInverseRoleInclusion.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testInverseRoleInclusion.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at.testRoleInclusion.owl#> \n"
				+ "SELECT ?x1 \n"
				+ "WHERE { \n"
				+ "  ?x2    uri:R2 ?x1  . \n"
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
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 3. Test Concept Inclusion
	 * **/
	@Test
	public void testConceptInclusion() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testConceptInclusion.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testConceptInclusion.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at.testConceptInclusion.owl#> \n"
				+ "SELECT ?x1 \n"
				+ "WHERE { \n"
				+ "  ?x1    uri:R ?x2  . \n"
				+ "  ?x2 a  uri:B2 . \n " + "} \n";
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

		for (List<String> answer : qaHornSHIQ.getDecodedAnswers()) {
			System.out.println(answer);
		}
		// expect answer: a1
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a1\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 4. Note that it's a little different from one in the thesis.
	 * Test Bottom rule
	 * **/
	@Test
	public void testBottomRule() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testBottomRule.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testBottomRule.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x1 \n" + "WHERE { \n"
				+ "  ?x1  a  uri:Nothing  . \n" + "} \n";

		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);

		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		qaHornSHIQ.runDatalogEngine();
		// expect 1 answer for q(x) :- Nothing(x) because the ontology is
		// inconsistent
		for (List<String> answer : qaHornSHIQ.getDecodedAnswers()) {
			System.out.println(answer);
		}
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());

	}

	/**
	 * Test case 5: see Example 23 in Chapter 4 of the master thesis Test rule:
	 * 
	 * **/
	//UPDATED 12 March: We don't use ABox signature restriction so we don't need this rule.
//	@Test
//	public void testExistentialRule1() throws RecognitionException {
//		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
//		KaosManager.getInstance().setVerboseLevel(2);
//		KaosManager.getInstance().setNamingStrategy(
//				NamingStrategy.LowerCaseFragment);
//		qaHornSHIQ.setDataLogName("AllTestCases/existentialRule.dl");
//		qaHornSHIQ.setOntologyName("AllTestCases/existentialRule.owl");
//
//		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
//				+ "SELECT ?x1 \n" + "WHERE { \n"
//				+ "  ?x1  a  uri:Nothing  . \n" + "} \n";
//		// expect no answer
//		System.out.println(sparql);
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
//		qaHornSHIQ.runDatalogEngine();
//		// expect 1 answer for q(x) :- Nothing(x) because the ontology is
//		// inconsistent
//		for (List<String> answer : qaHornSHIQ.getDecodedAnswers()) {
//			System.out.println(answer);
//		}
//		List<String> a = new ArrayList<String>();
//		a.add("q0(\"a\")");
//		assertEquals(a, qaHornSHIQ.getAnswers());
//
//	}

	/**
	 * Test case 6 in the Thesis
	 * 
	 * **/
	@Test
	public void testForallRule1() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testForallRule1.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testForallRule1.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x \n" + "WHERE { \n" + "  ?x  a  uri:A  . \n"
				+ "  ?x  a  uri:M  . \n" + "  ?x    uri:R ?y . \n"
				+ "  ?y  a  uri:N  . \n" + "  ?y  a  uri:B  . \n" +

				"} \n";

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
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 7 in the thesis
	 * **/
	@Test
	public void testForallRule2() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testForallRule2.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testForallRule2.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x \n" + "WHERE { \n" + "  ?x  a  uri:B ; \n"
				+ "      a  uri:M . \n " + "} \n";
		// expect no answer
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
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 8 in the thesis
	 * Test rule: \forall_3
	 * **/
	@Test
	public void testForallRule3() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/testForallRule3.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/testForallRule3.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x \n" 
				+ "WHERE { \n" 
				+ "  ?x  a  uri:A  ; \n"
				+ "      a  uri:C  ; \n" 
				+ "         uri:R ?y . \n"
				+ "  ?y  a  uri:B1  ; \n"
				+ "      a  uri:B2  . \n"
				+ "} \n";
		// expect no answer
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
		// expect answer: a
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 9 in the thesis
	 * @throws RecognitionException
	 */
	@Test
	public void testAtMostOne_MergeChildren() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ
				.setDataLogName("AllTestCases/testAtMostOne_MergeChildren.dl");
		qaHornSHIQ
				.setOntologyName("AllTestCases/testAtMostOne_MergeChildren.owl");

		String sparql = "PREFIX ub: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x \n" 
				+ "WHERE { \n" 
				+ "  ?x    ub:R ?y . \n"
				+ "  ?y  a ub:B  ;	" 
				+ "    a ub:C1  ;	" 
				+ "    a ub:C2  .	"
				+ "} \n";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();
		DecodeUtility decodeUtility = new DecodeUtility();
		System.out.println("Decoded query : " + decodeUtility.decodeQuery(cq));

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		qaHornSHIQ.runDatalogEngine();
		// assertEquals(qaHornSHIQ.getDecodedAnswers().size(), 0);
		// expect answer: a
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}

	/**
	 * Test case 10 in the thesis
	 * @throws RecognitionException
	 */
	@Test
	public void testAtMostOne_ParentChildCollapse() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/atMostParentChildCollapse.dl");
		qaHornSHIQ
				.setOntologyName("AllTestCases/atMostParentChildCollapse.owl");

		String sparql = "PREFIX ub: <http://www.kr.tuwien.ac.at/testcase02.owl#> \n"
				+ "SELECT ?x \n"
				+ "WHERE { \n"
				+ "  ?x  a  ub:A  . \n"
				+ "  ?x  a  ub:B  .	" + "} \n";
		System.out.println(sparql);

		CharStream stream = new ANTLRStringStream(sparql);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();
		DecodeUtility decodeUtility = new DecodeUtility();
		System.out.println("Decoded query : " + decodeUtility.decodeQuery(cq));

		String queryString = cq.toString();
		System.out.println(queryString);
		// qaHornSHIQ.setQueryString(queryString);
		qaHornSHIQ.setCq(cq);
		qaHornSHIQ.setDlvPath("lib/dlv");
		qaHornSHIQ.runDatalogEngine();
		// assertEquals(qaHornSHIQ.getDecodedAnswers().size(), 0);
		// expect answer: a
		List<String> a = new ArrayList<String>();
		a.add("q0(\"a\")");
		assertEquals(a, qaHornSHIQ.getAnswers());
	}
	
	/////////////////////////////////////////////////////////////////////
	// Test case for query rewriting. 
	// See Master thesis: Figure 5.2 for more detail
	////////////////////////////////////////////////////////////////////
	/**
	 * Test case 1
	 * @throws RecognitionException
	 */
	@Test
	public void testBasicRewriting() throws RecognitionException {
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		ClipperManager.getInstance().setVerboseLevel(2);
		ClipperManager.getInstance().setNamingStrategy(
				NamingStrategy.LowerCaseFragment);
		qaHornSHIQ.setDataLogName("AllTestCases/simpleRewriting.dl");
		qaHornSHIQ.setOntologyName("AllTestCases/simpleRewriting.owl");

		String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
				+ "SELECT ?x1 \n"
				+ "WHERE { \n"
				+ "  ?x1   a uri:A1  ; \n"
				+ "          uri:R1 ?x2 . \n"
				+ "  ?x2   a uri:A2  ; \n"
				+ "          uri:R2 ?x3 ; \n"
				+ "          uri:R3 ?x4 . \n"
				+ "  ?x3   a uri:A3  . \n"
				+ "  ?x4   a uri:A4  . \n"
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
		qaHornSHIQ.getDataLog();
		//Expect the rewritten query: q0(X0) :- r3(X2,X3), a(X2), a2(X2), a1(X0), a4(X3), r1(X0,X2).
		assertEquals(2, qaHornSHIQ.getRewrittenQueries().size());

	}
		/**
		 * Test case 2
		 * @throws RecognitionException
		 */
		@Test
		public void testQueryRewriting2() throws RecognitionException {
			QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
			ClipperManager.getInstance().setVerboseLevel(2);
			ClipperManager.getInstance().setNamingStrategy(
					NamingStrategy.LowerCaseFragment);
			qaHornSHIQ.setDataLogName("AllTestCases/testQueryRewriting2.dl");
			qaHornSHIQ.setOntologyName("AllTestCases/testQueryRewriting2.owl");

			String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
					+ "SELECT ?x1 \n"
					+ "WHERE { \n"
					+ "  ?x1   a uri:A1  ; \n"
					+ "          uri:R1 ?x2 . \n"
					+ "  ?x2   a uri:A2  ; \n"
					+ "          uri:R2 ?x3 ; \n"
					+ "          uri:R3 ?x4 . \n"
					+ "  ?x3   a uri:A3  . \n"
					+ "  ?x4   a uri:A4  . \n"
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
			qaHornSHIQ.getDataLog();
			//expected rewritten query: q0(X1) :- a1(X1), a(X1), a3(X1), a4(X1).
			assertEquals(2, qaHornSHIQ.getRewrittenQueries().size());
			
	}
		
		/**
		 * Test case 3
		 * @throws RecognitionException
		 */
		@Test
		public void testQueryRewriting3() throws RecognitionException {
			QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
			ClipperManager.getInstance().setVerboseLevel(2);
			ClipperManager.getInstance().setNamingStrategy(
					NamingStrategy.LowerCaseFragment);
			qaHornSHIQ.setDataLogName("AllTestCases/testQueryRewriting3.dl");
			qaHornSHIQ.setOntologyName("AllTestCases/testQueryRewriting3.owl");

			String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
					+ "SELECT ?x1 \n"
					+ "WHERE { \n"
					+ "  ?x1   a uri:A1  ; \n"
					+ "          uri:R2 ?x2 ; \n"
					+ "          uri:R1 <d> . \n"
					+ "   <d>    a uri:A4  ; \n"
					+ "          uri:R4 ?x3 . \n"
					+ "  ?x3   a uri:A3  . \n"
					+ "  ?x2   a uri:A2  ; \n"
					+ "          uri:R3 ?x3 . \n"
					
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
			qaHornSHIQ.getDataLog();
			//expect rewritten query: q0(X0) :- a("d"), a4("d"), r1(X0,"d"), a1(X0), r2(X0,"d"), a2("d").
			assertEquals(2, qaHornSHIQ.getRewrittenQueries().size());
		}
			/**
			 * Test case 4
			 * @throws RecognitionException
			 */
			@Test
			public void testQueryRewriting4() throws RecognitionException {
				QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
				ClipperManager.getInstance().setVerboseLevel(2);
				ClipperManager.getInstance().setNamingStrategy(
						NamingStrategy.LowerCaseFragment);
				qaHornSHIQ.setDataLogName("AllTestCases/testQueryRewriting3.dl");
				qaHornSHIQ.setOntologyName("AllTestCases/testQueryRewriting3.owl");

				String sparql = "PREFIX uri: <http://www.kr.tuwien.ac.at#> \n"
						+ "SELECT ?x1 \n"
						+ "WHERE { \n"
						+ "  ?x1   a uri:A1  ; \n"
						+ "          uri:R2 <d2> ; \n"
						+ "          uri:R1 <d> . \n"
						+ "   <d>    a uri:A4  ; \n"
						+ "          uri:R4 ?x3 . \n"
						+ "  ?x3   a uri:A3  . \n"
						+ "  <d2>   a uri:A2  ; \n"
						+ "          uri:R3 ?x3 . \n"
						
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
				qaHornSHIQ.getDataLog();
				//expect rewritten query: No rewritten query.
				assertEquals(1, qaHornSHIQ.getRewrittenQueries().size());
			
	}
}
