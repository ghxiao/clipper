package org.semanticweb.clipper.hornshiq.ontology;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectPropertyAssertionAxiom implements Axiom {
	int role;

	int individual1, individual2;
	
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
