package org.semanticweb.clipper.hornshiq.profile;

import java.io.File;

import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class HornALCHIQNormalizerTest {

	
	public static void main(String[] arg) throws OWLOntologyCreationException,
	OWLOntologyStorageException{
		File file = new File("TestData/tkien.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);
		
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer(); 
		OWLOntology normalizedOnt = normalizer.normalize(ontology);
		
		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);
		
		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
		man.saveOntology(normalizedOnt3,
				IRI.create(new File("TestData/nf-horn-alchiq-tkien.owl")));
	}
}
