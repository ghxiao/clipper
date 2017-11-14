package org.semanticweb.clipper.hornshiq.profile.aboxprofile;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.junit.Test;

import org.semanticweb.clipper.hornshiq.aboxprofile.ABoxProfileExtractorFromR2RML;
import org.semanticweb.clipper.hornshiq.aboxprofile.MappingProfile;

import java.io.FileNotFoundException;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class ABoxProfileExtractorFromR2RMLTest {

    @Test
    public void testBSBM() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/r2rml/bsbm/bsbm.ttl");

        report("BSBM", mappingProfileMap);
    }

    @Test
    public void testDBLP() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/r2rml/dblp/dblp-mappings.ttl");

        report("DBLP", mappingProfileMap);
    }


    @Test
    public void testNPD() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/r2rml/NPD/npd-v2-ql-postgres-ontop3.0.ttl");

        report("NPD", mappingProfileMap);
    }

    @Test
    public void testSlegge() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/r2rml/slegge/slegge.r2rml");

        report("SLEGGE", mappingProfileMap);
    }

    @Test
    public void testUOBM() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ABoxProfileExtractorFromR2RML.computeProfilesFromR2RML("src/test/resources/r2rml/UOBM/univ-bench-dl.ttl");

        report("UOBM", mappingProfileMap);
    }

    private void report(String mappingName, Map<String, MappingProfile> mappingProfileMap) {
        //mappingProfileMap.forEach((key, value) -> System.out.println(String.format("%s -> %s", key, value)));

        System.out.println("---------------------------------------------");

        System.out.println(mappingName);

        System.out.println("# templates = " + mappingProfileMap.size());

        final DoubleSummaryStatistics conceptsStatistics = mappingProfileMap.values().stream()
                .collect(Collectors.summarizingDouble(p -> p.getConcepts().size()));

        System.out.println("concepts summary: " + conceptsStatistics);

        final DoubleSummaryStatistics incomingRolesStatistics = mappingProfileMap.values().stream()
                .collect(Collectors.summarizingDouble(p -> p.getIncomingRoles().size()));
        System.out.println("incoming edges summary: " + incomingRolesStatistics);

        System.out.println("outgoing edges summary: " + mappingProfileMap.values().stream()
                .collect(Collectors.summarizingDouble(p -> p.getOutgoingRoles().size())));

        System.out.println("---------------------------------------------");
        System.out.println();
        //System.out.println(mappingProfileMap);
    }

}
