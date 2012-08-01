package org.semanticweb.clipper.cqparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.collect.ImmutableSet;

public class CQParserTest {

	@Test
	public void testParse() throws OWLOntologyCreationException, URISyntaxException, FileNotFoundException, IOException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(CQParserTest.class
				.getResource("/ontologies/LUBM/univ-bench.owl")));
		for (int i = 0; i <= 14; i++) {
			String name = String.format("/ontologies/LUBM/Queries/Query_%02d.txt", i);
			System.out.println(name);
			//final String name = "/ontologies/LUBM/Queries/Query_00.txt";
			InputStream istream = CQParser.class.getResourceAsStream(name);
			CQParser parser = new CQParser(istream, ImmutableSet.of(ontology));
			CQ cq = parser.parse();
			System.out.println(cq);
			System.out.println();
		}
	}

}
