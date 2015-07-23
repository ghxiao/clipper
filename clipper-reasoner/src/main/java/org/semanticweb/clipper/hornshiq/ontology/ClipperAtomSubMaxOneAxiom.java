package org.semanticweb.clipper.hornshiq.ontology;

/**
 * SubClassOf(concept1, role max 1 concept2)
 * */
public class ClipperAtomSubMaxOneAxiom implements ClipperTBoxAxiom {

	int concept1;
	int role;
	int concept2;

    @java.beans.ConstructorProperties({"concept1", "role", "concept2"})
    public ClipperAtomSubMaxOneAxiom(int concept1, int role, int concept2) {
        this.concept1 = concept1;
        this.role = role;
        this.concept2 = concept2;
    }

    @Override
	public String toString() {
		String r = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r = "inv(" + r + ")";
		}

		return String.format("%d SubClassOf %s max 1 %d", concept1, r, concept2);
	}

    public int getConcept1() {
        return this.concept1;
    }

    public int getRole() {
        return this.role;
    }

    public int getConcept2() {
        return this.concept2;
    }

    public void setConcept1(int concept1) {
        this.concept1 = concept1;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setConcept2(int concept2) {
        this.concept2 = concept2;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperAtomSubMaxOneAxiom)) return false;
        final ClipperAtomSubMaxOneAxiom other = (ClipperAtomSubMaxOneAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.concept1 != other.concept1) return false;
        if (this.role != other.role) return false;
        if (this.concept2 != other.concept2) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.concept1;
        result = result * PRIME + this.role;
        result = result * PRIME + this.concept2;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperAtomSubMaxOneAxiom;
    }
}
