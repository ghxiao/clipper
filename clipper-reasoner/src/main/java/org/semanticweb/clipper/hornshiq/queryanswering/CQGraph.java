package org.semanticweb.clipper.hornshiq.queryanswering;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

/**
 * Direct Graph Representation of a Conjunctive Query
 * 
 * @author xiao
 * 
 */
@SuppressWarnings("serial")
public class CQGraph extends DirectedSparseMultigraph<Variable, CQGraphEdge> {

	/**
	 * distinguished (answer, output) variables
	 */
	private List<Variable> answerVariables = Lists.newArrayList();

	/**
	 * the map from the Variables to list of concepts
	 */
	private Multimap<Variable, Integer> concepts = HashMultimap.create();

	/**
	 * never use
	 */
	private CQGraph() {
	}

	/**
	 * construct a CQGraph from a CQ
	 * 
	 * @param cq
	 */
	public CQGraph(CQ cq) {
		super();

		for (Atom atom : cq.getBody()) {
			Predicate predicate = atom.getPredicate();
			List<Term> terms = atom.getTerms();
			if (predicate.getArity() == 2) {
				CQGraphEdge edge = new CQGraphEdge((Variable) terms.get(0), (Variable) terms.get(1),
						predicate.getEncoding());
				this.addEdge(edge);
			} else if (predicate.getArity() == 1) {
				Variable var = (Variable) terms.get(0);
				this.addVertex(var);
				concepts.put(var, predicate.getEncoding());
			}
		}

		for (Term t : cq.getHead().getTerms()) {
			Variable var = (Variable) t;
			answerVariables.add(var);
		}

	}

	private void addEdge(CQGraphEdge edge) {
		this.addEdge(edge, edge.getVar1(), edge.getVar2());
	}

	public CQGraph deepCopy() {
		CQGraph g = new CQGraph();

		for (Variable vertex : this.getVertices()) {
			g.addVertex(vertex);
		}

		for (CQGraphEdge edge : this.getEdges()) {
			g.addEdge(edge);
		}

		// g.roles.putAll(this.roles);
		g.concepts.putAll(this.concepts);
		g.answerVariables.addAll(this.answerVariables);

		return g;

	}

	public void focus(Collection<Variable> leaves) {
		List<CQGraphEdge> outEdges = this.getOutEdges(leaves);
		List<CQGraphEdge> revertedEdges = new ArrayList<CQGraphEdge>();
		for (CQGraphEdge outEdge : outEdges) {
			CQGraphEdge inEdge = inverseEdge(outEdge);
			revertedEdges.add(inEdge);
		}

		for (CQGraphEdge outEdge : outEdges) {
			this.removeEdge(outEdge);
		}

		for (CQGraphEdge revertedEdge : revertedEdges) {
			this.addEdge(revertedEdge);
		}
	}

	public void clip(Collection<Variable> leaves, //
			Collection<CQGraphEdge> edges, //
			Map<CQGraphEdge, Integer> map, //
			List<Integer> type) {
		Variable vertex0 = leaves.iterator().next();

		checkState(this.containsVertex(vertex0), "vertex is not in the graph!");

		boolean distinguished = false;

		List<Integer> newType = Lists.newArrayList(type);

		// vertex0.concepts = type;
		for (CQGraphEdge edge : edges) {
			boolean removingEdge = true;

			if (map.containsKey(edge)) {
				Integer subRole = map.get(edge);
				CQGraphEdge newEdge = new CQGraphEdge(edge.getVar1(), vertex0, subRole);
				replaceEdge(edge, newEdge);
				if (edge.getRole().equals(subRole) && edge.getVar2().equals(vertex0)) {
					removingEdge = false;
				}
			} else {
				Variable parentVertex = edge.getVar1();

				List<CQGraphEdge> inEdges = Lists.newArrayList(getInEdges(parentVertex));
				for (CQGraphEdge e1 : inEdges) {
					CQGraphEdge newEdge = new CQGraphEdge(e1.getVar1(), vertex0, edge.getRole());
					replaceEdge(e1, newEdge);
				}
				List<CQGraphEdge> outEdges = Lists.newArrayList(this.getOutEdges(parentVertex));
				for (CQGraphEdge e1 : outEdges) {
					if (!edge.equals(e1)) {
						CQGraphEdge newEdge = new CQGraphEdge(vertex0, e1.getVar2(), edge.getRole());
						replaceEdge(e1, newEdge);
					}
				}
				if (isAnswerVariable(parentVertex)) {
					distinguished = true;
				}
				newType.addAll(getConcepts(parentVertex));

				removeVertex(parentVertex);
			}

			if (removingEdge) {
				removeEdge(edge);
			}

		}

		mergeLeafVertices(vertex0, leaves, newType, distinguished);
	}

	private void mergeLeafVertices(Variable selectedVertex, Collection<Variable> leafVertices, List<Integer> type,
			boolean distinguished) {
		Collection<CQGraphEdge> interLeafEdges = this.getInterEdges(leafVertices);
		for (CQGraphEdge edge : interLeafEdges) {
			this.removeEdge(edge);
		}
		for (Variable vertex : leafVertices) {
			if (vertex != selectedVertex) {
				this.removeVertex(vertex);
			}
		}

		getConcepts(selectedVertex).clear();
		getConcepts(selectedVertex).addAll(type);
		if (distinguished)
			answerVariables.add(selectedVertex);
	}

