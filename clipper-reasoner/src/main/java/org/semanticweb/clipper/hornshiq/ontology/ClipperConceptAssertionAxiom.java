package org.semanticweb.clipper.hornshiq.ontology;

public class ClipperConceptAssertionAxiom implements ClipperABoxAxiom {

	int concept;

	int individual;

    @java.beans.ConstructorProperties({"concept", "individual"})
    public ClipperConceptAssertionAxiom(int concept, int individual) {
        this.concept = concept;
        this.individual = individual;
    }

    @Override
	public String toString() {
		return String.format("%d(%d)", concept, individual);
	}

    public int getConcept() {
        return this.concept;
    }

    public int getIndividual() {
        return this.individual;
    }

    public void setConcept(int concept) {
        this.concept = concept;
    }

    public void setIndividual(int individual) {
        this.individual = individual;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperConceptAssertionAxiom)) return false;
        final ClipperConceptAssertionAxiom other = (ClipperConceptAssertionAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.concept != other.concept) return false;
        if (this.individual != other.individual) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.concept;
        result = result * PRIME + this.individual;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperConceptAssertionAxiom;
    }
}
