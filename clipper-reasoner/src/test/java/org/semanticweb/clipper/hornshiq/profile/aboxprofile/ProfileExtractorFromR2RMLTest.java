package org.semanticweb.clipper.hornshiq.profile.aboxprofile;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.junit.Test;

import org.semanticweb.clipper.hornshiq.aboxprofile.ActivatorsExtractorFromR2RML;
import org.semanticweb.clipper.hornshiq.aboxprofile.MappingProfile;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProfileExtractorFromR2RMLTest {

    @Test
    public void testBSBM() throws IOException, InvalidR2RMLMappingException {
        Collection<Set<Resource>> profiles = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(
                "src/test/resources/r2rml/bsbm/bsbm.ttl", "src/test/resources/r2rml/bsbm/bsbm.owl");


        profiles.forEach(System.out::println);
        //report("BSBM", mappingProfileMap);
        //System.out.println(profiles);
    }

    @Test
    public void testDBLP() throws IOException, InvalidR2RMLMappingException {
        Collection<Set<Resource>> profiles = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(
                "src/test/resources/r2rml/dblp/dblp-mappings.ttl", "src/test/resources/r2rml/dblp/dblp-mappings.owl");

        profiles.forEach(System.out::println);    }

    @Test
    public void testNPD() throws IOException, InvalidR2RMLMappingException {
        Collection<Set<Resource>> profiles = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(
                "src/test/resources/r2rml/npd/npd-v2-ql-postgres-ontop3.0.ttl", "src/test/resources/r2rml/npd/npd-v2-ql.owl");

        profiles.forEach(System.out::println);
    }


    @Test
    public void testSlegge() throws IOException, InvalidR2RMLMappingException {
        Collection<Set<Resource>> profiles = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(
                "src/test/resources/r2rml/slegge/slegge.r2rml", "src/test/resources/r2rml/slegge/subsurface-exploration.ttl");

        profiles.forEach(System.out::println);
    }

    @Test
    public void testUOBM() throws IOException, InvalidR2RMLMappingException {
        Collection<Set<Resource>> profiles = ActivatorsExtractorFromR2RML.computeProfilesFromR2RML(
                "src/test/resources/r2rml/uobm/univ-bench-dl.ttl","src/test/resources/r2rml/uobm/univ-bench-dl.owl");

        profiles.forEach(System.out::println);
    }

    @Test
    public void testBSBMActivators() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ActivatorsExtractorFromR2RML.computeActivators("src/test/resources/r2rml/NPD/npd-v2-ql-postgres-ontop3.0.ttl");

        report("BSBM",mappingProfileMap);
    }

    @Test
    public void testDBLPActivators() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ActivatorsExtractorFromR2RML.computeActivators("src/test/resources/r2rml/NPD/npd-v2-ql-postgres-ontop3.0.ttl");

        report("DBLP",mappingProfileMap);
    }

    @Test
    public void testNPDActivators() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ActivatorsExtractorFromR2RML.computeActivators("src/test/resources/r2rml/NPD/npd-v2-ql-postgres-ontop3.0.ttl");

        report("NPD",mappingProfileMap);
    }

    @Test
    public void testSleggeActivators() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ActivatorsExtractorFromR2RML.computeActivators("src/test/resources/r2rml/slegge/slegge.r2rml");

        report("Slegge",mappingProfileMap);
    }

    @Test
    public void testUOBMActivators() throws FileNotFoundException, InvalidR2RMLMappingException {
        final Map<String, MappingProfile> mappingProfileMap = ActivatorsExtractorFromR2RML.computeActivators("src/test/resources/r2rml/UOBM/univ-bench-dl.ttl");

        report("UOBM",mappingProfileMap);
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
