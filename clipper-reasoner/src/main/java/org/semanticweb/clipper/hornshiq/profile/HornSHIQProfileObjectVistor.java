package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.UseOfIllegalAxiom;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class HornSHIQProfileObjectVistor extends OWLOntologyWalkerVisitor<Object> {

	// FIXME: TEMP
	Map<OWLObjectPropertyExpression, Integer> map = new HashMap<OWLObjectPropertyExpression, Integer>();

	/**
	 * 
	 */
	private final HornSHIQProfile hornSHIQProfile;

	OWLObjectPropertyManager objectPropertyManager;

	private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

	private OWLOntologyManager manager;

	OWLObjectPropertyManager getPropertyManager() {
		if (objectPropertyManager == null) {
			objectPropertyManager = new OWLObjectPropertyManager(manager, getCurrentOntology());
		}
		return objectPropertyManager;
	}

	public HornSHIQProfileObjectVistor(HornSHIQProfile hornSHIQProfile, OWLOntologyWalker walker,
			OWLOntologyManager ontologyManager) {
		super(walker);
		this.hornSHIQProfile = hornSHIQProfile;
		this.manager = ontologyManager;

	}

	@Override
	public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));

		return null;
	}

	@Override
	public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));

		return null;
	}

	@Override
	public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));

		return null;
	}

	@Override
	public Object visit(OWLSubClassOfAxiom axiom) {

		// //FIXME
		// OWLClassExpression sup = axiom.getSuperClass();
		// if(sup instanceof OWLObjectAllValuesFrom){
		// System.out.println("in subclass");
		//
		// OWLObjectAllValuesFrom all =(OWLObjectAllValuesFrom)sup;
		// OWLObjectPropertyExpression p = all.getProperty();
		// if(map.containsKey(p)){
		// map.put(p, map.get(p)+1);
		// }else{
		// map.put(p, 1);
		// }
		// }

		if ((axiom.getSubClass().accept(this.hornSHIQProfile.getSub0()) //
				&& axiom.getSuperClass().accept(this.hornSHIQProfile.getSuper1())) //
				|| (axiom.getSubClass().accept(this.hornSHIQProfile.getSub1()) //
				&& axiom.getSuperClass().accept(this.hornSHIQProfile.getSuper0()))) {
		} else
			profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDisjointClassesAxiom axiom) {
		for (OWLClassExpression cls : axiom.getClassExpressions()) {
			if (!cls.accept(this.hornSHIQProfile.getSub1())) {
				profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
			}
		}
		return null;
	}

	@Override
	public Object visit(OWLDataPropertyDomainAxiom axiom) {

		OWLClassExpression domain = axiom.getDomain();
		if (!domain.accept(this.hornSHIQProfile.getSuper1())) {
			profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		}

		return null;
	}

	@Override
	public Object visit(OWLObjectPropertyDomainAxiom axiom) {
		OWLClassExpression domain = axiom.getDomain();
		if (!domain.accept(this.hornSHIQProfile.getSuper1())) {
			profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		}
		return null;
	}

	@Override
	public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		for(OWLAxiom ax:axiom.asSubObjectPropertyOfAxioms()){
			ax.accept(this);
		}
	
		return null;
	}

	@Override
	public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDifferentIndividualsAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLObjectPropertyRangeAxiom axiom) {
		if (!axiom.getRange().accept(this.hornSHIQProfile.getSuper1())) {
			profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		}
		return null;
	}

	@Override
	public Object visit(OWLObjectPropertyAssertionAxiom axiom) {

		return null;
	}

	@Override
	public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLDisjointUnionAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDeclarationAxiom axiom) {

		return null;
	}

	@Override
	public Object visit(OWLAnnotationAssertionAxiom axiom) {
		// profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
		// axiom));
		return null;
	}

	@Override
	public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
		// profileViolations
		// .add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDataPropertyRangeAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLClassAssertionAxiom axiom) {
		// FIXME
		if ((axiom.getClassExpression().getClassExpressionType() != ClassExpressionType.OWL_CLASS)) {
			profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		}

		return null;
	}

	@Override
	public Object visit(OWLEquivalentClassesAxiom axiom) {
		
		for(OWLAxiom ax:axiom.asOWLSubClassOfAxioms()){
			ax.accept(this);
		}
		
		return null;
	}

	@Override
	public Object visit(OWLDataPropertyAssertionAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLSubDataPropertyOfAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLSameIndividualAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLSubPropertyChainOfAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	// I
	@Override
	public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
		return null;
	}

	@Override
	public Object visit(OWLHasKeyAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(OWLDatatypeDefinitionAxiom axiom) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
		return null;
	}

	@Override
	public Object visit(SWRLRule rule) {
		profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
		return null;
	}

	public Set<OWLProfileViolation> getProfileViolations() {
		return profileViolations;
	}

}