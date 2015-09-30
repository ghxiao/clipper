package org.semanticweb.clipper.performancetuning;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.clipper.cqparser.CQParser;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.queryanswering.QAHornSHIQ;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public class ClipperOnLargeOntology {

	public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException, IOException {
//		String ontologyFile = "src/main/resources/LargeOntologies/nci/nci3.12e_dllite.owl";
//		String cqFile = "src/main/resources/LargeOntologies/nci/q5.cq";
//		String datalogResultFile="src/main/resources/LargeOntologies/nci/q5.result.dl";
		
//		String ontologyFile = "src/main/resources/LargeOntologies/galen/galen-doctored_elhi.owl";
//		String cqFile = "src/main/resources/LargeOntologies/galen/q1.cq";
//		String datalogResultFile="src/main/resources/LargeOntologies/galen/q1.result.dl";
//		
	
//		String ontologyFile = "src/main/resources/LargeOntologies/obo/obo-protein_elhi.owl";
//		String cqFile = "src/main/resources/LargeOntologies/obo/q2.cq";
//		String datalogResultFile="src/main/resources/LargeOntologies/obo/q2.result.dl";
//	
		
		String ontologyFile = "src/main/resources/LargeOntologies/nasa/nasa-sweet_v2_elhi.owl";
		String cqFile = "src/main/resources/LargeOntologies/nasa/q1.cq";
		String datalogResultFile="src/main/resources/LargeOntologies/nasa/q1.result.dl";
	
//		String ontologyFile = "src/main/resources/LargeOntologies/not-galen/not-galen_elhi.owl";
//		String cqFile = "src/main/resources/LargeOntologies/not-galen/q1.cq";
//		String datalogResultFile="src/main/resources/LargeOntologies/not-galen/q1.result.dl";
		
		System.setProperty("entityExpansionLimit", "0");// 0 means no limit
		QAHornSHIQ qaHornSHIQ = new QAHornSHIQ();
		qaHornSHIQ.setNamingStrategy(NamingStrategy.LOWER_CASE_FRAGMENT);

		OWLOntology ontology = OWLManager.createOWLOntologyManager()
				.loadOntologyFromOntologyDocument(new File(ontologyFile));
		Set<OWLOntology> ontologies = new HashSet<>();
		ontologies.add(ontology);

		qaHornSHIQ.setOntologies(ontologies);

		CQParser parser = new CQParser(new File(cqFile), ontologies);
		CQ cq = parser.parse();
		qaHornSHIQ.setQuery(cq);
		qaHornSHIQ.setDatalogFileName(datalogResultFile);
		qaHornSHIQ.generateQueriesAndCompletionRulesDatalog();

	}
}
