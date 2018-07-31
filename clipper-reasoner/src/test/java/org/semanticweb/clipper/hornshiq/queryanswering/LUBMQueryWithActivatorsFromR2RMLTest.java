package org.semanticweb.clipper.hornshiq.queryanswering;

import org.antlr.runtime.*;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.clipper.util.LUBMAnswerFileParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * TO run the test, put DLV into ~/bin/dlv
 *
 */
public class LUBMQueryWithActivatorsFromR2RMLTest {

	@Test
	public void query1() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query1.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>"+
				"SELECT ?x "+
				"		WHERE {"+
				"		  ?x a ub:GraduateStudent;"+
				"		       ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> ."+
				"		}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query1.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}


	@Test
	public void query2() throws RecognitionException, OWLOntologyCreationException {


		String tmpDatalogFile = "src/test/resources/TestData/lubm/query2.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = " PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>  \n"
				+ "SELECT ?x ?y ?z \n"
				+ "WHERE {"
				+ "	 ?x a ub:GraduateStudent . \n"
				+ "	 ?y a ub:University . \n"
				+ "	 ?z a ub:Department . \n"
				+ "	 ?x ub:memberOf ?z . \n "
				+ "	 ?z ub:subOrganizationOf ?y . \n"
				+ "	?x ub:undergraduateDegreeFrom ?y . \n}	";

		String answerFile = "src/test/resources/TestData/lubm/answers_query2.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query3() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query2.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X 	"
				+ "WHERE { \n"
				+ "?X a ub:Publication . \n "
				+ "?X ub:publicationAuthor <http://www.Department0.University0.edu/AssistantProfessor0>.} \n";
		String answerFile = "src/test/resources/TestData/lubm/answers_query3.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}

	@Test
	public void query4() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query4.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X ?Y1 ?Y2 ?Y3 	"
				+ "WHERE { \n"
				+ "?X a ub:Professor . \n "
				+ "?X ub:worksFor <http://www.Department0.University0.edu> . \n"
				+ "?X ub:name ?Y1 . \n "
				+ "?X ub:emailAddress ?Y2 .\n"
				+ "?X ub:telephone ?Y3 . \n" + "} \n";

		String answerFile = "src/test/resources/TestData/lubm/answers_query4.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}

	@Test
	public void query5() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query5.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X 	"
				+ "WHERE { \n"
				+ "?X a ub:Person . \n "
				+ "?X ub:memberOf <http://www.Department0.University0.edu> . \n"
				+ "} \n";
		String answerFile = "src/test/resources/TestData/lubm/answers_query5.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query6() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query6.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X 	"
				+ "WHERE { \n"
				+ "?X a ub:Student . \n "
				+ "} \n";

		String answerFile = "src/test/resources/TestData/lubm/answers_query6.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query7() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query7.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";
		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X ?Y	\n"
				+ "WHERE { \n"
				+ "	?X a ub:Student . \n"
				+ "	?Y a ub:Course . \n"
				+ "	?X ub:takesCourse ?Y . \n"
				+ "	<http://www.Department0.University0.edu/AssociateProfessor0> "
				+ "	  	ub:teacherOf  ?Y . \n}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query7.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}


	@Test
	public void query8() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query8.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X ?Y ?Z \n"
				+ "WHERE {\n"
				+ "	?X a ub:Student . \n"
				+ "	?Y a ub:Department . \n"
				+ "	?X ub:memberOf ?Y .\n"
				+ "	?Y ub:subOrganizationOf <http://www.University0.edu> .\n"
				+ "	?X ub:emailAddress ?Z .\n}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query8.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}

	@Test
	public void query9() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query9.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";


		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X ?Y ?Z \n"
				+ "WHERE {"
				+ "?X a ub:Student ."
				+ "?Y a ub:Faculty ."
				+ "?Z a ub:Course ."
				+ "?X ub:advisor ?Y ."
				+ "?Y ub:teacherOf ?Z ."
				+ "?X ub:takesCourse ?Z .}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query9.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query10() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query10.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X "
				+ "WHERE{"
				+ "?X a ub:Student ."
				+ "?X ub:takesCourse 	<http://www.Department0.University0.edu/GraduateCourse0> .} ";

		String answerFile = "src/test/resources/TestData/lubm/answers_query10.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}

	@Test
	public void query11() throws RecognitionException, OWLOntologyCreationException {
		String tmpDatalogFile = "src/test/resources/TestData/lubm/query11.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X \n"
				+ "WHERE{ \n"
				+ "?X a ub:ResearchGroup . \n"
				+ "?X ub:subOrganizationOf <http://www.University0.edu> .\n"
				+ "}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query11.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query12() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query12.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X ?Y \n"
				+ "WHERE{ \n"
				+ "?X a ub:Chair .\n"
				+ "?Y a ub:Department .\n"
				+ "?X ub:worksFor ?Y .\n"
				+ "?Y ub:subOrganizationOf <http://www.University0.edu> .\n}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query12.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);
	}

	@Test
	public void query13() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query13.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X \n"
				+ "WHERE 	{ \n"
				+ "?X a ub:Person .\n"
				+ "<http://www.University0.edu> ub:hasAlumnus ?X .\n}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query13.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void query14() throws RecognitionException, OWLOntologyCreationException {

		String tmpDatalogFile = "src/test/resources/TestData/lubm/query14.dl";
		String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#> \n"
				+ "SELECT ?X \n" + "WHERE {?X a ub:UndergraduateStudent .}";

		String answerFile = "src/test/resources/TestData/lubm/answers_query14.txt";

		testLUBMQuery(ontologyFile, sparql, answerFile, tmpDatalogFile);

	}

