package org.semanticweb.clipper.hornshiq.ontology;

public class ClipperInversePropertyOfAxiom implements ClipperTBoxAxiom {
	int role1, role2;

	@java.beans.ConstructorProperties({"role1", "role2"})
	public ClipperInversePropertyOfAxiom(int role1, int role2) {
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

		return String.format("Inv(%s, %s)", r1, r2);
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

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof ClipperInversePropertyOfAxiom)) return false;
		final ClipperInversePropertyOfAxiom other = (ClipperInversePropertyOfAxiom) o;
		if (!other.canEqual((Object) this)) return false;
		if (this.role1 != other.role1) return false;
		if (this.role2 != other.role2) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.role1;
		result = result * PRIME + this.role2;
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof ClipperInversePropertyOfAxiom;
	}
}
