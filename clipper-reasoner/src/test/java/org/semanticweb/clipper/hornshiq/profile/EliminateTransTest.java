package org.semanticweb.clipper.hornshiq.profile;

import java.io.File;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class EliminateTransTest {
	@Test
	public void test01() {
		File file = new File("TestData/EliminateTransTest01.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);
			
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer(); 
		OWLOntology normalizedOnt = normalizer.normalize(ontology);
		
		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
		
		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt2 = normalizer2.normalize(normalizedOnt1);
		man.saveOntology(normalizedOnt2,
				IRI.create(new File("TestData/EliminateTransTest01Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test02() {
		File file = new File("TestData/EliminateTransTest02.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);
			
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer(); 
		OWLOntology normalizedOnt = normalizer.normalize(ontology);
		
		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
		
		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt2 = normalizer2.normalize(normalizedOnt1);
		man.saveOntology(normalizedOnt2,
				IRI.create(new File("TestData/EliminateTransTest02Norm.owl")));
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
		File file = new File("TestData/EliminateTransTest03.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = man.loadOntologyFromOntologyDocument(file);
			
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer(); 
		OWLOntology normalizedOnt = normalizer.normalize(ontology);
		
		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
		
		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt2 = normalizer2.normalize(normalizedOnt1);
		man.saveOntology(normalizedOnt2,
				IRI.create(new File("TestData/EliminateTransTest03Norm.owl")));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
