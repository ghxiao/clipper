package org.semanticweb.clipper.hornshiq.queryrewriting;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

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

	private Set<Integer> selfLoopRoles;

	// TODO only consider non-simples roles

	public SmartSelfLoopComponentCluster(IndexedEnfContainer enfs, List<Integer> nonSimpleRoles) {
		selfLoopRoles = new HashSet<>();
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

	public SmartSelfLoopComponentCluster(Set<Integer> selfLoopRoles) {
		this.selfLoopRoles = selfLoopRoles;
	}

	@Override
	public Set<Set<Variable>> apply(CQGraph graph) {

        final Set<Variable> nonAnswerVariables = graph.getNonAnswerVariables();

        final DirectedSparseMultigraph<Variable, CQGraphEdge> g = new DirectedSparseMultigraph<>();

        nonAnswerVariables.forEach(g::addVertex);

        for(CQGraphEdge e : graph.getEdges()){
            if (nonAnswerVariables.contains(e.getSource()) &&
                    nonAnswerVariables.contains(e.getDest()) &&
                selfLoopRoles.contains(e.getRole()) ){
                g.addEdge(e, (Variable)e.getSource(), (Variable)e.getDest());
            }
        }

        WeakComponentClusterer<Variable, CQGraphEdge> clusterer = new WeakComponentClusterer<>();

        final Set<Set<Variable>> clusters = clusterer.apply(g);

        return clusters;
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
