package org.semanticweb.clipper.util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.cqparser.CQParserTest;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

public class Ucq2SqlConverterTest {

	@Test
	public void test() throws OWLOntologyCreationException, URISyntaxException,
			FileNotFoundException, IOException {

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI
				.create(CQParserTest.class.getResource("/LUBM-ex-20.owl")));

		// String s =
		// "ans(?0,?1) :- worksFor(?1,?3), Faculty(?1), Course(?2), memberOf(?0,?3), Student(?0), takesCourse(?0,?2), Department(?3), teacherOf(?1,?2).";
		// String s =
		// "ans(?0,?1) :- worksFor(?1,?3), Faculty(?1), Course(?2), memberOf(?0,?3), Student(?0), takesCourse(?0,?2), Department(?3), teacherOf(?1,?2).";
		String s = "";

//		s += "ans(X0,X1) :- worksFor(X1,X3), Faculty(X1), Course(X2), memberOf(X0,X3), Student(X0), takesCourse(X0,X2), Department(X3), teacherOf(X1,X2).";
//		s += "ans(X0,X1) :- worksFor(X1,X3), Faculty(X1), memberOf(X0,X3), Student(X0), takesCourse(X0,X2), Department(X3), teacherOf(X1,X2).";
//		s += "ans(X3,X3) :- Student(X3), takesCourse(X3,X2), Faculty(X3), teacherOf(X3,X2).";
//		s += "ans(X3,X3) :- Student(X3), Chair(X3), takesCourse(X3,X2), Faculty(X3), teacherOf(X3,X2).";
		
		s += "ans(?0,?1) :- worksFor(?1,?3), Faculty(?1), Course(?2), memberOf(?0,?3), Student(?0), takesCourse(?0,?2), Department(?3), teacherOf(?1,?2).";
		s += "ans(?0,?1) :- worksFor(?1,?3), Faculty(?1), memberOf(?0,?3), Student(?0), takesCourse(?0,?2), Department(?3), teacherOf(?1,?2).";
		s += "ans(?3,?3) :- Student(?3), takesCourse(?3,?2), Faculty(?3), teacherOf(?3,?2).";
		s += "ans(?3,?3) :- Student(?3), Chair(?3), takesCourse(?3,?2), Faculty(?3), teacherOf(?3,?2).";

		// final String name = "/ontologies/LUBM/Queries/Query_00.txt";
		// InputStream istream = CQParser.class.getResourceAsStream(name);
		CQParser parser = new CQParser(s, ImmutableSet.of(ontology));
		List<CQ> ucq = parser.parseUCQ();
		
		
		
		System.out.println(ucq);
		System.out.println();

		Joiner.on("\n").appendTo(System.out, ucq);
		
		System.out.println();
		
		Ucq2SqlConverter converter = new Ucq2SqlConverter();
		String sql = converter.convert(ucq, "v_q1");
		
		System.out.println(sql);
	}

}
