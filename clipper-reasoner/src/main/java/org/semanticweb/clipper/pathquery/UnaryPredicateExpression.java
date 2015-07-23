package org.semanticweb.clipper.pathquery;

import org.semanticweb.clipper.hornshiq.rule.Predicate;

public abstract class UnaryPredicateExpression implements Predicate {


	protected Predicate op;

    public UnaryPredicateExpression(Predicate op) {
        this.op = op;
    }

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
		throw new IllegalArgumentException();
	}


    public Predicate getOp() {
        return this.op;
    }
}
