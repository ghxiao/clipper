package org.semanticweb.clipper.hornshiq.queryanswering;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

import java.io.File;


public class SaturationWithActivatorsFromABoxTest {

    @Test
    public void testDolceDSaturationWithActivators() throws Exception {
        String ontologyFile = "src/test/resources/ontologies/DOLCE/00014-DOLCE.owl";
        testSaturationWithActivators(ontologyFile);
    }

    @Test
    public void testDolceSaturationWithoutActivators() throws Exception {
        String ontologyFile = "src/test/resources/ontologies/DOLCE/00014-DOLCE.owl";
        testSaturationWithoutActivators(ontologyFile);

    }

    @Test
    public void testFlySaturationWithActivators() throws Exception {
        String ontologyFile = "src/test/resources/ontologies/FlyOntology/fly_anatomy.owl";
        testSaturationWithActivators(ontologyFile);
    }

    @Test
    public void testFlySaturationWithoutActivators() throws Exception {
        String ontologyFile = "src/test/resources/ontologies/FlyOntology/fly_anatomy.owl";
        testSaturationWithoutActivators(ontologyFile);
    }


    private void testSaturationWithActivators(String ontologyFile) throws Exception {
        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQWithActivatorsFromABox();
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(4);


        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);
        qaHornSHIQ.preprocessOntologies();
        qaHornSHIQ.saturateTBox();

        System.out.println("TBox reasoning time: " + qaHornSHIQ.getClipperReport().getReasoningTime()
                + "  millisecond");
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

    }


}
