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


        String ontologyFile = "TestData/lubm/full-lubm.owl";

        System.setProperty("entityExpansionLimit", "512000");
        QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.INT_ENCODING);
        //ClipperManager.getInstance().setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);
        qaHornSHIQ.setOntologyName(ontologyFile);
        OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
                new File(ontologyFile));
        qaHornSHIQ.addOntology(ontology);
        qaHornSHIQ.setQueryRewriter("new");
        ClipperManager.getInstance().setVerboseLevel(1);

        OWLOntology owlOntology = qaHornSHIQ.exportSaturatedEnforceRelations("http://www.example.org/test/export");

        System.out.println(owlOntology);

        DLSyntaxObjectRenderer renderer = new DLSyntaxObjectRenderer();

        System.out.println(renderer.render(owlOntology));

    }

}
