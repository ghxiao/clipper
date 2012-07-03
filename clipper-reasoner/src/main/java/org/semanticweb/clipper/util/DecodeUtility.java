package org.semanticweb.clipper.util;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


public class DecodeUtility {
public String decodeQuery(CQ conjunctiveQuery){
	String decodedQuery = new String();
	int headPredicateCode = conjunctiveQuery.getHead().getPredicate().getEncoding();
	int headPredicateArity =conjunctiveQuery.getHead().getPredicate().getArity();
	if (headPredicateArity==1){
	decodedQuery += getUnaryPredicate(headPredicateCode);
	decodedQuery += "<--";
	} else if (headPredicateArity ==2){
		decodedQuery += getBinaryPredicate(headPredicateCode);
		decodedQuery += "<--";
	}
	
	for (Atom atom : conjunctiveQuery.getBody()){
		int atomPredicateCode = atom.getPredicate().getEncoding();
		int atomArity = atom.getPredicate().getArity();
		if (atomArity==1){
			decodedQuery += getUnaryPredicate(atomPredicateCode);
			decodedQuery += "<--";
			} else if (atomArity ==2){
				decodedQuery += getBinaryPredicate(atomPredicateCode);
				decodedQuery += ",";
			}
	}
	
	
	return decodedQuery;
}

private String getBinaryPredicate(int value) {
	switch (ClipperManager.getInstance().getNamingStrategy()) {
	case LowerCaseFragment:
		OWLObjectPropertyExpression owlExpression = ClipperManager
				.getInstance().getOwlObjectPropertyExpressionEncoder()
				.getSymbolByValue(value);
		if (owlExpression.isAnonymous())
			return "INVERSEOF("
					+ normalize(owlExpression.getNamedProperty().getIRI())
					+ ")";
		else {
			IRI iri = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(value).asOWLObjectProperty().getIRI();
			return normalize(iri);
		}

	case IntEncoding:
		return "r" + value;
	}
	throw new IllegalStateException("Not possible");
}

private String getUnaryPredicate(int value) {
	switch (ClipperManager.getInstance().getNamingStrategy()) {
	case LowerCaseFragment:
		IRI iri = ClipperManager.getInstance().getOwlClassEncoder()
				.getSymbolByValue(value).getIRI();

		return normalize(iri);
	case IntEncoding:
		return "c" + value;
	}
	throw new IllegalStateException("Not possible");
}

private void printClassNamesFromEncodedNamesSet(TIntHashSet encodedNames) {
	TIntIterator iterator = encodedNames.iterator();
	System.out.print("{ ");
	while (iterator.hasNext()) {
		int index = iterator.next();
		System.out.print(getUnaryPredicate(index) + ",");
	}
	System.out.print(" } ");
}

private void printRoleNamesFromEncodedNamesSet(TIntHashSet encodedNames) {
	TIntIterator iterator = encodedNames.iterator();
	System.out.print("{ ");
	while (iterator.hasNext()) {
		int index = iterator.next();
		System.out.print(getBinaryPredicate(index) + ",");
	}
	System.out.print(" } ");
}

private String normalize(IRI iri) {
	String fragment = iri.getFragment();
	if (fragment != null) {
		return fragment.replaceAll("[_-]", "").toLowerCase();
	} else {
		final String iriString = iri.toString();
		int i = iriString.lastIndexOf('/') + 1;
		return iriString.substring(i).replace("_-", "").toLowerCase();

	}

}
}
