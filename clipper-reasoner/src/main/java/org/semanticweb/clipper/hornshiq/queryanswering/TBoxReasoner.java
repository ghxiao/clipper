package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.*;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


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

    private final boolean withAxiomActivators;

    private Set<ClipperAxiomActivator> axiomActivators;
    private Set<ClipperAxiomActivator> newAxiomActivators;
    private Set<ClipperOverAproxPropagation> forwardPropagation;
    private Set<ClipperOverAproxPropagation> backwardPropagation;

    private int saturateIterator = 0;

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


//        for(HornImplication imp:impContainer)
//            System.out.println(imp.getHead()+"-"+imp.getBody());

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
        propagateConcepts(ont_bs);

    }

    private void propagateConcepts(ClipperHornSHIQOntology ontology) {
        // initialize forward and backward propagation structures
        this.forwardPropagation = new HashSet<>();
        this.backwardPropagation = new HashSet<>();


        //for each role occuring in the axioms of the form A -> (all) r.B
        //we gather the set of concepts that it forward propagates and
        //those it backpropagates
/*        final Set<Integer> propagatingRoles =
                ontology.getAtomSubAllAxioms()
                        .stream()
                        .map(ClipperAtomSubAllAxiom::getRole)
                        .collect(toSet());*/

        Set<Integer> propagatingRoles = new HashSet<>();
        for (ClipperAtomSubAllAxiom ax : ontology.getAtomSubAllAxioms()) {
            int role = BitSetUtilOpt.getRolename(ax.getRole());
            propagatingRoles.add(role);
        }

        for (int role : propagatingRoles) {
            ClipperOverAproxPropagation fwdProp = new ClipperOverAproxPropagation();
            ClipperOverAproxPropagation bckProp = new ClipperOverAproxPropagation();

            fwdProp.setRole(role);
            bckProp.setRole(role);

            for (ClipperAtomSubAllAxiom ax : ontology.getAtomSubAllAxioms()) {
                boolean axRoleInverse = false;
                int propagatedConcept = ax.getConcept2();

                if (role % 2 == 1)
                    axRoleInverse = true;

                //get only the atomic role
                int axRole = BitSetUtilOpt.getRolename(ax.getRole());

                //if the roles do not match continue to next iteration
                if (role != axRole)
                    continue;

                if (axRoleInverse) {
                    bckProp.getConcepts().add(propagatedConcept);
                } else {
                    fwdProp.getConcepts().add(propagatedConcept);
                }
            }

            this.backwardPropagation.add(bckProp);
            this.forwardPropagation.add(bckProp);
        }
    }

    private void initAxiomActivators(Collection<Set<Integer>> initAxiomActivators) {
        this.newAxiomActivators = new HashSet<>();

        this.axiomActivators = initAxiomActivators
                .stream()
                .map(ClipperAxiomActivator::new)
                .collect(toSet());
    }

    public TBoxReasoner(ClipperHornSHIQOntology ont_bs) {
        this.withAxiomActivators = false;

        initOntology(ont_bs);
    }

    public TBoxReasoner(ClipperHornSHIQOntology ont_bs, Collection<Set<Integer>> initAxiomActivators) {
        this.withAxiomActivators = true;
        initOntology(ont_bs);
        initAxiomActivators(initAxiomActivators);
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

                //todo:we check here if there exists some activator that would fire the axiom
                //todo: check code
                //TIntHashSet head = new TIntHashSet(new_imp.getHead());
                if (withAxiomActivators && !axiomApplicable(new_imp.getBody()))
                    continue;

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

                    //todo:we check here if there exists some activator that would fire the axiom
                    //todo: check code
                    if (withAxiomActivators && !axiomApplicable(newTuple.getType1()))
                        continue;

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

                    //todo:we check here if there exists some activator that would fire the axiom
                    //todo: check code
                    if (withAxiomActivators && !axiomApplicable(enf.getType1()))
                        continue;

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
                if (withAxiomActivators && !axiomApplicable(newEnf.getType1())) {
                    System.err.println("Activator helps!");
                    continue;
                }


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

                        //todo:we check here if there exists some activator that would fire the axiom
                        //todo: check code
                        if (withAxiomActivators && !axiomApplicable(enf.getType1()))
                            continue;

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
                            TIntHashSet body = new TIntHashSet(tuple1.getType1());
                            body.add(ax.getConcept2());
                            HornImplication new_imp = new HornImplication(body, i);

                            //todo:we check here if there exists some activator that would fire the axiom
                            //todo: check code
                            //TIntHashSet head = new TIntHashSet(new_imp.getHead());
                            if (withAxiomActivators && !axiomApplicable(body))
                                continue;

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

                        //todo:we check here if there exists some activator that would fire the axiom
                        //todo: check code
                        if (withAxiomActivators && !axiomApplicable(new_enf.getType1()))
                            continue;

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

        if (withAxiomActivators)
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
        int counter = 0;
        while (update && !inconsistent) {
            if (withAxiomActivators)
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

            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println("Iteration " + counter);
                System.out.println(" IMP size: " + enfContainer.getEnfs().size());
                System.out.println(" ENF size: " + impContainer.getImps().size());
                System.out.println(" ABoxType size: " + this.aboxTypes.size());
                System.out.println(" Max1 Axiom:" + this.maxOneCardinalityAxioms);
                System.out.println(" Sub roles:" + this.subObjectPropertyAxioms);
                System.out.println(" Inverse:" + this.inverseRoleAxioms);
                System.out.println(" Activators: " + this.axiomActivators.size());
            }
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
     * Saturates the activators with current TBox
     * 1- it applies the newly infered axioms to all activators
     * 2- it applies all TBox axioms (enf and imp) to un-processed activators
     */
    private void saturateActivators() {
        boolean saturated = false;

        while (!saturated) {
            saturated = true;
            System.out.println("calling Saturate Activators");
            saturated = saturated && !saturateActivatorsWithDeltaTBox();
            saturateActivatorsWithTBox();
            saturateIterator++;
            System.out.println("No of Activators after " + saturateIterator + " iteration:" + this.axiomActivators.size());
        }
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
    private boolean saturateActivatorsWithDeltaTBox() {
        boolean updated = false;

        for (ClipperAxiomActivator act : axiomActivators) {
            for (EnforcedRelation enf : newEnfs) {
                updated = applyEnfToActivator(act, enf) || updated;//and update it's status to unstable
            }

            for (HornImplication imp : newImps) {
                updated = applyImpToActivator(act, imp) || updated;
            }
        }

//        todo: check: this part has been moved inside the method applyEnfToActivator
       if (newAxiomActivators.size() > 0)
            mergeWithActivators(newAxiomActivators);

        //now clear the newAxiomActivator container
        newAxiomActivators.clear();

        return updated;
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

        boolean updated = true;
        while (updated) {
            updated = false;//the condition that controls the saturation criteria
            for (ClipperAxiomActivator act : axiomActivators) {
                //if the activator is stable than skip
                if (!act.isUpdated())
                    continue;

                act.setUpdated(false); //set the status to stable

                //apply old enforced relations
                for (EnforcedRelation enf : enfContainer) {
                    //if any change incurs from applying the rule, then we set the flag change to true
                    //to force the iteration
                    updated |= applyEnfToActivator(act, enf);//and update it's status to unstable
                }

                //apply newly enforced relations
                for (EnforcedRelation enf : newEnfs) {
                    //if any change incurs from applying the rule, then we set the flag change to true
                    //to force the iteration
                    updated |= applyEnfToActivator(act, enf);//and update it's status to unstable
                }

                //apply old implied relations
                for (HornImplication imp : impContainer) {
                    //if any new concept is added to the activator from applying the rule,
                    // then we set the flag change to true to force the iteration
                    updated |= applyImpToActivator(act, imp);
                }

                //apply newly implied relations
                for (HornImplication imp : newImps) {
                    //if any new concept is added to the activator from applying the rule,
                    // then we set the flag change to true to force the iteration
                    updated |= applyImpToActivator(act, imp);
                }
            }

//        todo: check: this part has been moved inside the method applyEnfToActivator
            if (newAxiomActivators.size() > 0)
                mergeWithActivators(newAxiomActivators);

            //now clear the newAxiomActivator container
            newAxiomActivators.clear();
        }
    }

    /* OLD implementation
     * If the application of an axiom alters the current activator or creates a new one
     * then it returns true
     * */
    public boolean old_applyEnfToActivator(ClipperAxiomActivator act, EnforcedRelation enf) {
        boolean changed = false;
        boolean found = false;//indicates a proper activator is already present

        //if(enf.getRoles())

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

            //get all concepts that are propagated to the parent activator (with over aproximation)
            TIntHashSet propagatedToParent = new TIntHashSet();
            for (ClipperOverAproxPropagation prop : backwardPropagation) {
                int Role = prop.getRole();

                if (enf.getRoles().contains(Role))
                    propagatedToParent.addAll(prop.getConcepts());

            }

            for (ClipperOverAproxPropagation prop : forwardPropagation) {
                int invRole = BitSetUtilOpt.inverseRole(prop.getRole());

                if (enf.getRoles().contains(invRole))
                    propagatedToParent.addAll(prop.getConcepts());

            }


            //if all the propagated concepts to parent aren't contained,
            //add them to the activator
            //and set the status of the activator to unstable and the changed flag to true
            if (!act.getConcepts().containsAll(propagatedToParent)) {
                act.getConcepts().addAll(propagatedToParent);
                act.setUpdated(true);
                changed = true;
            }

            //now we check if there exists a proper successor activator, if not we create it
            //first we get all the concepts that are forwardpropagated by the activator
            TIntHashSet propagatedToChild = new TIntHashSet();
            propagatedToChild.addAll(enf.getType2());
            for (ClipperOverAproxPropagation prop : forwardPropagation) {
                int Role = prop.getRole();

                if (enf.getRoles().contains(Role))
                    propagatedToChild.addAll(prop.getConcepts());
            }

            for (ClipperOverAproxPropagation prop : backwardPropagation) {
                int invRole = BitSetUtilOpt.inverseRole(prop.getRole());

                if (enf.getRoles().contains(invRole))
                    propagatedToChild.addAll(prop.getConcepts());
            }

            //second we iterate over the activators and check if there is one that allready
            //contains the set of concepts propagated by the role.
            // If not: we create a new activator and add it to the queue of NewActivators
            //         which are added to the set of activators at the end of the method
            //         and the changed flag to true
            for (ClipperAxiomActivator curr : this.axiomActivators) {
                if (curr.getConcepts().containsAll(propagatedToChild)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                newAxiomActivators.add(new ClipperAxiomActivator(propagatedToChild));
                changed = true;
            }
        }
        return changed;
    }


    /* Checks if the given existential axiom enf is activator by the provided Activator act.
       In case the axiom is activated and it results in creating a new activator, the
       created activator is added to the set. The method returns the indicator if
       a new activator has been added or not. */
    public boolean applyEnfToActivator(ClipperAxiomActivator act, EnforcedRelation enf) {
        boolean changed = false;
        boolean found = false;//indicates a proper activator is already present


        //check if the enforced relation is activated by the activator
        if (act.getConcepts().containsAll(enf.getType1())) {

            //now check if a proper activator allready exists for the successor
            for (ClipperAxiomActivator curr : this.axiomActivators) {
                if (curr.getConcepts().containsAll(enf.getType2())) {
                    found = true;
                    break;
                }
            }
            /*
            if no proper activator is found, then create a new one and add
            it to the list of activators.*/
            if (!found) {
                //now clear the newAxiomActivator container
                newAxiomActivators.add(new ClipperAxiomActivator(enf.getType2()));
                changed = true;
            }
        }

        return changed;
    }

    /* Alpha^{*} Closes an activator under implication rules (det axioms A->B).
     * If the activator is updated (i.e. new concepts are added), it sets
     * the update status of an activator to true and returns the flag changed as true,
     * otherwise false*/
    public boolean applyImpToActivator(ClipperAxiomActivator act, HornImplication imp) {
        boolean changed = false;
        if (act.getConcepts().containsAll(imp.getBody())
                && !act.getConcepts().contains(imp.getHead())) {
            act.getConcepts().add(imp.getHead());
            act.setUpdated(true);
            changed = true;
        }
        return changed;
    }

    /*This method checks if the axiom is applicable, i.e.
      if there is some A in AxiomActivators s.t. LHS subseteq A*/
    private boolean axiomApplicable(TIntHashSet lhs) {

        boolean b = axiomActivators.stream()
                .anyMatch(act -> act.getConcepts().containsAll(lhs));

        if (!b) {
            System.err.println("Activator helps!");
        }
        return b;

//        boolean applicable = false;
//
//        for (ClipperAxiomActivator act : axiomActivators) {
//            if (act.getConcepts().containsAll(lhs)) {
//                applicable = true;
//                break;
//            }
//
//        }
//        return applicable;
    }


    private void mergeWithActivators(Set<ClipperAxiomActivator> newAxiomActivators) {
        //add the new activators to the set
        axiomActivators.addAll(cloneOfActivators(newAxiomActivators));

        //clear the container of newAxiomActivators
        newAxiomActivators.clear();
    }


}
