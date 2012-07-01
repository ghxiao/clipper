package org.semanticweb.clipper.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.Axiom;
import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.profile.BitSetNormalHornALCHIQOntologyConverter;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornALCHIQTransNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQNormalizer;
import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.clipper.util.AnswerParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWLProfileReport;



public class AnswerParserTest {

	@Test
	public void test01() throws OWLOntologyCreationException{
		
		File file = new File("TestCaseOntologies/example2.owl");

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

		BitSetNormalHornALCHIQOntologyConverter converter = new BitSetNormalHornALCHIQOntologyConverter();
		NormalHornALCHIQOntology onto_bs = converter.convert(normalizedOnt3);

		for (Axiom ax : onto_bs.getAxioms()) {
			System.out.println(ax);
		}
		String s="q1(d1,d2)";
		List<String> answers = new ArrayList<String>();
		answers.add(s);
		AnswerParser parser= new AnswerParser();
		parser.setAnswers(answers);
		parser.parse();
		System.out.println(parser.getDecodedAnswers());
		
		
	}
}
