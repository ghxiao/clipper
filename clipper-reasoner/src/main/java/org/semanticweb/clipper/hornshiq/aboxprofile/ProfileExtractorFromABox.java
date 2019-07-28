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

    @Deprecated
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

    public static Collection<Set<Integer>> computeActivators(OWLOntology tbox, OWLOntology abox, String ontologyFilename) {
        ClipperManager km= ClipperManager.getInstance();
        ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
        ClipperHornSHIQOntology clipperTBox = converter.convert(tbox);
        ClipperHornSHIQOntology clipperABox = converter.convert(abox);

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

//            System.out.println("Get FWD and BCK concepts for the role:"+ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(entry.getKey()));

            Set<Integer> fwdConcepts2Add = new HashSet<>();
            Set<Integer> bckConcepts2Add = new HashSet<>();

            //get all concepts that are fwd/bck propagated for the current entry i.e. role
            for (ClipperAtomSubAllAxiom ax : clipperTBox.getAtomSubAllAxioms()) {

//                System.out.println("Apply " +ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(ax.getRole())
//                                            +"."+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept2())
//                );

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
//            System.out.println("ind:"     +ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(ax.getIndividual())
//                               +"concept:"+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept()));

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

            //System.out.println("from bckpropagation");
            if(conceptAssertionsFromBckProp!=null){
                //in case there is no initial activator for the given individual create one
                //there were a few cases where individuals didn't have concept assertions but only property assertions
                if (!individualActivators.containsKey(ax.getIndividual1())) {
                    individualActivators.put(ax.getIndividual1(), new HashSet<>());
                }
                individualActivators.get(ax.getIndividual1()).addAll(conceptAssertionsFromBckProp);
            }
//            System.out.println("from fwdpropagation");
            if(conceptAssertionsFromFwdProp!=null){
                //in case there is no initial activator for the given individual create one
                //there were a few cases where individuals didn't have concept assertions but only property assertions
                if (!individualActivators.containsKey(ax.getIndividual2())) {
                    individualActivators.put(ax.getIndividual2(), new HashSet<>());

                }
                individualActivators.get(ax.getIndividual2()).addAll(conceptAssertionsFromFwdProp);
            }
        }

        //we gather only sets of concepts without their ID's
        final HashSet<Set<Integer>> sets = new HashSet<>(individualActivators.values());

        //we deduplicate initial activators
        final Set<Set<Integer>> initialActivators =
                sets.parallelStream()
                        .filter(s -> sets.stream()
                                .noneMatch(t -> t.containsAll(s) && !t.equals(s)) // largest
                        )
                        .collect(toSet());

        //if(ClipperManager.getInstance().getVerboseLevel()>=-1)
        //    initialActivators.forEach(System.out::println);

        if(ClipperManager.getInstance().getVerboseLevel()==-2)
            printProfileStatistics(clipperABox,forwardPropagation,backwardPropagation, ontologyFilename);

        if(ClipperManager.getInstance().getVerboseLevel()==-3){
            System.out.println("ConceptAssertionAxioms\t PropertyAssertionAxioms\t AndSubAtomAxioms\t SubForAllAxioms\t SubMaxOneAxioms\t SubMinAxioms\t SuSomeAxioms");
            System.out.println(clipperABox.getConceptAssertionAxioms().size()
                                +"\t"+clipperABox.getPropertyAssertionAxioms().size()
                                +"\t"+clipperTBox.getAndSubAtomAxioms().size()
                                +"\t"+clipperTBox.getAtomSubAllAxioms().size()
                                +"\t"+clipperTBox.getAtomSubMaxOneAxioms().size()
                                +"\t"+clipperTBox.getAtomSubMinAxioms().size()
                                +"\t"+clipperTBox.getAtomSubSomeAxioms().size());
        }


        return initialActivators;
    }

    public static void printIndividualProfilesTemp(OWLOntology abox, String ontologyFilename) {
        ClipperManager km= ClipperManager.getInstance();
        ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
        ClipperHornSHIQOntology clipperABox = converter.convert(abox);

        Map<Integer, Set<Integer>> profileConceptAssertions = new HashMap<>();
        Map<Integer, Set<Integer>> profileIncomingRoles = new HashMap<>();
        Map<Integer, Set<Integer>> profileOutgoingRoles = new HashMap<>();

        //for each individual create an activator based on it's abox concept assertions
        for(ClipperConceptAssertionAxiom ax:clipperABox.getConceptAssertionAxioms()){
//            System.out.println("ind:"     +ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(ax.getIndividual())
//                               +"concept:"+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(ax.getConcept()));

            if (!profileConceptAssertions.containsKey(ax.getIndividual())) {
                profileConceptAssertions.put(ax.getIndividual(), new HashSet<>());

            }
            profileConceptAssertions.get(ax.getIndividual()).add(ax.getConcept());

        }


        //for each individual that can get new forward/backward propagated concepts, add explicitly the same in the ontology
        for(ClipperPropertyAssertionAxiom ax: clipperABox.getPropertyAssertionAxioms()) {

            int role = (ax.getRole()/2)*2;

            if (!profileOutgoingRoles.containsKey(ax.getIndividual1())) {
                profileOutgoingRoles.put(ax.getIndividual1(), new HashSet<>());
            }

            if (!profileIncomingRoles.containsKey(ax.getIndividual2())) {
                profileIncomingRoles.put(ax.getIndividual2(), new HashSet<>());
            }

            if (!profileConceptAssertions.containsKey(ax.getIndividual1())) {
                profileConceptAssertions.put(ax.getIndividual1(), new HashSet<>());
            }

            if (!profileConceptAssertions.containsKey(ax.getIndividual2())) {
                profileConceptAssertions.put(ax.getIndividual2(), new HashSet<>());
            }

            profileOutgoingRoles.get(ax.getIndividual1()).add(role);
            profileIncomingRoles.get(ax.getIndividual2()).add(role);

        }

        if(ClipperManager.getInstance().getVerboseLevel()==-2)
            for(Map.Entry<Integer, Set<Integer>> entry : profileConceptAssertions.entrySet()){
                System.out.println("===========================================================");
                System.out.println("Profile:"+ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(entry.getKey()));
                System.out.println("\tConcept Assertions");
                for(Integer concept:entry.getValue()){
                    System.out.println("\t\t"+ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(concept));
                }
                if(profileIncomingRoles.get(entry.getKey())!=null) {
                    System.out.println("\tIncoming Roles");
                    for (Integer incRole : profileIncomingRoles.get(entry.getKey())) {
                        System.out.println("\t\t" + ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(incRole));
                    }
                }
                if(profileIncomingRoles.get(entry.getKey())!=null) {
                    System.out.println("\tOutgoing Roles");
                    for (Integer incRole : profileOutgoingRoles.get(entry.getKey())) {
                        System.out.println("\t\t" + ClipperManager.getInstance().getOwlPropertyExpressionEncoder().getSymbolByValue(incRole));
                    }
                }
                System.out.println("");
            }

    }


    private static void  printProfileStatistics(ClipperHornSHIQOntology ABox,HashMap<Integer, Set<Integer>> forwardPropagation,HashMap<Integer, Set<Integer>> backwardPropagation, String ontologyFilename){
        //todo:add code to gather profiles statistics in the following lines
        //these hash maps all share the same id's and number of entries.
        //they store key statistical information regarding individuals
        Map<Integer, Set<Integer>> individualConceptsAsserted = new HashMap<>();
        Map<Integer, Set<Integer>> individualIncomingABoxRoles = new HashMap<>();
        Map<Integer, Set<Integer>> individualOutgoingABoxRoles = new HashMap<>();
        Map<Integer, Set<Integer>> individualConceptsFwd = new HashMap<>();
        Map<Integer, Set<Integer>> individualConceptsBck = new HashMap<>();
        Map<Integer, int[]> profilesStats = new HashMap<>();//final map that hold statistics for each

        for(ClipperConceptAssertionAxiom ax:ABox.getConceptAssertionAxioms()){
            //initialize HashMaps for those individuals that have incoming properties but are not
            // found in any of the HashMaps.
            if (!individualConceptsAsserted.containsKey(ax.getIndividual())) {
                individualConceptsAsserted.put(ax.getIndividual(), new HashSet<>());
                individualIncomingABoxRoles.put(ax.getIndividual(), new HashSet<>());
                individualOutgoingABoxRoles.put(ax.getIndividual(), new HashSet<>());
                individualConceptsFwd.put(ax.getIndividual(), new HashSet<>());
                individualConceptsBck.put(ax.getIndividual(), new HashSet<>());
            }

            //add the asserted concepts
            individualConceptsAsserted.get(ax.getIndividual()).add(ax.getConcept());
        }

        //add incoming and outgoing roles of each individual (iterate through all property assertions)
        for(ClipperPropertyAssertionAxiom ax:ABox.getPropertyAssertionAxioms()){

            //initialize HashMaps for those individuals that have incoming properties but are not
            // found in any of the HashMaps
            if (!individualConceptsAsserted.containsKey(ax.getIndividual1())) {
                individualConceptsAsserted.put(ax.getIndividual1(), new HashSet<>());
                individualIncomingABoxRoles.put(ax.getIndividual1(), new HashSet<>());
                individualOutgoingABoxRoles.put(ax.getIndividual1(), new HashSet<>());
                individualConceptsFwd.put(ax.getIndividual1(), new HashSet<>());
                individualConceptsBck.put(ax.getIndividual1(), new HashSet<>());
            }

            //initialize HashMaps for those individuals that have incoming properties but are not
            // found in any of the HashMaps
            if (!individualConceptsAsserted.containsKey(ax.getIndividual2())) {
                individualConceptsAsserted.put(ax.getIndividual2(), new HashSet<>());
                individualIncomingABoxRoles.put(ax.getIndividual2(), new HashSet<>());
                individualOutgoingABoxRoles.put(ax.getIndividual2(), new HashSet<>());
                individualConceptsFwd.put(ax.getIndividual2(), new HashSet<>());
                individualConceptsBck.put(ax.getIndividual2(), new HashSet<>());
            }

            //add the incoming respectively outgoing role for the indiviudals involved in the property
            //assertion
            int role = (ax.getRole()/2)*2;

            individualIncomingABoxRoles.get(ax.getIndividual2()).add(role);
            individualOutgoingABoxRoles.get(ax.getIndividual1()).add(role);
        }

        //add the forwarded propagated concepts by neighbours to each individual
        individualIncomingABoxRoles.forEach((id,roles)->{
            forwardPropagation.forEach((role,concepts)->{
                if(roles.contains(role)){
                    individualConceptsFwd.get(id).addAll(concepts);
                }
            });

        });

        //add the backward propagated concepts by neighbours to each individual
        individualOutgoingABoxRoles.forEach((id,roles)-> {
            backwardPropagation.forEach((role, concepts) -> {
                if (roles.contains(role)) {
                    individualConceptsBck.get(id).addAll(concepts);
                }
            });
        });

//        System.out.println("table,id,concept_assertions");
        //printout
        individualConceptsAsserted.forEach((id,concepts)->{
            if(concepts.size()>0)
                System.out.println("insert into imported_stats values('"+ontologyFilename+"','ca',"+id+","+concepts+");");
        });

        individualIncomingABoxRoles.forEach((id,roles)->{
            if(roles.size()>0)
                System.out.println("insert into imported_stats values('"+ontologyFilename+"','in_abox_roles',"+id+","+roles+");");
        });

        individualOutgoingABoxRoles.forEach((id,roles)->{
            if(roles.size()>0)
                System.out.println("insert into imported_stats values('"+ontologyFilename+"','out_abox_roles',"+id+","+roles+");");
        });

        individualConceptsFwd.forEach((id,fwdconcepts)->{
            if(fwdconcepts.size()>0)
                System.out.println("insert into imported_stats values('"+ontologyFilename+"','fwd_concepts',"+id+","+fwdconcepts+");");
        });

        individualConceptsBck.forEach((id,bckconcepts)->{
            if(bckconcepts.size()>0)
                System.out.println("insert into imported_stats values('"+ontologyFilename+"','bck_concepts',"+id+","+bckconcepts+");");
        });

        return;
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
