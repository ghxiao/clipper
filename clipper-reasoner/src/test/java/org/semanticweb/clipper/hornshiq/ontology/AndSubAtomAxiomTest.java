package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
