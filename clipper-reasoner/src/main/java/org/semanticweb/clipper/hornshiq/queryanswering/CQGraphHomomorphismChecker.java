package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import lombok.core.Main.VersionApp;

import org.apache.commons.collections15.MultiMap;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

public class CQGraphHomomorphismChecker {
	Map<Variable, Variable> map;
	private CQGraph g1;
	private CQGraph g2;

	Collection<Term> visited;

	public CQGraphHomomorphismChecker() {
		// TODO: consider constants
		map = Maps.newHashMap();
		visited = Sets.newHashSet();

	}

	public boolean isHomomorphism(CQGraph g1, CQGraph g2) {

		this.g1 = g1;
		this.g2 = g2;

		List<Variable> ansVars1 = g1.getAnswerVariables();
		List<Variable> ansVars2 = g2.getAnswerVariables();
		if (ansVars1.size() != ansVars2.size()) {
			return false;
		}

		Collection<Term> vars1 = g1.getVertices();
		Collection<Term> vars2 = g2.getVertices();

		int nAnsVars = ansVars1.size();

		for (int i = 0; i < nAnsVars; i++) {
			map.put(ansVars1.get(i), ansVars2.get(i));
		}

		Collection<Term> vertices_1 = g1.getVertices();

		Collection<Term> vertices_2 = g2.getVertices();

		int n1 = vertices_1.size();

		Queue<Term> q = new LinkedList<Term>();

		q.addAll(ansVars1);

		Set<Term> visited = Sets.newHashSet();
		visited.addAll(ansVars1);

		while (!q.isEmpty()) {
			Term term = q.poll();
			System.out.println(term);
			Collection<CQGraphEdge> edges1 = g1.getOutEdges(term);

			Collection<CQGraphEdge> edges2 = g2.getOutEdges(map.get(term));

			for (CQGraphEdge edge1 : edges1) {
				System.out.println(edge1);
				Term dest = edge1.getDest();
				if (!visited.contains(dest)) {
					visited.add(dest);
					q.add(dest);
				}
			}

		}

		return false;

	}

	public Map<Integer, Term> toMap(Collection<CQGraphEdge> edges) {
		Multimap<Integer, Term> ret = HashMultimap.create();
		for (CQGraphEdge edge : edges) {
			ret.put(edge.getRole(), edge.getDest());
		}

		return null;
	}

	public void deepthTraverse(CQGraph g) {
		List<Variable> answerVariables = g.getAnswerVariables();
		for (Variable var : answerVariables) {
			visited.add(var);
			deepthTraverse_recursive(g, var);
		}
	}

	public void deepthTraverse_recursive(CQGraph g, Term node) {
		System.out.println(node);
		Collection<CQGraphEdge> edges = g.getOutEdges(node);
		for (CQGraphEdge edge : edges) {
			System.out.println(edge);
			Term dest = edge.getDest();
			if (!visited.contains(dest)) {
				visited.add(dest);
				deepthTraverse_recursive(g, dest);
			}

		}
	}
	
	//chech(Map<VersionApp>)
}
