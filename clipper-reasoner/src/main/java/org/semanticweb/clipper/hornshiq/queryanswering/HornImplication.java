package org.semanticweb.clipper.hornshiq.queryanswering;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import gnu.trove.set.hash.TIntHashSet;

@EqualsAndHashCode
public class HornImplication {
	@Getter
	private TIntHashSet body;
	@Getter
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

}
