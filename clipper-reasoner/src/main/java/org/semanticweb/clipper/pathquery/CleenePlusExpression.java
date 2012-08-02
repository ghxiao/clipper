package org.semanticweb.clipper.pathquery;

import org.semanticweb.clipper.hornshiq.rule.Predicate;

public class CleenePlusExpression extends UnaryPredicateExpression {

	public CleenePlusExpression(Predicate op) {
		super(op);
	}

	@Override
	public int getArity() {
		return 2;
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return op + "+";
	}

}
