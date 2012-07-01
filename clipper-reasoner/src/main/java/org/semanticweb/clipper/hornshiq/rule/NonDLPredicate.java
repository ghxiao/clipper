package org.semanticweb.clipper.hornshiq.rule;

public class NonDLPredicate implements Predicate {
	int encoding;
	int arity = -1;
	String name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arity;
		result = prime * result + encoding;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonDLPredicate other = (NonDLPredicate) obj;
		if (arity != other.arity)
			return false;
		if (encoding != other.encoding)
			return false;
		return true;
	}

	public NonDLPredicate(String name) {
		this.name = name;
		// TODO computing encodings
	}

	public NonDLPredicate(String name, int arity) {
		this.name = name;
		this.arity = arity;
	}

	@Override
	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	@Override
	public int getArity() {
		return arity;
	}

	public void setArity(int arity) {
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

	@Override
	public String getName() {

		return name;
	}
}
