package org.semanticweb.clipper.hornshiq.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;

public class HornALCHIQNormalizer implements OWLAxiomVisitorEx<Object> {

	OWLOntology normalizedOnt;
	OWLOntology ontology;
	IRI ontologyIRI;
	int freshClassCounter = 0;
	OWLClass noThing;
	OWLOntologyManager manager;

	private OWLDataFactory factory;

	private Set<OWLClass> classesInSignature = new HashSet<OWLClass>();; // used
	// to
	// check
	// used
	// Class
	// Name
	// when
	// creating
	// a
	// new
	// fresh
	// concept
	// name
	private Set<OWLObjectProperty> objectPropertiesInSignature = new HashSet<OWLObjectProperty>();// used
	// when
	// creating
	// a
	// new
	// fresh
	// role
	// name

	private Set<OWLInverseObjectPropertiesAxiom> newInverseAxioms = new HashSet<OWLInverseObjectPropertiesAxiom>();

	@Override
	public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	public OWLOntology normalize(OWLOntology ont) {
		this.ontology = ont;
		this.manager = ontology.getOWLOntologyManager();
		this.factory = manager.getOWLDataFactory();

		classesInSignature = ontology.getClassesInSignature();
		objectPropertiesInSignature.addAll(ontology
				.getObjectPropertiesInSignature());

		OWLOntologyID ontologyID = ont.getOntologyID();
		ontologyIRI = ontologyID.getOntologyIRI();
		noThing = manager.getOWLDataFactory().getOWLNothing();
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

	private OWLClass createNewFreshConcept() {
		OWLClass newC = factory.getOWLClass(IRI
				.create("http://www.example.org/fresh#"
						+ "_eliminatedtransfresh_" + freshClassCounter));
		freshClassCounter++;
		return newC;
	}

	private boolean isTransitiveRole(OWLObjectPropertyExpression r) {
		if (r.isTransitive(ontology))
			return true;
		for (OWLObjectPropertyExpression invOfR: r.getInverses(ontology)){
			if (invOfR.isTransitive(ontology)) return true;
	//		System.out.println(invOfR);
		}
		return false;
	}

	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {

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

			if (isTransitiveRole(r)) {

				OWLClass bt = createNewFreshConcept();
				OWLClassExpression hasAllSubRoleFromBT = factory
						.getOWLObjectAllValuesFrom(r, bt);
				OWLSubClassOfAxiom newAxiom1 = factory.getOWLSubClassOfAxiom(a,
						hasAllSubRoleFromBT);
		//		System.out.println(newAxiom1);
				manager.addAxiom(normalizedOnt, newAxiom1);
				// allValuesFromAxioms.add(newAxiom1);

				OWLSubClassOfAxiom newAxiom2 = factory.getOWLSubClassOfAxiom(
						bt, hasAllSubRoleFromBT);
		//		System.out.println(newAxiom2);
				manager.addAxiom(normalizedOnt, newAxiom2);

				OWLSubClassOfAxiom newAxiom3 = factory.getOWLSubClassOfAxiom(
						bt, b);
		//		System.out.println(newAxiom3);
				manager.addAxiom(normalizedOnt, newAxiom3);
			}
			// If sub-role of r is transitive Then do the same thing as if r
			// is transitive
			else { /* not trans */
				manager.addAxiom(normalizedOnt, axiom);

				// get the set of all sub roles of r.
				Set<OWLSubObjectPropertyOfAxiom> subObjectPropertyAxiomSet = ontology
						.getObjectSubPropertyAxiomsForSuperProperty(r);
				if (subObjectPropertyAxiomSet.size() == 0)
					return null;
				for (OWLSubObjectPropertyOfAxiom roleAx : subObjectPropertyAxiomSet) {
					OWLObjectPropertyExpression subRole = roleAx.getSubProperty();
							
					// if the sub role of r is transitive
					if (isTransitiveRole(subRole)) {
						OWLClass bt = createNewFreshConcept();
						OWLClassExpression hasAllSubRoleFromBT = factory
								.getOWLObjectAllValuesFrom(subRole, bt);
						OWLSubClassOfAxiom newAxiom1 = factory
								.getOWLSubClassOfAxiom(a, hasAllSubRoleFromBT);
			//			System.out.println(newAxiom1);
						manager.addAxiom(normalizedOnt, newAxiom1);

						OWLSubClassOfAxiom newAxiom2 = factory
								.getOWLSubClassOfAxiom(bt, hasAllSubRoleFromBT);
			//			System.out.println(newAxiom2);
						manager.addAxiom(normalizedOnt, newAxiom2);

						OWLSubClassOfAxiom newAxiom3 = factory
								.getOWLSubClassOfAxiom(bt, b);
			//			System.out.println(newAxiom3);
						manager.addAxiom(normalizedOnt, newAxiom3);
					}
				}
			} /* trans */
		} else {
			manager.addAxiom(normalizedOnt, axiom);
		}

		return null;
	}

	@Override
	public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDisjointClassesAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDataPropertyDomainAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLObjectPropertyDomainAxiom axiom) {
		// TODO: Check
		manager.addAxiom(normalizedOnt, axiom);
		return null;
		// throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDifferentIndividualsAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLObjectPropertyRangeAxiom axiom) {
		// TODO Check
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLDisjointUnionAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public Object visit(OWLDeclarationAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;

	}

	@Override
	public Object visit(OWLAnnotationAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;

	}

	@Override
	public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLDataPropertyRangeAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLFunctionalDataPropertyAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLClassAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;

	}

	@Override
	public Object visit(OWLEquivalentClassesAxiom axiom) {

		return null;
	}

	@Override
	public Object visit(OWLDataPropertyAssertionAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLSubDataPropertyOfAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLSameIndividualAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLSubPropertyChainOfAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLHasKeyAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLDatatypeDefinitionAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(SWRLRule rule) {

		throw new IllegalArgumentException(rule.toString());

	}

}