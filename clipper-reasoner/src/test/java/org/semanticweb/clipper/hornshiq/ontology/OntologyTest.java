package org.semanticweb.clipper.hornshiq.ontology;

import org.junit.Test;

import java.util.List;

public class OntologyTest {

	@Test
	public void test() {
		ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(14, 12));
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(4, 6));
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(6, 8));
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(6, 10));
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(8, 18));

		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(4));
		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(14));

//		List<ClipperSubPropertyAxiom> nonSimpleSubPropertyClosure = ontology
//				.computeNonSimpleSubPropertyClosure();
//
//		System.out.println(nonSimpleSubPropertyClosure);

	}
}
