package org.semanticweb.clipper.util;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.clipper.util.Ontology2TBoxABox;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Ontology2TBoxABoxTest {

	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private OWLIndividual a;
	private OWLIndividual b;
	private OWLIndividual c;
	private OWLClass A;
	private OWLClass B;
	private OWLClass C;
	private OWLObjectProperty r;
	private OWLDataProperty r_d;
	private OWLObjectProperty s;
	private OWLDataProperty s_d;
	private OWLClass A1;
	private OWLClass B1;
	private OWLClass C1;
	private OWLClass A2;
	private OWLClass B2;
	private OWLClass C2;
	private OWLClass A3;
	private OWLClass B3;
	private OWLClass C3;
	private OWLClass A4;
	private OWLClass B4;
	private OWLClass C4;

	@Before
	public void setUp() {

		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		A = factory.getOWLClass(IRI.create("http://www.example.org/#A"));
		A1 = factory.getOWLClass(IRI.create("http://www.example.org/#A1"));
		A2 = factory.getOWLClass(IRI.create("http://www.example.org/#A2"));
		A3 = factory.getOWLClass(IRI.create("http://www.example.org/#A3"));
		A4 = factory.getOWLClass(IRI.create("http://www.example.org/#A4"));
		B = factory.getOWLClass(IRI.create("http://www.example.org/#B"));
		B1 = factory.getOWLClass(IRI.create("http://www.example.org/#B1"));
		B2 = factory.getOWLClass(IRI.create("http://www.example.org/#B2"));
		B3 = factory.getOWLClass(IRI.create("http://www.example.org/#B3"));
		B4 = factory.getOWLClass(IRI.create("http://www.example.org/#B4"));
		C = factory.getOWLClass(IRI.create("http://www.example.org/#C"));
		C1 = factory.getOWLClass(IRI.create("http://www.example.org/#C1"));
		C2 = factory.getOWLClass(IRI.create("http://www.example.org/#C2"));
		a = factory.getOWLNamedIndividual(IRI
				.create("http://www.example.org/#a"));
		b = factory.getOWLNamedIndividual(IRI
				.create("http://www.example.org/#b"));
		c = factory.getOWLNamedIndividual(IRI
				.create("http://www.example.org/#c"));
		r = factory.getOWLObjectProperty(IRI
				.create("http://www.example.org/#r"));
		s = factory.getOWLObjectProperty(IRI
				.create("http://www.example.org/#s"));
		r_d = factory.getOWLDataProperty(IRI
				.create("http://www.example.org/#r_d"));
		s_d = factory.getOWLDataProperty(IRI
				.create("http://www.example.org/#s_d"));
	}

	@Test
	public void testConvert() throws OWLOntologyCreationException {
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));

		axioms.add(factory.getOWLSubClassOfAxiom(A, B));
		axioms.add(factory.getOWLClassAssertionAxiom(A, a));

		OWLOntology ontology = manager.createOntology(axioms);
		Ontology2TBoxABox o2ta = new Ontology2TBoxABox();
		o2ta.extract(ontology);
		System.out.println("======== ABox =========");

		for (OWLAxiom ax : o2ta.getAbox().getAxioms()) {
			System.out.println(ax);
		}

		System.out.println("======== TBox =========");
		for (OWLAxiom ax : o2ta.getTbox().getAxioms()) {
			System.out.println(ax);
		}
	}

	@Test
	public void testCLI() throws OWLOntologyCreationException, OWLOntologyStorageException, FileNotFoundException {
		String ontology = "test-suite/ontology-semintec/semintec_0.owl";
		String tbox = "test-suite/ontology-semintec/tbox.owl";
		String abox = "test-suite/ontology-semintec/abox.owl";
		Ontology2TBoxABox.main(new String[] { ontology, tbox, abox });
	}

}
