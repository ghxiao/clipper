package org.semanticweb.clipper.hornshiq.queryanswering;

import org.antlr.runtime.*;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.sparql.SparqlLexer;
import org.semanticweb.clipper.hornshiq.sparql.SparqlParser;
import org.semanticweb.clipper.sparql.SparqlToCQConverter;
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
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.q1.dl";

        //language=SPARQL
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "\n" +
                "SELECT  ?x\n" +
                "WHERE\n" +
                "  { \n" +
                "    ?x a :ExplorationWellbore .\n" +
                "    ?x :isAssociatedTo ?y .\n" +
                //"    ?y a :ChronoStratigraphicColumn .\n" +
               "    ?y a :StratigraphicColumn .\n" +
                "  }";

        testQueryRewriting(ontologyFile, sparql, outputDatalogFile);
    }

    @Test
    public void query2() throws RecognitionException, OWLOntologyCreationException {

        String ontologyFile = "src/test/resources/ontologies/slegge/subsurface-exploration-trans.owl";
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.q2.dl";

        //language=TEXT
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "\n" +
                "SELECT  ?x\n" +
                "WHERE\n" +
                "  { \n" +
                "    ?x a :DevelopmentWellbore .\n" +
                "    ?x :isAssociatedTo ?y1 .\n" +
                "    ?x :isAssociatedTo ?y2 .\n" +
                //"    ?y1 a :ChronoStratigraphicColumn .\n" +
                "    ?y1 a :StratigraphicColumn .\n" +
                //"    ?y2 a :LithoStratigraphicColumn .\n" +
                "    ?y2 a :Reservoir .\n" +
                "  }";

        testQueryRewriting(ontologyFile, sparql, outputDatalogFile);
    }

    @Test
    public void query3() throws RecognitionException, OWLOntologyCreationException {

        String ontologyFile = "src/test/resources/ontologies/slegge/subsurface-exploration-trans.owl";
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.q3.dl";

        //language=TEXT
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "\n" +
                "SELECT  ?x1 ?x2\n" +
                "WHERE\n" +
                "  { \n" +
                "    ?x1 a :WellboreInterval .\n" +
                "    ?x2 a :WellboreInterval .\n" +
                "    ?x1 :overlapsWellboreInterval ?x2 .\n" +
                "    ?x1 :isAssociatedTo ?y .\n" +
                "    ?x2 :isAssociatedTo ?y .\n" +
                "    ?y a :StratigraphicColumn .    \n" +
                "  }";

        testQueryRewriting(ontologyFile, sparql, outputDatalogFile);
    }

    @Test
    public void query4() throws RecognitionException, OWLOntologyCreationException {

        String ontologyFile = "src/test/resources/ontologies/slegge/subsurface-exploration-trans.owl";
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.q4.dl";

        //language=TEXT
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "SELECT  ?x\n" +
                "WHERE\n" +
                "  { \n" +
                "    ?x a :Well .\n" +
                "    ?x :encompasses ?y1 .\n" +
                "    ?y1 a :ExplorationWellbore .\n" +
                "    ?x :encompasses ?y2 .\n" +
                "    ?y2 a :DevelopmentWellbore .    \n" +
                "    ?y1 :isAssociatedTo ?z .\n" +
                "    ?y2 :isAssociatedTo ?z .   \n" +
                "  }";

        testQueryRewriting(ontologyFile, sparql, outputDatalogFile);
    }

    @Test
    public void query5() throws RecognitionException, OWLOntologyCreationException {

        String ontologyFile = "src/test/resources/ontologies/slegge/subsurface-exploration-trans.owl";
        String outputDatalogFile = "src/test/resources/ontologies/slegge/subsurface-exploration.owl.q5.dl";

        //language=TEXT
        String sparql = "PREFIX  :     <http://slegger.gitlab.io/slegge-obda/ontology/subsurface-exploration#>\n" +
                "\n" +
                "SELECT  ?x\n" +
                "WHERE\n" +
                "  { \n" +
                "    ?y1 a :ExplorationWellbore .\n" +
                "    ?y2 a :DevelopmentWellbore .    \n" +
                "    ?y1 :encompasses ?x .\n" +
                "    ?y2 :encompasses ?x .   \n" +
                "    ?x a :Reservoir .   \n" +
                "    ?x :isAssociatedTo ?z .   \n" +
                "    ?z a :StratigraphicColumn .   \n" +
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

        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);

        System.out.println(sparqlString);

//        CharStream stream = new ANTLRStringStream(sparqlString);
//        SparqlLexer lexer = new SparqlLexer(stream);
//        TokenStream tokenStream = new CommonTokenStream(lexer);
//        SparqlParser parser = new SparqlParser(tokenStream);
//        CQ cq = parser.query();

        Query query = QueryFactory.create(sparqlString);
        CQ cq = new SparqlToCQConverter(ontology).compileQuery(query);


//        String queryString = cq.toString();
//        System.out.println(queryString);
        qaHornSHIQ.setQuery(cq);

        //qaHornSHIQ.execQuery();
        qaHornSHIQ.generateDatalog();
//		qaHornSHIQ.getQueriesAndRelatedRulesDataLog();
    }


}
