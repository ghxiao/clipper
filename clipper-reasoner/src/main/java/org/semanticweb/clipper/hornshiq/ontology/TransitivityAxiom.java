package org.semanticweb.clipper.hornshiq.ontology;

import lombok.Data;

@Data
public class TransitivityAxiom implements Axiom {
	private int role;

	public TransitivityAxiom(int role) {
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

}
