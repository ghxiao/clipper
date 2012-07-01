package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

/**
 * A1 and A2 and ... and An SubClassOf B
 * 
 * @author xiao
 * 
 */
public class AndSubAtomAxiom implements Axiom {
	public AndSubAtomAxiom(TIntHashSet left, int right) {
		super();
		this.left = left;
		this.right = right;
	}

	public AndSubAtomAxiom(int left, int right) {
		this(new TIntHashSet(new int[] { left }), right);
	}

	private TIntHashSet left;
	int right;

	@Override
	public String toString() {
		return left + " SubClassOf " + right;
	}

	public void setLeft(TIntHashSet left) {
		this.left = left;
	}

	public TIntHashSet getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}
}
