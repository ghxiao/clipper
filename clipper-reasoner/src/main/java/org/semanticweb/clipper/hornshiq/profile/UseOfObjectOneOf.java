package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.UseOfIllegalClassExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 * Modified by Kien: checking HornSHIQ.
 */
public class UseOfObjectOneOf extends UseOfIllegalClassExpression implements NFHornSHIQProfileViolation {

    private OWLObjectOneOf oneOf;

    public UseOfObjectOneOf(OWLOntology ontology, OWLAxiom axiom, OWLObjectOneOf oneOf) {
        super(ontology, axiom, oneOf);
        this.oneOf = oneOf;
    }

    @Override
	public void accept(NFHornSHIQProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLObjectOneOf getOWLObjectOneOf() {
        return oneOf;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of ObjectOneOf/Nominal : ");
        sb.append(getAxiom());
        sb.append(" [in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
