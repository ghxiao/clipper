package org.semanticweb.clipper.hornshiq.aboxprofile;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import eu.optique.r2rml.api.binding.jena.JenaR2RMLMappingManager;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.apache.commons.rdf.api.IRI;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class ABoxProfileExtractorFromR2RML {

    public static Map<String, MappingProfile> computeProfilesFromR2RML(String r2rmlFile) throws FileNotFoundException, InvalidR2RMLMappingException {
        InputStream fis = new FileInputStream(r2rmlFile);

        JenaR2RMLMappingManager mm = JenaR2RMLMappingManager.getInstance();

        Model m = ModelFactory.createDefaultModel();
        m = m.read(fis, "testMapping", "TURTLE");

        Collection<TriplesMap> coll = mm.importMappings(m);

        Multimap<String, IRI> classMaps = HashMultimap.create();
        Multimap<String, IRI> incomingRoleMaps = HashMultimap.create();
        Multimap<String, IRI> outgoingRoleMaps = HashMultimap.create();

        coll.forEach(
                triplesMap -> {
                    final SubjectMap subjectMap = triplesMap.getSubjectMap();
                    final String subjectTemplate =
                            subjectMap.getTemplate().getTemplateStringWithoutColumnNames();
                    classMaps.putAll(subjectTemplate, subjectMap.getClasses());

                    for (PredicateObjectMap predictedObjectsMap : triplesMap.getPredicateObjectMaps()) {
                        for (PredicateMap predicateMap : predictedObjectsMap.getPredicateMaps()) {
                            //
                            final IRI predicate = ((IRI) predicateMap.getConstant());
                            outgoingRoleMaps.put(subjectTemplate, predicate);

                            for (ObjectMap objectMap : predictedObjectsMap.getObjectMaps()) {

                                final Template template = objectMap.getTemplate();

                                if (template != null) {
                                    final String objectTemplate = template.getTemplateStringWithoutColumnNames();
                                    incomingRoleMaps.put(objectTemplate, predicate);
                                }

                            }

                        }
                    }

                }
        );

        Set<String> keys = new HashSet<>();

        keys.addAll(classMaps.keySet());
        keys.addAll(incomingRoleMaps.keySet());
        keys.addAll(outgoingRoleMaps.keySet());

        Map<String, MappingProfile> mappingProfileMap =

                keys.stream()
                        .collect(toMap(
                                k -> k,
                                k -> new MappingProfile(classMaps.get(k), incomingRoleMaps.get(k), outgoingRoleMaps.get(k))));


        return mappingProfileMap;

    }
}
