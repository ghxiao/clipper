package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClipperHornSHIQOntology {

	private List<ClipperAndSubAtomAxiom> andSubAtomAxioms;

	private List<ClipperDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms;

	private List<ClipperAtomSubSomeAxiom> atomSubSomeAxioms;

	private List<ClipperAtomSubAllAxiom> atomSubAllAxioms;

	private List<ClipperAtomSubMaxOneAxiom> atomSubMaxOneAxioms;

	private List<ClipperAtomSubMinAxiom> atomSubMinAxioms;

	private List<ClipperSomeSubAtomAxiom> someSubAtomAxioms;

	private List<ClipperConceptAssertionAxiom> conceptAssertionAxioms;

	private List<ClipperPropertyAssertionAxiom> propertyAssertionAxioms;

	private List<ClipperSubPropertyAxiom> subPropertyAxioms;

	private List<ClipperInversePropertyOfAxiom> inversePropertyOfAxioms;

	private List<ClipperTransitivityAxiom> transitivityAxioms;

	TIntHashSet aboxConcepts;

	public ClipperHornSHIQOntology() {
		this.andSubAtomAxioms = new ArrayList<>();
		this.atomSubMaxOneAxioms = new ArrayList<>();
		this.atomSubMinAxioms = new ArrayList<>();
		this.atomSubSomeAxioms = new ArrayList<>();
		this.atomSubAllAxioms = new ArrayList<>();
		this.conceptAssertionAxioms = new ArrayList<>();
		this.disjointObjectPropertiesAxioms = new ArrayList<>();
		this.propertyAssertionAxioms = new ArrayList<>();
		this.someSubAtomAxioms = new ArrayList<>();
		this.subPropertyAxioms = new ArrayList<>();
		this.inversePropertyOfAxioms = new ArrayList<>();
		this.transitivityAxioms = new ArrayList<>();
	}

	public List<ClipperAxiom> getAllAxioms() {

		List<ClipperAxiom> result = new ArrayList<>();
		result.addAll(andSubAtomAxioms);
		result.addAll(atomSubAllAxioms);
		result.addAll(atomSubMaxOneAxioms);
		result.addAll(atomSubMinAxioms);
		result.addAll(atomSubSomeAxioms);
		result.addAll(conceptAssertionAxioms);
		result.addAll(disjointObjectPropertiesAxioms);
		result.addAll(propertyAssertionAxioms);
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
						ClipperSubPropertyAxiom axiom = new ClipperSubPropertyAxiom(r1_1, r2_2);
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
