package org.semanticweb.clipper.pathquery;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

@RequiredArgsConstructor
@Getter
public abstract class UnaryPredicateExpression implements Predicate {

	@NonNull
	protected Predicate op;

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

}
