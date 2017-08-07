package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;

/**This class can be used to denote the fwd and backward propagation of a role
 * SubClassOf(Top, role ALL concept1)
 * SubClassOf(Top, role ALL concept2)
 * .
 * .
 * SubClassOf(Top, role ALL conceptN)
 * * */
public class ClipperOverAproxPropagation  {

    int role;
    TIntHashSet concepts;

    public ClipperOverAproxPropagation() {
        this.concepts = new TIntHashSet();
    }




    @Override
    public String toString() {
        String r = String.valueOf((role) );

		/* inverse role */
        if (role % 2 == 1) {
            r = "inv(" + r + ")";
        }

        return String.format("Top SubClassOf %s all (%s)", r, concepts);
    }

    public TIntHashSet getConcepts() {
        return this.concepts;
    }

    public int getRole() {
        return this.role;
    }

    public void setConcepts(TIntHashSet concept1) {
        this.concepts = concepts;
    }

    public void setRole(int role) {
        this.role = role;
    }

/*    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom)) return false;
        final org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom other = (org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.concept1 != other.concept1) return false;
        if (this.role != other.role) return false;
        if (this.concept2 != other.concept2) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.concept1;
        result = result * PRIME + this.role;
        result = result * PRIME + this.concept2;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
    }*/
}
