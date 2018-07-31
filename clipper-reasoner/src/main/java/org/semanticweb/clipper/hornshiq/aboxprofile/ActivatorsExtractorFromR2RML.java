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
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import uk.ac.ox.cs.JRDFox.JRDFoxException;
import uk.ac.ox.cs.JRDFox.Prefixes;
import uk.ac.ox.cs.JRDFox.store.DataStore;
import uk.ac.ox.cs.JRDFox.store.Resource;
import uk.ac.ox.cs.JRDFox.store.TupleIterator;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ActivatorsExtractorFromR2RML {


    public static Map<String, MappingProfile> computeActivators(String r2rmlFile) throws FileNotFoundException, InvalidR2RMLMappingException {
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

    public static Collection<Set<Resource>> computeProfilesFromR2RML(String r2rmlFile, String tboxFile) throws IOException, InvalidR2RMLMappingException {

        Map<String, MappingProfile> mappingProfileMap = computeActivators(r2rmlFile);

        String aboxFile = r2rmlFile + ".abox.ttl";
        FileWriter aboxFileWriter = new FileWriter(aboxFile);


        int counter = 0;

        for (String template : mappingProfileMap.keySet()) {

            Collection<IRI> classes = mappingProfileMap.get(template).concepts;

            String normalizedTemplate = template.replaceAll("\\{}", "__").replaceAll(" ", "");
            //String normalizedTemplate = template;

            for (IRI cls : classes) {
                aboxFileWriter.append(String.format("<%s> a <%s> . \n", normalizedTemplate, cls.getIRIString()));
            }

            for (IRI incoming : mappingProfileMap.get(template).incomingRoles) {
                aboxFileWriter.append(String.format("<#%s> <%s> <%s> . \n", counter++, incoming.getIRIString(), normalizedTemplate));
            }

            for (IRI outgoing : mappingProfileMap.get(template).outgoingRoles) {
                aboxFileWriter.append(String.format("<%s> <%s> <#%s> . \n", normalizedTemplate, outgoing.getIRIString(), counter++));
            }

        }

        aboxFileWriter.close();


        final OWLOntology ontology;
        try {
            ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new File(tboxFile));
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }

        // We now create the data store. RDFox supports different types of stores described in DataStore.StoreType,
        // each with the option of native equality reasoning.
        DataStore store = null;
        try {
            store = new DataStore(DataStore.StoreType.ParallelSimpleNN);
            System.out.println("Setting the number of threads...");
            store.setNumberOfThreads(2);

            System.out.println("Importing RDF data...");
            store.importFiles(new File[]{new File(aboxFile)});
            System.out.println("Number of tuples after import: " + store.getTriplesCount());
            return compute(ontology, store);

        } catch (JRDFoxException e) {
            throw new RuntimeException(e);
        } finally {
            // When no longer needed, the data store should be disposed so that all related resources are released.
            store.dispose();
        }
    }


    private static Collection<Set<Resource>> compute(OWLOntology ontology, DataStore store) throws JRDFoxException {
        Prefixes prefixes = Prefixes.DEFAULT_IMMUTABLE_INSTANCE;
        System.out.println("Retrieving all properties before materialisation.");

        TupleIterator tupleIterator = store.compileQuery("SELECT DISTINCT ?x ?z WHERE{ ?x a ?z }", prefixes);

        try {
            System.out.println("Adding the ontology to the store...");
            store.importOntology(ontology);
            store.applyReasoning();
            Map<Resource, Set<Resource>> activators = new HashMap<>();

            int numberOfRows = 0;
            System.out.println();
            System.out.println("=======================================================================================");
            int arity = tupleIterator.getArity();
            // We iterate trough the result tuples
            for (long multiplicity = tupleIterator.open(); multiplicity != 0; multiplicity = tupleIterator.advance()) {
                // We iterate trough the terms of each tuple
                final Resource individual = tupleIterator.getResource(0);

                if(individual.toString().startsWith("<#"))
                    continue;

                final Resource concept = tupleIterator.getResource(1);
                if (!activators.containsKey(individual)) {
                    activators.put(individual, new HashSet<>());
                }

                activators.get(individual).add(concept);

            }
            System.out.println("---------------------------------------------------------------------------------------");

            activators.forEach((k, v) -> System.out.println(k + " -> " + v));

            System.out.println("=======================================================================================");
            System.out.println();

            final HashSet<Set<Resource>> sets = new HashSet<>(activators.values());

            System.out.println("=======================================================================================");

            System.out.println("All profiles");

            final Set<Set<Resource>> finalProfile =
                    sets.parallelStream()
                            .filter(s -> sets.stream()
                                    .noneMatch(t -> t.containsAll(s) && !t.equals(s)) // largest
                            )
                            .collect(toSet());

 //           finalProfile.forEach(System.out::println);

            return finalProfile;
        } finally {
            // When no longer needed, the iterator should be disposed so that all related resources are released.
            tupleIterator.dispose();
        }
    }


}
