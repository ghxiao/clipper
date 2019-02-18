package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.aboxprofile.ProfileExtractorFromABox;
import org.semanticweb.owlapi.model.OWLOntology;

public class QAHornSHIQWithActivatorsFromABox extends QAHornSHIQWithActivators {

    public QAHornSHIQWithActivatorsFromABox(){
        super(true);
    }

    @Override protected void initializeActivators() {
        OWLOntology rlTBox = OWLOntologySplitter.extractRLTBox(combinedNormalizedOntology);
        OWLOntology aBox = OWLOntologySplitter.extractABox(combinedNormalizedOntology);

        this.activators = ProfileExtractorFromABox.computeProfiles(rlTBox, aBox);//todo:remove later, left only for purpose of comparison
        this.activatorsFromABox= ProfileExtractorFromABox.computeActivators(rlTBox, aBox);
    }
}
