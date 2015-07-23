package org.semanticweb.clipper.hornshiq.ontology;

public class ClipperPropertyAssertionAxiom implements ClipperABoxAxiom {
	int role;

	int individual1, individual2;

    @java.beans.ConstructorProperties({"role", "individual1", "individual2"})
    public ClipperPropertyAssertionAxiom(int role, int individual1, int individual2) {
        this.role = role;
        this.individual1 = individual1;
        this.individual2 = individual2;
    }

    @Override
	public String toString() {
		String r = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r = "inv(" + r + ")";
		}

		return String.format("%s(%d, %d)", r, individual1, individual2);
	}

    public int getRole() {
        return this.role;
    }

    public int getIndividual1() {
        return this.individual1;
    }

    public int getIndividual2() {
        return this.individual2;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setIndividual1(int individual1) {
        this.individual1 = individual1;
    }

    public void setIndividual2(int individual2) {
        this.individual2 = individual2;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperPropertyAssertionAxiom)) return false;
        final ClipperPropertyAssertionAxiom other = (ClipperPropertyAssertionAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.role != other.role) return false;
        if (this.individual1 != other.individual1) return false;
        if (this.individual2 != other.individual2) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.role;
        result = result * PRIME + this.individual1;
        result = result * PRIME + this.individual2;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperPropertyAssertionAxiom;
    }
}
