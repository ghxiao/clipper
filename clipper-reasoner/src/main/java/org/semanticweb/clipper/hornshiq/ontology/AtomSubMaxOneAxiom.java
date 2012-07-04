package org.semanticweb.clipper.hornshiq.ontology;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SubClassOf(concept1, role 1 max concept2)
 * */
@Data
@AllArgsConstructor
public class AtomSubMaxOneAxiom implements Axiom {

	int concept1;
	int role;
	int concept2;

	@Override
	public String toString() {
		String r = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r = "inv(" + r + ")";
		}

		return String.format("%d SubClassOf %s max1 %d", concept1, r, concept2);
	}
}
