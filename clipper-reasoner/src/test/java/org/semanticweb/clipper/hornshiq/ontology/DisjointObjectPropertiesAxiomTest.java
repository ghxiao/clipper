package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperDisjointObjectPropertiesAxiom;

public class DisjointObjectPropertiesAxiomTest {

	@Test
	public void test001() {
		ClipperDisjointObjectPropertiesAxiom axiom = new ClipperDisjointObjectPropertiesAxiom(1, 3);
		assertEquals("Disj(inv(0), inv(2))", axiom.toString());
	}

	@Test
	public void test002() {
		ClipperDisjointObjectPropertiesAxiom axiom = new ClipperDisjointObjectPropertiesAxiom(2, 4);
		assertEquals("Disj(2, 4)", axiom.toString());
	}
}
