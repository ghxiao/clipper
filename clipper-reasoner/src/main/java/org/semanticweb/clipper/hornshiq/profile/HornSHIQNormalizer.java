package org.semanticweb.clipper.hornshiq.profile;

import java.util.HashSet;
import java.util.Iterator;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
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

public class HornSHIQNormalizer implements OWLAxiomVisitorEx<Object> {

	OWLOntology normalizedOnt;

	int freshClassCounter = 0;

	OWLOntologyManager manager;

	private OWLDataFactory factory;

	HornSHIQProfile profile;

	private Sub0_ClassExpressionChecker sub0;

	private Sub1_ClassExpressionChecker sub1;

	private Super0_ClassExpressionChecker super0;

	private Super1_ClassExpressionChecker super1;

	public HornSHIQNormalizer() {

	}

	public OWLOntology normalize(OWLOntology ontology) {
		this.profile = new HornSHIQProfile();
		this.manager = ontology.getOWLOntologyManager();
		this.factory = manager.getOWLDataFactory();
		profile.setPropertyManager(new OWLObjectPropertyManager(manager, ontology));

		sub0 = profile.getSub0();
		sub1 = profile.getSub1();
		super0 = profile.getSuper0();
		super1 = profile.getSuper1();

		try {
			normalizedOnt = manager.createOntology();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		for (OWLAxiom axiom : ontology.getAxioms()) {
			axiom.getNNF().accept(this);
		}

		return normalizedOnt;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.owlapi.model.OWLAxiomVisitorEx#visit(org.semanticweb.
	 * owlapi.model.OWLSubClassOfAxiom)
	 */
	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {

		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();

//		// (C0- -> C1+) ~> (~C1+ -> ~C0-)
//		if (!(subClass.accept(sub0) && superClass.accept(super1))) {
//			System.out.println(axiom);
//
//			OWLClassExpression newSub = factory.getOWLObjectComplementOf(
//					superClass).getNNF();
//			OWLClassExpression newSuper = factory.getOWLObjectComplementOf(
//					subClass).getNNF();
//
//			
//			
//			if (!(newSub.accept(sub0) && newSuper.accept(super1))) {
//				throw new IllegalArgumentException(axiom
//						+ "is not in Horn fragment");
//			}
//
//			factory.getOWLSubClassOfAxiom(newSub, newSuper).accept(this);
//
//		}
		if (!((axiom.getSubClass().accept(sub0) //
				&& axiom.getSuperClass().accept(
						super1)) //
				|| (axiom.getSubClass().accept(sub1) //
				&& axiom.getSuperClass().accept(
						super0)))) {
//			throw new IllegalArgumentException(axiom
//					+ "is not in Horn fragment");
			System.err.println("Warning: " + axiom + "is not in Horn fragment");
		}
		// simple case: A subclass C
		else if ((subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
			if (!subClass.isOWLNothing() && !superClass.isOWLThing()) {
				manager.addAxiom(normalizedOnt, axiom);
			}
		}
		// A' subclass C'
		else if ((subClass.getClassExpressionType() != ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() != ClassExpressionType.OWL_CLASS)) {
			OWLClass freshClass = getFreshClass();
			factory.getOWLSubClassOfAxiom(subClass, freshClass).accept(this);
			factory.getOWLSubClassOfAxiom(freshClass, superClass).accept(this);
		}
		// A' subclass C
		else if (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
			// and(A', B) subclass C
			// and(B, A') subclass C
			// -> {A' subclass D, and(D,C) subclass B}
			if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {
				OWLObjectIntersectionOf inter = (OWLObjectIntersectionOf) subClass;
				Set<OWLClassExpression> operands = inter.getOperands();
				Set<OWLClassExpression> newOps = new HashSet<OWLClassExpression>();
				boolean normalized = true;
				for (OWLClassExpression op : operands) {
					if (!(op.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
						normalized = false;
						break;
					}
				}
				if (normalized) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					for (OWLClassExpression op : operands) {
						if (!(op.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
							OWLClass freshClass = getFreshClass();
							newOps.add(freshClass);
							// manager.addAxiom(normalizedOnt, factory
							// .getOWLSubClassOfAxiom(op, freshClass));
							//
							factory.getOWLSubClassOfAxiom(op, freshClass)
									.accept(this);
						} else {
							newOps.add(op);
						}
					}

					manager.addAxiom(normalizedOnt, factory
							.getOWLSubClassOfAxiom(
									factory.getOWLObjectIntersectionOf(newOps),
									superClass));
				}

			}

			// exist(R,A') subclass B
			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) subClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					//OWLClass freshClass = getFreshClass("some_");
					OWLClass freshClass = getFreshClass();
					factory.getOWLSubClassOfAxiom(
							factory.getOWLObjectSomeValuesFrom(
									some.getProperty(), freshClass), superClass) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(filler, freshClass).accept(
							this);
				}
			}
			// or(A', B) subclass C
			// or(B, A') subclass C
			// else if (subClass .getClassExpressionType() ==
			// ClassExpressionType. OWLObjectUnionOf) {

			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {
				OWLObjectUnionOf union = (OWLObjectUnionOf) subClass;
				Set<OWLClassExpression> operands = union.getOperands();
				Set<OWLClassExpression> newOps = new HashSet<OWLClassExpression>();
				boolean normalized = true;
				for (OWLClassExpression op : operands) {
					if (!(op.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
						normalized = false;
						break;
					}
				}
				if (normalized) {
					for (OWLClassExpression op : operands) {
						manager.addAxiom(normalizedOnt,
								factory.getOWLSubClassOfAxiom(op, superClass));
					}
				} else {
					for (OWLClassExpression op : operands) {
						if (!(op.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
							OWLClass freshClass = getFreshClass();
							newOps.add(freshClass);
							manager.addAxiom(normalizedOnt, factory
									.getOWLSubClassOfAxiom(op, freshClass));
						} else {
							newOps.add(op);
						}
					}

					factory.getOWLSubClassOfAxiom(
							factory.getOWLObjectUnionOf(newOps), superClass)
							.accept(this);
				}

			}

		}
		// A subclass C'
		else if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
			// (A -> or(B, C)) ~>
			// TODO: need to check
			if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {

				OWLObjectUnionOf union = (OWLObjectUnionOf) superClass;
				Set<OWLClassExpression> operands = union.getOperands();
				Iterator<OWLClassExpression> iterator = operands.iterator();
				OWLClass fresh = getFreshClass();
				OWLClassExpression first = iterator.next();

				OWLClassExpression rest;

				if (operands.size() == 2) {
					rest = iterator.next();
				} else /* operands.size() > 2 */{
					HashSet<OWLClassExpression> newOps = new HashSet<OWLClassExpression>(
							operands);
					newOps.remove(first);
					rest = factory.getOWLObjectUnionOf(newOps);
				}

				factory.getOWLSubClassOfAxiom(subClass, fresh).accept(this);
				if (first.accept(super0)) {
					factory.getOWLSubClassOfAxiom(
							factory.getOWLObjectIntersectionOf(fresh,//
									factory.getOWLObjectComplementOf(first)
											.getNNF()), rest).accept(this);
				} else {
					factory.getOWLSubClassOfAxiom(
							factory.getOWLObjectIntersectionOf(fresh,//
									factory.getOWLObjectComplementOf(rest)
											.getNNF()), fresh)//
							.accept(this);
				}
			}

			// A subclass and(B, C)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {
				OWLObjectIntersectionOf and = (OWLObjectIntersectionOf) superClass;
				for (OWLClassExpression op : and.getOperands()) {
					factory.getOWLSubClassOfAxiom(subClass, op).accept(this);
				}
			}
			// A subclass exists(R, C') -> {A subclass some(R,D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) superClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass("SOME_");
					factory.getOWLSubClassOfAxiom(
							subClass,
							factory.getOWLObjectSomeValuesFrom(
									some.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(
							this);
				}
			}

			// A subclass all(R, C') -> {A subclass all(R, D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
				OWLObjectAllValuesFrom all = (OWLObjectAllValuesFrom) superClass;
				OWLClassExpression filler = all.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass();
					factory.getOWLSubClassOfAxiom(
							subClass,
							factory.getOWLObjectAllValuesFrom(
									all.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(
							this);
				}
			}

			// A subclass max(R, 1, B') -> {A subclass max(R, 1, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MAX_CARDINALITY) {
				OWLObjectMaxCardinality max = (OWLObjectMaxCardinality) superClass;
				OWLClassExpression filler = max.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass();
					factory.getOWLSubClassOfAxiom(
							subClass,
							factory.getOWLObjectMaxCardinality(
									max.getCardinality(), max.getProperty(),
									freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(
							this);
				}
			}

			// A subclass min(R, n, B') -> {A subclass min(R, n, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MIN_CARDINALITY) {
				OWLObjectMinCardinality min = (OWLObjectMinCardinality) superClass;
				OWLClassExpression filler = min.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass();
					factory.getOWLSubClassOfAxiom(
							subClass,
							factory.getOWLObjectMinCardinality(
									min.getCardinality(), min.getProperty(),
									freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(
							this);
				}
			}

			// A subclass not(B)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_COMPLEMENT_OF) {
				OWLObjectComplementOf c = (OWLObjectComplementOf) superClass;
				OWLClassExpression operand = c.getOperand();
				OWLClass freshClass = getFreshClass();
				factory.getOWLSubClassOfAxiom(subClass, freshClass)
						.accept(this);
				factory.getOWLSubClassOfAxiom(
						factory.getOWLObjectIntersectionOf(freshClass, operand),
						factory.getOWLNothing()) //
						.accept(this);
			}
		}

