package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.assertEquals;

import java.util.BitSet;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubMaxOneAxiom;

public class AtomSubMaxOneAxiomTest {
	@Test
	public void test001() {
		AtomSubMaxOneAxiom axiom = new AtomSubMaxOneAxiom(0, 2, 3);
		assertEquals("0 SubClassOf 1 max 1 3", axiom.toString());
	}

	@Test
	public void test002() {
		AtomSubMaxOneAxiom axiom = new AtomSubMaxOneAxiom(0, 1, 3);
		assertEquals("0 SubClassOf inv(0) max 1 3", axiom.toString());
	}

}
