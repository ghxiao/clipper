package org.semanticweb.clipper.pathquery;


import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

import java.util.List;

public abstract class CompoundPredicateExpression implements Predicate {


	protected List<DLPredicate> ops;

    public CompoundPredicateExpression(List<DLPredicate> ops) {
        this.ops = ops;
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
	public int getArity() {
		return ops.iterator().next().getArity();
	}
	
	@Override
	public void setArity(int arity) {
		throw new IllegalArgumentException("read only");
	}

	@Override
	public String getName() {
		throw new IllegalArgumentException();
	}

    public List<DLPredicate> getOps() {
        return this.ops;
    }
}
