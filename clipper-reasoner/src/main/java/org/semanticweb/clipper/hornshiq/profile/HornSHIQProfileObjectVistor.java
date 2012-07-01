package org.semanticweb.clipper.hornshiq.profile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.AmalgamateSubClassAxioms;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
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
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.profiles.UseOfIllegalAxiom;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

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