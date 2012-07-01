package org.semanticweb.clipper.hornshiq.profile;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.Axiom;
import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.profile.BitSetNormalHornALCHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
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
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;


public class HornSHIQNormalizerTest {

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
		a = factory.getOWLNamedIndividual(IRI.create("http://www.example.org/#a"));
		b = factory.getOWLNamedIndividual(IRI.create("http://www.example.org/#b"));
		c = factory.getOWLNamedIndividual(IRI.create("http://www.example.org/#c"));
		r = factory.getOWLObjectProperty(IRI.create("http://www.example.org/#r"));
		s = factory.getOWLObjectProperty(IRI.create("http://www.example.org/#s"));
		r_d = factory.getOWLDataProperty(IRI.create("http://www.example.org/#r_d"));
		s_d = factory.getOWLDataProperty(IRI.create("http://www.example.org/#s_d"));
	}

	// A -> B
	@Test
	public void testNormalize001() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));

		axioms.add(factory.getOWLSubClassOfAxiom(A, B));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(1, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getAxioms()) {
			System.out.println(ax);
		}

	}

	// A -> B
	@Test
	public void testNormalize001A() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));

		axioms.add(factory.getOWLSubClassOfAxiom(A, B));

		
		
		OWLAnnotationSubject subject;
		IRI iri = IRI.create("http://example.org/ABC");
		OWLAnnotationProperty property = factory.getOWLAnnotationProperty(iri);

		OWLAnnotation owlAnnotation = factory.getOWLAnnotation(property, iri);
		axioms.add(factory.getOWLDeclarationAxiom(property));
		axioms.add(factory.getOWLAnnotationAssertionAxiom(iri, owlAnnotation));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(1, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getAxioms()) {
			System.out.println(ax);
		}
		


		HornALCHIQTransNormalizer normalizer1 = new HornALCHIQTransNormalizer();
		OWLOntology normalizedOnt1 = normalizer1.normalize(normalizedOnt);

		HornALCHIQNormalizer normalizer2 = new HornALCHIQNormalizer();
		OWLOntology normalizedOnt3 = normalizer2.normalize(normalizedOnt1);
		
		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);
		
		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}


	}

	// and(A, B) -> C
	@Test
	public void testNormalize002() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(A, B), C));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(1, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getAxioms()) {
			System.out.println(ax);
		}
	}

	// C -> and(A,B)
	@Test
	public void testNormalize003() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(C, factory.getOWLObjectIntersectionOf(A, B)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(2, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getAxioms()) {
			System.out.println(ax);
		}
	}

	// or(A1, A2) -> and(B1, B2)
	@Test
	public void testNormalize004() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));

		axioms.add(factory.getOWLDeclarationAxiom(B1));
		axioms.add(factory.getOWLDeclarationAxiom(B2));

		axioms.add(factory.getOWLSubClassOfAxiom(factory.getOWLObjectUnionOf(A1, A2),
				factory.getOWLObjectIntersectionOf(B1, B2)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// or(A1, some(r, A2)) -> and(B1, B2)
	@Test
	public void testNormalize005() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(B1));
		axioms.add(factory.getOWLDeclarationAxiom(B2));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		// A1 and r some A2 subclass B1 and B2
		axioms.add(factory.getOWLSubClassOfAxiom(
				factory.getOWLObjectUnionOf(A1, factory.getOWLObjectSomeValuesFrom(r, A2)),
				factory.getOWLObjectIntersectionOf(B1, B2)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(5, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// not B -> not A
	@Test
	public void testNormalize006() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));

		// not B -> not A
		axioms.add(factory.getOWLSubClassOfAxiom( //
				factory.getOWLObjectComplementOf(B), //
				factory.getOWLObjectComplementOf(A)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(1, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// not(and(A1, A2)) -> not(or(B1, B2))
	@Test
	public void testNormalize007() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(B1));
		axioms.add(factory.getOWLDeclarationAxiom(B2));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		// not(and(A1, A2)) -> not(or(B1, B2))
		axioms.add(factory.getOWLSubClassOfAxiom( //
				factory.getOWLObjectComplementOf(//
				factory.getOWLObjectIntersectionOf(A1, A2)), //
				factory.getOWLObjectComplementOf(//
				factory.getOWLObjectUnionOf(B1, B2))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// A -> not B
	@Test
	public void testNormalize008() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));

		axioms.add(factory.getOWLSubClassOfAxiom( //
				A, //
				factory.getOWLObjectComplementOf(B)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(2, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// A -> or(not B, not C)
	@Test
	public void testNormalize009() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(A,
				factory.getOWLObjectUnionOf(factory.getOWLObjectComplementOf(B), factory.getOWLObjectComplementOf(C))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// C -> not (and (A, B))
	@Test
	public void testNormalize010() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(C,
				factory.getOWLObjectComplementOf(factory.getOWLObjectIntersectionOf(A, B))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getAxioms()) {
			System.out.println(ax);
		}
	}

	// C = and(A, some(r, B))
	@Test
	public void testNormalize011() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLEquivalentClassesAxiom(C,
				factory.getOWLObjectIntersectionOf(A, factory.getOWLObjectSomeValuesFrom(r, B))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		// assertEquals(2, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// C -> and(not A, not B)
	@Test
	public void testNormalize012() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(
				C,
				factory.getOWLObjectIntersectionOf(factory.getOWLObjectComplementOf(A),
						factory.getOWLObjectComplementOf(B))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	@Test
	public void testNormalize013() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(
				C,
				factory.getOWLObjectIntersectionOf(factory.getOWLObjectComplementOf(A),
						factory.getOWLObjectComplementOf(B))));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(4, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	@Test
	public void testNormalize014() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(r));
		axioms.add(factory.getOWLDeclarationAxiom(s));

		axioms.add(factory.getOWLSubClassOfAxiom(
				A,
				factory.getOWLObjectAllValuesFrom(r,
						factory.getOWLObjectSomeValuesFrom(r, factory.getOWLObjectMaxCardinality(1, s, A1))))

		);

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(3, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	@Test
	public void testNormalize015() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(r));
		axioms.add(factory.getOWLDeclarationAxiom(s));

		axioms.add(factory.getOWLClassAssertionAxiom(A, a));

		axioms.add(factory.getOWLObjectPropertyAssertionAxiom(r, a, b));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(2, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// domain(r) = and(A1, A2)
	@Test
	public void testNormalize016() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLObjectPropertyDomainAxiom(r, factory.getOWLObjectIntersectionOf(A1, A2)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(3, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// range(r) = and(A1, A2)
	@Test
	public void testNormalize017() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLObjectPropertyRangeAxiom(r, factory.getOWLObjectIntersectionOf(A1, A2)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(3, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// range(r) = or(A1, A2)
	@Test
	public void testNormalize018() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLObjectPropertyRangeAxiom(r, factory.getOWLObjectUnionOf(A1, A2)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertFalse(report.isInProfile());

		System.out.println(report);

		// OWLOntology normalizedOnt = normalizer.normalize(ontology);
		//
		// assertEquals(3, normalizedOnt.getLogicalAxiomCount());
		//
		// for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
		// System.out.println(ax);
		// }
	}

	@Test
	public void testNormalize019() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(A2));
		axioms.add(factory.getOWLDeclarationAxiom(A3));
		axioms.add(factory.getOWLDeclarationAxiom(A4));
		axioms.add(factory.getOWLDeclarationAxiom(B1));
		axioms.add(factory.getOWLDeclarationAxiom(B2));
		axioms.add(factory.getOWLDeclarationAxiom(B3));
		axioms.add(factory.getOWLDeclarationAxiom(B4));

		axioms.add(factory.getOWLSubClassOfAxiom(factory.getOWLObjectUnionOf(A1, A2, A3, A4),
				factory.getOWLObjectIntersectionOf(B1, B2, B3, B4)));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		// assertFalse(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		// assertEquals(3, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// data property
	// domain(r) = A1
	@Test
	public void testNormalize020() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(r_d));

		axioms.add(factory.getOWLDataPropertyDomainAxiom(r_d, A1));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		// OWLOntology normalizedOnt = normalizer.normalize(ontology);
		//
		// assertEquals(3, normalizedOnt.getLogicalAxiomCount());
		//
		// for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
		// System.out.println(ax);
		// }
	}

	// disjoint(A, B, C)
	@Test
	public void testNormalize021() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLDisjointClassesAxiom(A, B, C));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		// assertEquals(6, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// Func(r)
	@Test
	public void testNormalize022() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLFunctionalObjectPropertyAxiom(r));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		assertEquals(1, normalizedOnt.getLogicalAxiomCount());

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}

	// InvFunc(r)
	@Test
	public void testNormalize023() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLInverseFunctionalObjectPropertyAxiom(r));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);
		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
		assertEquals(1, normalizedOnt.getLogicalAxiomCount());
	}

	// Symmetric(r)
	@Test
	public void testNormalize024() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(r));

		axioms.add(factory.getOWLSymmetricObjectPropertyAxiom(r));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);
		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
		assertEquals(2, normalizedOnt.getLogicalAxiomCount());
	}

	@Test
	public void testNormalize025() throws OWLOntologyCreationException {
		// SubClassOf(ObjectIntersectionOf(<http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine>
		// ObjectSomeValuesFrom(<http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#madeFromGrape>
		// <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#__nom25>)
		// ObjectMaxCardinality(1
		// <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#madeFromGrape>
		// owl:Thing))
		// <http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#PetiteSyrah>)

		OWLClass wine = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine"));
		OWLObjectProperty madeFromGrape = factory.getOWLObjectProperty(IRI
				.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine"));

		OWLClass nom25 = factory
				.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#__nom25"));

		OWLClass petiteSyrah = factory.getOWLClass(IRI
				.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#PetiteSyrah"));
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		axioms.add(factory.getOWLDeclarationAxiom(wine));
		axioms.add(factory.getOWLDeclarationAxiom(madeFromGrape));
		axioms.add(factory.getOWLDeclarationAxiom(nom25));
		axioms.add(factory.getOWLDeclarationAxiom(petiteSyrah));

		axioms.add(factory.getOWLSubClassOfAxiom(factory.getOWLObjectIntersectionOf(wine,
				factory.getOWLObjectSomeValuesFrom(madeFromGrape, nom25),
				factory.getOWLObjectMaxCardinality(1, madeFromGrape), factory.getOWLThing()), petiteSyrah));

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		// assertTrue(report.isInProfile());

		System.out.println(report);

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}

	}

	//
	@Test
	public void testNormalize026() throws OWLOntologyCreationException {
		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

		axioms.add(factory.getOWLDeclarationAxiom(r));
		axioms.add(factory.getOWLDeclarationAxiom(s));
		axioms.add(factory.getOWLDeclarationAxiom(A));
		axioms.add(factory.getOWLDeclarationAxiom(A1));
		axioms.add(factory.getOWLDeclarationAxiom(B));
		axioms.add(factory.getOWLDeclarationAxiom(C));

		axioms.add(factory.getOWLSubClassOfAxiom(
				factory.getOWLObjectSomeValuesFrom(
						r,
						factory.getOWLObjectIntersectionOf(A, factory.getOWLObjectSomeValuesFrom(s, B),
								factory.getOWLObjectSomeValuesFrom(s, A1))), C));

		;

		OWLOntology ontology = manager.createOntology(axioms);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);

		System.out.println(report);
		assertTrue(report.isInProfile());

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
		// assertEquals(2, normalizedOnt.getLogicalAxiomCount());
	}

	@Test
	public void testNormalizeT() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("TestData/t.owl");
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

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}

		man.saveOntology(normalizedOnt, IRI.create(new File("TestData/t.owl")));
	}

	@Test
	public void testNormalizeLUBM1() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("TestData/horn-univ-bench.owl");
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

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}

		man.saveOntology(normalizedOnt, IRI.create(new File("TestData/nf-horn-univ-bench.owl")));
	}

	// original
	@Test
	public void testNormalizeLUBM2() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("TestData/univ-bench.owl");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		//OWLProfile profile = new HornSHIQProfile();
		OWLProfile profile = new OWL2RLProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);
