package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.base.Objects;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

import java.util.*;
import java.util.stream.Collectors;

public class RelevantRulesForUCQExtractor {

    public static List<CQ> relevantRules(Collection<CQ> queries, Collection<CQ> rules) {

        // normally is "ans"
        Predicate queryPredicate = queries.iterator().next().getHead().getPredicate();

        DirectedGraph<Predicate, PredicatePair> depGraph = new DirectedSparseGraph<>();

        for (CQ cq : queries) {
            for (Atom bodyAtom : cq.getBody()) {
                addEdge(depGraph, queryPredicate, bodyAtom.getPredicate());
            }
        }

        for (CQ rule : rules) {
            for (Atom bodyAtom : rule.getBody()) {
                addEdge(depGraph, rule.getHead().getPredicate(), bodyAtom.getPredicate());
            }
        }

        Set<Predicate> reachablePredicates = findReachable(queryPredicate, depGraph);

        List<CQ> relevantRules = rules.stream().filter(
                rule -> reachablePredicates.contains(rule.getHead().getPredicate())
        ).collect(Collectors.toList());


        return relevantRules;
    }


    private static <V> Set<V> findReachable(
            V startNode, Graph<V, ?> graph) {
        Queue<V> queue = new LinkedList<V>();
        Set<V> visited = new LinkedHashSet<V>();
        queue.add(startNode);
        visited.add(startNode);
        while (!queue.isEmpty()) {
            V v = queue.poll();
            Collection<V> neighbors = graph.getSuccessors(v);
            for (V n : neighbors) {
                if (!visited.contains(n)) {
                    queue.offer(n);
                    visited.add(n);
                }
            }
        }
        return visited;
    }

    private static boolean addEdge(Graph<Predicate, PredicatePair> g, Predicate from, Predicate to) {
        return g.addEdge(new PredicatePair(from, to), from, to);

    }

    static class PredicatePair {
        final Predicate first, second;

        PredicatePair(Predicate first, Predicate second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PredicatePair that = (PredicatePair) o;
            return Objects.equal(first, that.first) &&
                    Objects.equal(second, that.second);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(first, second);
        }
    }

}
