package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

/**
 * 
 * Let g1 and g2 be two CQGraphs. We say g1 is homomorphism to g2, if there is a
 * mapping f : V(g1) -> V(g2) U I(g2), s.t.
 * 
 * <ul>
 * <li>f(ansVars(g1)) = ansVars(g2) ;</li>
 * <li>for all A(x) in g1, A(f(x)) in g2 ;</li>
 * <li>for all r(x, y) in g1, r(f(x), f(y)) in g2 .</li>
 * 
 * </ul>
 * 
 * 
 * NOTE: if g1 is homomorphism to g2, then query CQ(g2) is contained in CQ(g1).
 * In other words, g2 is redundant.
 * 
 * @author xiao
 * 
 */

public class CQGraphHomomorphismChecker {
	// Map<Variable, Variable> map;
	private CQGraph g1;
	private CQGraph g2;

	Collection<Term> visited;

	// compaitable table
	private Multimap<Term, Term> table;

	@Getter
	private Map<Term, Term> map;
	private Collection<Term> unvisited;

	public CQGraphHomomorphismChecker() {
		// TODO: consider constants
		// map = Maps.newHashMap();
		visited = Sets.newHashSet();
	}

	public boolean isHomomorphism(CQGraph g1, CQGraph g2) {

		this.g1 = g1;
		this.g2 = g2;
		table = HashMultimap.create();
		List<Variable> ansVars1 = g1.getAnswerVariables();
		List<Variable> ansVars2 = g2.getAnswerVariables();
		if (ansVars1.size() != ansVars2.size()) {
			return false;
		}

		int nAnsVars = ansVars1.size();

		Map<Term, Term> map = Maps.newHashMap();
		for (int i = 0; i < nAnsVars; i++) {
			table.put(ansVars1.get(i), ansVars2.get(i));
			map.put(ansVars1.get(i), ansVars2.get(i));
		}
		this.map = map;

		for (Term v1 : g1.getVertices()) {
			if (!table.containsKey(v1)) {
				Collection<Integer> concepts = g1.getConcepts(v1);
				Collection<CQGraphEdge> inEdges = g1.getInEdges(v1);
				List<Collection<Integer>> inRoles = roles(inEdges);
				Collection<CQGraphEdge> outEdges = g1.getOutEdges(v1);
				List<Collection<Integer>> outRoles = roles(outEdges);

				for (Term v2 : g2.getVertices()) {
					final Collection<Integer> concepts2 = g2.getConcepts(v2);
					final Collection<CQGraphEdge> inEdges2 = g2.getInEdges(v2);
					List<Collection<Integer>> inRoles2 = roles(inEdges2);
					final Collection<CQGraphEdge> outEdges2 = g2.getOutEdges(v2);
					List<Collection<Integer>> outRoles2 = roles(outEdges2);
					if (concepts2.containsAll(concepts) && subset(inRoles, inRoles2)
					// && inRoles2.containsAll(inRoles)
							&& outRoles2.containsAll(outRoles)) {
						table.put(v1, v2);
					}
				}

				// no compatible counterpart
				if (!table.containsKey(v1)) {
					return false;
				}
			}
		}

		// Set<Term> visited = Sets.newHashSet();
		// visited.addAll(ansVars1);

		// Set<Term> unvisited = Sets.newHashSet(g1.getVertices());
		// unvisited.removeAll(visited);
		// FIXME more components

		while (!unvisited.isEmpty()) {
			// there is an unvisited

			List<Variable> unvisitedAnsVars = Lists.newArrayList(g1.getAnswerVariables());
			unvisitedAnsVars.retainAll(unvisited);

			if (!unvisitedAnsVars.isEmpty()) {
				Variable v1 = unvisitedAnsVars.iterator().next();
				Term v2 = table.get(v1).iterator().next();
				this.map.put(v1, v2);

				if (!check(map, g2, g1))
					return false;

				if (!this.depthTraverse_recursive(v1, v2, this.map, null)) {
					return false;
				}
			} else {

			}

			HashSet<Term> unvisited = new HashSet<Term>(g1.getVertices());
			unvisited.removeAll(visited);

		}

		return true;

	}

