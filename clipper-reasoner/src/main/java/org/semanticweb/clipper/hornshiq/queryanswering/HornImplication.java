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
	
	public TIntHashSet getBody() {
		return this.body;
	}

	public int getHead() {
		return this.head;
	}

	public String toString() {
		return "{" + body + ", " + head + "}";
		// return this.type1 +" =====IMPLIES====> " + this.type2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + head;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HornImplication other = (HornImplication) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (head != other.head)
			return false;
		return true;
	}

	
	

}
