package org.semanticweb.clipper.hornshiq.queryanswering;

/* ReachBottom is a class representing inconsistent Type. A type is a set of OWLClass.
 * */
import java.util.BitSet;
/* ReachBottom is a class representing inconsistent Type. A type is a set of OWLClass.
 * */

@Deprecated
public class ReachBottom {
	private BitSet type;


	public  ReachBottom(){
		this.type = new BitSet();
	}
	
	public ReachBottom(BitSet t){
		this.type = t;
	}
	//==================

	

	@Override
	public String toString() {
		return "ReachBottom [type=" + type + "]";
	}

	public BitSet getType() {
		return type;
	}

	public void setType(BitSet type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReachBottom other = (ReachBottom) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
