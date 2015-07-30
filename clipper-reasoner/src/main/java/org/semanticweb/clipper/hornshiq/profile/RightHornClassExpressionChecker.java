package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/**
 *
 * Concepts not allowed to occur in the right (positively) in the Horn ontology
 *
 *  -  C ⊔ D
 *  -  ≤ mR.C with m > 1
 *
 * @author xiao
 * 
 */
class RightHornClassExpressionChecker implements
		OWLClassExpressionVisitorEx<Boolean> {

	private final HornSHIQProfile hornSHIQProfile;
    private LeftHornClassExpressionChecker leftExpressionChecker;

    RightHornClassExpressionChecker(HornSHIQProfile hornSHIQProfile) {
		this.hornSHIQProfile = hornSHIQProfile;
        leftExpressionChecker = hornSHIQProfile.getLeftExpressionChecker();
    }

	@Override
	public Boolean visit(OWLClass ce) {
		return true;
	}

	@Override
	public Boolean visit(OWLObjectIntersectionOf ce) {
		for (OWLClassExpression e : ce.getOperands()) {
			if (!e.accept(this)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean visit(OWLObjectUnionOf ce) {
        return false;
	}

	@Override
	public Boolean visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(leftExpressionChecker);
	}

	@Override
	public Boolean visit(OWLObjectSomeValuesFrom ce) {
		return ce.getFiller().accept(this);
	}

	@Override
	public Boolean visit(OWLObjectAllValuesFrom ce) {
		OWLClassExpression filler = ce.getFiller();
		OWLObjectPropertyExpression property = ce.getProperty();

		if (this.hornSHIQProfile.getPropertyManager().isNonSimple(property)) {
			return filler.accept(leftExpressionChecker);
		} else {
			return filler.accept(this);
		}

	}

	@Override
	public Boolean visit(OWLObjectHasValue ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectMinCardinality ce) {
		return ce.getFiller().accept(this);
	}

	@Override
	public Boolean visit(OWLObjectExactCardinality ce) {

		return ce.getCardinality() == 1;
	}

	@Override
	public Boolean visit(OWLObjectMaxCardinality ce) {

		return ce.getCardinality() == 1 && ce.getFiller().accept(leftExpressionChecker);
	}

	@Override
	public Boolean visit(OWLObjectHasSelf ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectOneOf ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataSomeValuesFrom ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataAllValuesFrom ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataHasValue ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataMinCardinality ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataExactCardinality ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLDataMaxCardinality ce) {

		return false;
	}

}