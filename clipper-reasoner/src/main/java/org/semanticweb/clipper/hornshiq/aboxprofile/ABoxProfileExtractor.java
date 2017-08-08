// JRDFox(c) Copyright University of Oxford, 2013. All Rights Reserved.

package org.semanticweb.clipper.hornshiq.aboxprofile;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import uk.ac.ox.cs.JRDFox.JRDFoxException;
import uk.ac.ox.cs.JRDFox.Prefixes;
import uk.ac.ox.cs.JRDFox.store.DataStore;
import uk.ac.ox.cs.JRDFox.store.Resource;
import uk.ac.ox.cs.JRDFox.store.TupleIterator;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class ABoxProfileExtractor {

    public static Collection<Set<Resource>> computeProfiles(String tboxFile, String aboxFile) {

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

    public static Collection<Set<Resource>> computeProfiles(OWLOntology tbox, OWLOntology abox) {
        // We now create the data store. RDFox supports different types of stores described in DataStore.StoreType,
        // each with the option of native equality reasoning.
        DataStore store = null;
        try {
            store = new DataStore(DataStore.StoreType.ParallelSimpleNN);
            System.out.println("Setting the number of threads...");
            store.setNumberOfThreads(2);

            System.out.println("Importing RDF data...");
            final StringDocumentTarget target = new StringDocumentTarget();
            OWLManager.createOWLOntologyManager().saveOntology(abox, new TurtleOntologyFormat(), target);
            final String text = target.toString();
            store.importText(text);

            System.out.println("Number of tuples after import: " + store.getTriplesCount());
            return compute(tbox, store);

        } catch (JRDFoxException | OWLOntologyStorageException e) {
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
            Map<Resource, Set<Resource>> profiles = new HashMap<>();

            int numberOfRows = 0;
            System.out.println();
            System.out.println("=======================================================================================");
            int arity = tupleIterator.getArity();
            // We iterate trough the result tuples
            for (long multiplicity = tupleIterator.open(); multiplicity != 0; multiplicity = tupleIterator.advance()) {
                // We iterate trough the terms of each tuple
                final Resource individual = tupleIterator.getResource(0);
                final Resource concept = tupleIterator.getResource(1);
                if (!profiles.containsKey(individual)) {
                    profiles.put(individual, new HashSet<>());
                }

                profiles.get(individual).add(concept);

            }
            System.out.println("---------------------------------------------------------------------------------------");

            profiles.forEach((k, v) -> System.out.println(k + " -> " + v));

            System.out.println("=======================================================================================");
            System.out.println();

            final HashSet<Set<Resource>> sets = new HashSet<>(profiles.values());

            System.out.println("=======================================================================================");

            System.out.println("All profiles");

            final Set<Set<Resource>> finalProfile =
                    sets.parallelStream()
                            .filter(s -> sets.stream()
                                    .noneMatch(t -> t.containsAll(s) && !t.equals(s)))
                            .collect(toSet());

            finalProfile.forEach(System.out::println);

            return finalProfile;
        } finally {
            // When no longer needed, the iterator should be disposed so that all related resources are released.
            tupleIterator.dispose();
        }
    }


    public static void writeProfilesToFile(String tboxFile, String aboxFile, String outputFile) throws Exception {
        final Collection<Set<Resource>> profiles = computeProfiles(tboxFile, aboxFile);

        try (FileWriter os = new FileWriter(new File(outputFile))) {
            for (Set<Resource> p : profiles) {
                final String line = p.stream().map(Object::toString).collect(joining(","));
                os.write(line);
                os.write("\n");
            }
        }
    }

    /**
     * @param args 0 - ontology file (TBox)
     *             1 - ABox turtle file
     *             2 - output file
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.println("Usage: ABoxProfileExtractor tbox.owl abox.ttl output.txt");
            System.exit(-1);
        }

        final String tboxFile = args[0];
        final String aboxFile = args[1];
        final String outputFile = args[2];


        final Collection<Set<Resource>> profiles = computeProfiles(tboxFile, aboxFile);

        try (FileWriter os = new FileWriter(new File(outputFile))) {
            for (Set<Resource> p : profiles) {
                final String line = p.stream().map(Object::toString).collect(joining(","));
                os.write(line);
                os.write("\n");
            }
        }

    }

}
