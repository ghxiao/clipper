package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A1 and A2 and ... and An SubClassOf B
 * 
 * @author xiao
 * 
 */
@Data
@AllArgsConstructor
public class AndSubAtomAxiom implements Axiom {
	
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


}
