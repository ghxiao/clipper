package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.impl.hash.TIntHash;
import gnu.trove.set.hash.TIntHashSet;


/*Data structure that represnts the profile of an individual in the ABox
* i.e. {A | A(a) in ABox} U
*       {Some (r)| r(a,b) in ABox} U
*       {Some (r-)| r(b,a) in ABox} for some individual a in ABox
* Concepts and roles are encoded like integers i.e.
*/
public class ABoxIndividualProfile {

    private TIntHashSet concepts;
    private TIntHashSet roles;

    public ABoxIndividualProfile(TIntHashSet concepts, TIntHashSet roles) {
        this.concepts = concepts;
        this.roles = roles;
    }

    public TIntHashSet getConcepts() {
        return concepts;
    }

    public TIntHashSet getRoles() {
        return roles;
    }

    public void setConcepts(TIntHashSet concepts) {
        this.concepts = concepts;
    }

    public void addConcept(int conc) {
        concepts.add(conc);
    }

    public ActivatorProfile getActivatorProfiles(){
        //we have to accumulate all the concepts from a profile in this set
        TIntHashSet activeconcepts = new TIntHashSet();

        //todo:add code to return infered concepts from Individual Profile

        //ActivatorProfile profile = new ActivatorProfile(activeconcepts);

        //return profile;
        return null;

    }

}
