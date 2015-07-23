package org.semanticweb.clipper.hornshiq.ontology;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectPropertyAssertionAxiomTest {
	@Test
	public void test001() {
		ClipperConceptAssertionAxiom axiom = new ClipperConceptAssertionAxiom(0, 3);
		assertEquals("0(3)", axiom.toString());
	}
}
