package org.semanticweb.clipper.hornshiq.ontology;

import static org.junit.Assert.*;

import gnu.trove.set.hash.TIntHashSet;

import java.util.BitSet;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.AndSubAtomAxiom;

public class AndSubAtomAxiomTest {
	@Test
	public void test001() {
		TIntHashSet left = new TIntHashSet();
		left.add(0);
		left.add(2);
		int right = 1;
		AndSubAtomAxiom axiom = new AndSubAtomAxiom(left, right);
		assertEquals("{0, 2} SubClassOf 1", axiom.toString());
	}
}
