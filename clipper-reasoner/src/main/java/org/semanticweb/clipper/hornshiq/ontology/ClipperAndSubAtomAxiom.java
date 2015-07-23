package org.semanticweb.clipper.hornshiq.ontology;

import gnu.trove.set.hash.TIntHashSet;

/**
 * A1 and A2 and ... and An SubClassOf B
 * 
 * @author xiao
 * 
 */
public class ClipperAndSubAtomAxiom implements ClipperTBoxAxiom {
	
	public ClipperAndSubAtomAxiom(int left, int right) {
		this(new TIntHashSet(new int[] { left }), right);
	}

	private TIntHashSet left;
	int right;

    @java.beans.ConstructorProperties({"left", "right"})
    public ClipperAndSubAtomAxiom(TIntHashSet left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
	public String toString() {
		return left + " SubClassOf " + right;
	}

	public void setLeft(TIntHashSet left) {
		this.left = left;
	}


    public TIntHashSet getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperAndSubAtomAxiom)) return false;
        final ClipperAndSubAtomAxiom other = (ClipperAndSubAtomAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$left = this.left;
        final Object other$left = other.left;
        if (this$left == null ? other$left != null : !this$left.equals(other$left)) return false;
        if (this.right != other.right) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $left = this.left;
        result = result * PRIME + ($left == null ? 0 : $left.hashCode());
        result = result * PRIME + this.right;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperAndSubAtomAxiom;
    }
}
