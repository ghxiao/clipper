package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.List;

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

    public List<ClipperAndSubAtomAxiom> getAndSubAtomAxioms() {
        return this.andSubAtomAxioms;
    }

    public List<ClipperDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms() {
        return this.disjointObjectPropertiesAxioms;
    }

    public List<ClipperAtomSubSomeAxiom> getAtomSubSomeAxioms() {
        return this.atomSubSomeAxioms;
    }

    public List<ClipperAtomSubAllAxiom> getAtomSubAllAxioms() {
        return this.atomSubAllAxioms;
    }

    public List<ClipperAtomSubMaxOneAxiom> getAtomSubMaxOneAxioms() {
        return this.atomSubMaxOneAxioms;
    }

    public List<ClipperAtomSubMinAxiom> getAtomSubMinAxioms() {
        return this.atomSubMinAxioms;
    }

    public List<ClipperSomeSubAtomAxiom> getSomeSubAtomAxioms() {
        return this.someSubAtomAxioms;
    }

    public List<ClipperConceptAssertionAxiom> getConceptAssertionAxioms() {
        return this.conceptAssertionAxioms;
    }

    public List<ClipperPropertyAssertionAxiom> getPropertyAssertionAxioms() {
        return this.propertyAssertionAxioms;
    }

    public List<ClipperSubPropertyAxiom> getSubPropertyAxioms() {
        return this.subPropertyAxioms;
    }

    public List<ClipperInversePropertyOfAxiom> getInversePropertyOfAxioms() {
        return this.inversePropertyOfAxioms;
    }

    public List<ClipperTransitivityAxiom> getTransitivityAxioms() {
        return this.transitivityAxioms;
    }

    public TIntHashSet getAboxConcepts() {
        return this.aboxConcepts;
    }

    public void setAndSubAtomAxioms(List<ClipperAndSubAtomAxiom> andSubAtomAxioms) {
        this.andSubAtomAxioms = andSubAtomAxioms;
    }

    public void setDisjointObjectPropertiesAxioms(List<ClipperDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms) {
        this.disjointObjectPropertiesAxioms = disjointObjectPropertiesAxioms;
    }

    public void setAtomSubSomeAxioms(List<ClipperAtomSubSomeAxiom> atomSubSomeAxioms) {
        this.atomSubSomeAxioms = atomSubSomeAxioms;
    }

    public void setAtomSubAllAxioms(List<ClipperAtomSubAllAxiom> atomSubAllAxioms) {
        this.atomSubAllAxioms = atomSubAllAxioms;
    }

    public void setAtomSubMaxOneAxioms(List<ClipperAtomSubMaxOneAxiom> atomSubMaxOneAxioms) {
        this.atomSubMaxOneAxioms = atomSubMaxOneAxioms;
    }

    public void setAtomSubMinAxioms(List<ClipperAtomSubMinAxiom> atomSubMinAxioms) {
        this.atomSubMinAxioms = atomSubMinAxioms;
    }

    public void setSomeSubAtomAxioms(List<ClipperSomeSubAtomAxiom> someSubAtomAxioms) {
        this.someSubAtomAxioms = someSubAtomAxioms;
    }

    public void setConceptAssertionAxioms(List<ClipperConceptAssertionAxiom> conceptAssertionAxioms) {
        this.conceptAssertionAxioms = conceptAssertionAxioms;
    }

    public void setPropertyAssertionAxioms(List<ClipperPropertyAssertionAxiom> propertyAssertionAxioms) {
        this.propertyAssertionAxioms = propertyAssertionAxioms;
    }

    public void setSubPropertyAxioms(List<ClipperSubPropertyAxiom> subPropertyAxioms) {
        this.subPropertyAxioms = subPropertyAxioms;
    }

    public void setInversePropertyOfAxioms(List<ClipperInversePropertyOfAxiom> inversePropertyOfAxioms) {
        this.inversePropertyOfAxioms = inversePropertyOfAxioms;
    }

    public void setTransitivityAxioms(List<ClipperTransitivityAxiom> transitivityAxioms) {
        this.transitivityAxioms = transitivityAxioms;
    }

    public void setAboxConcepts(TIntHashSet aboxConcepts) {
        this.aboxConcepts = aboxConcepts;
    }
}
