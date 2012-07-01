package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.DisjointObjectPropertiesAxiom;

public class DisjointObjectPropertiesAxiomTest {

	@Test
	public void test001() {
		DisjointObjectPropertiesAxiom axiom = new DisjointObjectPropertiesAxiom(1, 3);
		assertEquals("Disj(inv(0), inv(2))", axiom.toString());
	}

	@Test
	public void test002() {
		DisjointObjectPropertiesAxiom axiom = new DisjointObjectPropertiesAxiom(2, 4);
		assertEquals("Disj(2, 4)", axiom.toString());
	}
}
