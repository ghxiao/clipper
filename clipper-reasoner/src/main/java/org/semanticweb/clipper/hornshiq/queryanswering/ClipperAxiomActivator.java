package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;

import java.util.HashSet;
import java.util.Set;

/*Data structure that holds the concepts that can fire
* TBox axioms.
* Whenever a concept is added to the list of concepts
*       or a role is added to the queue,
*       the status of the object is set to Updated=true*/
public class ClipperAxiomActivator {
    //todo: add two different set of roles (incoming and outgoing)
    //todo: add a proper indexed set for roles (with an indicator of inverse)
    private TIntHashSet concepts;
    private boolean unstable;
    private HashSet<TIntHashSet> processedRoles;//qued roles (to be applied later)
    private HashSet<TIntHashSet> quedRoles;//qued roles (to be applied later)

    public ClipperAxiomActivator(Set<Integer> concepts) {
        this.concepts = new TIntHashSet();
        for (Integer i : concepts) {
            int val = i;
            this.concepts.add(val);
        }

        //add top to each activator
        this.concepts.add(ClipperManager.getInstance().getThing());
        this.unstable = true;
    }

    public ClipperAxiomActivator(TIntHashSet concepts) {
        this.concepts = new TIntHashSet();
        this.concepts.addAll(concepts);
        //add top to each activator
        this.concepts.add(ClipperManager.getInstance().getThing());
        this.unstable = true;
    }

    // constructor to get a clone of an EnforcedRelation
    public ClipperAxiomActivator(ClipperAxiomActivator clonedAct) {
        this.concepts = new TIntHashSet(clonedAct.getConcepts());
        this.unstable = clonedAct.isUnstable();
    }


    public TIntHashSet getConcepts() {
        return concepts;
    }

    public void setConcepts(TIntHashSet concepts) {
        this.concepts = concepts;
    }

    public void addConcept(int conc) {
        this.concepts.add(conc);
        this.unstable = true;
    }

    public boolean isUnstable() {
        return this.unstable;
    }

    public void setUnstable(boolean unstable) {
        this.unstable = unstable;
    }

    public void addQuedRole(TIntHashSet role) {
        this.quedRoles.add(role);
        this.unstable = true;
    }

    /*Caller: Is called after processing the roles in the que
    * Behaviour:Adds the qued roles to the set of processed roles
    * and clears the que
    * */
    public void removeQuedRoles() {
        this.processedRoles.addAll(this.quedRoles);
        this.quedRoles.clear();//should check if the processedRoles are affected
    }

    /*Behaviour:Checks if the provided role is processed*/
    public boolean isProcessed(TIntHashSet role) {
        boolean contained = false;
        for (TIntHashSet r : this.processedRoles)
            //if role is the same
            if (role.containsAll(r) && r.containsAll(role)) {
                contained = true;
                break;
            }
        return contained;
    }
}