	/**
	 * @param var
	 * @return
	 */
	public Collection<Integer> getConcepts(Variable var) {
		return concepts.get(var);
	}

	/**
	 * @param variable
	 * @return
	 */
	public boolean isAnswerVariable(Variable variable) {
		return answerVariables.contains(variable);
	}

	/**
	 * replace the old edge by a new edge, and transfer the roles from the old
	 * to the new
	 * 
	 * @param oldEdge
	 * @param newEdge
	 */
	private void replaceEdge(CQGraphEdge oldEdge, CQGraphEdge newEdge) {
		this.removeEdge(oldEdge);
		this.addEdge(newEdge);
	}

	@Override
	public boolean removeVertex(Variable vertex) {
		checkState(!isAnswerVariable(vertex));
		this.concepts.removeAll(vertex);
		// this.answerVariables.remove(vertex);
		return super.removeVertex(vertex);
	}

	private CQGraphEdge inverseEdge(CQGraphEdge inEdge) {

		Integer role = inEdge.getRole();

		// TODO: don't forget InvAxioms
		int invRole;
		if (role % 2 == 0) {
			invRole = role + 1;
		} else {
			invRole = role - 1;
		}

		CQGraphEdge outEdge = new CQGraphEdge(inEdge.getVar2(), inEdge.getVar1(), invRole);

		return outEdge;
	}

	public List<CQGraphEdge> getOutEdges(Collection<Variable> leaves) {
		List<CQGraphEdge> outEdges = new ArrayList<CQGraphEdge>();
		for (Variable vertex : leaves) {
			Collection<CQGraphEdge> possibleOutEdges = this.getOutEdges(vertex);

			for (CQGraphEdge edge : possibleOutEdges) {
				Variable second = edge.getVar2();
				if (!leaves.contains(second)) {
					outEdges.add(edge);
				}
			}

		}
		return outEdges;
	}

	public List<CQGraphEdge> getInEdges(Collection<Variable> vertices) {
		List<CQGraphEdge> inEdges = new ArrayList<CQGraphEdge>();
		for (Variable vertex : vertices) {
			Collection<CQGraphEdge> possibleInEdges = this.getInEdges(vertex);
			for (CQGraphEdge edge : possibleInEdges) {
				Variable first = edge.getVar1();
				if (!vertices.contains(first)) {
					inEdges.add(edge);
				}
			}
		}
		return inEdges;
	}

	/**
	 * get the edges between the vertices,
	 * 
	 * NOTE that self loop edges (e=(v,v)) are not included
	 * 
	 * @param vertices
	 * @return
	 */
	public Collection<CQGraphEdge> getInterEdges(Collection<Variable> vertices) {
		Set<CQGraphEdge> interEdges = Sets.newHashSet();
		for (Variable vertex : vertices) {
			Preconditions.checkState(this.containsVertex(vertex), "the vertex is not in the graph");
			for (CQGraphEdge edge : this.getInEdges(vertex)) {
				Variable first = this.getSource(edge);
				if (vertices.contains(first) && !edge.getVar1().equals(edge.getVar2())) {
					interEdges.add(edge);
				}
			}
			for (CQGraphEdge edge : this.getOutEdges(vertex)) {
				Variable second = this.getDest(edge);
				if (vertices.contains(second) && !edge.getVar1().equals(edge.getVar2())) {
					interEdges.add(edge);
				}
			}
		}
		return interEdges;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		boolean first = true;
		for (Variable var : getVertices()) {
			if (!first)
				sb.append(", ");
			sb.append(var).append("[");
			Joiner.on(", ").appendTo(sb, getConcepts(var));
			sb.append("]");
			first = false;
		}
		sb.append(", ");

		Joiner.on(", ").appendTo(sb, getEdges());
		sb.append(" }");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(vertices, edges, answerVariables, concepts);
	}

	public CQ toCQ() {
		Set<Atom> bodyAtoms = new HashSet<Atom>();
		for (CQGraphEdge e : this.getEdges()) {
			Variable firstVar = e.getVar1();
			Variable secondVar = e.getVar2();
			int role = e.getRole();
			if (role != 0 && role != 1) {
				if (role % 2 == 0) {
					DLPredicate r = new DLPredicate(role, 2);
					bodyAtoms.add(new Atom(r, firstVar, secondVar));
				} else {
					DLPredicate r = new DLPredicate(role - 1, 2);
					bodyAtoms.add(new Atom(r, secondVar, firstVar));
				}
			}

		}

		for (Variable vertex : this.getVertices()) {
			for (int concept : getConcepts(vertex)) {
				if (concept != 0) {
					DLPredicate c = new DLPredicate(concept, 1);
					bodyAtoms.add(new Atom(c, vertex));
				}
			}
		}

		List<Term> answerVars = new ArrayList<Term>(answerVariables);
		Predicate headPredicate = new NonDLPredicate("q");
		Atom head = new Atom(headPredicate, answerVars);
		CQ cq = new CQ();
		cq.setHead(head);
		cq.setBody(bodyAtoms);

		return cq;
	}

	public List<Variable> getAnswerVariables() {
		return answerVariables;
	}

}

@Data
@RequiredArgsConstructor
class CQGraphEdge {

	@Override
	public String toString() {
		return "<" + var1 + ", " + var2 + ">[" + role + "]";
	}

	@NonNull
	private Variable var1, var2;

	@NonNull
	private Integer role;

}