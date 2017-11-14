package org.semanticweb.clipper.hornshiq.aboxprofile;

import com.google.common.base.MoreObjects;
import org.apache.commons.rdf.api.IRI;

import java.util.Collection;

public class MappingProfile {
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("concepts", concepts)
                .add("incomingRoles", incomingRoles)
                .add("outgoingRoles", outgoingRoles)
                .toString();
    }

    public Collection<IRI> getConcepts() {
        return concepts;
    }

    public Collection<IRI> getIncomingRoles() {
        return incomingRoles;
    }

    public Collection<IRI> getOutgoingRoles() {
        return outgoingRoles;
    }

    Collection<IRI> concepts;

    Collection<IRI> incomingRoles;

    Collection<IRI> outgoingRoles;

    public MappingProfile(Collection<IRI> concepts, Collection<IRI> incomingRoles, Collection<IRI> outgoingRoles) {
        this.concepts = concepts;
        this.incomingRoles = incomingRoles;
        this.outgoingRoles = outgoingRoles;
    }
}
