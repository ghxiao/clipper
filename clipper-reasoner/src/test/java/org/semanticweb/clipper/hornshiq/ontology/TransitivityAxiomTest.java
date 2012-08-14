package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransitivityAxiomTest {

	@Test
	public void test01() {
		int r = 10;
		ClipperAxiom ax = new ClipperTransitivityAxiom(r);
		assertEquals("trans(10)", ax.toString());
		System.out.println(ax);
	}
	
	@Test
	public void test02() {
		int r = 11;
		ClipperAxiom ax = new ClipperTransitivityAxiom(r);
		assertEquals("trans(10)", ax.toString());
		System.out.println(ax);
	}
}
