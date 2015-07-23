package org.semanticweb.clipper;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.OWLOntology;

import java.util.Collection;

public interface QueryRewrtingSystem {

	void setOntologies(Collection<OWLOntology> ontologies);

	void addOntology(OWLOntology ontology);

	void setQuery(CQ cq);

	Collection<CQ> rewrite();

	Collection<CQ> rewriteTbox();

	Collection<CQ> rewriteOntology();
}
