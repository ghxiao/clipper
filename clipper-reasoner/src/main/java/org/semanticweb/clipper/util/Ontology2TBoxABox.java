package org.semanticweb.clipper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Ontology2TBoxABox {

	OWLOntology ontology;

	private OWLOntology abox;

	private OWLOntology tbox;

	OWLOntologyManager manager;

	public Ontology2TBoxABox() {

	}

	public void extract(OWLOntology ontology) {
		this.ontology = ontology;
		this.manager = OWLManager.createOWLOntologyManager();
		try {
			abox = manager.createOntology(ontology.getABoxAxioms(false));
			tbox = manager.createOntology(ontology.getTBoxAxioms(false));
			manager.addAxioms(tbox, ontology.getRBoxAxioms(false));

		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

	public OWLOntology getAbox() {
		return abox;
	}

	public OWLOntology getTbox() {
		return tbox;
	}

	public static void main(String[] args) throws OWLOntologyCreationException,
			OWLOntologyStorageException, FileNotFoundException {
		if (args.length < 3) {
			System.out
					.println("Usage: Ontology2TBoxABox onto.owl tbox.owl abox.owl");
			System.exit(0);
		}

		File ontoFile = new File(args[0]);
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(ontoFile);

		Ontology2TBoxABox o2ta = new Ontology2TBoxABox();
		o2ta.extract(ontology);
		man.saveOntology(o2ta.getTbox(), new RDFXMLOntologyFormat(),
				new FileOutputStream(new File(args[1])));

		man.saveOntology(o2ta.getAbox(), new RDFXMLOntologyFormat(),
				new FileOutputStream(new File(args[2])));
	}
}
