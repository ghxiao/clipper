package org.semanticweb.clipper.hornshiq.profile;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;

public class HornSHIQProfile implements OWLProfile {

	private Sub0_ClassExpressionChecker sub0;

	private Sub1_ClassExpressionChecker sub1;

	private Super0_ClassExpressionChecker super0;

	private Super1_ClassExpressionChecker super1;

	HornSHIQProfileObjectVistor profileObjectVisitor;

	private OWLObjectPropertyManager propertyManager;

	public HornSHIQProfile() {
		sub0 = new Sub0_ClassExpressionChecker(this);
		sub1 = new Sub1_ClassExpressionChecker(this);
		super0 = new Super0_ClassExpressionChecker(this);
		super1 = new Super1_ClassExpressionChecker(this);
	}

	@Override
	public String getName() {
		return "Horn-SHIQ";
	}

	// use it only when you know what you are doing
	void setPropertyManager(OWLObjectPropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	OWLObjectPropertyManager getPropertyManager() {
		if (propertyManager == null)
			propertyManager = profileObjectVisitor.getPropertyManager();
		return propertyManager;
	}

	@Override
	public OWLProfileReport checkOntology(OWLOntology ontology) {
		OWL2DLProfile profile = new OWL2DLProfile();
		OWLProfileReport report = profile.checkOntology(ontology);
		Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
		violations.addAll(report.getViolations());

		OWLOntologyWalker walker = new OWLOntologyWalker(
				ontology.getImportsClosure());
		profileObjectVisitor = new HornSHIQProfileObjectVistor(this, walker,
				ontology.getOWLOntologyManager());
		
		walker.walkStructure(profileObjectVisitor);
		
//		//FIXME
//		System.out.print("HERE!!!!");
//		for(Entry<OWLObjectPropertyExpression, Integer> e:profileObjectVisitor.map.entrySet()){
//			System.out.println(e.getKey() + " -> " +e.getValue());
//		}
//		System.exit(0);
		
		violations.addAll(profileObjectVisitor.getProfileViolations());
		return new OWLProfileReport(this, violations);
	}

	public Super0_ClassExpressionChecker getSuper0() {
		return super0;
	}

	public Sub1_ClassExpressionChecker getSub1() {
		return sub1;
	}

	public Sub0_ClassExpressionChecker getSub0() {
		return sub0;
	}

	public Super1_ClassExpressionChecker getSuper1() {
		return super1;
	}

}
