package org.semanticweb.clipper.pathquery;

import com.google.common.base.Joiner;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;

import java.util.List;

public class OrPredicateExpression extends CompoundPredicateExpression {

	static final String SEPRATOR = " | ";

	public OrPredicateExpression(List<DLPredicate> ops) {
		super(ops);
	}

	@Override
	public String toString() {
		return Joiner.on(SEPRATOR).join(getOps());
	}

}
