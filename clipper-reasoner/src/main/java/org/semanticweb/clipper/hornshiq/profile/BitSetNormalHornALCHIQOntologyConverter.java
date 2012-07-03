package org.semanticweb.clipper.hornshiq.profile;


import gnu.trove.set.hash.TIntHashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.ontology.AndSubAtomAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubMinAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubSomeAxiom;
import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ConceptAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.DisjointObjectPropertiesAxiom;
import org.semanticweb.clipper.hornshiq.ontology.InversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ObjectPropertyAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.SomeSubAtomAxiom;
import org.semanticweb.clipper.hornshiq.ontology.SubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
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


public class BitSetNormalHornALCHIQOntologyConverter implements OWLAxiomVisitorEx<Object> {

	private NormalHornALCHIQOntology ontology;
	private ClipperManager km;

	public NormalHornALCHIQOntology convert(OWLOntology owlOntology) {
		ontology = new NormalHornALCHIQOntology();
		km = ClipperManager.getInstance();
		for (OWLAxiom ax : owlOntology.getLogicalAxioms()) {
			ax.accept(this);
		}
		return ontology;
	}

	@Override
	public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {
		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();

		// simple case: A subclass C
		if ((subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
			if (!subClass.isOWLNothing() && !superClass.isOWLThing()) {
				TIntHashSet left = new TIntHashSet();
				left.add(km.getOwlClassEncoder().getValueBySymbol((OWLClass) subClass));
				int right = (km.getOwlClassEncoder().getValueBySymbol((OWLClass) superClass));
				ontology.getAndSubAtomAxioms().add(new AndSubAtomAxiom(left, right));
			}
		}

		// A' subclass C
		else if (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {

			int right = (km.getOwlClassEncoder().getValueBySymbol((OWLClass) superClass));

			if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {

				OWLObjectIntersectionOf inter = (OWLObjectIntersectionOf) subClass;
				Set<OWLClassExpression> operands = inter.getOperands();

				TIntHashSet left = new TIntHashSet();

				boolean normalized = true;
				for (OWLClassExpression op : operands) {
					left.add(km.getOwlClassEncoder().getValueBySymbol((OWLClass) op));
				}
				ontology.getAndSubAtomAxioms().add(new AndSubAtomAxiom(left, right));

			}

			// exist(R,A') subclass B
			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) subClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int concept1 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					int role = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(some.getProperty());
					ontology.getSomeSubAtomAxioms().add(new SomeSubAtomAxiom(concept1, role, right));
				} else {
					throw new IllegalStateException();
				}
			}
			// or(A', B) subclass C
			// or(B, A') subclass C
			// else if (subClass .getClassExpressionType() ==
			// ClassExpressionType. OWLObjectUnionOf) {

			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {
				throw new IllegalStateException();

			}

		}
		// A subclass C'
		else if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {

			int left = km.getOwlClassEncoder().getValueBySymbol((OWLClass) subClass);

			// (A -> or(B, C)) ~>
			// TODO: need to check
			if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {

				throw new IllegalStateException();
			}

			// A subclass and(B, C)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {
				throw new IllegalStateException();

			}
			// A subclass exists(R, C') -> {A subclass some(R,D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) superClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(some.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubSomeAxioms().add(new AtomSubSomeAxiom(left, role, concept2));
				} else {
					throw new IllegalStateException();
				}
			}

			// A subclass all(R, C') -> {A subclass all(R, D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
				OWLObjectAllValuesFrom all = (OWLObjectAllValuesFrom) superClass;
				OWLClassExpression filler = all.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(all.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubAllAxioms().add(new AtomSubAllAxiom(left, role, concept2));
				} else {
					throw new IllegalStateException();
				}
			}

			// A subclass max(R, 1, B') -> {A subclass max(R, 1, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MAX_CARDINALITY) {
				OWLObjectMaxCardinality max = (OWLObjectMaxCardinality) superClass;
				OWLClassExpression filler = max.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(max.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubMaxOneAxioms().add(new AtomSubMaxOneAxiom(left, role, concept2));
				} else {
					throw new IllegalStateException();
				}
			}

			// A subclass min(R, n, B') -> {A subclass min(R, n, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MIN_CARDINALITY) {
				OWLObjectMinCardinality min = (OWLObjectMinCardinality) superClass;
				OWLClassExpression filler = min.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(min.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubMinAxioms().add(new AtomSubMinAxiom(left, role, concept2, min.getCardinality()));
				} else {
					throw new IllegalStateException();
				}
			}

			// A subclass not(B)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_COMPLEMENT_OF) {
				throw new IllegalStateException();
			}
		}

		return null;

	}

	@Override
	public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDisjointClassesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDataPropertyDomainAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLObjectPropertyDomainAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDifferentIndividualsAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {

		OWLObjectPropertyExpression[] properties = axiom.getProperties().toArray(new OWLObjectPropertyExpression[0]);

		for (int i = 0; i < properties.length; i++) {
			for (int j = i + 1; j < properties.length; j++) {
				OWLObjectPropertyExpression first = properties[i];
				OWLObjectPropertyExpression second = properties[j];
				int r = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(first);
				int s = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(second);
				ontology.getDisjAxioms().add(new DisjointObjectPropertiesAxiom(r, s));
			}
		}
		return null;

	}

	@Override
	public Object visit(OWLObjectPropertyRangeAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
		int p = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(axiom.getProperty());
		int s = km.getOwlIndividualEncoder().getValueBySymbol(axiom.getSubject());
		int o = km.getOwlIndividualEncoder().getValueBySymbol(axiom.getObject());
		ontology.getRoleAssertionAxioms().add(new ObjectPropertyAssertionAxiom(p, s, o));
		return null;
	}

	@Override
	public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
		OWLObjectPropertyExpression sub = axiom.getSubProperty();
		OWLObjectPropertyExpression sup = axiom.getSuperProperty();
		int r = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(sub);
		int s = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(sup);
		return ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(r, s));
	}

	@Override
	public Object visit(OWLDisjointUnionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDeclarationAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLAnnotationAssertionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDataPropertyRangeAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLClassAssertionAxiom axiom) {

		int cls = km.getOwlClassEncoder().getValueBySymbol((OWLClass) axiom.getClassExpression());
		int ind = km.getOwlIndividualEncoder().getValueBySymbol(axiom.getIndividual());
		ontology.getConceptAssertionAxioms().add(new ConceptAssertionAxiom(cls, ind));
		return null;

	}

	@Override
	public Object visit(OWLEquivalentClassesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDataPropertyAssertionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSubDataPropertyOfAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSameIndividualAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLSubPropertyChainOfAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLInverseObjectPropertiesAxiom axiom) {

		OWLObjectPropertyExpression first = axiom.getFirstProperty();
		OWLObjectPropertyExpression second = axiom.getSecondProperty();
		int r = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(first);
		int s = km.getOwlObjectPropertyExpressionEncoder().getValueBySymbol(second);
		ontology.getInversePropertyOfAxioms().add(new InversePropertyOfAxiom(r, s));
		return null;

		// throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLHasKeyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(OWLDatatypeDefinitionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object visit(SWRLRule rule) {
		throw new UnsupportedOperationException();

	}

}
