package org.semanticweb.clipper.hornshiq.aboxprofile;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import uk.ac.ox.cs.JRDFox.store.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class ABoxProfileLoader {

    public static void main(String[] args) throws IOException {
        final Collection<Set<Integer>> profile = getActivatorsFromStoredProfiles(args[0]);
        System.out.println(profile);
    }

    public static Collection<Set<Integer>> getActivatorsFromStoredProfiles(String file) throws IOException {

        final ClipperManager clipperManager = ClipperManager.getInstance();
        final SymbolEncoder<OWLClass> owlClassEncoder = clipperManager.getOwlClassEncoder();
        final OWLDataFactory owlDataFactory = OWLManager.getOWLDataFactory();

        return Files.lines(Paths.get(file)).map(
                line -> Arrays.stream(line.split(","))
                        .map(s -> s.substring(1, s.length() - 1))
                        .map(iri -> owlClassEncoder.getValueBySymbol(owlDataFactory.getOWLClass(IRI.create(iri))))
                        .collect(toSet())
        ).collect(toSet());

    }

    public static Collection<Set<Integer>> getActivatorsFromProfileObjects(Collection<Set<Resource>> prmAboxProfiles) throws IOException {

        final ClipperManager clipperManager = ClipperManager.getInstance();
        final SymbolEncoder<OWLClass> owlClassEncoder = clipperManager.getOwlClassEncoder();
        final OWLDataFactory owlDataFactory = OWLManager.getOWLDataFactory();

        Collection<Set<Integer>> activators = new HashSet<>();

        for (Set<Resource> p : prmAboxProfiles) {
            final String line = p.stream().map(Object::toString).collect(joining(","));

            activators.add(
                            Arrays.stream(line.split(","))
                                    .map(s -> s.substring(1, s.length() - 1))
                                    .map(iri -> owlClassEncoder.getValueBySymbol(owlDataFactory.getOWLClass(IRI.create(iri))))
                                    .collect(toSet()));
        }

        return activators;
    }



}
