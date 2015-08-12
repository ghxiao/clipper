package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAndSubAtomAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubSomeAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperConceptAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Using TBoxReasoing instead
 * @author xiao
 *
 */
@Deprecated
public class TBoxReasoningOpt {

	private IndexedHornImpContainer imp_relation;
	private IndexedEnfContainer enf_relation;

	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
	private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
	private List<ClipperInversePropertyOfAxiom> inverseRoleAxioms;
	private List<ClipperAtomSubMaxOneAxiom> maxOneCardinalityAxioms;

	// protected int noThing = 1;
	// protected int thing = 0;
	// protected int toProperty = 0;
	// protected int bottomProperty = 2;

	private TIntHashSet aboxType;

	public TBoxReasoningOpt(ClipperHornSHIQOntology ont_bs,
			TIntHashSet aboxType) {

		init(ont_bs);

		this.aboxType = BitSetUtilOpt.getInstanceWithTop(aboxType);

	}

	protected void init(ClipperHornSHIQOntology ont_bs) {
		// initializing allValuesFromAxioms
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();

		// initializing private List<InversePropertyOfAxiom> inverseRoleAxioms;
		inverseRoleAxioms = ont_bs.getInversePropertyOfAxioms();

		// initializing maxOneCardinalityAxioms;
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();

		// initializing subObjectPropertyAxioms
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		// initializing coreImp
		imp_relation = new IndexedHornImpContainer();
		for (ClipperAndSubAtomAxiom axiom : ont_bs.getAndSubAtomAxioms()) {
			imp_relation.add(new HornImplication(axiom.getLeft(), axiom
					.getRight()));
		}

		// initializing coreEnf
		enf_relation = new IndexedEnfContainer();
		for (ClipperAtomSubSomeAxiom axiom : ont_bs.getAtomSubSomeAxioms()) {
			EnforcedRelation enf = new EnforcedRelation(axiom.getConcept1(),
					axiom.getRole(), axiom.getConcept2());

			enf.setImp_rel(imp_relation);
			enf.setInverseRoleAxioms(inverseRoleAxioms);
			enf.setSubObjectPropertyAxioms(subObjectPropertyAxioms);
			enf.setAllValuesFromAxioms(allValuesFromAxioms);
			enf_relation.add(enf);
		}

	}

	public TBoxReasoningOpt(ClipperHornSHIQOntology ont_bs) {
		init(ont_bs);

		this.aboxType = BitSetUtilOpt.getInstanceWithTop();
		for (ClipperConceptAssertionAxiom ca : ont_bs.getConceptAssertionAxioms()) {
			this.aboxType.add(ca.getConcept());
		}
		//
		// for (int concept = 0; concept <= KaosManager.getInstance()
		// .getOwlClassEncoder().getMax(); concept++) {
		// this.aboxType.add(concept);
		// }

	}

	/**
	 * apply rule \f$R_{\bot}$ </br>
	 * 
	 * $enf(T1,R,T2), \bot \in T2 --> imp(T1,\bot)$
	 * 
	 * @return true if new axioms are inferred
	 */
	private boolean bottomRule() {
		boolean modifiedIMPS = false;
		for (EnforcedRelation tuple : this.enf_relation) {
			if (tuple.getType2().contains(
					ClipperManager.getInstance().getNothing())) {
				HornImplication new_imp = new HornImplication(tuple.getType1(),
						ClipperManager.getInstance().getNothing());
				if (imp_relation.add(new_imp)) {
					this.enf_relation.updateNewImpl(new_imp);
					this.computeAboxTypeClosure();
					modifiedIMPS = true;
				}
			}
		}
		return modifiedIMPS;
	}

	private Collection<EnforcedRelation> infRule_ForAllRule_AboxType() {

		Collection<EnforcedRelation> addition = new ArrayList<EnforcedRelation>();

		for (EnforcedRelation tuple : this.enf_relation) {
			for (ClipperAtomSubAllAxiom ax : allValuesFromAxioms) {

				if (tuple.getRoles().contains(ax.getRole())
						&& !tuple.getType1().contains(ax.getConcept1())
						&& this.aboxType.containsAll(tuple.getType1())
						&& this.aboxType.contains(ax.getConcept1())) {
					TIntHashSet T1 = BitSetUtilOpt.getInstanceWithTop(tuple
							.getType1());
					T1.add(ax.getConcept1());
					TIntHashSet R = BitSetUtilOpt.getInstanceWithTop(tuple
							.getRoles());
					TIntHashSet T2 = BitSetUtilOpt.getInstanceWithTop(tuple
							.getType2());
					T2.add(ax.getConcept2());

					EnforcedRelation enf = new EnforcedRelation(T1, R, T2);
					enf.setImp_rel(imp_relation);
					enf.setInverseRoleAxioms(inverseRoleAxioms);
					enf.setSubObjectPropertyAxioms(subObjectPropertyAxioms);
					enf.setAllValuesFromAxioms(allValuesFromAxioms);
					enf.ensureRoleClosure();
					enf.ensureForAllClosure();
					enf.computeImplicationClosure();
					addition.add(enf);
				}
			}
		}
		return addition;
	}

