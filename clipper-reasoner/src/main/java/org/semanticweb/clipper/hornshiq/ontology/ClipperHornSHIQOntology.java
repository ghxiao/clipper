package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClipperHornSHIQOntology {

	List<ClipperAndSubAtomAxiom> andSubAtomAxioms;

	List<ClipperDisjointObjectPropertiesAxiom> disjAxioms;

	List<ClipperAtomSubSomeAxiom> atomSubSomeAxioms;

	List<ClipperAtomSubAllAxiom> atomSubAllAxioms;

	List<ClipperAtomSubMaxOneAxiom> atomSubMaxOneAxioms;

	List<ClipperAtomSubMinAxiom> atomSubMinAxioms;

	List<ClipperSomeSubAtomAxiom> someSubAtomAxioms;

	List<ClipperConceptAssertionAxiom> conceptAssertionAxioms;

	List<ClipperObjectPropertyAssertionAxiom> roleAssertionAxioms;

	List<ClipperSubPropertyAxiom> subPropertyAxioms;

	List<ClipperInversePropertyOfAxiom> inversePropertyOfAxioms;

	private List<ClipperTransitivityAxiom> transitivityAxioms;

	TIntHashSet aboxConcepts;

	
	public ClipperHornSHIQOntology() {
		this.andSubAtomAxioms = new ArrayList<ClipperAndSubAtomAxiom>();
		this.atomSubMaxOneAxioms = new ArrayList<ClipperAtomSubMaxOneAxiom>();
		this.atomSubMinAxioms = new ArrayList<ClipperAtomSubMinAxiom>();
		this.atomSubSomeAxioms = new ArrayList<ClipperAtomSubSomeAxiom>();
		this.atomSubAllAxioms = new ArrayList<ClipperAtomSubAllAxiom>();
		this.conceptAssertionAxioms = new ArrayList<ClipperConceptAssertionAxiom>();
		this.disjAxioms = new ArrayList<ClipperDisjointObjectPropertiesAxiom>();
		this.roleAssertionAxioms = new ArrayList<ClipperObjectPropertyAssertionAxiom>();
		this.someSubAtomAxioms = new ArrayList<ClipperSomeSubAtomAxiom>();
		this.subPropertyAxioms = new ArrayList<ClipperSubPropertyAxiom>();
		this.inversePropertyOfAxioms = new ArrayList<ClipperInversePropertyOfAxiom>();
		this.transitivityAxioms = new ArrayList<ClipperTransitivityAxiom>();
	}

	public List<ClipperAxiom> getAxioms() {

		List<ClipperAxiom> result = new ArrayList<ClipperAxiom>();
		result.addAll(andSubAtomAxioms);
		result.addAll(atomSubAllAxioms);
		result.addAll(atomSubMaxOneAxioms);
		result.addAll(atomSubMinAxioms);
		result.addAll(atomSubSomeAxioms);
		result.addAll(conceptAssertionAxioms);
		result.addAll(disjAxioms);
		result.addAll(roleAssertionAxioms);
		result.addAll(someSubAtomAxioms);
		result.addAll(subPropertyAxioms);
		result.addAll(inversePropertyOfAxioms);
		result.addAll(transitivityAxioms);

		return result;

	}

	/*
	 * compute all <r,s> s.t. trans(r), r \sqsubseteq^* s
	 */
	public List<ClipperSubPropertyAxiom> computeNonSimpleSubPropertyClosure() {
		List<ClipperSubPropertyAxiom> result = new ArrayList<ClipperSubPropertyAxiom>();
		for (ClipperTransitivityAxiom transAx : transitivityAxioms) {
			int r1 = transAx.getRole();
			ClipperSubPropertyAxiom axiom = new ClipperSubPropertyAxiom(r1, r1);
			result.add(axiom);
		}

		boolean updated = true;
		// TODO semi-naive style
		while (updated) {
			updated = false;
			List<ClipperSubPropertyAxiom> delta = new ArrayList<ClipperSubPropertyAxiom>();
			for (ClipperSubPropertyAxiom subAx1 : result) {
				
				int r1_1 = subAx1.getRole1();
				int r1_2 = subAx1.getRole2();

				for (ClipperSubPropertyAxiom subAx2 : subPropertyAxioms) {
					int r2_1 = subAx2.getRole1();
					if (r1_2 == r2_1) {
						int r2_2 = subAx2.getRole2();
						ClipperSubPropertyAxiom axiom = new ClipperSubPropertyAxiom(r1_1,
								r2_2);
						if (!result.contains(axiom)) {
							updated = true;
							delta.add(axiom);
						}
					}
				}
			}
			result.addAll(delta);
		}
		return result;
	}
}
