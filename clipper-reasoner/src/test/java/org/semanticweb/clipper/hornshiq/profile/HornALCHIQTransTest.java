package org.semanticweb.clipper.hornshiq.profile;

import java.io.File;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class HornALCHIQTransTest {

	@Test
	public void test01() {
		File file = new File("TestData/HornALCHIQTrans01.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);

			HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
			OWLOntology normalizedOnt = normalizer.normalize(ontology);

			HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
			OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

			// HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
			// OWLOntology normalizedOnt2 =
			// normalizer2.normalize(normalizedOnt1);
			man.saveOntology(normalizedOnt1,
					IRI.create(new File("TestData/HornALCHIQTrans01Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void printOntologyAxioms(OWLOntology ontology) {

		for (OWLAxiom axiom : ontology.getAxioms(
				AxiomType.TRANSITIVE_OBJECT_PROPERTY, false))

			System.out.println(axiom);
		for (OWLAxiom axiom : ontology.getAxioms(AxiomType.SUBCLASS_OF, false))

			System.out.println(axiom);
		for (OWLAxiom axiom : ontology.getAxioms(AxiomType.SUB_OBJECT_PROPERTY,
				false))

			System.out.println(axiom);
	}

	@Test
	public void test02() {
		File file = new File("TestData/HornALCHIQTrans02.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);

			System.out.println("Original ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(ontology);

			HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
			OWLOntology normalizedOnt = normalizer.normalize(ontology);

			HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
			OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

			System.out.println("Normalized ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(normalizedOnt1);
			// HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
			// OWLOntology normalizedOnt2 =
			// normalizer2.normalize(normalizedOnt1);
			man.saveOntology(normalizedOnt1,
					IRI.create(new File("TestData/HornALCHIQTrans02Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test03() {
		File file = new File("TestData/HornALCHIQTrans03.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);
			System.out.println("Original ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(ontology);
			HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
			OWLOntology normalizedOnt = normalizer.normalize(ontology);

			HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
			OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
			System.out.println("Normalized ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(normalizedOnt1);
			// HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
			// OWLOntology normalizedOnt2 =
			// normalizer2.normalize(normalizedOnt1);
			man.saveOntology(normalizedOnt1,
					IRI.create(new File("TestData/HornALCHIQTrans03Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test04() {
		File file = new File("TestData/HornALCHIQTrans04.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);

			System.out.println("Original ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(ontology);

			HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
			OWLOntology normalizedOnt = normalizer.normalize(ontology);

			HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
			OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
			System.out.println("Normalized ontology: ");
			System.out.println("========================================== ");
			printOntologyAxioms(normalizedOnt1);
			// HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
			// OWLOntology normalizedOnt2 =
			// normalizer2.normalize(normalizedOnt1);
			man.saveOntology(normalizedOnt1,
					IRI.create(new File("TestData/HornALCHIQTrans04Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
