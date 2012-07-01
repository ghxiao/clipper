package org.semanticweb.clipper.hornshiq.ontology;

public class DisjointObjectPropertiesAxiom implements Axiom {

	int role1, role2;

	public int getRole1() {
		return role1;
	}

	public void setRole1(int role1) {
		this.role1 = role1;
	}

	public int getRole2() {
		return role2;
	}

	public void setRole2(int role2) {
		this.role2 = role2;
	}

	public DisjointObjectPropertiesAxiom(int role1, int role2) {
		super();
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

		return String.format("Disj(%s, %s)", r1, r2);
	}
}
