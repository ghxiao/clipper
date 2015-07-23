package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import java.util.Collection;
import java.util.List;


/**
 * Every object in the class Imp satisfying implication: in very model K, every
 * d that realizes Type1 also realizes Type2.
 * */
public class EnforcedRelation {
	public void setImp_rel(IndexedHornImpContainer imp_rel) {
		this.imp_rel = imp_rel;
	}

	private TIntHashSet type1;
	private TIntHashSet roles;
	private TIntHashSet type2;

	private IndexedHornImpContainer imp_rel;
	private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
	private List<ClipperInversePropertyOfAxiom> inverseRoleAxioms;
	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;

	public void setAllValuesFromAxioms(List<ClipperAtomSubAllAxiom> allValuesFromAxioms) {
		this.allValuesFromAxioms = allValuesFromAxioms;
	}

	public EnforcedRelation() {
		this.type1 = new TIntHashSet(); 
		this.type1.add(ClipperManager.getInstance().getThing());
		this.type2 = new TIntHashSet(); 
		this.type2.add(ClipperManager.getInstance().getThing());
		this.roles = new TIntHashSet();
		
	}

	// =====================
	public EnforcedRelation(int T1, int R, int T2) {
		this.type1 = new TIntHashSet();
		this.type1.add(T1);
		this.type1.add(ClipperManager.getInstance().getThing());
		this.type2 = new TIntHashSet();
		this.type2.add(T2);
		this.type2.add(ClipperManager.getInstance().getThing());
		this.roles = new TIntHashSet();
		this.roles.add(R);
	}

	// =======================
	public EnforcedRelation(TIntHashSet T1, TIntHashSet R, TIntHashSet T2) {
		this.type1 =  new TIntHashSet(T1);
		this.type1.add(ClipperManager.getInstance().getThing());
		this.type2 =  new TIntHashSet(T2);
		this.type2.add(ClipperManager.getInstance().getThing());
		this.roles = new TIntHashSet(R);
		}
	// constructor to get a clone of an EnforcedRelation
	public EnforcedRelation(EnforcedRelation clonedEnf){
		this.type1= new TIntHashSet(clonedEnf.getType1());
		this.roles= new TIntHashSet(clonedEnf.getRoles());
		this.type2= new TIntHashSet(clonedEnf.getType2());
	}
	// ===========================

	public EnforcedRelation(TIntHashSet T1, int r, TIntHashSet T2) {
		this.type1 = new TIntHashSet(T1);
		this.type1.add(ClipperManager.getInstance().getThing());
		this.type2 = new TIntHashSet(T2);
		this.type2.add(ClipperManager.getInstance().getThing());
		TIntHashSet R = new TIntHashSet();
		R.add(r);
		this.roles = R;
	}

	// =====================
	public void setType1(TIntHashSet T1) {
		this.type1 = T1;
	}

	// =====================
	public void setType2(TIntHashSet T2) {
		this.type2 = T2;
	}

	// =====================
	public void setR(TIntHashSet R) {
		this.roles = R;
	}

	// =====================
	public TIntHashSet getType1() {
		return this.type1;
	}

	// =====================
	public TIntHashSet getType2() {
		return this.type2;
	}

	// =====================
	public TIntHashSet getRoles() {
		return this.roles;
	}

	@Override
	public String toString() {
		return "{" + type1 + ", " + roles + ", " + type2 + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		EnforcedRelation other = (EnforcedRelation) obj;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
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

	

	public void setSubObjectPropertyAxioms(
			List<ClipperSubPropertyAxiom> subObjectPropertyAxioms) {
		this.subObjectPropertyAxioms = subObjectPropertyAxioms;
	}

	public void setInverseRoleAxioms(
			List<ClipperInversePropertyOfAxiom> inverseRoleAxioms) {
		this.inverseRoleAxioms = inverseRoleAxioms;
	}


	//Rule R_{\sqsubseteq}^C
	public boolean computeImplicationClosure() {

		TIntHashSet set = new TIntHashSet();
		
		boolean modified = true;
		while (modified) {

			modified = false;

			for (int at : type2.toArray()) {
				set.clear();
				set.add(at);
				Collection<HornImplication> list = imp_rel.matchBody(set);				
				for (HornImplication imp : list) {
					if (type2.containsAll(imp.getBody())) {
						if (type2.add(imp.getHead())) {
							modified = true;
						}
					}
				}
			}
		}

		return modified;
	}
	
	// apply Rule R_{\sqsubseteq}^R
	public boolean ensureRoleClosure() {
		boolean modified = false;
		boolean doMore = true;
		while (doMore) {
			doMore = false;

			for (ClipperSubPropertyAxiom ax : this.subObjectPropertyAxioms) {
				int r = ax.getRole1();
				int s = ax.getRole2();
				if (this.getRoles().contains(r) && !this.getRoles().contains(s)) {
					this.getRoles().add(s);
					doMore = true;
				}
				if (this.getRoles().contains(BitSetUtilOpt.inverseRole(r))
						&& !this.getRoles().contains(BitSetUtilOpt.inverseRole(s))) {
					this.getRoles().add(BitSetUtilOpt.inverseRole(s));
					doMore = true;
				}
			}

			for (ClipperInversePropertyOfAxiom ax : inverseRoleAxioms) {
				int r = ax.getRole1();
				int s = ax.getRole2();
				if (this.getRoles().contains(r)
						&& !this.getRoles().contains(BitSetUtilOpt.inverseRole(s))) {
					this.getRoles().add(BitSetUtilOpt.inverseRole(s));
					doMore = true;
				}

				if (this.getRoles().contains(BitSetUtilOpt.inverseRole(r))
						&& !this.getRoles().contains(s)) {
					this.getRoles().add(s);
					doMore = true;
				}
				if (this.getRoles().contains(s)
						&& !this.getRoles().contains(BitSetUtilOpt.inverseRole(r))) {
					this.getRoles().add(BitSetUtilOpt.inverseRole(r));
					doMore = true;
				}

				if (this.getRoles().contains(BitSetUtilOpt.inverseRole(s))
						&& !this.getRoles().contains(r)) {
					this.getRoles().add(r);
					doMore = true;
				}
			}

			if (doMore)
				modified = true;
		}

		return modified;
	}

	// rule R_{\forall}^+
	public boolean ensureForAllClosure() {
		boolean modified = false;

		for (ClipperAtomSubAllAxiom ax : allValuesFromAxioms) {
			if (this.getRoles().contains(ax.getRole())
					&& this.getType1().contains(ax.getConcept1())) {
				if (!this.getType2().contains(ax.getConcept2())) {
					this.getType2().add(ax.getConcept2());
					modified = true;
				}
			}
		}

		return modified;
	}
	
	  

}
