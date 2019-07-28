package org.semanticweb.clipper.hornshiq.queryanswering;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.semanticweb.clipper.hornshiq.aboxprofile.ABoxProfileLoader;
import org.semanticweb.clipper.hornshiq.aboxprofile.ActivatorsExtractorFromR2RML;
import org.semanticweb.clipper.hornshiq.aboxprofile.ProfileExtractorFromABox;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntologyConverter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class QAHornSHIQWithActivatorsFromMapping extends QAHornSHIQWithActivators {

    private final String r2rmlFile;
    private final String tboxFile;

    private String abstractAboxFile;

    public QAHornSHIQWithActivatorsFromMapping(String r2rmlFile, String tboxFile) {
        super(true);
        this.r2rmlFile = r2rmlFile;
        this.tboxFile = tboxFile;

        try {
            this.abstractAboxFile = ActivatorsExtractorFromR2RML.computeAbstractABoxFromR2RML(r2rmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidR2RMLMappingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initializeActivators() {
        long startInitializeActivatorsTimepoint = System.currentTimeMillis();

        OWLOntology rlTBox = OWLOntologySplitter.extractRLTBox(combinedNormalizedOntology);
        OWLOntology aBox = OWLOntologySplitter.extractABox(combinedNormalizedOntology);
        OWLOntology abstractAbox=null;

        try {
            abstractAbox = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new File(abstractAboxFile));

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        //Note:Abstract Abox is obtained from mappings
        this.activatorsFromMappings= ProfileExtractorFromABox.computeActivators(rlTBox, abstractAbox, this.getOntologyFilename());
        ProfileExtractorFromABox.printIndividualProfilesTemp(abstractAbox, this.getOntologyFilename());


        long endInitializeActivatorsTimepoint = System.currentTimeMillis();

        clipperReport.setInitializeActivatorsTime(endInitializeActivatorsTimepoint-startInitializeActivatorsTimepoint);

        //todo:review the old method of getting activators from R2RML
//        try {
//            Collection<Set<Resource>> activators = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(r2rmlFile, tboxFile);
//            this.activators = activators;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidR2RMLMappingException e) {
//            e.printStackTrace();
//        }

    }

//    public QAHornSHIQWithActivatorsFromMapping(String r2rmlFile, String tboxFile) {
//        super(true);
//        this.r2rmlFile = r2rmlFile;
//        this.tboxFile = tboxFile;
//    }
//
//
//    @Override
//    protected void initializeActivators() {
//        long startInitializeActivatorsTimepoint = System.currentTimeMillis();
//
//        try {
//            Collection<Set<Resource>> activators = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(r2rmlFile, tboxFile);
//            this.activators = activators;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidR2RMLMappingException e) {
//            e.printStackTrace();
//        }
//
//    }



    /**
     * todo:lb
     * This method is used to extract activators from profiles previously stored in a file
     */
    public Collection<Set<Integer>> extractActivatorsFromProfiles(String tboxFileName, String aboxFileName, String profilesFileName) throws Exception {

        ProfileExtractorFromABox.writeProfilesToFile(tboxFileName, aboxFileName, profilesFileName);

        return ABoxProfileLoader.getActivatorsFromStoredProfiles(profilesFileName);
    }

}
