package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

import java.io.StringWriter;
import java.util.Set;

public class HornALCHIQNormalizer implements OWLAxiomVisitor {

    OWLOntology normalizedOnt;
    OWLOntology ontology;
    IRI ontologyIRI;
    int freshClassCounter = 0;
    OWLClass noThing;
    OWLOntologyManager manager;

    private OWLDataFactory factory;

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    public OWLOntology normalize(OWLOntology ont) {
        this.ontology = ont;
        this.manager = ontology.getOWLOntologyManager();
        this.factory = manager.getOWLDataFactory();


        OWLOntologyID ontologyID = ont.getOntologyID();
        ontologyIRI = ontologyID.getOntologyIRI();

        try {
            normalizedOnt = manager.createOntology();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        for (OWLAxiom axiom : ontology.getAxioms()) {
            axiom.accept(this);
        }

        return normalizedOnt;
    }

    private OWLClass createNewFreshConcept(OWLClass b, OWLObjectPropertyExpression r) {

        String bName = manchesterString(b);

        String rName = manchesterString(r);

        IRI iri;

        String freshConceptPrefix = "http://www.example.org/elem_trans_fresh#";

        iri = IRI.create(freshConceptPrefix + rName + "_" + bName);

        return factory.getOWLClass(iri);

    }

    private String manchesterString(OWLObject owlObject) {
        StringWriter writer = new StringWriter();

        ManchesterOWLSyntaxObjectRenderer renderer = new ManchesterOWLSyntaxObjectRenderer(writer, new SimpleShortFormProvider());

        owlObject.accept(renderer);

        String s = writer.toString();
        return s.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\s+", "_");
    }

    private boolean isTransitiveRole(OWLObjectPropertyExpression r) {
        if (r.isTransitive(ontology))
            return true;
        for (OWLObjectPropertyExpression invOfR : r.getInverses(ontology)) {
            if (invOfR.isTransitive(ontology))
                return true;
            // System.out.println(invOfR);
        }
        return false;
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {

        OWLClassExpression subClass = axiom.getSubClass();
        OWLClassExpression superClass = axiom.getSuperClass();

        // elimination of Transitivity. Transitivity only interact with axiom of
        // the form:
        // a SubClasOf all(r,b)
        // Where r is transitive
        if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS
                && superClass.getClassExpressionType() == ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
            OWLObjectAllValuesFrom allValueFrom = (OWLObjectAllValuesFrom) superClass;
            OWLClass a = subClass.asOWLClass();
            OWLObjectPropertyExpression r = allValueFrom.getProperty();

            OWLClass b = allValueFrom.getFiller().asOWLClass();


            // FIXME when trans(r) holds, we still need to consider the sub roles
            if (isTransitiveRole(r)) {

                simulateTrans(a, r, b);
            }
            // If sub-role of r is transitive Then do the same thing as if r
            // is transitive
            else { /* not trans */
                manager.addAxiom(normalizedOnt, axiom);

                // get the set of all sub roles of r.
                Set<OWLSubObjectPropertyOfAxiom> subObjectPropertyAxiomSet = ontology
                        .getObjectSubPropertyAxiomsForSuperProperty(r);

                for (OWLSubObjectPropertyOfAxiom roleAx : subObjectPropertyAxiomSet) {
                    OWLObjectPropertyExpression subRole = roleAx.getSubProperty();

                    // if the sub role of r is transitive
                    if (isTransitiveRole(subRole)) {
                        simulateTrans(a, subRole, b);
                    }
                }
            } /* trans */
        } else {
            manager.addAxiom(normalizedOnt, axiom);
        }
    }

    /**
     *  (i) adding for every A ⊑ ∀s.B ∈ T and every transitive role r with r ⊑∗T s,
     *  the axioms A ⊑ ∀r.Br, Br ⊑ ∀r.Br and Br ⊑ B, where Br is a fresh concept name
     */
    private void simulateTrans(OWLClass a, OWLObjectPropertyExpression r, OWLClass b) {
        OWLClass bt = createNewFreshConcept(b, r);
        OWLClassExpression hasAllSubRoleFromBT = factory.getOWLObjectAllValuesFrom(r, bt);
        OWLSubClassOfAxiom newAxiom1 = factory.getOWLSubClassOfAxiom(a, hasAllSubRoleFromBT);
        // System.out.println(newAxiom1);
        manager.addAxiom(normalizedOnt, newAxiom1);
        // allValuesFromAxioms.add(newAxiom1);

        OWLSubClassOfAxiom newAxiom2 = factory.getOWLSubClassOfAxiom(bt, hasAllSubRoleFromBT);
        // System.out.println(newAxiom2);
        manager.addAxiom(normalizedOnt, newAxiom2);

        OWLSubClassOfAxiom newAxiom3 = factory.getOWLSubClassOfAxiom(bt, b);
        // System.out.println(newAxiom3);
        manager.addAxiom(normalizedOnt, newAxiom3);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // Should not happen after the first normalization
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        // Should not happen after the first normalization
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        // Should not happen after the first normalization
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        // Should not happen after the first normalization
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        // Should not happen after the first normalization
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        throw new IllegalArgumentException(axiom.toString());
    }

    @Override
    public void visit(SWRLRule rule) {
        throw new IllegalArgumentException(rule.toString());
    }

}