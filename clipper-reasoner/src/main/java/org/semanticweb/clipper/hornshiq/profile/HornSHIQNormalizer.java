package org.semanticweb.clipper.hornshiq.profile;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

public class HornSHIQNormalizer implements OWLAxiomVisitor {

	OWLOntology normalizedOnt;

	int freshClassCounter = 0;

	OWLOntologyManager manager;

	private OWLDataFactory factory;

	HornSHIQProfile profile;

	private LeftHornClassExpressionChecker sub0;

	private RightHornClassExpressionChecker super1;

	public HornSHIQNormalizer() {

	}

	public OWLOntology normalize(OWLOntology ontology) {
		this.profile = new HornSHIQProfile();
		this.manager = ontology.getOWLOntologyManager();
		this.factory = manager.getOWLDataFactory();
		profile.setPropertyManager(new OWLObjectPropertyManager(manager, ontology));

		sub0 = profile.getLeftExpressionChecker();
		super1 = profile.getRightExpressionChecker();

		try {
			normalizedOnt = manager.createOntology();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		for (OWLAxiom axiom : ontology.getAxioms()) {
			//axiom.getNNF().accept(this);
            axiom.accept(this);
		}

		return normalizedOnt;
	}

	@Override
	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

	}

	@Override
	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {

	}

	@Override
	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {

	}


