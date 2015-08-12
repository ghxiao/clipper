package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.BitSetUtilOpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CQGraphRewriter implements QueryRewriter {

    private final Logger log = LoggerFactory.getLogger(CQGraphRewriter.class);

    CQContainmentCheckUnderLIDs checker;

    ClipperHornSHIQOntology ontology;
    IndexedEnfContainer enfs;

    Multimap<Integer, Integer> transSuperRole2SubRolesMmap;

    List<CQGraph> rewrittenCQGraphs;
    List<CQ> rewrittenCQs;

    private SelfLoopComponentCluster slcc;

    public CQGraphRewriter(ClipperHornSHIQOntology ontology, IndexedEnfContainer enfs) {
        this.ontology = ontology;
        this.enfs = enfs;

        //checker = new CQGraphHomomorphismChecker();
        checker = new CQContainmentCheckUnderLIDs();

        List<ClipperSubPropertyAxiom> subPropertyAxioms = ontology.computeNonSimpleSubPropertyClosure();

        transSuperRole2SubRolesMmap = HashMultimap.create();
        for (ClipperSubPropertyAxiom subPropertyAxiom : subPropertyAxioms) {
            int subRole = subPropertyAxiom.getRole1();
            int superRole = subPropertyAxiom.getRole2();
            transSuperRole2SubRolesMmap.put(superRole, subRole);
        }
    }

    /**
     * rewrite a CQ graph into a collection of CQ graphs
     *
     * @param g input CQ graph
     * @return
     */
    public List<CQGraph> rewrite(CQGraph g) {
        log.debug("rewrite(CQGraph g)");
        log.debug("g = {}", g);

        rewrittenCQGraphs = Lists.newArrayList();
        rewrittenCQs = Lists.newArrayList();

        //slcc = new SmartSelfLoopComponentCluster(enfs);
        slcc = new NaiveSelfLoopComponentCluster();

        rewrite_recursive(g);
        return rewrittenCQGraphs;
    }

    /**
     * rewrite a CQ graph: recursive entry
     *
     * @param g
     */
    private void rewrite_recursive(CQGraph g) {

        log.debug("rewrite_recursive(CQGraph g)");
        log.debug("g = {}", g);
        log.debug("cq(g) = {}", g.toCQ());

        rewrittenCQGraphs.add(g);
        rewrittenCQs.add(g.toCQ());

        Set<Set<Variable>> selfLoopComponents = slcc.transform(g);

        for (Set<Variable> component : selfLoopComponents) {
            for (Set<Variable> leaves : Sets.powerSet(component)) {
                if (!leaves.isEmpty()) {
                    rewrite(g, leaves);
                }
            }
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
            List<CQGraphEdge> someNonSimpleRoleEdgeList = Lists.newArrayList(someNonSimpleRoleEdges);

            List<Set<Integer>> candidates = Lists.newArrayList();

            for (CQGraphEdge e : someNonSimpleRoleEdgeList) {
                candidates.add(Sets.newHashSet(edge2SubRolesMmap.get(e)));
            }

            Set<List<Integer>> cartesianProduct = Sets.cartesianProduct(candidates);

            /**
             * For each atom α=s(y,x) in ρ, where x∈Vl,y̸∈Vl is
             * arbitrary and s is non-simple, either leave α untouched or replace it by two atoms r(y, u), r(u, x),
             * where u is a fresh variable and r is a transitive role with r ⊑∗T s.
             */
            for (List<Integer> replacement : cartesianProduct) {

                // the edges in replacementMap will be replaced by their sub roles

                Map<CQGraphEdge, Integer> replacementMap = Maps.newHashMap();

                int size = someNonSimpleRoleEdgeList.size();
                for (int i = 0; i < size; i++) {
                    replacementMap.put(someNonSimpleRoleEdgeList.get(i), replacement.get(i));
                }

                rewrite(g, leaves, inEdges, replacementMap);
            }
        }
    }

    /**
     *
     * Computes the new rewriting with the given parameters
     *
     * @param g
     * @param leaves
     * @param edges
     * @param map
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

            log.debug("mergable = {}", mergeable);

            if (mergeable) {

                List<Integer> type = toList(enf.getType1());

                log.debug("cq(g) = {}", g.toCQ());
                log.debug("edges = {}; map = {}", edges, map);
                log.debug("type = {}", type);
                CQGraph rewrittenCQGraph = g.clip(leaves, edges, map, type);

                CQ cq = rewrittenCQGraph.toCQ();
                // if (!redundant(g1)) {

                boolean redundant = false;

                List<CQGraph> toRemove = new ArrayList<>();

                for (CQGraph graph : rewrittenCQGraphs) {
                    if (checker.isContainedIn(rewrittenCQGraph, graph)) {
                        redundant = true;

                        break;
                    } else if (checker.isContainedIn(graph, rewrittenCQGraph)) {
                        toRemove.add(graph);
                    }
                }

                if (!toRemove.isEmpty()) {
                    rewrittenCQGraphs.removeAll(toRemove);
                }


                if (!redundant) {
                    log.debug("-- new cq = {}", cq);
                    rewrite_recursive(rewrittenCQGraph);
                } else {
                    log.debug("-- redundant cq: {}", cq);
                }
            }
        }

    }


    /**
     * @param tmp
     * @return
     */
    private List<Integer> toList(TIntHashSet tmp) {
        List<Integer> type = Lists.newArrayList();
        for (Integer t : tmp.toArray()) {
            type.add(t);
        }
        return type;
    }

    /**
     * check whether the leaves can be merged into one single node
     *
     * @param g
     * @param enf
     * @param leaves
     * @return
     */
    private boolean mergeable(CQGraph g, EnforcedRelation enf, Collection<Variable> leaves) {

        if (leaves.size() == 1) {
            return true;
        }

        // FIXME: general case is to be fixed

        Set<Term> visited = Sets.newHashSet();

        Collection<CQGraphEdge> leafInterEdges = g.getInterEdges(leaves);

        if (leafInterEdges.isEmpty())
            return false;

        // TODO: check again
        for (CQGraphEdge edge : leafInterEdges) {
            Integer role = edge.getRole();
            int inverseRole = BitSetUtilOpt.inverseRole(role);
            if ((enf.getRoles().contains(role) && enf.getRoles().contains(inverseRole))) {
                TIntHashSet type1 = enf.getType2();
                TIntHashSet type2 = enf.getType2();
                TIntHashSet roles = new TIntHashSet();
                roles.add(role);
                roles.add(inverseRole);
                if (enfs.matchRolesAndType2(roles, type1).size() == 0
                        && enfs.matchRolesAndType2(roles, type2).size() == 0) {
                    return false;
                }

                visited.add(edge.getSource());
                visited.add(edge.getDest());
            }
        }

        return (visited.containsAll(leaves));


    }

    public List<CQGraph> getResult() {
        return rewrittenCQGraphs;
    }

    @Override
    public Collection<CQ> rewrite(CQ query) {
        rewrite(new CQGraph(query));
        return rewrittenCQs;
    }
}
