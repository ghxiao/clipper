package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperConceptAssertionAxiom;

public class ObjectPropertyAssertionAxiomTest {
	@Test
	public void test001() {
		ClipperConceptAssertionAxiom axiom = new ClipperConceptAssertionAxiom(0, 3);
		assertEquals("0(3)", axiom.toString());
	}
}
