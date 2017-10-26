package org.semanticweb.clipper.hornshiq.profile.aboxprofile;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.junit.Ignore;
import org.junit.Test;

import org.semanticweb.clipper.hornshiq.aboxprofile.ABoxProfileExtractorFromR2RML;

import java.io.FileNotFoundException;

public class ABoxProfileExtractorFromR2RMLTest {

    @Test
    public void test() throws FileNotFoundException, InvalidR2RMLMappingException {
        ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/mappingFiles/test30.ttl");
    }

}
