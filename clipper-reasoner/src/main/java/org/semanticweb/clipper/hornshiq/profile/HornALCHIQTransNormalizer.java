package org.semanticweb.clipper.hornshiq.profile;

import java.util.ArrayList;
import java.util.*;
import java.util.HashSet;

import java.util.Random;
import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
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

public class HornALCHIQTransNormalizer implements OWLAxiomVisitorEx<Object> {

	OWLOntology normalizedOnt;
	OWLOntology ontology;
	IRI ontologyIRI;
	int freshConceptCounter = 0;
	OWLClass noThing;
	OWLOntologyManager manager;
	private OWLDataFactory factory;

	private OWLClass getFreshClass() {
		freshConceptCounter++;
		return factory.getOWLClass(IRI.create("http://www.example.org/fresh#"
				+ "eliminateMinCard_fresh" + freshConceptCounter));
	}

	/*
	 * Given a role r, return inverse role of r or create a new one if inverse
	 * of r does not exists.
	 */
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

	@Override
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	private Set<OWLObjectPropertyExpression> getInverseRole(
			OWLObjectPropertyExpression r) {
		Set<OWLObjectPropertyExpression> inverseRoles = new HashSet<OWLObjectPropertyExpression>();
		if (r.isAnonymous()) {
			inverseRoles.add(r.getNamedProperty());
			return inverseRoles;
		} else if (!r.getInverses(ontology).isEmpty()) {
			for (OWLObjectPropertyExpression rBar : r
					.getInverses(ontology))
				inverseRoles.add(rBar);
			return inverseRoles;
		} else {
			inverseRoles.add(r.getInverseProperty());
			return inverseRoles;
		}

	}

	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {

		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();
		// some r a SubClassOf b
		// should be changed to:
		// a SubClassOf only rBar B
		// inverse(r,rBar)
		if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM
				&& superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
			// System.out.println(axiom);

			OWLObjectSomeValuesFrom objectSomeExpress = (OWLObjectSomeValuesFrom) subClass;
			OWLClassExpression filler = objectSomeExpress.getFiller();
			OWLClass a = filler.asOWLClass();
			OWLObjectPropertyExpression r = objectSomeExpress.getProperty();
			OWLClass b = superClass.asOWLClass();

			for (OWLObjectPropertyExpression rBar : getInverseRole(r)) {
		//		System.out.println("rBar:"+ rBar);
				OWLClassExpression hasAllRbarB = factory
						.getOWLObjectAllValuesFrom(rBar, b);
				OWLSubClassOfAxiom newAxiom = factory.getOWLSubClassOfAxiom(a,
						hasAllRbarB);
				// System.out.println(newAxiom);
				manager.addAxiom(normalizedOnt, newAxiom);
				if (r.isTransitive(ontology)) {
					factory.getOWLTransitiveObjectPropertyAxiom(rBar).accept(
							this);
				}

				// If SubRole(r2,r) Then SubRole(r2Bar,rBar)
				Set<OWLObjectPropertyExpression> subRoleExpressions = r
						.getSubProperties(ontology);
				for (OWLObjectPropertyExpression r2 : subRoleExpressions) {
					for (OWLObjectPropertyExpression r2Bar : getInverseRole(r2)) {
						// if r2 is transitive then r2Bar is transitive
						if (r2.isTransitive(ontology)) {
							factory.getOWLTransitiveObjectPropertyAxiom(r2Bar)
									.accept(this);
						}
						// add SubRole(r2Bar,rBar)
						factory.getOWLSubObjectPropertyOfAxiom(r2Bar, rBar)
								.accept(this);
					}
				}

				// If SubRole(r,r1) Then SubRole(rBar,r1Bar)
				Set<OWLObjectPropertyExpression> superRoleExpressions = r
						.getSuperProperties(ontology);
				for (OWLObjectPropertyExpression r1 : superRoleExpressions) {
					for (OWLObjectPropertyExpression r1Bar : getInverseRole(r1)) {
						// if r is transitive then rBar is transitive
						if (r1.isTransitive(ontology)) {
							factory.getOWLTransitiveObjectPropertyAxiom(r1Bar)
									.accept(this);
						}
						// add SubRole(r2Bar,rBar)
						factory.getOWLSubObjectPropertyOfAxiom(rBar, r1Bar)
								.accept(this);
					}
				}
			}
		} else

		// a SubclassOf Min n(r,c)
		// should be changed to:
		// a SubClassOf exist(r,bi)
		// bi SubClassOf c
		// and(bi,bj) SubClassOf Bottom
		if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS
				&& superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MIN_CARDINALITY) {
			OWLClass a = subClass.asOWLClass();
			OWLObjectMinCardinality minCard = (OWLObjectMinCardinality) superClass;
			int n = minCard.getCardinality();
			OWLObjectPropertyExpression r = minCard.getProperty();
			OWLClass c = minCard.getFiller().asOWLClass();

			List<OWLClass> freshClassNameArray = new ArrayList<OWLClass>();
			for (int i = 1; i <= n; i++) {
				OWLClass b = getFreshClass();
				freshClassNameArray.add(b);
				// add axiom: A \sqsubseteq exist r.Bi
				OWLClassExpression rSomeb = factory.getOWLObjectSomeValuesFrom(
						r, b);
				OWLSubClassOfAxiom aSubClassOfRSomeb = factory
						.getOWLSubClassOfAxiom(a, rSomeb);
				// manager.addAxiom(normalizedOnt, hasSomeRBAxiom);
				aSubClassOfRSomeb.accept(this);
				// add axiom: Bi \sqsubseteq C
				OWLSubClassOfAxiom bSubClassOfC = factory
						.getOWLSubClassOfAxiom(b, c);
				manager.addAxiom(normalizedOnt, bSubClassOfC);
			}

			// add axiom : Bi \cap Bj \sqsubseteq Bottom
			int numberOfFreshName = freshClassNameArray.size();
			for (int i = 0; i < numberOfFreshName; i++)
				for (int j = i + 1; j < numberOfFreshName; j++) {
					OWLClassExpression biANDbj = factory
							.getOWLObjectIntersectionOf(
									freshClassNameArray.get(i),
									freshClassNameArray.get(j));
					OWLSubClassOfAxiom biANDbjSubClassOfNothing = factory
							.getOWLSubClassOfAxiom(biANDbj, noThing);
					manager.addAxiom(normalizedOnt, biANDbjSubClassOfNothing);

					// System.out.println(biANDbjSubClassOfNothing);
				}
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

		Set<OWLClassExpression> classes = axiom.getClassExpressions();

		for (OWLClassExpression cls1 : classes) {
			for (OWLClassExpression cls2 : classes) {
				if (cls1.equals(cls2))
					continue;
				factory.getOWLSubClassOfAxiom(cls1, cls2).accept(this);
			}
		}

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