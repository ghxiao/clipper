package org.semanticweb.clipper.pathquery;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

@RequiredArgsConstructor
public abstract class CompoundPredicateExpression implements Predicate {

	@Getter
	@NonNull
	protected List<DLPredicate> ops;

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


}
