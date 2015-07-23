package org.semanticweb.clipper.util;

import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplication;
import org.semanticweb.clipper.hornshiq.queryanswering.ImplicationRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.ReachBottom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PrintingUtilities {
	public PrintingUtilities() {

	}

	/*
	 * Print elements in coreImp, which contain most important implication
	 * relations
	 */
	public void printCoreImps(Collection<ImplicationRelation> set) {
		System.out.println("Core Imp: ");
		for (ImplicationRelation e : set) {
			System.out.println(e);
		}
	}

	/*
	 * Print elements in coreImp, which contain most important implication
	 * relations
	 */
	public void printHornCoreImps(Collection<HornImplication> set) {
		System.out.println("Core Imp: ");
		for (HornImplication e : set) {
			System.out.println(e);
		}
	}
	
	/*
	 * Print elements in coreEnf, which contain most important enforced
	 * relations
	 */
	public void printCoreEnfs(Collection<EnforcedRelation> coreEnf) {
		System.out.println("Core Enforce: ");
		for (EnforcedRelation e : coreEnf) {
			System.out.println(e);
		}
	}
	// =============================
	public void printInversePropertyAxioms(List<  ClipperInversePropertyOfAxiom> invAxioms) {
		System.out.println("Role Inclusion Axioms: ");
		for (ClipperInversePropertyOfAxiom e : invAxioms) {
			System.out.println(e);
		}
	}
	// ===========================
	public void printValueRestricitons(
			List<ClipperAtomSubAllAxiom> subClassAxiomsWithValueRestriction) {
		System.out.println("Value Restriction Axioms: ");
		for (ClipperAtomSubAllAxiom e : subClassAxiomsWithValueRestriction) {
			System.out.println(e);
		}
	}//
		// ===========================

	public void printMaxOneCarinalityAxioms(
			List<ClipperAtomSubMaxOneAxiom> subClassAxiomsWithMaxCardinality) {
		System.out.println("MaxOneCardinality Axioms: ");
		for (ClipperAtomSubMaxOneAxiom e : subClassAxiomsWithMaxCardinality) {
			System.out.println(e);
		}
	}

	// =============================
	public void printSubObjectPropertyAxioms(List<ClipperSubPropertyAxiom> subObjectPropertyAxioms) {
		System.out.println("Role Inclusion Axioms: ");
		for (ClipperSubPropertyAxiom e : subObjectPropertyAxioms) {
			System.out.println("                  " + e);
		}
	}
	
	
	// ======================
	public void printReachBottom(Set<ReachBottom> reachBottoms) {
		System.out.println("Reach-bottom Types: ");
		for (ReachBottom e : reachBottoms) {
			System.out.println(e);
		}
	}

	// ========================
	public void printRoleAssertions(
			HashSet<OWLObjectPropertyAssertionAxiom> roleAssertions) {
		System.out.println("Role Assertion Axioms: ");
		for (OWLObjectPropertyAssertionAxiom e : roleAssertions) {
			System.out.println("                  " + e);
		}
	}

	public void printClassAssertions(
			HashSet<OWLClassAssertionAxiom> classAssertions) {
		System.out.println("Class Assertion Axioms: ");
		for (OWLClassAssertionAxiom e : classAssertions) {
			System.out.println("                  " + e);
		}
	}
}
