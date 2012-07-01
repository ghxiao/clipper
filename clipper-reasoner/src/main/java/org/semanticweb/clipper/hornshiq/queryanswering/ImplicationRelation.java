package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.BitSet;

/**
 * Every object in the class Imp satisfying implication: in very model K, every
 * d that realizes Type1 also realizes Type2.
 * */
@Deprecated
public class ImplicationRelation {
	private BitSet type1;
	private BitSet type2;

	// ======================================
	public ImplicationRelation() {
		this.type1 = new BitSet();
		this.type2 = new BitSet();
	}

	// ========================================
	public ImplicationRelation(BitSet T1, BitSet T2) {
		this.type1 = (BitSet) T1.clone();
		this.type2 = (BitSet) T2.clone();
	}

	/**
	 * 
	 * @param c
	 *            OWLClass
	 * 
	 * @param T2
	 */
	public ImplicationRelation(int c, BitSet T2) {
		this.type1 = new BitSet();
		type1.set(c);
		this.type2 = (BitSet) T2.clone();
	}

	/**
	 * 
	 * @param T1
	 * @param c
	 *            OWLClass
	 */
	public ImplicationRelation(BitSet T1, int c) {
		this.type1 = (BitSet) T1.clone();
		BitSet T2 = new BitSet();
		T2.set(c);
		this.type2 = T2;
	}

	/**
	 * 
	 * @param c1
	 *            OWLClass
	 * @param c2
	 *            OWLClass
	 */
	public ImplicationRelation(int c1, int c2) {
		this.type1 = new BitSet();
		type1.set(c1);
		this.type2 = new BitSet();
		type2.set(c2);
	}

	/*
	 * */
	public ImplicationRelation(BitSet T1, BitSet T2, BitSet T2plus) {
		this.type1 = (BitSet) T1.clone();
		BitSet temp = new BitSet();
		temp.or(T2);
		temp.or(T2plus);
		this.type2 = temp;
	}

	// =====================
	public void setType1(BitSet T1) {
		this.type1 = T1;
	}

	// =====================
	public void setType2(BitSet T2) {
		this.type2 = T2;
	}

	// =====================
	public BitSet getType1() {
		return this.type1;
	}

	// =====================
	public BitSet getType2() {
		return this.type2;
	}

	// ========================
	public boolean equalsType1(BitSet t1) {

		return this.getType1().equals(t1);

	}

	// ========================
	public boolean equalsType2(BitSet t2) {
		return this.getType2().equals(t2);
	}

	public boolean isNotTrivial() {
		return (!type1.equals(type2));
	}

	// ==========override=================
	// @Override
	// public boolean equals(Object obj) {
	// if(this == obj) {
	// return true;
	// }
	//
	// if(obj.getClass() != getClass()){
	// return false;
	// }
	// // if (!(obj instanceof ImplicationRelation)) {
	// // return false;
	// // }
	// ImplicationRelation impObject = (ImplicationRelation)obj;
	// return this.equalsType1(impObject.getType1()) &&
	// this.equalsType2(impObject.getType2());
	//
	// }
	// =======Override=============
	/*
	 * @Override public int hashCode () { final int multiplier = 31; int code =
	 * 1; code = multiplier * code + this.type1.hashCode(); code = multiplier *
	 * code + this.type2.hashCode(); //hashCode = code;
	 * 
	 * return code; }
	 */
	// @Override
	// public int hashCode(){
	// return 0;
	// }
	// ==========override========
	// public String toString() {
	// String s1 = new String("{");
	// for (OWLClass oc : type1) {
	// s1 = s1 + oc.getIRI().getFragment() + ",";
	// }
	// s1 = s1 + "}";
	//
	// String s2 = new String("{");
	// for (OWLClass oc2 : type2) {
	// s2 = s2 + oc2.getIRI().getFragment() + ",";
	// }
	// s2 = s2 + "}";
	// String s = String.format("%50s%5s%s%5s%s", s1, "", "==implies=>", "",
	// s2);
	// return s;
	// // return this.type1 +" =====IMPLIES====> " + this.type2;
	// }

	public String toString() {
		return "{" + type1 + ", " + type2 + "}";
		// return this.type1 +" =====IMPLIES====> " + this.type2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type1 == null) ? 0 : type1.hashCode());
		result = prime * result + ((type2 == null) ? 0 : type2.hashCode());
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
		ImplicationRelation other = (ImplicationRelation) obj;
		if (type1 == null) {
			if (other.type1 != null)
				return false;
		} else if (!type1.equals(other.type1))
			return false;
		if (type2 == null) {
			if (other.type2 != null)
				return false;
		} else if (!type2.equals(other.type2))
			return false;
		return true;
	}

	// TODO
	// // ==============update or add types
	// public boolean updateType1byAdding(BitSet s) {
	// return this.type1.addAll(s);
	// }
	//
	// public boolean updateType2byAdding(BitSet s) {
	// return this.type2.addAll(s);
	// }
	//
	// public boolean updateType2byAdding(OWLClass c) {
	// return this.type2.add(c);
	// }

}
