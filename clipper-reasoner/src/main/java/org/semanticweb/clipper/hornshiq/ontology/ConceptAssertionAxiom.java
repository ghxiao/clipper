package org.semanticweb.clipper.hornshiq.ontology;

public class ConceptAssertionAxiom implements Axiom {
	public ConceptAssertionAxiom(int concept, int individual) {
		super();
		this.concept = concept;
		this.individual = individual;
	}

	int concept;

	int individual;

	public int getConcept() {
		return concept;
	}

	public void setConcept(int concept) {
		this.concept = concept;
	}

	public int getIndividual() {
		return individual;
	}

	public void setIndividual(int individual) {
		this.individual = individual;
	}

	@Override
	public String toString() {
		return String.format("%d(%d)", concept, individual);
	}
}
