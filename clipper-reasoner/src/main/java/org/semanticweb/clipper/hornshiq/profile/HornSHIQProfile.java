package org.semanticweb.clipper.hornshiq.profile;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;

import java.util.HashSet;
import java.util.Set;

public class HornSHIQProfile implements OWLProfile {



//	private Sub1_ClassExpressionChecker sub1;
//
//	private Super0_ClassExpressionChecker super0;

    private LeftHornClassExpressionChecker leftExpressionChecker;
	private RightHornClassExpressionChecker rightExpressionChecker;

	HornSHIQProfileObjectVisitor profileObjectVisitor;

	private OWLObjectPropertyManager propertyManager;

	public HornSHIQProfile() {

//		sub1 = new Sub1_ClassExpressionChecker(this);
//		super0 = new Super0_ClassExpressionChecker(this);
        leftExpressionChecker = new LeftHornClassExpressionChecker(this);
		rightExpressionChecker = new RightHornClassExpressionChecker(this);
	}

	@Override
	public String getName() {
		return "Horn-SHIQ";
	}

    // TODO: replace it by a proper IRI
    @Override
    public IRI getIRI() {
        return IRI.create("http://ghxiao.org/HornSHIQ");
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

    // TODO: FIXME
	@Override
	public OWLProfileReport checkOntology(OWLOntology ontology) {
		OWL2DLProfile profile = new OWL2DLProfile();
		//System.out.println(ontology);
		OWLProfileReport report = profile.checkOntology(ontology);
		
		
		Set<OWLProfileViolation> violations = new HashSet<>();
		violations.addAll(report.getViolations());

		OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
//		OWLOntologyWalker walker = new OWLOntologyWalker(
//				ImmutableSet.of( ontology));
		profileObjectVisitor = new HornSHIQProfileObjectVisitor(this, walker,
				ontology.getOWLOntologyManager());
		
		walker.walkStructure(profileObjectVisitor);
		
//		//FIXME
//		System.out.print("HERE!!!!");
//		for(Entry<OWLObjectPropertyExpression, Integer> e:profileObjectVisitor.map.entrySet()){
//			System.out.println(e.getKey() + " -> " +e.getValue());
//		}
//		System.exit(0);
		//Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
		violations.addAll(profileObjectVisitor.getProfileViolations());
		return new OWLProfileReport(this, violations);
	}

//	public Super0_ClassExpressionChecker getSuper0() {
//		return super0;
//	}
//
//	public Sub1_ClassExpressionChecker getSub1() {
//		return sub1;
//	}

	public LeftHornClassExpressionChecker getLeftExpressionChecker() {
		return leftExpressionChecker;
	}

	public RightHornClassExpressionChecker getRightExpressionChecker() {
		return rightExpressionChecker;
	}

}
