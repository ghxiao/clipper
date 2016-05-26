package org.semanticweb.clipper.hornshiq.queryrewriting;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.CQContainmentCheckUnderLIDs;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.BitSetUtilOpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class HornSHIQQueryRewriter implements QueryRewriter {

    private final Logger log = LoggerFactory.getLogger(HornSHIQQueryRewriter.class);
    private Set<Integer> possibleSelfLoopRoles = null;

    CQContainmentCheckUnderLIDs cqContainmentChecker;

    ClipperHornSHIQOntology ontology;
    IndexedEnfContainer enfs;

    Multimap<Integer, Integer> transSuperRole2SubRolesMmap;

    List<CQGraph> rewrittenCQGraphs;

    private SelfLoopComponentCluster slcc;

    public HornSHIQQueryRewriter(ClipperHornSHIQOntology ontology, IndexedEnfContainer enfs,
                                 boolean rewritingTransitivity) {
        // TODO: 
    }


    public HornSHIQQueryRewriter(ClipperHornSHIQOntology ontology, IndexedEnfContainer enfs) {
        this.ontology = ontology;
        this.enfs = enfs;

        cqContainmentChecker = new CQContainmentCheckUnderLIDs();

        List<ClipperSubPropertyAxiom> subPropertyAxioms = ontology.computeNonSimpleSubPropertyClosure();

        transSuperRole2SubRolesMmap = HashMultimap.create();
        for (ClipperSubPropertyAxiom subPropertyAxiom : subPropertyAxioms) {
            int subRole = subPropertyAxiom.getRole1();
            int superRole = subPropertyAxiom.getRole2();

            transSuperRole2SubRolesMmap.put(superRole, subRole);
        }

        possibleSelfLoopRoles = new HashSet<>();
        for(int superRole : transSuperRole2SubRolesMmap.keySet()){
            final Collection<Integer> integers = transSuperRole2SubRolesMmap.get(superRole);
            final List<Integer> list = new ArrayList<>(integers);
            Collections.sort(list);
            int n = list.size();
            for(int i = 0; i < n - 1; i ++){
                if(list.get(i+1) - list.get(i) == 1){
                    possibleSelfLoopRoles.add(superRole);
                }
            }
        }


    }

    /**
     * rewrites a CQ graph into a collection of CQ graphs
     *
     * @param g input CQ graph
     * @return rewritten CQ graphs
     */
    public List<CQGraph> rewrite(CQGraph g) {
        log.debug("rewrite(CQGraph g)");
        log.debug("g = {}", g);

        rewrittenCQGraphs = new ArrayList<>();

        //slcc = new NaiveSelfLoopComponentCluster();
        slcc = new SmartSelfLoopComponentCluster(possibleSelfLoopRoles);

        rewrite_recursive(g);
        return rewrittenCQGraphs;
    }

    /**
     * rewrites a CQ graph: recursive entry
     */
    private void rewrite_recursive(CQGraph g) {

        log.debug("rewrite_recursive(CQGraph g)");
        log.debug("g = {}", g);
        log.debug("cq(g) = {}", g.toCQ());

        rewrittenCQGraphs.add(g);

        Set<Set<Variable>> selfLoopComponents = slcc.apply(g);

        for (Set<Variable> component : selfLoopComponents) {
            Sets.powerSet(component).stream()
                    .filter(leaves -> !leaves.isEmpty())
                    .forEach(leaves -> rewrite(g, leaves));
        }
    }

    /**
     * Rewrites CQ <code>q</code> w.r.t. the selected non-empty set of non-distinguished variables
     *
     * @param g      input CQ Graph
     * @param leaves selected non-empty set of non-distinguished variables
     */
    private void rewrite(CQGraph g, Collection<Variable> leaves) {
        log.debug("leaves = {}", leaves);

        // (S2)
        g = g.focus(leaves);

        /*
         * Collects all atoms α = s(y,x) in ρ
         */
        List<CQGraphEdge> inEdges = g.getInEdges(leaves);

        // A map from edges (with non-simple roles) to their sub roles
        Multimap<CQGraphEdge, Integer> edge2SubRolesMmap = HashMultimap.create();

        for (CQGraphEdge inEdge : inEdges) {
            // when inEdge does not correpond to non-simple roles, the following is a no-op
            edge2SubRolesMmap.putAll(inEdge, transSuperRole2SubRolesMmap.get(inEdge.getRole()));
        }

        // all the edges with non-simple roles
        Set<CQGraphEdge> nonSimpleRoleEdges = edge2SubRolesMmap.keySet();

        // Check all possible subsets of non-simple role edges}
        for (Set<CQGraphEdge> someNonSimpleRoleEdges : Sets.powerSet(nonSimpleRoleEdges)) {

            // a list representation of the chosen non-simple role edges
            List<CQGraphEdge> someNonSimpleRoleEdgeList = new ArrayList<>(someNonSimpleRoleEdges);

            List<Set<Integer>> candidates = new ArrayList<>();

            for (CQGraphEdge e : someNonSimpleRoleEdgeList) {
                candidates.add(Sets.newHashSet(edge2SubRolesMmap.get(e)));
            }

            Set<List<Integer>> cartesianProduct = Sets.cartesianProduct(candidates);

            /**
             * For each atom α = s(y,x) in ρ, where x ∈ Vl,y ∉ Vl is
             * arbitrary and s is non-simple, either leave α untouched or replace it by two atoms r(y, u), r(u, x),
             * where u is a fresh variable and r is a transitive role with r ⊑∗T s.
             */
            for (List<Integer> replacement : cartesianProduct) {

                // the edges in replacementMap will be replaced by their sub roles

                Map<CQGraphEdge, Integer> replacementMap = new HashMap<>();

                int size = someNonSimpleRoleEdgeList.size();
                for (int i = 0; i < size; i++) {
                    replacementMap.put(someNonSimpleRoleEdgeList.get(i), replacement.get(i));
                }

                rewrite(g, leaves, inEdges, replacementMap);
            }
        }
    }

    /**
     * Computes the new rewriting with the given parameters
     */
    private void rewrite(CQGraph g, Collection<Variable> leaves, List<CQGraphEdge> edges, Map<CQGraphEdge, Integer> map) {

        TIntHashSet roles = new TIntHashSet();

        for (CQGraphEdge edge : edges) {
            if (map.containsKey(edge)) {
                roles.add(map.get(edge));
            } else {
                roles.add(edge.getRole());
            }
        }

        TIntHashSet type2 = new TIntHashSet();

        for (Variable leaf : leaves) {
            type2.addAll(g.getConcepts(leaf));
        }

        Collection<EnforcedRelation> matchedEnfs = enfs.matchRolesAndType2(roles, type2);

        for (EnforcedRelation enf : matchedEnfs) {

            boolean mergeable = mergeable(g, enf, leaves);

            log.debug("mergeable = {}", mergeable);

            if (mergeable) {

                List<Integer> type = toList(enf.getType1());

                log.debug("cq(g) = {}", g.toCQ());
                log.debug("edges = {}; map = {}", edges, map);
                log.debug("type = {}", type);
                CQGraph rewrittenCQGraph = g.clip(leaves, edges, map, type);

                CQ cq = rewrittenCQGraph.toCQ();

                boolean redundant = false;

                List<CQGraph> toRemove = new ArrayList<>();

                for (CQGraph graph : rewrittenCQGraphs) {
                    if (cqContainmentChecker.isContainedIn(rewrittenCQGraph, graph)) {
                        redundant = true;
                        break;
                    } else if (cqContainmentChecker.isContainedIn(graph, rewrittenCQGraph)) {
                        toRemove.add(graph);
                    }
                }

                if (!toRemove.isEmpty()) {
                    rewrittenCQGraphs.removeAll(toRemove);
                }

                if (!redundant) {
                    log.debug("-- new cq: {}", cq);
                    rewrite_recursive(rewrittenCQGraph);
                } else {
                    log.debug("-- redundant cq: {}", cq);
                }
            }
        }

    }


    /**
     * @param set an instance of TIntHashSet
     * @return a List of Integer
     */
    private List<Integer> toList(TIntHashSet set) {
        List<Integer> type = new ArrayList<>();
        for (Integer t : set.toArray()) {
            type.add(t);
        }
        return type;
    }

    /**
     * Checks whether the leaves can be merged into one single node
     * <p>
     * (S5).(c) for each atom r(x, y) in body(ρ) with x, y ∈ Vl there is a transitive s ⊑∗T r such that
     * i. {s,s−} ⊆ S, or
     * ii. there is an axiom M′ ⊑ ∃S′.N′ ∈ Ξ(T^*) such that M′ ⊆ N and {s,s−} ⊆ S′.
     */
    private boolean mergeable(CQGraph g, EnforcedRelation enf, Collection<Variable> leaves) {

        TIntHashSet rolesInEnf = enf.getRoles();

        // for each atom r(x, y) in body(ρ) with x, y ∈ Vl
        Collection<CQGraphEdge> leafInterEdges = g.getInterEdges(leaves);


        for (CQGraphEdge edge : leafInterEdges) {
            Integer role = edge.getRole();

            /*
             * there is a transitive s ⊑∗T r such that
             * i. {s,s−} ⊆ S,or
             */
            boolean firstConditionSatisfied = false;
            for (Integer subRole : transSuperRole2SubRolesMmap.get(role)) {
                int inverseSubRole = BitSetUtilOpt.inverseRole(subRole);
                if (rolesInEnf.contains(subRole) && rolesInEnf.contains(inverseSubRole)) {
                    firstConditionSatisfied = true;
                    break;
                }
            }

            if (firstConditionSatisfied)
                continue;

            /*
             * there is a transitive s ⊑∗T r such that
             * ii. there is an axiom M′ ⊑ ∃S′.N′ ∈ Ξ(T*) such that M′ ⊆ N and{s, s−} ⊆ S′.
             */
            boolean secondConditionSatisfied = false;

            for (Integer subRole : transSuperRole2SubRolesMmap.get(role)) {
                int inverseSubRole = BitSetUtilOpt.inverseRole(subRole);
                TIntHashSet type2 = enf.getType2();
                TIntHashSet roles = new TIntHashSet();
                roles.add(subRole);
                roles.add(inverseSubRole);
                /*
                 * there is an axiom M′ ⊑ ∃S′.N′ ∈ Ξ(T*)
                 * such that M′ ⊆ N and {s, s−} ⊆ S′.
                 */
                if (!enfs.matchRolesAndType1(roles, type2).isEmpty()) {
                    secondConditionSatisfied = true;
                }
            }

            if (!secondConditionSatisfied)
                return false;
        }

        return true;

    }

    @Override
    public Collection<CQ> rewrite(CQ query) {
        rewrite(new CQGraph(query));
        return rewrittenCQGraphs.stream()
                .map(CQGraph::toCQ)
                .collect(Collectors.toList());
    }
}
