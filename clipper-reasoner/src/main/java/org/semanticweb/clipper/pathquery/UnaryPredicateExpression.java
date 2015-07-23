package org.semanticweb.clipper.pathquery;

import lombok.NonNull;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

public abstract class UnaryPredicateExpression implements Predicate {

	@NonNull
	protected Predicate op;

    @java.beans.ConstructorProperties({"op"})
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

    @NonNull
    public Predicate getOp() {
        return this.op;
    }
}
