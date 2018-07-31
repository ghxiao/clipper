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


public class SaturationWithActivatorsFromR2RMLTest {

    @Test
    public void testNPDSaturationWithActivators() throws Exception {
        String r2rmlFile = "src/test/resources/r2rml/npd/npd-v2-ql-postgres-ontop3.0.ttl";
        String ontologyFile = "src/test/resources/r2rml/npd/npd-v2-ql.owl";
        testSaturationWithActivators(r2rmlFile, ontologyFile);
    }

    @Test
    public void testNPDSaturationWithoutActivators() throws Exception {
        String ontologyFile = "src/test/resources/r2rml/npd/npd-v2-ql.owl";

        testSaturationWithoutActivators(ontologyFile);

    }

    @Test
    public void testSleggeSaturationWithActivators() throws Exception {
        String r2rmlFile = "src/test/resources/r2rml/slegge/slegge.r2rml";
        String ontologyFile = "src/test/resources/r2rml/slegge/subsurface-exploration.owl";
        testSaturationWithActivators(r2rmlFile, ontologyFile);
    }

    @Test
    public void testSleggeSaturationWithoutActivators() throws Exception {
        String ontologyFile = "src/test/resources/r2rml/slegge/subsurface-exploration.owl";

        testSaturationWithoutActivators(ontologyFile);
    }


    @Test
    public void testUOBMSaturationWithActivators() throws Exception {
        String r2rmlFile = "src/test/resources/r2rml/uobm/univ-bench-dl.ttl";
        String ontologyFile = "src/test/resources/r2rml/uobm/univ-bench-dl.owl";
        testSaturationWithActivators(r2rmlFile, ontologyFile);
    }

    @Test
    public void testUOBMSaturationWithoutActivators() throws Exception {
        String ontologyFile = "src/test/resources/r2rml/uobm/univ-bench-dl.owl";
        testSaturationWithoutActivators(ontologyFile);
    }

    private void testSaturationWithActivators(String r2rmlFile, String ontologyFile) throws Exception {
        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQWithActivatorsFromMapping(r2rmlFile, ontologyFile);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(2);


        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);
        qaHornSHIQ.preprocessOntologies();
        qaHornSHIQ.saturateTBox();

        System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
                + "  millisecond");
        System.out.println("Query rewriting time: "
                + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
    }

    private void testSaturationWithoutActivators(String ontologyFile) throws Exception {
        System.setProperty("entityExpansionLimit", "512000");

        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(2);


        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);
        qaHornSHIQ.preprocessOntologies();
        qaHornSHIQ.saturateTBox();

        System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
                + "  millisecond");
        System.out.println("Query rewriting time: "
                + qaHornSHIQ.getClipperReport().getQueryRewritingTime() + "  millisecond");
    }


}
