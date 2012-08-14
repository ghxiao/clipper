package org.semanticweb.clipper;

import java.util.Collection;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

public interface QueryRewrtingSystem {

	void setOntologies(Collection<OWLOntology> ontologies);

	void addOntology(OWLOntology ontology);

	void setQuery(CQ cq);

	Collection<CQ> rewrite();

	Collection<CQ> rewriteTbox();

	Collection<CQ> rewriteOntology();
}
