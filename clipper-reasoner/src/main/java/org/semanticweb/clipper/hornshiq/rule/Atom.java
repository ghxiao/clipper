package org.semanticweb.clipper.hornshiq.rule;

import java.util.Arrays;
import java.util.List;

public class Atom {

	Predicate predicate;

	List<Term> terms;

	// public Atom(){
	// predicate = new Predicate();
	//
	// }

	public Predicate getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate predicate) {
		this.predicate = predicate;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public Atom() {
	}

	public Atom(Predicate predicate, List<Term> terms) {
		this.predicate = predicate;
		this.terms = terms;
	}

	public Atom(Predicate predicate, Term... terms) {
		this(predicate, Arrays.asList(terms));
	}

	// public void changeFirstTerm(Term x){
	// this.terms[0]= x;
	// }
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
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
		Atom other = (Atom) obj;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (terms == null) {
			if (other.terms != null)
				return false;
		} else if (!terms.equals(other.terms))
			return false;
		return true;
	}
}
