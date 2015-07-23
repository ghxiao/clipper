package org.semanticweb.clipper;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.List;

public interface QueryAnsweringSystem {


	void addOntology(OWLOntology ontology);

	void setQuery(CQ cq);

	List<List<String>> execQuery();
}
