package org.semanticweb.clipper.hornshiq.rule;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(exclude = "name")
public class NonDLPredicate implements Predicate {

	@Getter
	@Setter
	int encoding;

	@Getter
	@Setter
	int arity = -1;

	@Getter
	String name;

	public NonDLPredicate(String name) {
		this.name = name;
		// TODO computing encodings
	}

	public NonDLPredicate(String name, int arity) {
		this.name = name;
		this.arity = arity;
	}

	public NonDLPredicate(int encoding, int arity) {
		this.encoding = encoding;
		this.arity = arity;
	}

	@Override
	public String toString() {
		if (name != null)
			return name;

		if (encoding != -1)
			return "q" + encoding;
		else
			return "q";
	}

	public boolean isDLPredicate() {
		return false;
	}

}
