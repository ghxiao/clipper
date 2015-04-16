package org.semanticweb.clipper;

import java.util.Collection;
import java.util.List;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

public interface QueryAnsweringSystem {


	void addOntology(OWLOntology ontology);

	void setQuery(CQ cq);

	List<List<String>> execQuery();
}
