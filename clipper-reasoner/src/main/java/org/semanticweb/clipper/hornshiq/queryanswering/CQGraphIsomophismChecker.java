package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class CQGraphIsomophismChecker {

	public static boolean isSubCQGraph(CQGraph g1, CQGraph g2) {

		BiMap<Variable, Variable> biMap = HashBiMap.create();

		List<Variable> ansVars1 = g1.getAnswerVariables();
		List<Variable> ansVars2 = g2.getAnswerVariables();
		if (ansVars1.size() != ansVars2.size()) {
			return false;
		}

		Collection<Term> vars1 = g1.getVertices();
		Collection<Term> vars2 = g2.getVertices();

		int nAnsVars = ansVars1.size();

		for (int i = 0; i < nAnsVars; i++) {
			biMap.put(ansVars1.get(i), ansVars2.get(i));
		}

		boolean allMapped = false;
		while (!allMapped) {
			
		}

		return false;

	}
}
