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
 */
public class SleggeQueryTest {

    @Test
    public void query1() throws RecognitionException, OWLOntologyCreationException {

        String ontologyFile = "src/test/resources/ontologies/slegge/subsurface-exploration-trans.owl";
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.query.dl";

        //language=TEXT
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "\n" +
                "SELECT  *\n" +
                "WHERE\n" +
                "  { \n" +
                "  ?x     :isAncestorUnitOf ?y .\n" +
                "  }";


        testQueryRewriting(ontologyFile, sparql, outputDatalogFile);
    }


    private static void testQueryRewriting(String ontologyFile, String sparqlString, String outputDatalogFile) throws OWLOntologyCreationException, RecognitionException {
        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ(false);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setDatalogFileName(outputDatalogFile);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(2);


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

        //qaHornSHIQ.execQuery();
        qaHornSHIQ.generateDatalog();
//		qaHornSHIQ.getQueriesAndRelatedRulesDataLog();
    }


}
