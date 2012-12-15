package org.semanticweb.clipper.hornshiq.queryanswering;

public enum NamingStrategy {
	// OriginalName,
	/** Using fragment for predicate with original case, full iri for constants */
	FRAGMENT,
	/** Using fragment for predicate and with lower case initial, full iri for constants */
	LOWER_CASE_FRAGMENT,
	/** Everything has an integer encoding */
	INT_ENCODING,
}