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
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.io.File;

import static org.junit.Assert.assertEquals;


public class HSHIQQueryRewriterTest {
    /////////////////////////////////////////////////////////////////////
    // Test case for query rewriting.
    // See Master thesis: Figure 5.2 for more detail
    ////////////////////////////////////////////////////////////////////
    /**
     * Test case 1
     * @throws org.antlr.runtime.RecognitionException
     */
    @Test
    public void testBasicRewriting() throws RecognitionException, OWLOntologyCreationException {
        String tempDatalogFile = "AllTestCases/simpleRewriting.dl";

        String ontologyFile = "AllTestCases/simpleRewriting.owl";

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

        testRewriter(ontologyFile, sparql, 2, tempDatalogFile);
    }

    /**
     * Test case 2
     * @throws RecognitionException
     */
    @Test
    public void testQueryRewriting2() throws RecognitionException, OWLOntologyCreationException {

        String tempDatalogFile = "AllTestCases/testQueryRewriting2.dl";

        String ontologyFile = "AllTestCases/testQueryRewriting2.owl";

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

        //expected rewritten query: q0(X1) :- a1(X1), a(X1), a3(X1), a4(X1).
        testRewriter(ontologyFile, sparql, 2, tempDatalogFile);
    }

    /**
     * Test case 3
     * @throws RecognitionException
     */
    @Test
    public void testQueryRewriting3() throws RecognitionException, OWLOntologyCreationException {

        String tempDatalogFile = "AllTestCases/testQueryRewriting3.dl";

        String ontologyFile = "AllTestCases/testQueryRewriting3.owl";

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

        //expect rewritten query: q0(X0) :- a("d"), a4("d"), r1(X0,"d"), a1(X0), r2(X0,"d"), a2("d").
        testRewriter(ontologyFile, sparql, 2, tempDatalogFile);
    }
    /**
     * Test case 4
     * @throws RecognitionException
     */
    @Test
    public void testQueryRewriting4() throws RecognitionException, OWLOntologyCreationException {

        String tempDatalogFile = "AllTestCases/testQueryRewriting3.dl";

        String ontologyFile = "AllTestCases/testQueryRewriting3.owl";

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

        //expect rewritten query: q0(X0) :- a("d"), a4("d"), r1(X0,"d"), a1(X0), r2(X0,"d"), a2("d").
        testRewriter(ontologyFile, sparql, 1, tempDatalogFile);
    }
    private static void testRewriter(String ontologyFile, String sparqlString, int expected, String tmpDatalogFile) throws OWLOntologyCreationException, RecognitionException {
        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        //qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(1);

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

        assertEquals(expected, qaHornSHIQ.getRewrittenQueries().size());
    }
}
