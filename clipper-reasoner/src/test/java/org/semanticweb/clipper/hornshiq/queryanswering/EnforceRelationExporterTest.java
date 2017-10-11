package org.semanticweb.clipper.hornshiq.queryanswering;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import java.io.File;

public class EnforceRelationExporterTest {

    @Test
    public void testExport() throws RecognitionException, OWLOntologyCreationException {


        String ontologyFile = "src/test/resources/TestData/lubm/full-lubm.owl";

        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ(false);

        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);

        OWLOntology owlOntology = qaHornSHIQ.exportSaturatedEnforceRelations("http://www.example.org/test/export");

        System.out.println(owlOntology);

        DLSyntaxObjectRenderer renderer = new DLSyntaxObjectRenderer();

        System.out.println(renderer.render(owlOntology));
    }

    @Test
    public void testExport2() throws RecognitionException, OWLOntologyCreationException {

        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ(false);

        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                this.getClass().getResourceAsStream("/UOBM/univ-bench-dl.owl"));
        qaHornSHIQ.addOntology(ontology);

        OWLOntology owlOntology = qaHornSHIQ.exportNormalizedAxiomsAndSaturatedEnforceRelations("http://www.example.org/test/export");

        System.out.println(owlOntology);

        DLSyntaxObjectRenderer renderer = new DLSyntaxObjectRenderer();

        System.out.println(renderer.render(owlOntology));
    }

}
