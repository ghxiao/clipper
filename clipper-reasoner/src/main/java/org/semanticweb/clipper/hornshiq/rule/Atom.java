package org.semanticweb.clipper.hornshiq.rule;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Atom {

	Predicate predicate;

	List<Term> terms;

	public Atom(Predicate predicate, Term... terms) {
		this(predicate, Arrays.asList(terms));
	}

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

}
