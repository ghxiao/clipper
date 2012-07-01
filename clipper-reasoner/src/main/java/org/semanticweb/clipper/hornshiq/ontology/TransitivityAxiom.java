package org.semanticweb.clipper.hornshiq.ontology;

public class TransitivityAxiom implements Axiom {
	private int role;

	public TransitivityAxiom(int role) {
		// normalization. in case role is inv(r), convert it to r
		this.role = role / 2 * 2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + role;
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
		TransitivityAxiom other = (TransitivityAxiom) obj;
		if (role != other.role)
			return false;
		return true;
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
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
