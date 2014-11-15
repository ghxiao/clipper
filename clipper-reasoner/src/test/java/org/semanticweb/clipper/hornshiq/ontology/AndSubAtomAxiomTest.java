package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.*;

import gnu.trove.set.hash.TIntHashSet;

import java.util.BitSet;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAndSubAtomAxiom;

public class AndSubAtomAxiomTest {
	@Test
	public void test001() {
		TIntHashSet left = new TIntHashSet();
		left.add(0);
		left.add(2);
		int right = 1;
		ClipperAndSubAtomAxiom axiom = new ClipperAndSubAtomAxiom(left, right);

		assertTrue(axiom.getLeft().contains(0));
		assertTrue(axiom.getLeft().contains(2));
		assertFalse(axiom.getLeft().contains(1));
		//assertEquals("{0, 2} SubClassOf 1", axiom.toString());
	}
}
