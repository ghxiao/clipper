package org.semanticweb.clipper.hornshiq.ontology;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClipperConceptAssertionAxiom implements ClipperABoxAxiom {

	int concept;

	int individual;

	@Override
	public String toString() {
		return String.format("%d(%d)", concept, individual);
	}
}
