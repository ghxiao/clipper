package org.semanticweb.clipper.hornshiq.ontology;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConceptAssertionAxiomTest {
	@Test
	public void test001() {
		ClipperPropertyAssertionAxiom axiom = new ClipperPropertyAssertionAxiom(0, 3, 1);
		assertEquals("0(3, 1)", axiom.toString());
	}
}