	@Override
	public void visit(OWLSubClassOfAxiom axiom) {

		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();


        boolean inProfile = axiom.getSubClass().accept(sub0) && axiom.getSuperClass().accept(super1);

        if (!inProfile){
            System.err.println("Warning: " + axiom + " is not in Horn SHIQ");
        }
        // simple case: A ⊑ C
		else if ((subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
			if (!subClass.isOWLNothing() && !superClass.isOWLThing()) {
				manager.addAxiom(normalizedOnt, axiom);
			}
		}
		// A' ⊑ C'
		else if ((subClass.getClassExpressionType() != ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() != ClassExpressionType.OWL_CLASS)) {
			OWLClass freshClass = getFreshClass(superClass);
			factory.getOWLSubClassOfAxiom(subClass, freshClass).accept(this);
			factory.getOWLSubClassOfAxiom(freshClass, superClass).accept(this);
		}
		// A' ⊑ C
		else if (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
			// and(A', B) ⊑ C
			// and(B, A') ⊑ C
			// -> {A' ⊑ D, and(D,C) ⊑ B}
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
							OWLClass freshClass = getFreshClass(op);
							newOps.add(freshClass);
							// manager.addAxiom(normalizedOnt, factory
							// .getOWLSubClassOfAxiom(op, freshClass));
							//
							factory.getOWLSubClassOfAxiom(op, freshClass).accept(this);
						} else {
							newOps.add(op);
						}
					}

					manager.addAxiom(normalizedOnt,
                            factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(newOps), superClass));
				}

			}

			// exist(R,A') ⊑ B
			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) subClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass(filler);
                    factory.getOWLSubClassOfAxiom(
                                factory.getOWLObjectSomeValuesFrom(some.getProperty(), freshClass), superClass) //
                            .accept(this);
                    factory.getOWLSubClassOfAxiom(filler, freshClass).accept(this);
                }
			}
			// or(A', B) ⊑ C
			// or(B, A') ⊑ C
			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {

                // TODO: simplify this block

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
						manager.addAxiom(normalizedOnt, factory.getOWLSubClassOfAxiom(op, superClass));
					}
				} else {
					for (OWLClassExpression op : operands) {
						if (!(op.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
							OWLClass freshClass = getFreshClass(op);
							newOps.add(freshClass);
							manager.addAxiom(normalizedOnt, factory.getOWLSubClassOfAxiom(op, freshClass));
						} else {
							newOps.add(op);
						}
					}

					factory.getOWLSubClassOfAxiom(factory.getOWLObjectUnionOf(newOps), superClass).accept(this);
				}

			}

		}
		// A ⊑ C'
		else if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
			// (A -> or(B, C)) ~>
			if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {

                // TODO: need to check

//				OWLObjectUnionOf union = (OWLObjectUnionOf) superClass;
//				Set<OWLClassExpression> operands = union.getOperands();
//				Iterator<OWLClassExpression> iterator = operands.iterator();
//				OWLClass fresh = getFreshClass();
//				OWLClassExpression first = iterator.next();
//
//				OWLClassExpression rest;
//
//				if (operands.size() == 2) {
//					rest = iterator.next();
//				} else /* operands.size() > 2 */{
//					HashSet<OWLClassExpression> newOps = new HashSet<>(operands);
//					newOps.remove(first);
//					rest = factory.getOWLObjectUnionOf(newOps);
//				}
//
//				factory.getOWLSubClassOfAxiom(subClass, fresh).accept(this);
//				if (first.accept(super0)) {
//					factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(fresh,//
//							factory.getOWLObjectComplementOf(first).getNNF()), rest).accept(this);
//				} else {
//					factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(fresh,//
//							factory.getOWLObjectComplementOf(rest).getNNF()), fresh)//
//							.accept(this);
//				}
			}

			// A ⊑ and(B, C)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {
				OWLObjectIntersectionOf and = (OWLObjectIntersectionOf) superClass;
				for (OWLClassExpression op : and.getOperands()) {
					factory.getOWLSubClassOfAxiom(subClass, op).accept(this);
				}
			}
			// A ⊑ exists(R, C') -> {A ⊑ some(R,D), D ⊑ C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) superClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					//OWLClass freshClass = getFreshClass("SOME_");
                    OWLClass freshClass = getFreshClass(filler);
					factory.getOWLSubClassOfAxiom(subClass,
							factory.getOWLObjectSomeValuesFrom(some.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(this);
				}
			}

			// A ⊑ all(R, C') -> {A ⊑ all(R, D), D ⊑ C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
				OWLObjectAllValuesFrom all = (OWLObjectAllValuesFrom) superClass;
				OWLClassExpression filler = all.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass(filler);
					factory.getOWLSubClassOfAxiom(subClass,
							factory.getOWLObjectAllValuesFrom(all.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(this);
				}
			}

			// A ⊑ max(R, 1, B') -> {A ⊑ max(R, 1, D), D ⊑ B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MAX_CARDINALITY) {
				OWLObjectMaxCardinality max = (OWLObjectMaxCardinality) superClass;
				OWLClassExpression filler = max.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass(filler);
					factory.getOWLSubClassOfAxiom(subClass,
							factory.getOWLObjectMaxCardinality(max.getCardinality(), max.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(this);
				}
			}

			// A ⊑ min(R, n, B') -> {A ⊑ min(R, n, D), D ⊑ B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MIN_CARDINALITY) {
				OWLObjectMinCardinality min = (OWLObjectMinCardinality) superClass;
				OWLClassExpression filler = min.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					manager.addAxiom(normalizedOnt, axiom);
				} else {
					OWLClass freshClass = getFreshClass(filler);
					factory.getOWLSubClassOfAxiom(subClass,
							factory.getOWLObjectMinCardinality(min.getCardinality(), min.getProperty(), freshClass)) //
							.accept(this);
					factory.getOWLSubClassOfAxiom(freshClass, filler).accept(this);
				}
			}

			// A ⊑ not(B) -> A ⊓ B ⊑ ⊥
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_COMPLEMENT_OF) {
				OWLObjectComplementOf c = (OWLObjectComplementOf) superClass;
				OWLClassExpression operand = c.getOperand();
//				OWLClass freshClass = getFreshClass();
//				factory.getOWLSubClassOfAxiom(subClass, freshClass).accept(this);
				factory.getOWLSubClassOfAxiom(
                            factory.getOWLObjectIntersectionOf(subClass, operand), factory.getOWLNothing()) //
						.accept(this);
			}
		}



	}

    private OWLClass getFreshClass(OWLClassExpression expression) {

        StringWriter writer = new StringWriter();

        ManchesterOWLSyntaxObjectRenderer renderer = new ManchesterOWLSyntaxObjectRenderer(writer, new SimpleShortFormProvider());

        expression.accept(renderer);

        String s = writer.toString();
        String newName = s.replaceAll("\\(","").replaceAll("\\)","").replaceAll("\\s+", "_");

        IRI iri;

//        boolean meaningfulName = true;

//        if(expression.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF){
//            for (OWLClassExpression op : ((OWLObjectIntersectionOf) expression).getOperands()) {
//                if(op.getClassExpressionType() != ClassExpressionType.OWL_CLASS) {
//                    meaningfulName = false;
//                }
//            }
//        } else {
//            meaningfulName = false;
//        }

        String freshConceptPrefix = "http://www.example.org/fresh#";

//        if(meaningfulName){
//            String nameFromConjunction = extractConceptNamesFromConjunction((OWLObjectIntersectionOf) expression);
//            iri = IRI.create(freshConceptPrefix + nameFromConjunction);
//        } else {
//            freshClassCounter++;
//            iri = IRI.create(freshConceptPrefix + "fresh" + freshClassCounter);
//        }

        iri = IRI.create(freshConceptPrefix + newName);

        return factory.getOWLClass(iri);
    }

    @Deprecated
	private OWLClass getFreshClass() {
		freshClassCounter++;
		return factory.getOWLClass(IRI.create("http://www.example.org/fresh#" + "fresh" + freshClassCounter));
	}

	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
