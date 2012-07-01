package org.semanticweb.clipper.hornshiq.ontology;

public class ObjectPropertyAssertionAxiom implements Axiom {
	int role;

	int individual1, individual2;

	public ObjectPropertyAssertionAxiom(int role, int individual1, int individual2) {
		super();
		this.role = role;
		this.individual1 = individual1;
		this.individual2 = individual2;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getIndividual1() {
		return individual1;
	}

	public void setIndividual1(int individual1) {
		this.individual1 = individual1;
	}

	public int getIndividual2() {
		return individual2;
	}

	public void setIndividual2(int individual2) {
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
}
