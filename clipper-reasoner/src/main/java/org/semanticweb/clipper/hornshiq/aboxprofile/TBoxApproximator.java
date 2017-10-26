package org.semanticweb.clipper.hornshiq.aboxprofile;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

import java.util.Set;

public class TBoxApproximator {

    private OWLOntology ontology;

    private OWLOntologyManager manager;
    private OWLDataFactory factory;
    private Set<OWLClass> classesInSignature;
    private Set<OWLObjectProperty> objectPropertiesInSignature;
    private IRI ontologyIRI;
    private OWLClass nothing;
    private OWLOntology approximatedOntology;

    public OWLOntology approximate(OWLOntology inputNormalizedOntology) {
        this.ontology = inputNormalizedOntology;
        this.manager = ontology.getOWLOntologyManager();
        this.factory = manager.getOWLDataFactory();

        classesInSignature = ontology.getClassesInSignature();
        objectPropertiesInSignature.addAll(ontology.getObjectPropertiesInSignature());

        OWLOntologyID ontologyID = inputNormalizedOntology.getOntologyID();
        ontologyIRI = ontologyID.getOntologyIRI();
        nothing = manager.getOWLDataFactory().getOWLNothing();
        try {
            approximatedOntology = manager.createOntology();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        for (OWLAxiom axiom : ontology.getAxioms()) {

            if (axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

                final OWLSubClassOfAxiom subClassOfAxiom = (OWLSubClassOfAxiom) axiom;

                if (subClassOfAxiom.getSuperClass().getClassExpressionType() ==
                        ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
                    manager.addAxiom(approximatedOntology, factory.getOWLSubClassOfAxiom(
                            factory.getOWLThing(),
                            subClassOfAxiom.getSuperClass()
                    ));
                } else {
                    manager.addAxiom(approximatedOntology, axiom);
                }
            } else {
                manager.addAxiom(approximatedOntology, axiom);
            }
        }

        return approximatedOntology;
    }


}