		return null;

	}

	private OWLClass getFreshClass() {
		freshClassCounter++;
		return factory.getOWLClass(IRI.create("http://www.example.org/fresh#" + "fresh" + freshClassCounter));
	}

	private OWLClass getFreshClass(String suffix) {
		freshClassCounter++;
		return factory.getOWLClass(IRI.create("http://www.example.org/fresh#" + suffix + "fresh" + freshClassCounter));
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
		for (OWLClassExpression cls1 : axiom.getClassExpressions()) {
			for (OWLClassExpression cls2 : axiom.getClassExpressions()) {
				if (!cls1.equals(cls2)) {
					factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(cls1, cls2),
							factory.getOWLNothing())//
							.accept(this);
				}

			}
		}

		return null;
	}

	@Override
	public Object visit(OWLDataPropertyDomainAxiom axiom) {
		factory.getOWLSubClassOfAxiom(factory.getOWLDataSomeValuesFrom(axiom.getProperty(), factory.getTopDatatype()),
				axiom.getDomain()) //
				.accept(this);

		return null;
	}

	// domain(r) = C ~> some(r, T) subclass C
	@Override
	public Object visit(OWLObjectPropertyDomainAxiom axiom) {

		factory.getOWLSubClassOfAxiom(factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), factory.getOWLThing()),
				axiom.getDomain()) //
				.accept(this);
		return null;
	}

	@Override
	public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		for (OWLAxiom ax : axiom.asSubObjectPropertyOfAxioms()) {
			ax.accept(this);
		}
		return null;
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
		// TODO
		throw new IllegalArgumentException(axiom.toString());
	}

	// range(r) = C ~> T subclass all(r, C)
	@Override
	public Object visit(OWLObjectPropertyRangeAxiom axiom) {

		factory.getOWLSubClassOfAxiom(factory.getOWLThing(),
				factory.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())) //
				.accept(this);
		return null;
	}

	@Override
	public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {

		OWLObjectPropertyExpression property = axiom.getProperty();
		factory.getOWLSubClassOfAxiom(factory.getOWLThing(), factory.getOWLObjectMaxCardinality(1, property))//
				.accept(this);

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
		OWLObjectPropertyExpression property = axiom.getProperty();
		factory.getOWLSubObjectPropertyOfAxiom(property, property.getInverseProperty()).accept(this);
		factory.getOWLSubObjectPropertyOfAxiom(property.getInverseProperty(), property).accept(this);
		return null;
	}

	@Override
	public Object visit(OWLDataPropertyRangeAxiom axiom) {
		// TODO Check
		// throw new IllegalArgumentException(axiom.toString());
		manager.addAxiom(normalizedOnt, axiom);
		return null;
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
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		// TODO
		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLSubDataPropertyOfAxiom axiom) {

		throw new IllegalArgumentException(axiom.toString());

	}

	@Override
	public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		factory.getOWLSubClassOfAxiom(factory.getOWLThing(),
				factory.getOWLObjectMaxCardinality(1, property.getInverseProperty()))//
				.accept(this);
		return null;
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
