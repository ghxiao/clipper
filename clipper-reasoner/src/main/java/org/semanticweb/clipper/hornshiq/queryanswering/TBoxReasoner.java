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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TBoxReasoner {

	private IndexedHornImpContainer impContainer;
	private IndexedEnfContainer enfContainer;
	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
	private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
	private List<ClipperInversePropertyOfAxiom> inverseRoleAxioms;
	private List<ClipperAtomSubMaxOneAxiom> maxOneCardinalityAxioms;
	private TIntHashSet aboxTypes;
	private Set<EnforcedRelation> newEnfs;
	private Set<HornImplication> newImps;
	private boolean inconsistent = false; // to check if the ontology is
											// consistent in case of Ontology
											// contain ABox
	private boolean hasABox = true;

	private void init(ClipperHornSHIQOntology ont_bs) {
		// initializing allValuesFromAxioms
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();

		// initializing private List<InversePropertyOfAxiom> inverseRoleAxioms;
		inverseRoleAxioms = ont_bs.getInversePropertyOfAxioms();

		// initializing maxOneCardinalityAxioms;
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();

		// initializing subObjectPropertyAxioms
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		// initialize ABoxType
		aboxTypes = new TIntHashSet();
		for (ClipperConceptAssertionAxiom ca : ont_bs.getConceptAssertionAxioms()) {
			this.aboxTypes.add(ca.getConcept());
		}
		// If Ontology contain TBox only, then set ABoxType as a set of all
		// concepts in TBox
		if (this.aboxTypes.size() == 0) {
			for (int concept = 0; concept <= ClipperManager.getInstance()
					.getOwlClassEncoder().getMax(); concept++) {
				this.aboxTypes.add(concept);
				// this.hasABox = false;
			}
		}
		this.aboxTypes.add(ClipperManager.getInstance().getThing());
		// initializing coreImp
		impContainer = new IndexedHornImpContainer();
		for (ClipperAndSubAtomAxiom axiom : ont_bs.getAndSubAtomAxioms()) {
			impContainer.add(new HornImplication(axiom.getLeft(), axiom
					.getRight()));
		}

		// initializing coreEnf
		enfContainer = new IndexedEnfContainer();
		for (ClipperAtomSubSomeAxiom axiom : ont_bs.getAtomSubSomeAxioms()) {
			EnforcedRelation enf = new EnforcedRelation(axiom.getConcept1(),
					axiom.getRole(), axiom.getConcept2());
			enfContainer.add(enf);
		}

		// initialize newEnfs and newImps
		newEnfs = new HashSet<EnforcedRelation>();
		newImps = new HashSet<HornImplication>();

	}

	public TBoxReasoner(ClipperHornSHIQOntology ont_bs) {
		init(ont_bs);
	}

	// setters and getters
	public IndexedHornImpContainer getImpContainer() {
		return impContainer;
	}

	public IndexedEnfContainer getEnfContainer() {
		return enfContainer;
	}

	public List<ClipperAtomSubAllAxiom> getAllValuesFromAxioms() {
		return allValuesFromAxioms;
	}

	public List<ClipperSubPropertyAxiom> getSubObjectPropertyAxioms() {
		return subObjectPropertyAxioms;
	}

	public List<ClipperInversePropertyOfAxiom> getInverseRoleAxioms() {
		return inverseRoleAxioms;
	}

	public List<ClipperAtomSubMaxOneAxiom> getMaxOneCardinalityAxioms() {
		return maxOneCardinalityAxioms;
	}

	public TIntHashSet getAboxTypes() {
		return aboxTypes;
	}

	public void setAboxTypes(TIntHashSet aboxTypes) {
		this.aboxTypes = aboxTypes;
	}

	/**
	 * 
	 * @param enfs
	 * @return a copy of a set of enforced relation.
	 */
	private Set<EnforcedRelation> cloneOfEnfs(Set<EnforcedRelation> enfs) {
		Set<EnforcedRelation> clonedSet = new HashSet<EnforcedRelation>();
		for (EnforcedRelation enf : enfs) {
			clonedSet.add(new EnforcedRelation(enf));
		}
		return clonedSet;

	}

	/**
	 * 
	 * @param imps
	 * @return copy of a set of HornImplication objects
	 */
	private Set<HornImplication> cloneOfImps(Set<HornImplication> imps) {
		Set<HornImplication> clonedSet = new HashSet<HornImplication>();
		for (HornImplication imp : imps) {
			clonedSet.add(new HornImplication(imp));
		}
		return clonedSet;

	}

	/**
	 * Compute Closure of ABoxType. Keep in mind that if we do reasoning with
	 * TBox only, and use this result together with ABox, Then we still need to
	 * provide the ABoxType or just set ABoxType as a set of concepts
	 * 
	 * @param imps
	 * @return
	 */
	public boolean computeAboxTypeClosure(Set<HornImplication> imps) {
		boolean update = false; // to inform other rules that there is an update
								// in aBoxTypes.
		boolean modified = true; // to compute closure of ABox Type wrt imps
		while (modified) {
			modified = false;
			for (HornImplication imp : imps) {
				if (this.aboxTypes.containsAll(imp.getBody())) {
					// if bottom in Abox Type, then ontology is inconsistent
					if (imp.getHead() == ClipperManager.getInstance().getNothing()) {
						this.inconsistent = true;
						return true;
					} else if (this.aboxTypes.add(imp.getHead())) {
						modified = true;
						update = true;
					}
				}
			}
		}
		return update;
	}

	/**
	 * enf(T1,R,T2), \bot \in T2 --> imp(T1,\bot)
	 * 
	 * @return
	 */
	private boolean bottomRule(Set<EnforcedRelation> enfs) {
		boolean modifiedIMPS = false;
		for (EnforcedRelation tuple : enfs) {
			if (tuple.getType2().contains(
					ClipperManager.getInstance().getNothing())) {
				HornImplication new_imp = new HornImplication(tuple.getType1(),
						ClipperManager.getInstance().getNothing());
				if (impContainer.add(new_imp)) {
					newImps.add(new_imp);
					modifiedIMPS = true;
				}
			}
		}
		return modifiedIMPS;
	}

	/**
	 * \sqsubseteq_1 rule
	 * 
	 * @return
	 */
	private boolean roleInclusionRule(Set<EnforcedRelation> enfs) {
		boolean update = false;
		for (EnforcedRelation tuple : enfs) {
			for (ClipperSubPropertyAxiom ax : this.subObjectPropertyAxioms) {
				int r = ax.getRole1();
				int s = ax.getRole2();
				// r is subrole of s
				if (tuple.getRoles().contains(r)
						&& !tuple.getRoles().contains(s)) {
					EnforcedRelation newTuple = new EnforcedRelation(tuple);
					newTuple.getRoles().add(s);
					if (enfContainer.add(newTuple)) {
						enfContainer.remove(tuple);
						// we only add a copy of it
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}
				}

				// r- is subrole of s-
				if (tuple.getRoles().contains(BitSetUtilOpt.inverseRole(r))
						&& !tuple.getRoles().contains(
								BitSetUtilOpt.inverseRole(s))) {

					EnforcedRelation newTuple = new EnforcedRelation(tuple);
					newTuple.getRoles().add(BitSetUtilOpt.inverseRole(s));
					if (enfContainer.add(newTuple)) {
						enfContainer.remove(tuple);
						// only add copy of it
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}

				}
			}// end of for subObjectPropertyAxioms

			// Notice: There can be the case that in ontology, there cab have
			// two ways to specify inverse role.
			// 1) explicitly specify inverse(r1,r2).
			// 2) use inverseOf(r1) as the inverse role of r1.
			// So we need to make sure that if R contain inverseOf(r1), then r2
			// should be in R
			for (ClipperInversePropertyOfAxiom ax : inverseRoleAxioms) {
				int r = ax.getRole1();
				int s = ax.getRole2();

				// IF (inverse(r,s), R contains r)
				// THEN R should contain all inverse of s
				if (tuple.getRoles().contains(r)
						&& !tuple.getRoles().contains(
								BitSetUtilOpt.inverseRole(s))) {
					EnforcedRelation newTuple = new EnforcedRelation(tuple);
					newTuple.getRoles().add(BitSetUtilOpt.inverseRole(s));
					if (this.enfContainer.add(newTuple)) {
						this.enfContainer.remove(tuple);
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}
				}

				// IF (inverse(r,s), R contains invesre of r)
				// THEN R should contain s
				if (tuple.getRoles().contains(BitSetUtilOpt.inverseRole(r))
						&& !tuple.getRoles().contains(s)) {
					EnforcedRelation newTuple = new EnforcedRelation(tuple);
					newTuple.getRoles().add(s);
					if (this.enfContainer.add(newTuple)) {
						this.enfContainer.remove(tuple);
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}
				}

				// IF (inverse(r,s), R contains s)
				// THEN R should contain all inverse of r
				if (tuple.getRoles().contains(s)
						&& !tuple.getRoles().contains(
								BitSetUtilOpt.inverseRole(r))) {
					EnforcedRelation newTuple = new EnforcedRelation(tuple);
					newTuple.getRoles().add(BitSetUtilOpt.inverseRole(r));
					if (this.enfContainer.add(newTuple)) {
						this.enfContainer.remove(tuple);
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}
				}

				// IF (inverse(r,s), R contains inverse of s)
				// THEN R should contain r
				if (tuple.getRoles().contains(BitSetUtilOpt.inverseRole(s))
						&& !tuple.getRoles().contains(r)) {
					EnforcedRelation newTuple = new EnforcedRelation(tuple);

					newTuple.getRoles().add(r);
					if (this.enfContainer.add(newTuple)) {
						this.enfContainer.remove(tuple);
						newEnfs.add(new EnforcedRelation(newTuple));
						update = true;
					}
				}
			}

		}
		return update;
	}

	/**
	 * Rule: \sqsubseteq_2
	 *
	 * @param imps
	 *            set of HornImplication object
	 * @return
	 */
	private boolean conceptInclusionRule(Set<HornImplication> imps) {
		boolean update = false;
		for (HornImplication imp : imps) {
			Collection<EnforcedRelation> matchedEnfs = this.enfContainer
					.matchType2(imp.getBody());
			for (EnforcedRelation enf : matchedEnfs) {
				if (!enf.getType2().contains(imp.getHead())) {
					EnforcedRelation newEnf = new EnforcedRelation(enf);
					newEnf.getType2().add(imp.getHead());
					if (this.enfContainer.add(newEnf)) {
						this.enfContainer.remove(enf);
						update = true;
						newEnfs.add(new EnforcedRelation(newEnf));
					}
				}
			}
		}
		return update;
	}

	/**
	 * Rule: \exists_1
	 * 
	 * @param enfs
	 * @return
	 */
	private boolean existentialRule(Set<EnforcedRelation> enfs) {
		boolean update = false;
		for (EnforcedRelation right : enfs) {

			Collection<EnforcedRelation> matchedRights = this.enfContainer
					.matchType2(right.getType1());
			for (EnforcedRelation left : matchedRights) {
				if (!right.getType1().containsAll(left.getType2())) {
					TIntHashSet T1 = new TIntHashSet(left.getType2());
					TIntHashSet R = new TIntHashSet(right.getRoles());
					TIntHashSet T2 = new TIntHashSet(right.getType2());
					EnforcedRelation enf = new EnforcedRelation(T1, R, T2);
					if (this.enfContainer.add(enf)) {
						newEnfs.add(new EnforcedRelation(enf));
						update = true;
					}
				}
			}
		}

		return update;
	}

	/**
	 * Rule: R_∀
	 *
     * <pre>
     *  M ⊑ ∃(S⊓r).N    A ⊑ ∀r.B
     *  ------------------------
     *  M ⊓ A ⊑ ∃(S ⊓ r).(N ⊓ B)
     * </pre>
     *
     *
     * Rule: R_∀-
     * <pre>
     *  M ⊑ ∃(S ⊓ inv(r)).(N ⊓ A)   A ⊑ ∀r.B
     *  ------------------------------------
     *  M ⊑ B
     * </pre>
     *
     *
	 * @param enfs
	 * @return
	 * 
	 *         Xiao: param enfs is not used inside the method, this method is
	 *         not implemented incrementally
	 */
	private boolean forAllRule(Set<EnforcedRelation> enfs) {
		boolean update = false;

		for (ClipperAtomSubAllAxiom ax : allValuesFromAxioms) {
			//  Rule: R_∀
			TIntHashSet role = new TIntHashSet();
			role.add(ax.getRole());
			TIntHashSet axType1 = new TIntHashSet();
			axType1.add(ax.getConcept1());

			//Collection<EnforcedRelation> matches = this.enfContainer.matchRolesAndType1(role, axType1);

            Collection<EnforcedRelation> matches = this.enfContainer.matchRoles(role);

			for (EnforcedRelation enf : matches) {
				EnforcedRelation newEnf = new EnforcedRelation(enf);

                newEnf.getType1().add(ax.getConcept1());
				newEnf.getType2().add(ax.getConcept2());
				if (this.enfContainer.add(newEnf)) {
					//this.enfContainer.remove(enf);
					newEnfs.add(new EnforcedRelation(newEnf));
					update = true;
				}
			}

			// Rule forAll_2 : dealing with inverse role
			TIntHashSet invRole = new TIntHashSet();
			invRole.add(BitSetUtilOpt.inverseRole(ax.getRole()));
			Collection<EnforcedRelation> matchesRolesAndType2 = this.enfContainer
					.matchRolesAndType2(invRole, axType1);

			for (EnforcedRelation enf : matchesRolesAndType2) {
				HornImplication newImp = new HornImplication(enf.getType1(),
						ax.getConcept2());
				if (this.impContainer.add(newImp)) {
					newImps.add(new HornImplication(newImp));
					update = true;
				}

			}
		}

		return update;
	}

	/**
	 * Rule : \forAll_3 ,
	 * 
	 * @param enfs
	 * @return
	 */
	private boolean forAllRuleABoxType(Set<EnforcedRelation> enfs) {
		boolean update = false;

		for (EnforcedRelation tuple : enfs) {
			for (ClipperAtomSubAllAxiom ax : allValuesFromAxioms) {
				if (tuple.getRoles().contains(ax.getRole())
						&& !tuple.getType1().contains(ax.getConcept1())
						&& this.aboxTypes.containsAll(tuple.getType1())
						&& this.aboxTypes.contains(ax.getConcept1())) {
					TIntHashSet T1 = new TIntHashSet(tuple.getType1());
					T1.add(ax.getConcept1());
					TIntHashSet R = new TIntHashSet(tuple.getRoles());
					TIntHashSet T2 = new TIntHashSet(tuple.getType2());
					T2.add(ax.getConcept2());
					EnforcedRelation enf = new EnforcedRelation(T1, R, T2);
					if (this.enfContainer.add(enf)) {
						newEnfs.add(new EnforcedRelation(enf));
						update = true;
					}

				}
			}
		}
		return update;
	}

	/**
	 * atMostRule_1: Merge children
	 * 
	 * for each A ⊑≤ 1r.C ∈ T enf(T, R1, T1),enf(T, R2, T2), A∈T,r∈R1,r∈R2, C
	 * ∈T1,C ∈T2 → enf(T,R1∪R2,T1∪T2)
	 * 
	 * @return updated
	 */
	private boolean atMostOneRule_MergeChildren() {
		boolean update = false;
		for (ClipperAtomSubMaxOneAxiom ax : maxOneCardinalityAxioms) {
			TIntHashSet axRole = new TIntHashSet();
			axRole.add(ax.getRole());

			TIntHashSet axType1 = new TIntHashSet();
			axType1.add(ax.getConcept1());

			TIntHashSet axType2 = new TIntHashSet();
			axType2.add(ax.getConcept2());

			Collection<EnforcedRelation> matchesRolesAndType2 = this.enfContainer
					.matchRolesAndType2(axRole, axType2);

			for (EnforcedRelation tuple1 : matchesRolesAndType2)
				for (EnforcedRelation tuple2 : matchesRolesAndType2) {
					if (!tuple1.equals(tuple2)) {

						TIntHashSet T1 = new TIntHashSet(tuple1.getType1());
						T1.addAll(tuple2.getType1());
						T1.add(ax.getConcept1());

						TIntHashSet R = new TIntHashSet(tuple1.getRoles());
						R.addAll(tuple2.getRoles());

						TIntHashSet T2 = new TIntHashSet(tuple1.getType2());
						T2.addAll(tuple2.getType2());
						EnforcedRelation enf = new EnforcedRelation(T1, R, T2);

						if (this.enfContainer.add(enf)) {
							newEnfs.add(new EnforcedRelation(enf));
							update = true;
						}
					}
				}

		}
		return update;
	}

	private boolean atMostRule_ParentChildCollapsed() {
		boolean update = false;
		for (ClipperAtomSubMaxOneAxiom ax : maxOneCardinalityAxioms) {
			TIntHashSet axRole = new TIntHashSet();
			axRole.add(ax.getRole());

			TIntHashSet axInvRole = new TIntHashSet();
			axInvRole.add(BitSetUtilOpt.inverseRole(ax.getRole()));

			TIntHashSet axType1 = new TIntHashSet();
			axType1.add(ax.getConcept1());

			TIntHashSet axType2 = new TIntHashSet();
			axType2.add(ax.getConcept2());

			Collection<EnforcedRelation> matchesRolesAndType2 = this.enfContainer
					.matchRolesAndType2(axInvRole, axType2);

			Collection<EnforcedRelation> matchesRolesAndTyp1AndType2 = this.enfContainer
					.matchRolesAndType1AndType2(axRole, axType1, axType2);

			for (EnforcedRelation tuple1 : matchesRolesAndType2)
				for (EnforcedRelation tuple2 : matchesRolesAndTyp1AndType2) {
					if (!tuple1.equals(tuple2)
							&& tuple1.getType2().containsAll(tuple2.getType1())) {
						for (int i : tuple2.getType2().toArray()) {
							TIntHashSet body = new TIntHashSet(
									tuple1.getType1());
							body.add(ax.getConcept2());
							HornImplication new_imp = new HornImplication(body,
									i);
							if (this.impContainer.add(new_imp)) {
								newImps.add(new HornImplication(new_imp));
								update = true;
							}
						}

						TIntHashSet T1 = new TIntHashSet(tuple1.getType1());
						T1.add(ax.getConcept2());

						TIntHashSet R = new TIntHashSet(tuple1.getRoles());
						for (int i : tuple2.getRoles().toArray()) {
							R.add(BitSetUtilOpt.inverseRole(i));
						}

						EnforcedRelation new_enf = new EnforcedRelation(T1, R,
								tuple1.getType2());
						if (this.enfContainer.add(new_enf)) {
							newEnfs.add(new EnforcedRelation(new_enf));
							update = true;
						}

					}
				}

		}
		return update;
	}

	/**
	 * Apply the saturation rules on TBox
	 */
	public void saturate() {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("Start TBox reasoning");
			System.out.println(" IMP size: " + enfContainer.getEnfs().size());
			System.out.println(" ENF size: " + impContainer.getImps().size());
			System.out.println(" ABoxType size: " + this.aboxTypes.size());
			System.out.println(" Max1 Axiom:" + this.maxOneCardinalityAxioms);
			System.out.println(" Sub roles:" + this.subObjectPropertyAxioms);
			System.out.println(" Inverse:" + this.inverseRoleAxioms);
		}
		Set<EnforcedRelation> copyOfEnfs = cloneOfEnfs(this.enfContainer
				.getEnfs());
		Set<HornImplication> copyOfImps = cloneOfImps(this.impContainer
				.getImps());
		// Apply all rule for the first time, in next times, we only run rule
		// with newEnfs, and newImps
		bottomRule(copyOfEnfs);

		copyOfEnfs = cloneOfEnfs(this.enfContainer.getEnfs());
        roleInclusionRule(copyOfEnfs);

        conceptInclusionRule(this.impContainer.getImps());

		copyOfEnfs = cloneOfEnfs(this.enfContainer.getEnfs());
        forAllRule(copyOfEnfs);

		copyOfImps = cloneOfImps(this.impContainer.getImps());
        computeAboxTypeClosure(copyOfImps);
		// This rule make sense only we have ABox
		if (this.hasABox) {
			copyOfImps = cloneOfImps(this.impContainer.getImps());
			copyOfEnfs = cloneOfEnfs(this.enfContainer.getEnfs());
			forAllRuleABoxType(copyOfEnfs);
		}


        atMostOneRule_MergeChildren();

        atMostRule_ParentChildCollapsed();

		// Apply rule only in newEnfs and newImps
		boolean update = true;
		while (update && !inconsistent) {
            Set<EnforcedRelation> copyOfNewEnfs = cloneOfEnfs(newEnfs); // \Delta_{enf}
			Set<HornImplication> copyOfNewImps = cloneOfImps(newImps); // \Delta_{imp}
			copyOfEnfs = cloneOfEnfs(this.enfContainer.getEnfs());
			copyOfImps = cloneOfImps(this.impContainer.getImps());
			this.newEnfs.clear();
			this.newImps.clear();
			update = (bottomRule(copyOfNewEnfs)); // only applied to the Delta

            update |= (roleInclusionRule(copyOfNewEnfs)); // only applied to the Delta

            update |= (conceptInclusionRule(copyOfImps)); // only applied to the Delta

			// FIXME!!!: xiao: copyOfNewEnfs is not used inside the
			// implementation,
			// this makes the algorithim is not semi-naive
            update |= forAllRule(copyOfNewEnfs);

            update |= computeAboxTypeClosure(copyOfNewImps);
			// This rule make sense only we have ABox
			if (this.hasABox)
				if (forAllRuleABoxType(copyOfNewEnfs))
					update = true;

			// FIXME: not semi-naive style
            update |= atMostOneRule_MergeChildren();

			// FIXME: not semi-naive style
            update |= atMostRule_ParentChildCollapsed();

		}
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("End of reasoning");
			System.out.println(" IMP size: " + enfContainer.getEnfs().size());
			System.out.println(" ENF size: " + impContainer.getImps().size());
			System.out.println(" ABoxType size: " + this.aboxTypes.size());
			System.out.println(" Max1 Axiom:" + this.maxOneCardinalityAxioms);
			System.out.println(" Sub roles:" + this.subObjectPropertyAxioms);
			System.out.println(" Inverse:" + this.inverseRoleAxioms);
		}
	}

}
