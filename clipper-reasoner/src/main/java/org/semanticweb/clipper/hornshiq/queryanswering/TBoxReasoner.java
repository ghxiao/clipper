package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.impl.hash.TIntHash;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.*;
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
    private TIntHashSet propagatingRoles;//roles that propagate concepts, derived from ClipperAtomSubAllAxiom(s)
    private Set<EnforcedRelation> newEnfs;
    private Set<HornImplication> newImps;

    private Set<ClipperAxiomActivator> axiomActivators;
    private Set<ClipperAxiomActivator> newAxiomActivators;
    private Set<ClipperOverAproxPropagation> forwardPropagation;
    private Set<ClipperOverAproxPropagation> backwardPropagation;


    private boolean inconsistent = false; // to check if the ontology is
    // consistent in case of Ontology
    // contain ABox
    private boolean hasABox = true;

    private void initOntology(ClipperHornSHIQOntology ont_bs) {
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
        newEnfs = new HashSet<>();
        newImps = new HashSet<>();

        // initialize forward and backward propagation structures
        this.forwardPropagation = new HashSet<>();
        this.backwardPropagation= new HashSet<>();

        //for each role occuring in the axioms of the form A -> (all) r.B
        //we gather the set of concepts that it forward propagates and
        //those it backpropagates
        //todo:have to initialize forward and backward propagation
        this.propagatingRoles = new TIntHashSet();
        for (ClipperAtomSubAllAxiom ax : ont_bs.getAtomSubAllAxioms()) {
            this.propagatingRoles.add(ax.getRole());
        }

        for (int role : this.propagatingRoles.toArray()) {
            ClipperOverAproxPropagation prop = new ClipperOverAproxPropagation();
            prop.setRole(role);

            for (ClipperAtomSubAllAxiom ax : ont_bs.getAtomSubAllAxioms()) {
                if (ax.getRole() == role) {
                    prop.getConcepts().add(ax.getConcept2());
                }
            }


            //when the role is inverse, then it backpropagates
            if (role % 2 == 1)
                this.backwardPropagation.add(prop);
            else
                this.forwardPropagation.add(prop);
        }


    }

    private void initAxiomEnablers(Collection<Set<Integer>> initAxiomActivators) {
        this.axiomActivators = new HashSet<>();
        this.newAxiomActivators = new HashSet<>();

        for (Set<Integer> set : initAxiomActivators) {
            axiomActivators.add(new ClipperAxiomActivator(set));
        }
    }

    public TBoxReasoner(ClipperHornSHIQOntology ont_bs) {
        initOntology(ont_bs);
    }

    public TBoxReasoner(ClipperHornSHIQOntology ont_bs, Collection<Set<Integer>> initAxiomEnablers) {
        initOntology(ont_bs);
        initAxiomEnablers(initAxiomEnablers);
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
     * @param activators
     * @return a copy of a set of axiom activators relation.
     */
    private Set<ClipperAxiomActivator> cloneOfActivators(Set<ClipperAxiomActivator> activators) {
        Set<ClipperAxiomActivator> clonedSet = new HashSet<>();
        for (ClipperAxiomActivator act : activators) {
            clonedSet.add(new ClipperAxiomActivator(act));
        }
        return clonedSet;
    }


    /**
     * @param enfs
     * @return a copy of a set of enforced relation.
     */
    private Set<EnforcedRelation> cloneOfEnfs(Set<EnforcedRelation> enfs) {
        Set<EnforcedRelation> clonedSet = new HashSet<>();
        for (EnforcedRelation enf : enfs) {
            clonedSet.add(new EnforcedRelation(enf));
        }
        return clonedSet;
    }

    /**
     * @param imps
     * @return copy of a set of HornImplication objects
     */
    private Set<HornImplication> cloneOfImps(Set<HornImplication> imps) {
        Set<HornImplication> clonedSet = new HashSet<>();
        for (HornImplication imp : imps) {
            clonedSet.add(new HornImplication(imp));
        }
        return clonedSet;

    }

    /**
     * Compute Closure of ABoxType. Keep in mind that if we do reasoning with
     * TBox only, and use this result together with ABox, Then we still need to
     * provide the ABoxType or just set ABoxType as a set of concepts
     * rl+1)
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
     * @param imps set of HornImplication object
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
     * <p>
     * <pre>
     *  M ⊑ ∃(S⊓r).N    A ⊑ ∀r.B
     *  ------------------------
     *  M ⊓ A ⊑ ∃(S ⊓ r).(N ⊓ B)
     * </pre>
     * <p>
     * <p>
     * Rule: R_∀-
     * <pre>
     *  M ⊑ ∃(S ⊓ inv(r)).(N ⊓ A)   A ⊑ ∀r.B
     *  ------------------------------------
     *  M ⊑ B
     * </pre>
     *
     * @param enfs
     * @return Xiao: param enfs is not used inside the method, this method is
     * not implemented incrementally
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


                //todo:we check here if there exists some activator that would fire the axiom
                if (!axiomApplicable(newEnf.getType1()))
                    continue;


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
     * <p>
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

        saturateActivators();//we saturate activators

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
            saturateActivators();//we saturate the activators
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


    /**
     * Apply the saturation rules on TBox with axiom enablers
     */
    public void newSaturate() {

        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("Start TBox reasoning");
            System.out.println(" IMP size: " + enfContainer.getEnfs().size());
            System.out.println(" ENF size: " + impContainer.getImps().size());
            System.out.println(" ABoxType size: " + this.aboxTypes.size());
            System.out.println(" Max1 Axiom:" + this.maxOneCardinalityAxioms);
            System.out.println(" Sub roles:" + this.subObjectPropertyAxioms);
            System.out.println(" Inverse:" + this.inverseRoleAxioms);
            System.out.println(" Axiom Activators" + this.axiomActivators);
        }
        Set<EnforcedRelation> copyOfEnfs = cloneOfEnfs(this.enfContainer
                .getEnfs());
        Set<HornImplication> copyOfImps = cloneOfImps(this.impContainer
                .getImps());

        Set<ClipperAxiomActivator> copyOfActivators = cloneOfActivators(this.axiomActivators);

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

    /*
    * recursively closes Axioms Enablers under
    * 1- deterministic rules (horn implications)
    * 2- existential rules
    * */
/*
    private void closeAxiomEnabler(ClipperAxiomActivator ae){
        //first close the enabler under existential axioms
        closeAxiomEnablerUnderExistentials(ae);

        //if the enabler is updated, then close it under deterministic axioms
        if(ae.isUnstable()) {
            ae.setUnstable(false);
            closeAxiomEnablerUnderImplication(ae);
        }

        if(ae.isUnstable()) {
            ae.setUnstable(false);
            closeAxiomEnabler(ae);
        }
    }
*/

    /**
     * Closes the enabler under deterministic axioms, in case
     * there are new concepts added it returns updated status
     * @return updated
     */
/*
    private boolean closeAxiomEnablerUnderImplication(ClipperAxiomActivator ae){
        boolean update=false;
        for (HornImplication imp : this.impContainer) {
            if(ae.getConcepts().containsAll(imp.getBody()) &&
                    !ae.getConcepts().contains(imp.getHead()))
                ae.addConcept(imp.getHead());
        }
        return update;
    }
*/

    /**
     * Closes the enabler under existential axioms, in case
     * there are new concepts added it returns updated status
     *
     * @return updated
     */
/*    private boolean closeAxiomEnablerUnderExistentials(ClipperAxiomActivator ae){
        boolean update=false;
        for(EnforcedRelation enf:this.enfContainer){
            //check if axiom is applicable to the enabler
            //and hasn't been allready applied
            if(ae.getConcepts().containsAll(enf.getType1())
                    && !ae.isProcessed(enf.getRoles())){
                //add the role to queue
                ae.addQuedRole(enf.getRoles());

                //now we check if there is some enabler that contains the successor
                //if yes then nothing
                //  otherwise add the newly created enabler to the member set axiomEnablers
                TIntHashSet succConcepts = new TIntHashSet();
                succConcepts.addAll(enf.getType2());

                for(ClipperAtomSubAllAxiom ax:this.allValuesFromAxioms){
                    //add all consequences from universal axioms that match only on role (overapproximation)
                    if(enf.getRoles().size()==1 && enf.getRoles().contains(ax.getRole()))//what about roles with conjunction
                        succConcepts.add(ax.getConcept2());
                }
                boolean found=false;
                for(ClipperAxiomActivator others:this.axiomActivators){
                    if(others.getConcepts().containsAll(succConcepts)
                            && succConcepts.containsAll(others.getConcepts())){
                        found=true;
                        break;
                    }
                }
                if(!found) {
                    ClipperAxiomActivator succ = new ClipperAxiomActivator(succConcepts,enf.getRoles());
                    succ.setUnstable(false);//everything has been allready taken care of
                }
            }
        }
        return update;
    }*/
    private void saturateActivators() {
        applyDeltaTBoxToActivators();
        saturateActivatorsWithTBox();
    }

    /**
     * This method applies the newly infered axioms (delta TBox) to
     * all activators in axiomActivators.
     *
     * @behaviour 1- Updates the status of each activator that receives changes
     * during the course of applying delta TBox to unstable=true
     * 2- adds newly introduced activators to AxiomActivators
     * @consequences 1- leaves the affected AxiomActivators in unstable condition
     * and the newly created ones
     */
    private void applyDeltaTBoxToActivators() {
        for (ClipperAxiomActivator act : axiomActivators) {
            for (EnforcedRelation enf : newEnfs) {
                applyEnfToActivator(act, enf);//and update it's status to unstable
            }

            for (HornImplication imp : newImps) {
                applyImpToActivator(act, imp);
            }
        }

        //if there are new activators infered, add them to the list
        if (newAxiomActivators.size() > 0)
            mergeWithActivators(newAxiomActivators);

        //now clear the newAxiomActivator container
        newAxiomActivators.clear();

    }

    /**
     * This method saturates axiomActivators under current TBox
     * i.e. old + newly infered axioms
     *
     * @behaviour 1- Applies the TBox to unstable AxiomActivators, and
     * keeps them in that condition as long as they change.
     * Whenever TBox has no effect in an activator, that
     * activator is updated to the status stable. The computation
     * continues, until all activators become stable, i.e. no
     * axiom in TBox is applicable to any AxiomActivator
     */
    private void saturateActivatorsWithTBox() {

        boolean changed = true;
        while (changed) {
            changed = false;//the condition that controls the saturation criteria
            for (ClipperAxiomActivator act : axiomActivators) {
                //if the activator is stable than skip
                if (!act.isUnstable())
                    continue;

                act.setUnstable(false); //set the status to stable

                //apply old enforced relations
                for (EnforcedRelation enf : enfContainer) {
                    //if any change incurs from applying the rule, then we set the flag change to true
                    //to force the iteration
                    changed = applyEnfToActivator(act, enf);//and update it's status to unstable
                }

                //apply newly enforced relations
                for (EnforcedRelation enf : newEnfs) {
                    //if any change incurs from applying the rule, then we set the flag change to true
                    //to force the iteration
                    changed = applyEnfToActivator(act, enf);//and update it's status to unstable
                }

                //apply old implied relations
                for (HornImplication imp : impContainer) {
                    //if any new concept is added to the activator from applying the rule,
                    // then we set the flag change to true to force the iteration
                    changed = applyImpToActivator(act, imp);
                }

                //apply newly implied relations
                for (HornImplication imp : newImps) {
                    //if any new concept is added to the activator from applying the rule,
                    // then we set the flag change to true to force the iteration
                    changed = applyImpToActivator(act, imp);
                }
            }

            if (newAxiomActivators.size() > 0) {
                mergeWithActivators(newAxiomActivators);
            }
        }
    }

    /*
    * If the application of an axiom alters the current activator or creates a new one
    * then it returns yes
    * */
    public boolean applyEnfToActivator(ClipperAxiomActivator act, EnforcedRelation enf) {
        boolean changed = false;
        boolean found = false;//indicates a proper activator is allerady present

        //if the enforced relation is activated by an act
        //
        //   and if there are new consequences in act
        //   then add the consequences to act and update it's status to unstable
        //        and set the return flag to true (there is change in the set of Activators)
        //
        //   and if there is no suitable successor in the list of Activators
        //   then add a new activator to the list of NewActivators
        //        and set the return flag to true (there is change in the set of Activators)
        if (act.getConcepts().containsAll(enf.getType1())) {
            //get all concepts that backpropagated to the activator (with over aproximation)
            TIntHashSet backPropConcapts = new TIntHashSet();
            for (ClipperOverAproxPropagation prop : backwardPropagation) {
                if (enf.getRoles().contains(prop.getRole()))
                    backPropConcapts.addAll(prop.getConcepts());

            }

            //if the backpropagated concepts aren't contained,
            //add them to the activator
            //and set the status of the activator to unstable and the changed flag to true
            if (!act.getConcepts().containsAll(backPropConcapts)) {
                act.getConcepts().addAll(backPropConcapts);
                act.setUnstable(true);
                changed = true;
            }

            //now we check if there exists a proper successor activator, if not we create it
            //first we get all the concepts that are forwardpropagated by the activator
            TIntHashSet fwdPropConcapts = new TIntHashSet();
            fwdPropConcapts.addAll(enf.getType2());
            for (ClipperOverAproxPropagation prop : forwardPropagation) {
                if (enf.getRoles().contains(prop.getRole()))
                    fwdPropConcapts.addAll(prop.getConcepts());
            }

            //second we iterate over the activators and check if there is one that allready
            //contains the set of concepts propagated by the role.
            // If not: we create a new activator and add it to the queue of NewActivators
            //         which are added to the set of activators at the end of the method
            //         and the changed flag to true
            for (ClipperAxiomActivator curr : this.axiomActivators) {
                if (curr.getConcepts().containsAll(fwdPropConcapts)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                newAxiomActivators.add(new ClipperAxiomActivator(fwdPropConcapts, enf.getRoles()));
                changed = true;
            }
        }
        return changed;
    }

    /*
    * If the application of an axiom alters the current activator or creates a new one
    * then it returns yes
    * */
    private boolean applyImpToActivator(ClipperAxiomActivator act, HornImplication imp) {
        boolean changed = false;
        //if the implication rule is applicable to the activator
        //   and some new concept is added to the activator
        //   then update activators status to unstable
        //        and set the return flag to true(there is change in the set of Activators)
        if (act.getConcepts().contains(imp.getHead())
                && !act.getConcepts().containsAll(imp.getBody())) {
            act.getConcepts().addAll(imp.getBody());
            act.setUnstable(true);
            changed = true;
        }
        return changed;
    }

    /*This method checks if the axiom is applicable, i.e.
      if there is some A in AxiomActivators s.t. LHS subseteq A*/
    private boolean axiomApplicable(TIntHashSet lhs) {
        boolean applicable = false;

        for (ClipperAxiomActivator act : axiomActivators) {
            if (act.getConcepts().containsAll(lhs)) {
                applicable = true;
                break;
            }

        }
        return applicable;
    }

    private void mergeWithActivators(Set<ClipperAxiomActivator> newAxiomActivators) {
        //add the new activators to the set
        axiomActivators.addAll(cloneOfActivators(newAxiomActivators));

        //clear the container of newAxiomActivators
        newAxiomActivators.clear();
    }


}
