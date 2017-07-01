// JRDFox(c) Copyright University of Oxford, 2013. All Rights Reserved.

package uk.ac.ox.cs.JRDFox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import uk.ac.ox.cs.JRDFox.store.DataStore;
import uk.ac.ox.cs.JRDFox.store.Resource;
import uk.ac.ox.cs.JRDFox.store.TupleIterator;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class ABoxProfileExtractor {

    public static Collection<Set<Resource>> computeProfiles(String tboxFile, String aboxFile) throws Exception {

        final OWLOntology ontology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(new File(tboxFile));

        // We now create the data store. RDFox supports different types of stores described in DataStore.StoreType,
        // each with the option of native equality reasoning.
        DataStore store = new DataStore(DataStore.StoreType.ParallelSimpleNN);
        try {
            System.out.println("Setting the number of threads...");
            store.setNumberOfThreads(2);

            System.out.println("Importing RDF data...");
            store.importFiles(new File[]{new File(aboxFile)});
            System.out.println("Number of tuples after import: " + store.getTriplesCount());

            Prefixes prefixes = Prefixes.DEFAULT_IMMUTABLE_INSTANCE;
            System.out.println("Retrieving all properties before materialisation.");

            TupleIterator tupleIterator = store.compileQuery("SELECT DISTINCT ?x ?z WHERE{ ?x a ?z }", prefixes);

            try {

                System.out.println("Adding the ontology to the store...");
                store.importOntology(ontology);

                store.applyReasoning();

                return computeProfile(tupleIterator);

            } finally {
                // When no longer needed, the iterator should be disposed so that all related resources are released.
                tupleIterator.dispose();
            }
        } finally {
            // When no longer needed, the data store should be disposed so that all related resources are released.
            store.dispose();
        }
    }

    public static Collection<Set<Resource>> computeProfile(TupleIterator tupleIterator) throws JRDFoxException {


        Map<Resource, Set<Resource>> profiles = new HashMap<Resource, Set<Resource>>();

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
                profiles.put(individual, new HashSet<Resource>());
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
