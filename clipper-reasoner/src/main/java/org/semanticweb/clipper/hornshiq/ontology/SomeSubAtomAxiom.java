package org.semanticweb.clipper.hornshiq.ontology;
/**
 *  SubclassOf ( concept1 SOME role, concept2)
 * **/
public class SomeSubAtomAxiom implements Axiom {

	int concept1;
	int role;
	int concept2;

	public SomeSubAtomAxiom(int concept1, int role, int concept2) {
		super();
		this.concept1 = concept1;
		this.role = role;
		this.concept2 = concept2;
	}

	public int getConcept1() {
		return concept1;
	}

	public void setConcept1(int concept1) {
		this.concept1 = concept1;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getConcept2() {
		return concept2;
	}

	public void setConcept2(int concept2) {
		this.concept2 = concept2;
	}

	@Override
	public String toString() {
		String r = String.valueOf((role) / 2 * 2);

		/* inverse role */
		if (role % 2 == 1) {
			r = "inv(" + r + ")";
		}

		return String.format(" %d some %s SubClassOf %d", concept1, r, concept2);
	}
}