	private Collection<EnforcedRelation> infRule_EnfAssupmptionAugment() {

		Collection<EnforcedRelation> addition = new ArrayList<EnforcedRelation>();

		for (EnforcedRelation right : this.enf_relation) {

			Collection<EnforcedRelation> selection = this.enf_relation
					.matchType2(right.getType1());

			for (EnforcedRelation left : selection) {

				if (!right.getType1().containsAll(left.getType2())) {
					TIntHashSet T1 = BitSetUtilOpt.getInstanceWithTop(left
							.getType2());
					TIntHashSet R = BitSetUtilOpt.getInstanceWithTop(right
							.getRoles());
					TIntHashSet T2 = BitSetUtilOpt.getInstanceWithTop(right
							.getType2());
					EnforcedRelation enf = new EnforcedRelation(T1, R, T2);
					enf.setImp_rel(imp_relation);
					enf.setInverseRoleAxioms(inverseRoleAxioms);
					enf.setSubObjectPropertyAxioms(subObjectPropertyAxioms);
					enf.setAllValuesFromAxioms(allValuesFromAxioms);
					addition.add(enf);
				}
			}
		}
		return addition;
	}

	/**
	 * Apply rule R_\forall^{-}
	 * 
	 * <pre>
	 *  enf(T1, R, T2), inv(r) \in R, A \in T2 -> imp(T1, {B}).
	 * </pre>
	 * @return
	 */
	private boolean infRule_ForAllRule_Inverse() {
		boolean modifiedIMPS = false;

		for (EnforcedRelation tuple : this.enf_relation) {
			for (ClipperAtomSubAllAxiom ax : allValuesFromAxioms) {
				if (tuple.getRoles().contains(
						BitSetUtilOpt.inverseRole(ax.getRole()))
						&& tuple.getType2().contains(ax.getConcept1())) {

					HornImplication new_imp = new HornImplication(
							tuple.getType1(), ax.getConcept2());

					if (imp_relation.add(new_imp)) {
						this.enf_relation.updateNewImpl(new_imp);
						this.computeAboxTypeClosure();
						modifiedIMPS = true;
					}
				}
			}
		}
		return modifiedIMPS;
	}

	private Collection<EnforcedRelation> infRule_AtMostRule_MergeChildren() {

		Collection<EnforcedRelation> addition = new ArrayList<EnforcedRelation>();

		for (ClipperAtomSubMaxOneAxiom ax : maxOneCardinalityAxioms) {
			for (EnforcedRelation tuple1 : this.enf_relation) {
				if (tuple1.getRoles().contains(ax.getRole())
						&& tuple1.getType2().contains(ax.getConcept2())) {
					for (EnforcedRelation tuple2 : this.enf_relation) {
						if (tuple2.getRoles().contains(ax.getRole())
								&& tuple2.getType2().contains(ax.getConcept2())) {
							TIntHashSet T1 = BitSetUtilOpt
									.getInstanceWithTop(tuple1.getType1());
							T1.addAll(tuple2.getType1());
							T1.add(ax.getConcept1());

							TIntHashSet R = BitSetUtilOpt
									.getInstanceWithTop(tuple1.getRoles());
							R.addAll(tuple2.getRoles());

							TIntHashSet T2 = BitSetUtilOpt
									.getInstanceWithTop(tuple1.getType2());
							T2.addAll(tuple2.getType2());
							EnforcedRelation enf = new EnforcedRelation(T1, R,
									T2);
							enf.setImp_rel(imp_relation);
							enf.setInverseRoleAxioms(inverseRoleAxioms);
							enf.setSubObjectPropertyAxioms(subObjectPropertyAxioms);
							enf.setAllValuesFromAxioms(allValuesFromAxioms);
							enf.ensureRoleClosure();
							enf.ensureForAllClosure();
							enf.computeImplicationClosure();
							addition.add(enf);
						}
					}
				}
			}
		}
		return addition;
	}

