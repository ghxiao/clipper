package org.semanticweb.clipper.hornshiq.ontology;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SubClassOf (concept1,role  min n  concept2)
 * 
 *
 */
@Data
@AllArgsConstructor
public class AtomSubMinAxiom implements Axiom {

	int concept1;
	int role;
	int concept2;
	int n;

	
	@Override
	public String toString() {
		String r = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r = "inv(" + r + ")";
		}

		return String.format("%d SubClassOf %d min %d %d", concept1, r, n, concept2);
	}
}
