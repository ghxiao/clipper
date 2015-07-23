package org.semanticweb.clipper.hornshiq.ontology;

public class ClipperTransitivityAxiom implements ClipperTBoxAxiom {
	private int role;

	public ClipperTransitivityAxiom(int role) {
		// normalization. in case role is inv(r), convert it to r
		this.role = role / 2 * 2;
	}

	@Override
	public String toString() {
		String r1 = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r1 = "inv(" + r1 + ")";
		}
		return "trans(" + r1 + ")";
	}

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperTransitivityAxiom)) return false;
        final ClipperTransitivityAxiom other = (ClipperTransitivityAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.role != other.role) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.role;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperTransitivityAxiom;
    }
}