	/**
	 * @param edges
	 * @return roles in edges
	 * 
	 * 
	 */
	private List<Collection<Integer>> roles(Collection<CQGraphEdge> edges) {
		List<Collection<Integer>> ret = Lists.newArrayListWithCapacity(edges.size());

		Multimap<Term, Integer> mmap = HashMultimap.create();

		for (CQGraphEdge e : edges) {
			mmap.put(e.getDest(), e.getRole());
		}

		for (Term t : mmap.keySet()) {
			ret.add(mmap.get(t));
		}

		return ret;
	}

	public boolean subset(List<Collection<Integer>> lst1, List<Collection<Integer>> lst2) {
		for (Collection<Integer> elt1 : lst1) {
			boolean in = false;
			for (Collection<Integer> elt2 : lst2) {
				if (elt2.containsAll(elt1)) {
					in = true;
					break;
				}
			}
			if (!in) {
				return false;
			}
		}
		return true;
	}

	private Collection<Term> dests(Collection<CQGraphEdge> edges) {
		Set<Term> roles = Sets.newHashSetWithExpectedSize(edges.size());
		for (CQGraphEdge e : edges) {
			roles.add(e.getDest());
		}
		return roles;
	}

	private Collection<Term> sources(Collection<CQGraphEdge> edges) {
		Set<Term> roles = Sets.newHashSetWithExpectedSize(edges.size());
		for (CQGraphEdge e : edges) {
			roles.add(e.getSource());
		}
		return roles;
	}

	public boolean depthTraverse_recursive(Term node1, Term node2, Map<Term, Term> map, List<Term> unvistedNodes) {
		// visited.add(node1);

		boolean ret = false;

		System.out.println(node1);

		List<Term> unvisitedNeghbors = Lists.newArrayList(unvistedNodes);
		Collection<Term> neighbors = g1.getNeighbors(node1);
		unvisitedNeghbors.retainAll(neighbors);

		Collection<Term> unvistedDests = dests(g1.getOutEdges(node1));

		unvistedDests.retainAll(unvistedNodes);

		if (unvisitedNeghbors.size() > 0) {

			Collection<CQGraphEdge> edges1;
			Collection<CQGraphEdge> edges2;

			edges1 = g1.getOutEdges(node1);
			edges2 = g2.getOutEdges(node2);

			for (Term dest1 : dests(edges1)) {
				for (Term dest2 : dests(edges2)) {
					if (table.containsEntry(dest1, dest2)) {
						Map<Term, Term> newMap = Maps.newHashMap(map);
						newMap.put(dest1, dest2);
						if (depthTraverse_recursive(dest1, dest2, newMap, null)) {
							return true;
						}
					}
				}
			}

			edges1 = g1.getInEdges(node1);
			edges2 = g2.getInEdges(node2);

			for (Term src1 : sources(edges1)) {
				for (Term src2 : sources(edges2)) {
					if (table.containsEntry(src1, src2)) {
						Map<Term, Term> newMap = Maps.newHashMap(map);
						newMap.put(src1, src2);
						if (depthTraverse_recursive(src1, src2, newMap, null)) {
							return true;
						}
					}
				}
			}

		} else if (unvisited.size() > 0) {
			// search another component

		} else {
			this.map = map;
			return true;
		}
		return false;

	}

	public void depthTraverse(Term node1, Term node2, Map<Term, Term> map) {

	}

	boolean check(Map<Term, Term> map, CQGraph g1, CQGraph g2) {

		for (Variable vertex : g1.getAnswerVariables()) {
			if (!g2.isAnswerVariable(map.get(vertex))) {
				return false;
			}
		}

		for (Term vertex : g1.getVertices()) {
			if (!g2.containsVertex(map.get(vertex))) {
				return false;
			}
		}

		for (CQGraphEdge edge : g1.getEdges()) {
			CQGraphEdge edge2 = new CQGraphEdge(map.get(edge.getSource()), map.get(edge.getDest()), edge.getRole());
			if (!g2.containsEdge(edge2)) {
				return false;
			}
		}

		return true;

	}
}
