package org.semanticweb.clipper.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Ucq2SqlConverter {

	StringBuilder builder = new StringBuilder();

	String viewName;

	public String convert(CQ cq) {
		return convert(Lists.newArrayList(cq));
	}

	public String convert(List<CQ> ucq) {
		return convert(ucq, null);
	}

	public String convert(List<CQ> ucq, String viewName) {

		this.viewName = viewName;

		builder = new StringBuilder();

		if (viewName != null)
			builder.append(String.format("CREATE OR REPLACE VIEW %s AS \n",
					viewName));

		boolean first = true;

		for (CQ cq : ucq) {
			if (!first)
				builder.append("UNION\n");
			first = false;
			builder.append("(\n");

			processCQ(cq);

			builder.append(")\n");
		}

		return builder.toString();
	}

	private void processCQ(CQ cq) {

		Multiset<Predicate> predicates = HashMultiset.create();

		Multimap<Variable, Atom> var2AtomsMmap = HashMultimap.create();

		Map<Atom, Integer> atom2IndexMap = new HashMap<>();

		for (Atom atom : cq.getBody()) {
			for (Term term : atom.getTerms()) {
				if (term.isVariable()) {
					Variable variable = term.asVariable();
					var2AtomsMmap.put(variable, atom);
				}
			}

			predicates.add(atom.getPredicate());

			atom2IndexMap.put(atom, predicates.count(atom.getPredicate()));
		}

		builder.append("SELECT \n");

		boolean first = true;

		int i = 0;
		for (Term term : cq.getHead().getTerms()) {
			i++;
			if (!first)
				builder.append(", ");
			first = false;

			Variable var = term.asVariable();

			Atom atom = var2AtomsMmap.get(var).iterator().next();

			int k = 0;
			if (atom.getTerms().get(0).equals(var)) {
				k = 1;
			} else if (atom.getTerms().get(1).equals(var)) {
				k = 2;
			}
			builder.append(String.format("v_%s_%d.att%d AS att%d ",
					atom.getPredicate(), atom2IndexMap.get(atom), k, i));
		}

		builder.append("\nFROM \n");

		first = true;
		for (Entry<Predicate> e : predicates.entrySet()) {

			Predicate predicate = e.getElement();
			int count = e.getCount();
			for (i = 1; i <= count; i++) {
				if (!first)
					builder.append(",\n");
				first = false;
				builder.append(String.format("v_%s v_%s_%d", predicate,
						predicate, i));
			}
		}

		builder.append("\nWHERE \n");

		first = true;

		for (java.util.Map.Entry<Variable, Collection<Atom>> e : var2AtomsMmap
				.asMap().entrySet()) {
			Variable var = e.getKey();
			Collection<Atom> atoms = e.getValue();
			if (atoms.size() > 1) {
				Iterator<Atom> iterator = atoms.iterator();
				Atom firstAtom = iterator.next();

				Predicate firstPredicate = firstAtom.getPredicate();
				int firstArity = firstPredicate.getArity();

				int firstK = 0;
				if (firstAtom.getTerms().get(0).equals(var)) {
					firstK = 1;
				} else if (firstAtom.getTerms().get(1).equals(var)) {
					firstK = 2;
				}

				if (firstArity == 2
						&& firstAtom.getTerms().get(0)
								.equals(firstAtom.getTerms().get(1))) {
					if (!first) {
						builder.append(" AND ");
					}
					first = false;
					builder.append(String.format(
							"v_%s_%d.att%d = v_%s_%d.att%d\n", firstPredicate,
							1, firstK, firstPredicate, 1, 2));
				}

				int c = 0;

				while (iterator.hasNext()) {
					c++;
					Atom thisAtom = iterator.next();
					Predicate thisPredicate = thisAtom.getPredicate();
					int thisArity = thisPredicate.getArity();

					for (int j = 0; j < thisArity; j++)

						if (thisAtom.getTerms().get(j).equals(var)) {
							if (!first) {
								builder.append(" AND ");
							}
							first = false;

							builder.append(String.format(
									"v_%s_%d.att%d = v_%s_%d.att%d\n",
									firstPredicate, 1, firstK, thisPredicate,
									atom2IndexMap.get(thisAtom), j + 1));
						}

				}
			}
		}
	}

}
