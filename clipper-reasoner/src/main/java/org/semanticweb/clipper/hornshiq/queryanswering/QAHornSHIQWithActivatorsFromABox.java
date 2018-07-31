package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.aboxprofile.ProfileExtractorFromABox;
import org.semanticweb.owlapi.model.OWLOntology;

public class QAHornSHIQWithActivatorsFromABox extends QAHornSHIQ {

    public QAHornSHIQWithActivatorsFromABox(){
        super(true);
    }

    @Override protected void initializeActivators() {
        OWLOntology rlTBox = OWLOntologySplitter.extractRLTBox(combinedOntology);
        OWLOntology aBox = OWLOntologySplitter.extractABox(combinedOntology);

        this.activators = ProfileExtractorFromABox.computeProfiles(rlTBox, aBox);
    }
}
