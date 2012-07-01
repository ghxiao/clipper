package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.*;
/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 * Modified by Kien: checking HornSHIQ.
 */
public class UseOfObjectUnionOf extends UseOfIllegalClassExpression implements NFHornSHIQProfileViolation {

    private OWLObjectUnionOf unionOf;

    public UseOfObjectUnionOf(OWLOntology ontology, OWLAxiom axiom, OWLObjectUnionOf unionOf) {
        super(ontology, axiom, unionOf);
        this.unionOf = unionOf;
    }

    @Override
	public void accept(NFHornSHIQProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLObjectUnionOf getOWLObjectUnionOf() {
        return unionOf;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of ObjectUnionOf : ");
        sb.append(getAxiom());
        sb.append(" [in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
