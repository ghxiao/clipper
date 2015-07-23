package org.semanticweb.clipper.hornshiq.rule;

public class NonDLPredicate implements Predicate {

	int encoding;

	int arity = -1;

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

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof NonDLPredicate)) return false;
        final NonDLPredicate other = (NonDLPredicate) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.encoding != other.encoding) return false;
        if (this.arity != other.arity) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.encoding;
        result = result * PRIME + this.arity;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof NonDLPredicate;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public int getArity() {
        return this.arity;
    }

    public String getName() {
        return this.name;
    }

    public void setEncoding(int encoding) {
        this.encoding = encoding;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }
}
