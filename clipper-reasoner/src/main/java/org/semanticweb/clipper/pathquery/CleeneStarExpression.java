package org.semanticweb.clipper.pathquery;

import org.semanticweb.clipper.hornshiq.rule.Predicate;

public class CleeneStarExpression extends UnaryPredicateExpression {

	public CleeneStarExpression(Predicate op) {
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
	public String toString(){
		return op + "*";
	}

}