	@Test
	public void queryTest() throws RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ(false);
		ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
		qaHornSHIQ.setDatalogFileName("src/test/resources/TestData/lubm/queryTest.dl");
		qaHornSHIQ.setOntologyName("src/test/resources/TestData/lubm/lubm1.owl");

		String sparql = "PREFIX ub: <http://www.lehigh.edu/~zhp2/2004/0401/univ-bench.owl#>"+
			" SELECT ?X ?Z "+
			"WHERE {"+
			"?X a ub:Student ."+
			"?Y a ub:Department ."+
			"?X ub:memberOf ?Y ."+
			"?Z a ub:Professor ;"+
			"ub:worksFor  ?Y ."+
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
		qaHornSHIQ.generateABoxDatalog();
	//	qaHornSHIQ.getAnswers();

		// Set predictedAnswser = new HashSet<String>();
		// List<String> ans1= new List<String>();

		// System.out.println(qaHornSHIQ.getDecodedAnswers());

		System.out.println("reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ "  millisecond");
		System.out.println("reasoning time: "
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
			}

	private static void testLUBMQuery(String ontologyFile, String sparqlString, String answerFile, String tmpDatalogFile) throws OWLOntologyCreationException, RecognitionException {
		System.setProperty("entityExpansionLimit", "512000");
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQWithActivatorsFromMapping("src/test/resources/r2rml/lubm/univ-benchQL.ttl", "src/test/resources/r2rml/lubm/univ-benchQL.owl");
		//ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
		//ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
		qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
		qaHornSHIQ.setQueryRewriter("new");
		ClipperManager.getInstance().setVerboseLevel(2);

		qaHornSHIQ.setDatalogFileName(tmpDatalogFile);

		qaHornSHIQ.setOntologyName(ontologyFile);
		OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
				new File(ontologyFile));
		qaHornSHIQ.addOntology(ontology);


		System.out.println(sparqlString);

		CharStream stream = new ANTLRStringStream(sparqlString);
		SparqlLexer lexer = new SparqlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		CQ cq = parser.query();

		String queryString = cq.toString();
		System.out.println(queryString);
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.execQuery();


		System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
				+ "  millisecond");
		System.out.println("Query rewriting time: "
				+ qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");

		List<List<String>> actualAnswersList = qaHornSHIQ.getDecodedAnswers();

		Set<List<String>> actualAnswers = new HashSet<>(actualAnswersList);

		LUBMAnswerFileParser answerParser = new LUBMAnswerFileParser();

		Set<List<String>> expectedAnswers = answerParser.readAnswers(answerFile);

//		int n = 0;

//		for(List<String> actual : actualAnswers) {
//			boolean b = expectedAnswers.contains(actual);
//
//			if(!b){
//				n++;
//				System.out.println("missing" + actual);
//			}
//
//		}

//		System.out.println("expected : " + expectedAnswers.size());
//		System.out.println("actual   : " + n);


		assertEquals(expectedAnswers.size(), actualAnswers.size());

		assertEquals(expectedAnswers, actualAnswers);

	}


}
