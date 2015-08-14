package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.owlapi.model.*;

import java.util.Set;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClipperHornSHIQOntologyConverter implements OWLAxiomVisitor {

    final Logger log = LoggerFactory.getLogger(ClipperHornSHIQOntologyConverter.class);

    private ClipperHornSHIQOntology ontology;
	private ClipperManager km;

	public ClipperHornSHIQOntology convert(OWLOntology owlOntology) {
		ontology = new ClipperHornSHIQOntology();
		km = ClipperManager.getInstance();
		for (OWLAxiom ax : owlOntology.getLogicalAxioms()) {
			ax.accept(this);
		}
		return ontology;
	}

	@Override
	public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {

        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);

		//throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);

		//throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);

		//throw new UnsupportedOperationException();
	}

	@Override
	public void visit(OWLSubClassOfAxiom axiom) {
		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();

		// simple case: A subclass C
		if ((subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)
				&& (superClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS)) {
			if (!subClass.isOWLNothing() && !superClass.isOWLThing()) {
				TIntHashSet left = new TIntHashSet();
				left.add(km.getOwlClassEncoder().getValueBySymbol((OWLClass) subClass));
				int right = (km.getOwlClassEncoder().getValueBySymbol((OWLClass) superClass));
				ontology.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(left, right));
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
				ontology.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(left, right));

			}

			// exist(R,A') subclass B
			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) subClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int concept1 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					int role = km.getOwlPropertyExpressionEncoder().getValueBySymbol(some.getProperty());
					ontology.getSomeSubAtomAxioms().add(new ClipperSomeSubAtomAxiom(concept1, role, right));
				} else {
                    log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                    //throw new IllegalStateException();
				}
			}
			// or(A', B) subclass C
			// or(B, A') subclass C
			// else if (subClass .getClassExpressionType() ==
			// ClassExpressionType. OWLObjectUnionOf) {

			else if (subClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {
                // TODO: check how this can happen
                log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
            }

		}
		// A subclass C'
		else if (subClass.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {

			int left = km.getOwlClassEncoder().getValueBySymbol((OWLClass) subClass);

			if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_UNION_OF) {
                log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                //throw new IllegalStateException();
			}

			// A subclass and(B, C)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_INTERSECTION_OF) {
                log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                //throw new IllegalStateException();

			}
			// A subclass exists(R, C') -> {A subclass some(R,D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_SOME_VALUES_FROM) {
				OWLObjectSomeValuesFrom some = (OWLObjectSomeValuesFrom) superClass;
				OWLClassExpression filler = some.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlPropertyExpressionEncoder().getValueBySymbol(some.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(left, role, concept2));
				} else {
                    log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                    //throw new IllegalStateException();
				}
			}

			// A subclass all(R, C') -> {A subclass all(R, D), D subclass C'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_ALL_VALUES_FROM) {
				OWLObjectAllValuesFrom all = (OWLObjectAllValuesFrom) superClass;
				OWLClassExpression filler = all.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlPropertyExpressionEncoder().getValueBySymbol(all.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubAllAxioms().add(new ClipperAtomSubAllAxiom(left, role, concept2));
				} else {
                    log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                    //throw new IllegalStateException();
				}
			}

			// A subclass max(R, 1, B') -> {A subclass max(R, 1, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MAX_CARDINALITY) {
				OWLObjectMaxCardinality max = (OWLObjectMaxCardinality) superClass;
				OWLClassExpression filler = max.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlPropertyExpressionEncoder().getValueBySymbol(max.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubMaxOneAxioms().add(new ClipperAtomSubMaxOneAxiom(left, role, concept2));
				} else {
                    log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                    //throw new IllegalStateException();
				}
			}

			// A subclass min(R, n, B') -> {A subclass min(R, n, D), D subclass
			// B'}
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_MIN_CARDINALITY) {
				OWLObjectMinCardinality min = (OWLObjectMinCardinality) superClass;
				OWLClassExpression filler = min.getFiller();
				if (filler.getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
					int role = km.getOwlPropertyExpressionEncoder().getValueBySymbol(min.getProperty());
					int concept2 = km.getOwlClassEncoder().getValueBySymbol((OWLClass) filler);
					ontology.getAtomSubMinAxioms().add(
							new ClipperAtomSubMinAxiom(left, role, concept2, min.getCardinality()));
				} else {
                    log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                    //throw new IllegalStateException();
				}
			}

			// A subclass not(B)
			else if (superClass.getClassExpressionType() == ClassExpressionType.OBJECT_COMPLEMENT_OF) {
                log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
                //throw new IllegalStateException();
			}
		}

    }

	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);

		//throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		throw new UnsupportedOperationException();

	}

	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

		OWLObjectPropertyExpression[] properties = axiom.getProperties().toArray(new OWLObjectPropertyExpression[0]);

		for (int i = 0; i < properties.length; i++) {
			for (int j = i + 1; j < properties.length; j++) {
				OWLObjectPropertyExpression first = properties[i];
				OWLObjectPropertyExpression second = properties[j];
				int r = km.getOwlPropertyExpressionEncoder().getValueBySymbol(first);
				int s = km.getOwlPropertyExpressionEncoder().getValueBySymbol(second);
				ontology.getDisjointObjectPropertiesAxioms().add(new ClipperDisjointObjectPropertiesAxiom(r, s));
			}
		}


	}

	@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
	}

	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		int p = km.getOwlPropertyExpressionEncoder().getValueBySymbol(axiom.getProperty());
		int s = km.getOwlIndividualAndLiteralEncoder().getValueBySymbol(axiom.getSubject());
		int o = km.getOwlIndividualAndLiteralEncoder().getValueBySymbol(axiom.getObject());
		ontology.getPropertyAssertionAxioms().add(new ClipperPropertyAssertionAxiom(p, s, o));
	}

	@Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		OWLObjectPropertyExpression sub = axiom.getSubProperty();
		OWLObjectPropertyExpression sup = axiom.getSuperProperty();
		int r = km.getOwlPropertyExpressionEncoder().getValueBySymbol(sub);
		int s = km.getOwlPropertyExpressionEncoder().getValueBySymbol(sup);
        ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(r, s));
	}

	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLDeclarationAxiom axiom) {

    }

	@Override
	public void visit(OWLAnnotationAssertionAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		int cls = km.getOwlClassEncoder().getValueBySymbol((OWLClass) axiom.getClassExpression());
		int ind = km.getOwlIndividualAndLiteralEncoder().getValueBySymbol(axiom.getIndividual());
		ontology.getConceptAssertionAxioms().add(new ClipperConceptAssertionAxiom(cls, ind));
	}

	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		int p = km.getOwlPropertyExpressionEncoder().getValueBySymbol(axiom.getProperty());
		int s = km.getOwlIndividualAndLiteralEncoder().getValueBySymbol(axiom.getSubject());
		int o = km.getOwlIndividualAndLiteralEncoder().getValueBySymbol(axiom.getObject());
		ontology.getPropertyAssertionAxioms().add(new ClipperPropertyAssertionAxiom(p, s, o));
		//return null;

	}

	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		int p = km.getOwlPropertyExpressionEncoder().getValueBySymbol(axiom.getProperty());
		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(p));
	}

	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
	}

	@Override
	public void visit(OWLSubDataPropertyOfAxiom axiom) {
		OWLDataPropertyExpression sub = axiom.getSubProperty();
		OWLDataPropertyExpression sup = axiom.getSuperProperty();
		int r = km.getOwlPropertyExpressionEncoder().getValueBySymbol(sub);
		int s = km.getOwlPropertyExpressionEncoder().getValueBySymbol(sup);
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(r, s));
	}

	@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLSameIndividualAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
	}

	@Override
	public void visit(OWLSubPropertyChainOfAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
	}

	@Override
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        // TODO: converts it to sub property axioms
		OWLObjectPropertyExpression first = axiom.getFirstProperty();
		OWLObjectPropertyExpression second = axiom.getSecondProperty();
		int r = km.getOwlPropertyExpressionEncoder().getValueBySymbol(first);
		int s = km.getOwlPropertyExpressionEncoder().getValueBySymbol(second);
		ontology.getInversePropertyOfAxioms().add(new ClipperInversePropertyOfAxiom(r, s));

	}

	@Override
	public void visit(OWLHasKeyAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(OWLDatatypeDefinitionAxiom axiom) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", axiom);
    }

	@Override
	public void visit(SWRLRule rule) {
        log.warn("ClipperHornSHIQOntologyConverter ignores {}", rule);
    }

}
