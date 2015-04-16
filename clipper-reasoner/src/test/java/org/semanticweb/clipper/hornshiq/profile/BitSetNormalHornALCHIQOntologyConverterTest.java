package org.semanticweb.clipper.hornshiq.profile;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperDisjointObjectPropertiesAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.profiles.OWLProfileReport;


public class BitSetNormalHornALCHIQOntologyConverterTest {
	// original
	@Test
	public void testNormalizeLUBM2() throws OWLOntologyCreationException, OWLOntologyStorageException {
		//File file = new File("TestData/univ-bench.owl");
		InputStream stream = BitSetNormalHornALCHIQOntologyConverterTest.class.getResourceAsStream("/ontologies/LUBM/univ-bench.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(stream);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
		
		ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
		ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);
		
		for (ClipperAxiom ax : onto_bs.getAllAxioms()) {
			System.out.println(ax);
		}
	}
	@Test
	public void testNormalizeWithInverseRoleAxioms() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("TestCaseOntologies/testEncodingOfInverseRole.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		System.out.println(report);
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
		
		ClipperHornSHIQOntologyConverter converter = new ClipperHornSHIQOntologyConverter();
		ClipperHornSHIQOntology onto_bs = converter.convert(normalizedOnt3);
		
		for (ClipperAxiom ax : onto_bs.getAllAxioms()) {
			System.out.println(ax);
		}
	}
}