	private Collection<EnforcedRelation> infRule_AtMostRule_ParentChildCollapse() {
		Collection<EnforcedRelation> addition = new ArrayList<EnforcedRelation>();

		for (ClipperAtomSubMaxOneAxiom ax : maxOneCardinalityAxioms) {
			for (EnforcedRelation tuple1 : this.enf_relation) {
				if (tuple1.getRoles().contains(
						BitSetUtilOpt.inverseRole(ax.getRole()))
						&& tuple1.getType2().contains(ax.getConcept1())) {
					for (EnforcedRelation tuple2 : this.enf_relation) {
						if (tuple2.getType1().contains(ax.getConcept1())
								&& tuple1.getType2().containsAll(
										tuple2.getType1())
								&& tuple2.getRoles().contains(ax.getRole())
								&& (tuple2.getType2()
										.contains(ax.getConcept2()) || ax
								// .getConcept2() == this.thing)) {
										.getConcept2() == ClipperManager
										.getInstance().getThing())) {

							for (int i : tuple2.getType2().toArray()) {
								TIntHashSet body = BitSetUtilOpt
										.getInstanceWithTop(tuple1.getType1());
								body.add(ax.getConcept2());

								HornImplication new_imp = new HornImplication(
										body, i);
								if (this.imp_relation.add(new_imp)) {
									this.enf_relation.updateNewImpl(new_imp);
									this.computeAboxTypeClosure();
								}
							}

							TIntHashSet T1 = BitSetUtilOpt
									.getInstanceWithTop(tuple1.getType1());
							T1.add(ax.getConcept2());

							TIntHashSet R = BitSetUtilOpt
									.getInstanceWithTop(tuple1.getRoles());
							for (int i : tuple2.getRoles().toArray()) {
								R.add(BitSetUtilOpt.inverseRole(i));
							}

							EnforcedRelation enf = new EnforcedRelation(T1, R,
									tuple1.getType2());
							enf.setImp_rel(imp_relation);
							enf.setInverseRoleAxioms(inverseRoleAxioms);
							enf.setSubObjectPropertyAxioms(subObjectPropertyAxioms);
							enf.setAllValuesFromAxioms(allValuesFromAxioms);
							enf.ensureRoleClosure();
							enf.ensureForAllClosure();
							enf.computeImplicationClosure();
							addition.add(enf);
						}
					}
				}
			}

		}

		return addition;

	}

	public boolean computeAboxTypeClosure() {

		TIntHashSet set = BitSetUtilOpt.getInstanceWithTop();

		boolean modified = true;
		while (modified) {

			modified = false;

			for (int at : this.aboxType.toArray()) {
				set.clear();
				set.add(at);
				Collection<HornImplication> list = this.imp_relation
						.matchBody(set);
				for (HornImplication imp : list) {
					if (this.aboxType.containsAll(imp.getBody())) {
						if (this.aboxType.add(imp.getHead())) {
							modified = true;
						}
					}
				}
			}
		}

		return modified;
	}

	/**
	 * TBox saturation
	 * 
	 */
	public void reasoning() {

		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("Start IMP size: " + imp_relation.size());
			System.out.println("Start ENF size: " + enf_relation.size());
			System.out.println("Start ABoxType size: " + this.aboxType.size());
		}

		boolean modified = true;
		Collection<EnforcedRelation> addition;

		while (modified) {
			modified = false;
			for (EnforcedRelation tuple : enf_relation) {
				tuple.ensureRoleClosure();
				tuple.ensureForAllClosure();
				tuple.computeImplicationClosure();
			}

			if (infRule_ForAllRule_Inverse())
				modified = true;
			if (bottomRule())
				modified = true;

			addition = infRule_ForAllRule_AboxType();
			if (this.enf_relation.addAll(addition)) {
				modified = true;
				addition.clear();
			}

			addition = infRule_AtMostRule_MergeChildren();
			if (this.enf_relation.addAll(addition)) {
				modified = true;
				addition.clear();
			}

			addition = infRule_AtMostRule_ParentChildCollapse();
			if (this.enf_relation.addAll(addition)) {
				modified = true;
				addition.clear();

			}

			addition = infRule_EnfAssupmptionAugment();
			if (this.enf_relation.addAll(addition)) {
				modified = true;
				addition.clear();
			}

			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println("ITERATION STATS:");
				System.out.println("IMP size: " + imp_relation.size());
				System.out.println("ENF size: " + enf_relation.size());
				System.out.println("ABoxType size: " + this.aboxType.size());
			}

		}

	}

	public void setCoreImps(IndexedHornImpContainer coreImps) {
		this.imp_relation = coreImps;
	}

	public void setCoreEnfs(IndexedEnfContainer coreEnfs) {
		this.enf_relation = coreEnfs;
	}

	public List<ClipperInversePropertyOfAxiom> getInverseRoleAxioms() {
		return inverseRoleAxioms;
	}

	public void setInverseRoleAxioms(
			List<ClipperInversePropertyOfAxiom> inverseRoleAxioms) {
		this.inverseRoleAxioms = inverseRoleAxioms;
	}

	public IndexedHornImpContainer getIndexedHornImpContainer() {
		return imp_relation;
	}

	public IndexedEnfContainer getIndexedEnfContainer() {
		return enf_relation;
	}

	public List<ClipperAtomSubAllAxiom> getAllValuesFromAxioms() {
		return allValuesFromAxioms;
	}

}