//		throw new IllegalArgumentException(axiom.toString());
        // DO NOTHING
	}

	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {
		for (OWLClassExpression cls1 : axiom.getClassExpressions()) {
			for (OWLClassExpression cls2 : axiom.getClassExpressions()) {
				if (!cls1.equals(cls2)) {
					factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(cls1, cls2),
							factory.getOWLNothing())//
							.accept(this);
				}

			}
		}


	}

	@Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		factory.getOWLSubClassOfAxiom(factory.getOWLDataSomeValuesFrom(axiom.getProperty(), factory.getTopDatatype()),
				axiom.getDomain()) //
				.accept(this);


	}

	// domain(r) = C ~> some(r, T) ⊑ C
	@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {

		factory.getOWLSubClassOfAxiom(factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), factory.getOWLThing()),
				axiom.getDomain()) //
				.accept(this);

	}

	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		for (OWLAxiom ax : axiom.asSubObjectPropertyOfAxioms()) {
			ax.accept(this);
		}
	}

	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {
		// DO NOTHING
		//throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		throw new IllegalArgumentException(axiom.toString());
	}

	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        // TODO: handle it properly

//		throw new IllegalArgumentException(axiom.toString());
	}

	// range(r) = C ~> T ⊑ all(r, C)
	@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {

		factory.getOWLSubClassOfAxiom(factory.getOWLThing(),
				    factory.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())) //
				.accept(this);

	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);

	}

	@Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {

		OWLObjectPropertyExpression property = axiom.getProperty();
		factory.getOWLSubClassOfAxiom(factory.getOWLThing(), factory.getOWLObjectMaxCardinality(1, property))//
				.accept(this);


	}

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);

	}

	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {
        // TODO: handle it properly
        // ignore

    }

	@Override
	public void visit(OWLDeclarationAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);


	}

	@Override
	public void visit(OWLAnnotationAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);


	}

	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {

        axiom.asSubPropertyAxioms().forEach(ax -> ax.accept(this));


	}

	@Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		// ignore

	}

	@Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        // TODO: handle it properly
        // ignore

    }

	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        // TODO: handle it properly
        // ignore

	}

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);


	}

	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {

		Set<OWLClassExpression> classes = axiom.getClassExpressions();

		for (OWLClassExpression cls1 : classes) {
			for (OWLClassExpression cls2 : classes) {
				if (cls1.equals(cls2))
					continue;
				factory.getOWLSubClassOfAxiom(cls1, cls2).accept(this);
			}
		}


	}

	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);


	}

	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);

	}

	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        // TODO: handle it properly
        // ignore

    }

	@Override
	public void visit(OWLSubDataPropertyOfAxiom axiom) {
        manager.addAxiom(normalizedOnt, axiom);
    }

	@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		OWLObjectPropertyExpression property = axiom.getProperty();
		factory.getOWLSubClassOfAxiom(factory.getOWLThing(),
				factory.getOWLObjectMaxCardinality(1, property.getInverseProperty()))//
				.accept(this);

	}

	@Override
	public void visit(OWLSameIndividualAxiom axiom) {
        // ignore

	}

	@Override
	public void visit(OWLSubPropertyChainOfAxiom axiom) {
        // TODO: check how far we can go
        // ignore

	}

	@Override
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.asSubObjectPropertyOfAxioms().forEach(ax -> ax.accept(this));


	}

	@Override
	public void visit(OWLHasKeyAxiom axiom) {
        // ignore

	}

	@Override
	public void visit(OWLDatatypeDefinitionAxiom axiom) {
        // ignore

	}

	@Override
	public void visit(SWRLRule rule) {
        // ignore

	}

}
