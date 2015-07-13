package org.semanticweb.clipper.hornshiq.rule;

import java.util.Arrays;
import java.util.List;

public class Atom {

	Predicate predicate;

	List<Term> terms;

	public Atom(Predicate predicate, Term... terms) {
		this(predicate, Arrays.asList(terms));
	}

	public Atom(Predicate predicate, List<Term> terms) {
		this.predicate = predicate;
		this.terms = terms;
	}

	public Atom() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(predicate);
		sb.append("(");
		boolean first = true;
		for (Term t : terms) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			sb.append(t);
		}
		sb.append(")");
		return sb.toString();
	}

	public Predicate getPredicate() {
		return this.predicate;
	}

	public List<Term> getTerms() {
		return this.terms;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Atom)) return false;
		final Atom other = (Atom) o;
		if (!other.canEqual(this)) return false;
		final Object this$predicate = this.predicate;
		final Object other$predicate = other.predicate;
		if (this$predicate == null ? other$predicate != null : !this$predicate.equals(other$predicate)) return false;
		final Object this$terms = this.terms;
		final Object other$terms = other.terms;
		return !(this$terms == null ? other$terms != null : !this$terms.equals(other$terms));
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $predicate = this.predicate;
		result = result * PRIME + ($predicate == null ? 0 : $predicate.hashCode());
		final Object $terms = this.terms;
		result = result * PRIME + ($terms == null ? 0 : $terms.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof Atom;
	}

    public int getArity() {
        return terms.size();
    }

    public Term getTerm(int i) {
        return terms.get(i);
    }
}
