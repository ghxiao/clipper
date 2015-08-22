package org.semanticweb.clipper.hornshiq.queryrewriting;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.apache.commons.collections15.Buffer;
import org.apache.commons.collections15.buffer.UnboundedFifoBuffer;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;

/**
 * This class pre computes all the possible sets of variables in the CQGraph, such that each set
 * can be possibly merged to one variable in one step of query rewriting
 *
 * TODO: ongoing work
 */
public class SmartSelfLoopComponentCluster implements SelfLoopComponentCluster {

	private List<Integer> selfLoopRoles;

	// TODO only consider non-simples roles

	public SmartSelfLoopComponentCluster(IndexedEnfContainer enfs, List<Integer> nonSimpleRoles) {
		selfLoopRoles = new ArrayList<>();
		boolean mayLoop = false;
		for (EnforcedRelation enf : enfs) {
			mayLoop = true;
			TIntHashSet roles = enf.getRoles();
			TIntIterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				int role = iterator.next();
				int invRole = BitSetUtilOpt.inverseRole(role);
				if (nonSimpleRoles.contains(role) && roles.contains(invRole)) {
					mayLoop = true;
				}
			}

			if (mayLoop) {
				// restart the iterator
				iterator = roles.iterator();
				while (iterator.hasNext()) {
					int role = iterator.next();
					int invRole = BitSetUtilOpt.inverseRole(role);
					selfLoopRoles.add(role);
					selfLoopRoles.add(invRole);
				}
			}
		}
	}

	public SmartSelfLoopComponentCluster(List<Integer> selfLoopRoles) {
		this.selfLoopRoles = selfLoopRoles;
	}

	@Override
	public Set<Set<Variable>> transform(CQGraph graph) {

		Set<Set<Variable>> clusterSet = new HashSet<Set<Variable>>();

		HashSet<Variable> unvisitedVertices = new HashSet<Variable>();

		for (Term vtx : graph.getVertices()) {
			if (vtx.isVariable()) {
				unvisitedVertices.add(vtx.asVariable());
			}
		}

		while (!unvisitedVertices.isEmpty()) {
			Variable root = unvisitedVertices.iterator().next();
			unvisitedVertices.remove(root);

			if (!graph.isAnswerVariable(root)) {
				Set<Variable> cluster = new HashSet<Variable>();
				cluster.add(root);

				Buffer<Variable> queue = new UnboundedFifoBuffer<Variable>();
				queue.add(root);

				while (!queue.isEmpty()) {
					Variable currentVertex = queue.remove();
					Collection<Variable> neighbors = getNeighbors(graph, currentVertex);

					for (Variable neighbor : neighbors) {
						if (unvisitedVertices.contains(neighbor)) {
							queue.add(neighbor);
							unvisitedVertices.remove(neighbor);
							cluster.add(neighbor);
						}
					}
				}
				clusterSet.add(cluster);
			}

		}
		return clusterSet;
	}

	private Collection<Variable> getNeighbors(CQGraph g, Variable vertex) {
		List<Variable> neighbors = new ArrayList<Variable>();

		Collection<CQGraphEdge> incidentEdges = g.getIncidentEdges(vertex);
		requireNonNull(incidentEdges, "incidentEdges == null");
		for (CQGraphEdge edge : incidentEdges) {
			// if (selfLoopRoles.containsAll(g.getRoles(edge))) {
			if (selfLoopRoles.contains(edge.getRole())) {
				Term source = edge.getSource();
				Term dest = edge.getDest();
				if (vertex.equals(source) && dest.isVariable() && !g.isAnswerVariable(dest)) {
					neighbors.add(dest.asVariable());
				} else if (vertex.equals(dest) && dest.isVariable() && !g.isAnswerVariable(source)) {
					neighbors.add(source.asVariable());
				}
			}
		}

		return neighbors;
	}
}
