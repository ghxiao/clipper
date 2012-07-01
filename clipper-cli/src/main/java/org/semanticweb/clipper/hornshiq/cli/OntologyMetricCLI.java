package org.semanticweb.clipper.hornshiq.cli;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.profile.HornSHIQProfile;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;


public class OntologyMetricCLI {
	public static void main(String[] args) throws OWLOntologyCreationException {
		String owlFileName = args[0];

		File file = new File(owlFileName);

		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		// Now load the local copy
		OWLOntology ont = man.loadOntologyFromOntologyDocument(file);

		
		System.out.format("%10s%20s", "Ontology Name:", file.getName());
		System.out.format("%10s%20d", "Concepts:", ont.getClassesInSignature().size());
		System.out.format("%10s%20d", "Object Properties:", ont.getObjectPropertiesInSignature().size());
		System.out.format("%10s%20d", "Data Properties:", ont.getDataPropertiesInSignature().size());
		System.out.format("%10s%20d", "Individuals:", ont.getIndividualsInSignature().size());
		System.out.format("%10s%20d", "Axioms:", ont.getAxioms().size());
		System.out.format("%10s%20d", "Logical Axioms:", ont.getLogicalAxioms().size());
		System.out.format("%10s%20d", "Tbox Axioms:", ont.getTBoxAxioms(true).size());
		System.out.format("%10s%20d", "Abox Axioms:", ont.getABoxAxioms(true).size());

		
		
		OWLProfile[] profiles = { new OWL2DLProfile(), new OWL2RLProfile(),
				new OWL2QLProfile(), new OWL2ELProfile(), new HornSHIQProfile() };

		
		
		for (OWLProfile profile : profiles) {
			OWLProfileReport report = profile.checkOntology(ont);

			Set<OWLAxiom> violatedAxioms = new HashSet<OWLAxiom>();

			if (report.isInProfile()) {
				System.out.println("The ontology is in " + profile.getName());
			} else {
				int n = 0;
				System.out.println("The ontology is not in "
						+ profile.getName());
				for (OWLProfileViolation v : report.getViolations()) {
					n++;
					System.out.println(n + ". " + v);
					violatedAxioms.add(v.getAxiom());
				}
			}

		}

		
	}
}
