package org.semanticweb.clipper.pathquery;

import org.semanticweb.clipper.hornshiq.rule.Predicate;

public class BooleanCombination implements Predicate {

	private Predicate role;
	private Predicate concept;
	
	@Override
	public boolean isDLPredicate() {
		return false;
	}

	@Override
	public int getEncoding() {
		throw new IllegalArgumentException();
	}

	@Override
	public void setArity(int arity) {
		throw new IllegalArgumentException("read only");
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public String getName() {
		throw new IllegalArgumentException();
	}

}
