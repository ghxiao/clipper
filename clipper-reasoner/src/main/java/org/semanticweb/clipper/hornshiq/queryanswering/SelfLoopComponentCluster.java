package org.semanticweb.clipper.hornshiq.queryanswering;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections15.Buffer;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.buffer.UnboundedFifoBuffer;

import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.BitSetUtilOpt;

import edu.uci.ics.jung.graph.util.Pair;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

public class SelfLoopComponentCluster implements Transformer<CQGraph, Set<Set<Variable>>> {

	private List<Integer> selfLoopRoles;

	
	//TODO only consider non-simples roles
	
	public SelfLoopComponentCluster(IndexedEnfContainer enfs) {
		selfLoopRoles = new ArrayList<Integer>();
		boolean mayLoop = true;
		for (EnforcedRelation enf : enfs) {
			mayLoop = true;
			TIntHashSet roles = enf.getRoles();
			TIntIterator iterator = roles.iterator();
			while (iterator.hasNext()) {
				int role = iterator.next();
				int invRole = BitSetUtilOpt.inverseRole(role);
				if (!roles.contains(invRole)) {
					mayLoop = false;
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

	public SelfLoopComponentCluster(List<Integer> selfLoopRoles) {
		this.selfLoopRoles = selfLoopRoles;
	}

	@Override
	public Set<Set<Variable>> transform(CQGraph graph) {

		Set<Set<Variable>> clusterSet = new HashSet<Set<Variable>>();

		HashSet<Variable> unvisitedVertices = new HashSet<Variable>(graph.getVertices());

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
		checkNotNull(incidentEdges, "incidentEdges == null");
		for (CQGraphEdge edge : incidentEdges) {
			// if (selfLoopRoles.containsAll(g.getRoles(edge))) {
			if (selfLoopRoles.contains(edge.getRole())) {
				Pair<Variable> endpoints = g.getEndpoints(edge);
				checkNotNull(endpoints, "endpoints == null !");
				if (vertex.equals(endpoints.getFirst()) && !g.isAnswerVariable(endpoints.getSecond())) {
					neighbors.add(endpoints.getSecond());
				} else if (vertex.equals(endpoints.getSecond()) && !g.isAnswerVariable(endpoints.getFirst())) {
					neighbors.add(endpoints.getFirst());
				}
			}
		}

		return neighbors;
	}
}
