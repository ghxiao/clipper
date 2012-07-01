package org.semanticweb.clipper.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.ontology.AtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.InversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.SubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplication;
import org.semanticweb.clipper.hornshiq.queryanswering.ImplicationRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.ReachBottom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;


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
	public void printInversePropertyAxioms(List<  InversePropertyOfAxiom> invAxioms) {
		System.out.println("Role Inclusion Axioms: ");
		for (InversePropertyOfAxiom e : invAxioms) {
			System.out.println(e);
		}
	}
	// ===========================
	public void printValueRestricitons(
			List<AtomSubAllAxiom> subClassAxiomsWithValueRestriction) {
		System.out.println("Value Restriction Axioms: ");
		for (AtomSubAllAxiom e : subClassAxiomsWithValueRestriction) {
			System.out.println(e);
		}
	}//
		// ===========================

	public void printMaxOneCarinalityAxioms(
			List<AtomSubMaxOneAxiom> subClassAxiomsWithMaxCardinality) {
		System.out.println("MaxOneCardinality Axioms: ");
		for (AtomSubMaxOneAxiom e : subClassAxiomsWithMaxCardinality) {
			System.out.println(e);
		}
	}

	// =============================
	public void printSubObjectPropertyAxioms(List<SubPropertyAxiom> subObjectPropertyAxioms) {
		System.out.println("Role Inclusion Axioms: ");
		for (SubPropertyAxiom e : subObjectPropertyAxioms) {
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
