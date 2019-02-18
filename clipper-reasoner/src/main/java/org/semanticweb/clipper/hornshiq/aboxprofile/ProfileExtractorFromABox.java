// JRDFox(c) Copyright University of Oxford, 2013. All Rights Reserved.

package org.semanticweb.clipper.hornshiq.aboxprofile;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.clipper.hornshiq.ontology.*;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.util.BitSetUtilOpt;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import uk.ac.ox.cs.JRDFox.JRDFoxException;
import uk.ac.ox.cs.JRDFox.Prefixes;
import uk.ac.ox.cs.JRDFox.store.DataStore;
import uk.ac.ox.cs.JRDFox.store.Resource;
import uk.ac.ox.cs.JRDFox.store.TupleIterator;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class ProfileExtractorFromABox {

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
                                    .noneMatch(t -> t.containsAll(s) && !t.equals(s)) // largest
                            )
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

    public static Collection<Set<Integer>> computeActivators(OWLOntology tbox, OWLOntology abox) {
        ClipperManager km= ClipperManager.getInstance();
        ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
        ClipperHornSHIQOntology clipperTBox = converter.convert(tbox);
        ClipperHornSHIQOntology clipperABox = converter.convert(abox);

//        System.out.println("clipperTBox axioms");
//        for(ClipperAxiom ax: clipperTBox.getAllAxioms())
//            ax.toString();
//
//        System.out.println("clipperABox axioms");
//        for(ClipperAxiom ax: clipperABox.getAllAxioms())
//            ax.toString();

        Set<Integer> aboxRoles = new HashSet<>();
        HashMap<Integer, Set<Integer>> aboxSuperRoles = new HashMap<>();

        // initialize forward and backward propagation structures
        HashMap<Integer, Set<Integer>> forwardPropagation = new HashMap<>();
        HashMap<Integer, Set<Integer>> backwardPropagation = new HashMap<>();

        //first we gather roles that are being used in the ABox
        for (ClipperPropertyAssertionAxiom ax : clipperABox.getPropertyAssertionAxioms()) {
            int role = (ax.getRole()/2)*2;
            aboxRoles.add(role);
        }

        //then for each aboxRole we get it's hierarchy of superroles
        for (Integer role : aboxRoles) {
            Set<Integer> superRoles = new HashSet<>();
            superRoles.add(role);
            aboxSuperRoles.put(role, superRoles);
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (Integer role : aboxRoles) {
                for (ClipperSubPropertyAxiom ax : clipperTBox.getSubPropertyAxioms()) {
                    System.out.println("subrole:"+ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(ax.getRole1()));
                    System.out.println("superrole:"+ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(ax.getRole2()));

                    int subrole=(ax.getRole1()/2)*2;
                    int suprole=(ax.getRole2()/2)*2;

                    if (aboxSuperRoles.get(role).contains(subrole)
                            && !aboxSuperRoles.get(role).contains(suprole)) {
                        changed = true;
                        aboxSuperRoles.get(role).add(suprole);
                    }
                }
            }
        }


        //now for each ABox role we get all the concepts that are
        //forward and backward propagated by it
        for (Map.Entry<Integer, Set<Integer>> entry : aboxSuperRoles.entrySet()) {
            int entryRole = (entry.getKey()/2)*2;

            System.out.println("Get FWD and BCK concepts for the role:"+ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(entry.getKey()));

            Set<Integer> fwdConcepts2Add = new HashSet<>();
            Set<Integer> bckConcepts2Add = new HashSet<>();

            //get all concepts that are fwd/bck propagated for the current entry i.e. role
            for (ClipperAtomSubAllAxiom ax : clipperTBox.getAtomSubAllAxioms()) {

                System.out.println("Apply " +ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(ax.getRole())
                                            +"."+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept2())
                );

                ax.toString();

                int role=(ax.getRole()/2)*2;

                if (entry.getValue().contains(role)) {
                    if (ax.getRole() % 2 == 1)
                        bckConcepts2Add.add(ax.getConcept2());
                    else
                        fwdConcepts2Add.add(ax.getConcept2());
                }
            }

            if (bckConcepts2Add.size() > 0)
                backwardPropagation.put(entryRole, bckConcepts2Add);

            if (fwdConcepts2Add.size() > 0)
                forwardPropagation.put(entryRole, fwdConcepts2Add);
        }

        Map<Integer, Set<Integer>> individualActivators = new HashMap<>();
        //for each individual create an activator based on it's abox concept assertions
        for(ClipperConceptAssertionAxiom ax:clipperABox.getConceptAssertionAxioms()){
            System.out.println("ind:"     +ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(ax.getIndividual())
                               +"concept:"+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept()));

            if (!individualActivators.containsKey(ax.getIndividual())) {
                individualActivators.put(ax.getIndividual(), new HashSet<>());
            }
            individualActivators.get(ax.getIndividual()).add(ax.getConcept());
        }


        //for each individual that can get new forward/backward propagated concepts, add explicitly the same in the ontology
        for(ClipperPropertyAssertionAxiom ax: clipperABox.getPropertyAssertionAxioms()) {

            int role = (ax.getRole()/2)*2;

            Set<Integer> conceptAssertionsFromFwdProp= new HashSet<>();
            conceptAssertionsFromFwdProp=forwardPropagation.get(role);

            Set<Integer> conceptAssertionsFromBckProp=new HashSet<>();
            conceptAssertionsFromBckProp = backwardPropagation.get(role);

            System.out.println("from bckpropagation");
            if(conceptAssertionsFromBckProp!=null){
                    individualActivators.get(ax.getIndividual1()).addAll(conceptAssertionsFromBckProp);

//                    clipperABox.getConceptAssertionAxioms().add(new ClipperConceptAssertionAxiom(ax.getIndividual2(),concept));
//                    System.out.println("Individual"+ax.getIndividual2()+"-"+ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder()
//                            .getSymbolByValue(ax.getIndividual2()).toString()+
//                                        " Concept"+concept+"-"+ClipperManager.getInstance().getOwlClassEncoder()
//                            .getSymbolByValue(concept).toString())
            }
            System.out.println("from fwdpropagation");
            if(conceptAssertionsFromFwdProp!=null){
                for(Integer concept:conceptAssertionsFromBckProp){
                    individualActivators.get(ax.getIndividual2()).addAll(conceptAssertionsFromFwdProp);
//                    clipperABox.getConceptAssertionAxioms().add(new ClipperConceptAssertionAxiom(ax.getIndividual1(),concept));
//                    System.out.println("Individual"+ax.getIndividual1()+"-"+ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder()
//                            .getSymbolByValue(ax.getIndividual1()).toString()+
//                            " Concept"+concept+"-"+ClipperManager.getInstance().getOwlClassEncoder()
//                            .getSymbolByValue(concept).toString());
                }
            }
        }



        System.out.println("check");
        //now we simply extract initial activators from clipperABox
        for(ClipperConceptAssertionAxiom ax:clipperABox.getConceptAssertionAxioms()){
//            System.out.println("ind:"     +ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(ax.getIndividual())
//                               +"concept:"+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept()));

            if (!individualActivators.containsKey(ax.getIndividual())) {
                individualActivators.put(ax.getIndividual(), new HashSet<>());
            }
            individualActivators.get(ax.getIndividual()).add(ax.getConcept());
        }

        //list of duplicate activators
        final HashSet<Set<Integer>> sets = new HashSet<>(individualActivators.values());

        System.out.println("=======================================================================================");

        System.out.println("All initial activators");

        final Set<Set<Integer>> initialActivators =
                sets.parallelStream()
                        .filter(s -> sets.stream()
                                .noneMatch(t -> t.containsAll(s) && !t.equals(s)) // largest
                        )
                        .collect(toSet());

        initialActivators.forEach(System.out::println);

        return initialActivators;
    }





    /**
     * @param args 0 - ontology file (TBox)
     *             1 - ABox turtle file
     *             2 - output file
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.println("Usage: ProfileExtractorFromABox tbox.owl abox.ttl output.txt");
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
