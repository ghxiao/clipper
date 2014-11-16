package org.semanticweb.clipper.util;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import org.semanticweb.clipper.hornshiq.queryanswering.CQFormatter;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class DecodeUtility {
	
	CQFormatter cqFormatter;

	public DecodeUtility(NamingStrategy namingStrategy){
		this.cqFormatter = new CQFormatter(namingStrategy);
	}
	
	public String decodeQuery(CQ conjunctiveQuery) {
		String decodedQuery = new String();
		int headPredicateCode = conjunctiveQuery.getHead().getPredicate().getEncoding();
		int headPredicateArity = conjunctiveQuery.getHead().getPredicate().getArity();
		if (headPredicateArity == 1) {
			decodedQuery += cqFormatter.getUnaryPredicate(headPredicateCode);
			decodedQuery += "<--";
		} else if (headPredicateArity == 2) {
			decodedQuery += cqFormatter.getBinaryPredicate(headPredicateCode);
			decodedQuery += "<--";
		}

		for (Atom atom : conjunctiveQuery.getBody()) {
			int atomPredicateCode = atom.getPredicate().getEncoding();
			int atomArity = atom.getPredicate().getArity();
			if (atomArity == 1) {
				decodedQuery += cqFormatter.getUnaryPredicate(atomPredicateCode);
				decodedQuery += "<--";
			} else if (atomArity == 2) {
				decodedQuery += cqFormatter.getBinaryPredicate(atomPredicateCode);
				decodedQuery += ",";
			}
		}

		return decodedQuery;
	}

}
