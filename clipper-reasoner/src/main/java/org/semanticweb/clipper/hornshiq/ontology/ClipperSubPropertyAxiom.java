package org.semanticweb.clipper.hornshiq.ontology;

/**
 * SubPropertyOf(role1, role2)
 */
public class ClipperSubPropertyAxiom implements ClipperTBoxAxiom {
	int role1, role2;

    @java.beans.ConstructorProperties({"role1", "role2"})
    public ClipperSubPropertyAxiom(int role1, int role2) {
        this.role1 = role1;
        this.role2 = role2;
    }

    @Override
	public String toString() {
		String r1 = String.valueOf((role1) / 2 * 2);

		/* inverse role */
		if (role1 % 2 == 1) {
			r1 = "inv(" + r1 + ")";
		}

		String r2 = String.valueOf((role2) / 2 * 2);

		/* inverse role */
		if (role2 % 2 == 1) {
			r2 = "inv(" + r2 + ")";
		}

		return String.format("%s SubPropertyOf %s", r1, r2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + role1;
		result = prime * result + role2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClipperSubPropertyAxiom other = (ClipperSubPropertyAxiom) obj;
		if (role1 != other.role1)
			return false;
		if (role2 != other.role2)
			return false;
		return true;
	}

    public int getRole1() {
        return this.role1;
    }

    public int getRole2() {
        return this.role2;
    }

    public void setRole1(int role1) {
        this.role1 = role1;
    }

    public void setRole2(int role2) {
        this.role2 = role2;
    }
}
