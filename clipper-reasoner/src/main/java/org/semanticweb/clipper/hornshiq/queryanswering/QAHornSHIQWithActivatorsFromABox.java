package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.aboxprofile.ProfileExtractorFromABox;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.util.Set;

public class QAHornSHIQWithActivatorsFromABox extends QAHornSHIQWithActivators {

    public QAHornSHIQWithActivatorsFromABox(){
        super(true);
    }

    @Override protected void initializeActivators() {
        long startInitializeActivatorsTimepoint = System.currentTimeMillis();


        OWLOntology rlTBox = OWLOntologySplitter.extractRLTBox(combinedNormalizedOntology);
        OWLOntology aBox = OWLOntologySplitter.extractABox(combinedNormalizedOntology);

        final SymbolEncoder<OWLClass> owlClassEncoder = ClipperManager.getInstance().getOwlClassEncoder();

//        this.activators = ProfileExtractorFromABox.computeProfiles(rlTBox, aBox);//todo:remove later, left only for purpose of comparison

//        System.out.println("========Activators from RDFox ABox======");
//
//        for(Set<Resource> set: this.activators) {
//            System.out.println("{");
//            for (Resource foxResource : set) {
//                System.out.println(foxResource.toString());
//            }
//            System.out.println("}");
//        }

        this.activatorsFromABox= ProfileExtractorFromABox.computeActivators(rlTBox, aBox, this.getOntologyFilename());

        long endInitializeActivatorsTimepoint = System.currentTimeMillis();

        clipperReport.setInitializeActivatorsTime(endInitializeActivatorsTimepoint-startInitializeActivatorsTimepoint);

//        System.out.println("========Activators from ABox=============");
//        for(Set<Integer> set: this.activatorsFromABox){
//            System.out.println("{");
//            for (Integer res : set) {
//                System.out.println(owlClassEncoder.getSymbolByValue(res).toString());
//            }
//            System.out.println("}");
//        }

        //System.out.println("Abox axioms......");

        //for(OWLAxiom ax: aBox.getAxioms(AxiomType.CLASS_ASSERTION)){
        //    System.out.println(ax.toString());
        //}

    }
}
