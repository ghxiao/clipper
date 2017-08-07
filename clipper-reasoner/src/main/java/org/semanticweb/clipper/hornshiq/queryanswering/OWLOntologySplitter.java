package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.collect.Sets;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;

import java.util.Set;

public class OWLOntologySplitter {

    public static OWLOntology extractRLTBox(OWLOntology ontology) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

        final Set<OWLAxiom> tBoxAxioms = ontology.getTBoxAxioms(false);
        final Set<OWLAxiom> rBoxAxioms = ontology.getRBoxAxioms(false);
        final Set<OWLDeclarationAxiom> declarationAxioms = ontology.getAxioms(AxiomType.DECLARATION);

        final OWLOntology tbox;
        try {
            tbox = manager.createOntology();
            manager.addAxioms(tbox, declarationAxioms);
            manager.addAxioms(tbox, tBoxAxioms);
            manager.addAxioms(tbox, rBoxAxioms);

        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }

        OWLProfile profile = new OWL2RLProfile();

        OWLProfileReport report = profile.checkOntology(tbox);

        // TODO: proper clean up. For the moment we rely on RDFox.

//        for (OWLProfileViolation violation : report.getViolations()) {
//            OWLAxiom axiom = violation.getAxiom();
//            if (axiom != null) {
//                manager.removeAxiom(tbox, axiom);
//            }
//        }

        return tbox;
    }

    public static OWLOntology extractABox(OWLOntology ontology) {
        Set<OWLDeclarationAxiom> declarationAxioms = ontology.getAxioms(AxiomType.DECLARATION);

        final Set<OWLAxiom> aBoxAxioms = ontology.getABoxAxioms(false);

        final OWLOntology aBox;
        try {
            aBox = OWLManager.createOWLOntologyManager().createOntology(Sets.union(declarationAxioms, aBoxAxioms));
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
        return aBox;
    }
}
