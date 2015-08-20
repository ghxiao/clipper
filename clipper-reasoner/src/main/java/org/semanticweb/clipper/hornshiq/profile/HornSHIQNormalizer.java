package org.semanticweb.clipper.hornshiq.profile;

import java.io.StringWriter;
import java.util.HashSet;
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
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxObjectRenderer;

public class HornSHIQNormalizer implements OWLAxiomVisitorEx<Object> {

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
	public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
		return null;
	}


	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {

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

		return null;

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

	// domain(r) = C ~> some(r, T) ⊑ C
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
        // TODO: handle it properly
        return null;
//		throw new IllegalArgumentException(axiom.toString());
	}

	// range(r) = C ~> T ⊑ all(r, C)
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
        // TODO: handle it properly
        // ignore
        return null;
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

        axiom.asSubPropertyAxioms().forEach(ax -> ax.accept(this));

		return null;
	}

	@Override
	public Object visit(OWLDataPropertyRangeAxiom axiom) {
		// ignore
		return null;
	}

	@Override
	public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
        // TODO: handle it properly
        // ignore
        return null;
    }

	@Override
	public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
        // TODO: handle it properly
        // ignore
        return null;
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
		manager.addAxiom(normalizedOnt, axiom);

		return null;
	}

	@Override
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		manager.addAxiom(normalizedOnt, axiom);
		return null;
	}

	@Override
	public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        // TODO: handle it properly
        // ignore
        return null;
    }

	@Override
	public Object visit(OWLSubDataPropertyOfAxiom axiom) {
        // TODO: handle it properly
        // ignore
        return null;
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
        // ignore
        return null;
	}

	@Override
	public Object visit(OWLSubPropertyChainOfAxiom axiom) {
        // TODO: check how far we can go
        // ignore
        return null;
	}

	@Override
	public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.asSubObjectPropertyOfAxioms().forEach(ax -> ax.accept(this));

		return null;
	}

	@Override
	public Object visit(OWLHasKeyAxiom axiom) {
        // ignore
        return null;
	}

	@Override
	public Object visit(OWLDatatypeDefinitionAxiom axiom) {
        // ignore
        return null;
	}

	@Override
	public Object visit(SWRLRule rule) {
        // ignore
        return null;
	}

}
