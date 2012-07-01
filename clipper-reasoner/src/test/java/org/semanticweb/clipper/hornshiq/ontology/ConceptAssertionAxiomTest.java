package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ObjectPropertyAssertionAxiom;

public class ConceptAssertionAxiomTest {
	@Test
	public void test001() {
		ObjectPropertyAssertionAxiom axiom = new ObjectPropertyAssertionAxiom(0, 3, 1);
		assertEquals("0(3, 1)", axiom.toString());
	}
}
