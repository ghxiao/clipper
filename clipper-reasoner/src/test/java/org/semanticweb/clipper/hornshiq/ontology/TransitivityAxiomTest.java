package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransitivityAxiomTest {

	@Test
	public void test01() {
		int r = 10;
		Axiom ax = new TransitivityAxiom(r);
		assertEquals("trans(10)", ax.toString());
		System.out.println(ax);
	}
	
	@Test
	public void test02() {
		int r = 11;
		Axiom ax = new TransitivityAxiom(r);
		assertEquals("trans(10)", ax.toString());
		System.out.println(ax);
	}
}
