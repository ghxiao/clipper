package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormalHornALCHIQOntology {

	List<AndSubAtomAxiom> andSubAtomAxioms;

	List<DisjointObjectPropertiesAxiom> disjAxioms;

	List<AtomSubSomeAxiom> atomSubSomeAxioms;

	List<AtomSubAllAxiom> atomSubAllAxioms;

	List<AtomSubMaxOneAxiom> atomSubMaxOneAxioms;

	List<AtomSubMinAxiom> atomSubMinAxioms;

	List<SomeSubAtomAxiom> someSubAtomAxioms;

	List<ConceptAssertionAxiom> conceptAssertionAxioms;

	List<ObjectPropertyAssertionAxiom> roleAssertionAxioms;

	List<SubPropertyAxiom> subPropertyAxioms;

	List<InversePropertyOfAxiom> inversePropertyOfAxioms;

	private List<TransitivityAxiom> transitivityAxioms;

	TIntHashSet aboxConcepts;

	
	public NormalHornALCHIQOntology() {
		this.andSubAtomAxioms = new ArrayList<AndSubAtomAxiom>();
		this.atomSubMaxOneAxioms = new ArrayList<AtomSubMaxOneAxiom>();
		this.atomSubMinAxioms = new ArrayList<AtomSubMinAxiom>();
		this.atomSubSomeAxioms = new ArrayList<AtomSubSomeAxiom>();
		this.atomSubAllAxioms = new ArrayList<AtomSubAllAxiom>();
		this.conceptAssertionAxioms = new ArrayList<ConceptAssertionAxiom>();
		this.disjAxioms = new ArrayList<DisjointObjectPropertiesAxiom>();
		this.roleAssertionAxioms = new ArrayList<ObjectPropertyAssertionAxiom>();
		this.someSubAtomAxioms = new ArrayList<SomeSubAtomAxiom>();
		this.subPropertyAxioms = new ArrayList<SubPropertyAxiom>();
		this.inversePropertyOfAxioms = new ArrayList<InversePropertyOfAxiom>();
		this.transitivityAxioms = new ArrayList<TransitivityAxiom>();
	}

	public List<Axiom> getAxioms() {

		List<Axiom> result = new ArrayList<Axiom>();
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
	public List<SubPropertyAxiom> computeNonSimpleSubPropertyClosure() {
		List<SubPropertyAxiom> result = new ArrayList<SubPropertyAxiom>();
		for (TransitivityAxiom transAx : transitivityAxioms) {
			int r1 = transAx.getRole();
			SubPropertyAxiom axiom = new SubPropertyAxiom(r1, r1);
			result.add(axiom);
		}

		boolean updated = true;
		// TODO semi-naive style
		while (updated) {
			updated = false;
			List<SubPropertyAxiom> delta = new ArrayList<SubPropertyAxiom>();
			for (SubPropertyAxiom subAx1 : result) {
				
				int r1_1 = subAx1.getRole1();
				int r1_2 = subAx1.getRole2();

				for (SubPropertyAxiom subAx2 : subPropertyAxioms) {
					int r2_1 = subAx2.getRole1();
					if (r1_2 == r2_1) {
						int r2_2 = subAx2.getRole2();
						SubPropertyAxiom axiom = new SubPropertyAxiom(r1_1,
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