//
//		assertTrue(report.isInProfile());
//
//		System.out.println(report);
//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
//			System.out.println(ax);
//		}
	}

	// original
	@Test
	public void testNormalizeWine() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("test-suite/ontology-wine/terminology.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		HornSHIQProfile profile = new HornSHIQProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		assertFalse(report.isInProfile());

		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}
	
	// original
	@Test
	public void testSteel() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("test-suite/ontology-steel/steel.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		OWLProfile profile = new HornSHIQProfile();
//		OWLProfile profile = new OWL2RLProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		//assertFalse(report.isInProfile());

//		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();
//
//		OWLOntology normalizedOnt = normalizer.normalize(ontology);
//
//		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
//			System.out.println(ax);
//		}
	}

	@Test
	public void testEqv() throws OWLOntologyCreationException, OWLOntologyStorageException {
		File file = new File("TestData/testNorm.owl");

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = man.loadOntologyFromOntologyDocument(file);

		System.out.println(ontology);

		OWLProfile profile = new HornSHIQProfile();
//		OWLProfile profile = new OWL2RLProfile();

		OWLProfileReport report = profile.checkOntology(ontology);
		System.out.println(report);

		//assertFalse(report.isInProfile());

		HornSHIQNormalizer normalizer = new HornSHIQNormalizer();

		OWLOntology normalizedOnt = normalizer.normalize(ontology);

		for (OWLAxiom ax : normalizedOnt.getLogicalAxioms()) {
			System.out.println(ax);
		}
	}
	
	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException{
		new HornSHIQNormalizerTest().testEqv();
	}
}
