package org.semanticweb.clipper.hornshiq.ontology;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OntologyTest {

	@Test
	public void test() {
		NormalHornALCHIQOntology ontology = new NormalHornALCHIQOntology();

		ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(14, 12));
		ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(4, 6));
		ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(6, 8));
		ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(6, 10));
		ontology.getSubPropertyAxioms().add(new SubPropertyAxiom(8, 18));

		ontology.getTransitivityAxioms().add(new TransitivityAxiom(4));
		ontology.getTransitivityAxioms().add(new TransitivityAxiom(14));

		List<SubPropertyAxiom> nonSimpleSubPropertyClosure = ontology
				.computeNonSimpleSubPropertyClosure();
		
		System.out.println(nonSimpleSubPropertyClosure);

	}
}
