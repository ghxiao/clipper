package org.semanticweb.clipper.hornshiq.queryanswering;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.semanticweb.clipper.hornshiq.aboxprofile.ABoxProfileLoader;
import org.semanticweb.clipper.hornshiq.aboxprofile.ActivatorsExtractorFromR2RML;
import org.semanticweb.clipper.hornshiq.aboxprofile.ProfileExtractorFromABox;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class QAHornSHIQWithActivatorsFromMapping extends QAHornSHIQWithActivators {

    private final String r2rmlFile;

    private final String tboxFile;

    public QAHornSHIQWithActivatorsFromMapping(String r2rmlFile, String tboxFile) {
        super(true);
        this.r2rmlFile = r2rmlFile;
        this.tboxFile = tboxFile;
    }


    @Override
    protected void initializeActivators() {
        try {
            Collection<Set<Resource>> activators = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(r2rmlFile, tboxFile);
            this.activators = activators;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidR2RMLMappingException e) {
            e.printStackTrace();
        }
    }


    /**
     * todo:lb
     * This method is used to extract activators from profiles previously stored in a file
     */
    public Collection<Set<Integer>> extractActivatorsFromProfiles(String tboxFileName, String aboxFileName, String profilesFileName) throws Exception {

        ProfileExtractorFromABox.writeProfilesToFile(tboxFileName, aboxFileName, profilesFileName);

        return ABoxProfileLoader.getActivatorsFromStoredProfiles(profilesFileName);
    }

}