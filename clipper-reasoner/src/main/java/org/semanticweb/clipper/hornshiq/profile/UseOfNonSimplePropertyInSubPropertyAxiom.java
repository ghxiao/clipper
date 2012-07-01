package org.semanticweb.clipper.hornshiq.profile;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.profiles.*;

/**
 * Author: Matthew Horridge<br> 
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 * Modified by  Kien (TU WIEN).<br>
 */
public class UseOfNonSimplePropertyInSubPropertyAxiom extends OWLProfileViolation implements NFHornSHIQProfileViolation {

    public UseOfNonSimplePropertyInSubPropertyAxiom(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom);
    }


    public void accept(NFHornSHIQProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Non simple properties are not allowed in SubProperty Axiom. ");
        sb.append(getAxiom());
        sb.append(" [in ontology ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
