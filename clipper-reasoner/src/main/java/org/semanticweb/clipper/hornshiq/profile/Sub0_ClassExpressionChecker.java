package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataRange;
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
 * C_0^-
 * 
 * @author xiao
 * 
 */
class Sub0_ClassExpressionChecker implements
		OWLClassExpressionVisitorEx<Boolean> {

	/**
	 * 
	 */
	private final HornSHIQProfile hornSHIQProfile;

	public Sub0_ClassExpressionChecker(HornSHIQProfile hornSHIQProfile) {
		this.hornSHIQProfile = hornSHIQProfile;

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
		for (OWLClassExpression e : ce.getOperands()) {
			if (!e.accept(this)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Boolean visit(OWLObjectComplementOf ce) {

		return ce.getOperand().accept(this.hornSHIQProfile.getSuper0());
	}

	@Override
	public Boolean visit(OWLObjectSomeValuesFrom ce) {

		OWLClassExpression filler = ce.getFiller();
		OWLObjectPropertyExpression property = ce.getProperty();

		if (this.hornSHIQProfile.getPropertyManager().isNonSimple(property)) {
			return filler.accept(this);
		} else {
			return filler.accept(this.hornSHIQProfile.getSub1());
		}
	}

	@Override
	public Boolean visit(OWLObjectAllValuesFrom ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectHasValue ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectMinCardinality ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectExactCardinality ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectMaxCardinality ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectHasSelf ce) {

		return false;
	}

	@Override
	public Boolean visit(OWLObjectOneOf ce) {

		return false;
	}

	// TODO: Check
	// only literals are supported
	@Override
	public Boolean visit(OWLDataSomeValuesFrom ce) {
		OWLDataRange filler = ce.getFiller();
//		OWLDataPropertyExpression property = ce.getProperty();
		return (filler.getDataRangeType() == DataRangeType.DATATYPE);
		
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