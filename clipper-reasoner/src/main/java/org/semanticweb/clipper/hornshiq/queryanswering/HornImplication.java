package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;

public class HornImplication {
	private TIntHashSet body;
	private int head;

	public HornImplication(TIntHashSet T, int head) {
		this.body = new TIntHashSet(T);
		this.head = head;
	}
	/**
	 * Make a copy of HornImplication object
	 * @param clonedImp
	 */
	public HornImplication(HornImplication clonedImp){
		this.body = clonedImp.getBody();
		this.head= clonedImp.getHead();
	}
	


	@Override
	public String toString() {
		return "{" + body + ", " + head + "}";
		// return this.type1 +" =====IMPLIES====> " + this.type2;
	}

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof HornImplication)) return false;
        final HornImplication other = (HornImplication) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$body = this.body;
        final Object other$body = other.body;
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        if (this.head != other.head) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $body = this.body;
        result = result * PRIME + ($body == null ? 0 : $body.hashCode());
        result = result * PRIME + this.head;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof HornImplication;
    }

    public TIntHashSet getBody() {
        return this.body;
    }

    public int getHead() {
        return this.head;
    }
}
