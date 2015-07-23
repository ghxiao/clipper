package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Direct Multigraph Representation of a Conjunctive Query
 * <p/>
 * Immutable. All the public methods have no side effects
 *
 * @author xiao
 */
@SuppressWarnings("serial")
public class CQGraph extends DirectedSparseMultigraph<Term, CQGraphEdge> {

    private final Logger log = LoggerFactory.getLogger(CQGraph.class);

    /**
     * distinguished (answer, output) variables
     */
    private List<Variable> answerVariables = Lists.newArrayList();

    /**
     * The labels of the vertices (a.k.a the map from the Variables to list of concepts)
     */
    private Multimap<Term, Integer> termToConceptsMap = HashMultimap.create();

    private String headPredicateName;

    private int maxVariableIndex = -1;

    /**
     * never use
     */
    private CQGraph() {
    }

    /**
     * construct a CQGraph from a CQ
     *
     * @param cq a Conjunctive Query
     */
    public CQGraph(CQ cq) {
        super();

        this.headPredicateName = cq.getHead().getPredicate().toString();

        for (Atom atom : cq.getBody()) {
            Predicate predicate = atom.getPredicate();
            List<Term> terms = atom.getTerms();
            if (predicate.getArity() == 2) {
                CQGraphEdge edge = new CQGraphEdge(terms.get(0), terms.get(1), predicate.getEncoding());
                this.addEdge(edge);
            } else if (predicate.getArity() == 1) {
                Term var = terms.get(0);
                this.addVertex(var);
                termToConceptsMap.put(var, predicate.getEncoding());
            }
        }

        for (Term t : cq.getHead().getTerms()) {
            Variable var = (Variable) t;
            answerVariables.add(var);
        }

    }

    private void addEdge(CQGraphEdge edge) {
        this.addEdge(edge, edge.getSource(), edge.getDest());
    }

    public CQGraph deepCopy() {
        CQGraph g = new CQGraph();

        this.getVertices().forEach(g::addVertex);

        this.getEdges().forEach(g::addEdge);

        // g.roles.putAll(this.roles);
        g.termToConceptsMap.putAll(this.termToConceptsMap);
        g.answerVariables.addAll(this.answerVariables);
        g.headPredicateName = this.headPredicateName;

        return g;

    }


    /**
     * (S2)
     * <p/>
     * Replace each role atom r(x, y) in ρ, where x ∈ Vl and y ∉ Vl is arbitrary, by the atom
     * inv(r)(y, x).
     * <p/>
     * NO side effects!
     *
     * @param leaves Vl
     * @return a new CQGraph
     */
    public CQGraph focus(Collection<Variable> leaves) {
        CQGraph newCQGraph = this.deepCopy();
        newCQGraph.focus0(leaves);
        return newCQGraph;
    }

    private void focus0(Collection<Variable> leaves) {
        List<CQGraphEdge> outEdges = this.getOutEdges(leaves);
        List<CQGraphEdge> revertedEdges = new ArrayList<>();
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

    /*
     * No side effect !
     */
    @CheckReturnValue
    public CQGraph clip(Collection<Variable> leaves, //
                        Collection<CQGraphEdge> edges, //
                        Map<CQGraphEdge, Integer> map, //
                        List<Integer> type) {
        CQGraph newCQGraph = this.deepCopy();
        newCQGraph.clip0(leaves, edges, map, type);
        return newCQGraph;
    }

    private void clip0(Collection<Variable> leaves, //
                       Collection<CQGraphEdge> edges, //
                       Map<CQGraphEdge, Integer> map, //
                       List<Integer> type) {


        boolean distinguished = false;

        Term newLeaf = computeNewLeaf(edges, map);

        replaceEdgesByTransitiveSubEdgePairs(edges, map);

        log.debug("replace Edges By Transitive Sub Edge Pairs: \n{}\n", this);

        List<CQGraphEdge> inEdges = this.getInEdges(leaves);

        /**
         * Let Vp ={y|∃r : r(y,x) ∈ body(ρ) ∧ x ∈ Vl ∧ y ∉ Vl}.
         */
        List<Term> parentVertices = inEdges.stream().map(CQGraphEdge::getSource)
                                    .collect(Collectors.toList());

        leaves.stream().flatMap(term -> this.getIncidentEdges(term).stream())
                .forEach(this::removeEdge);

        leaves.forEach(this::removeVertex);

        mergeVerticesAndReplace(parentVertices, newLeaf, type);

        //mergeLeafVertices(newLeaf, leaves, newType, distinguished);
    }

    /**
     * (S6) Drop each atom from ρ containing a variable from Vl.
     * (S7) Select some x ∈ Vl and rename each y ∈ Vp of ρ by x.
     * (S8) Add the atoms {A(x)| A ∈ M} to ρ.
     *
     * @param parentVertices parent vertices Vp
     * @param newLeaf new leaf x∈ Vl
     * @param type M
     */
    private void mergeVerticesAndReplace(List<Term> parentVertices, Term newLeaf, List<Integer> type) {

        this.addVertex(newLeaf);
        this.termToConceptsMap.putAll(newLeaf, type);

        List<CQGraphEdge> edgesToAdd = new ArrayList<>();
        List<CQGraphEdge> edgesToRemove = new ArrayList<>();

        parentVertices.forEach(v -> this.termToConceptsMap.putAll(newLeaf, this.termToConceptsMap.get(v)));

        this.getInEdges(parentVertices).forEach(e -> {
                    edgesToAdd.add(new CQGraphEdge(e.getSource(), newLeaf, e.getRole()));
                    edgesToRemove.add(e);
                }
        );

        this.getOutEdges(parentVertices).forEach(e -> {
                    edgesToAdd.add(new CQGraphEdge(newLeaf, e.getDest(), e.getRole()));
                    edgesToRemove.add(e);
                }
        );

        this.getInterEdges(parentVertices).forEach(e -> {
                    edgesToAdd.add(new CQGraphEdge(newLeaf, newLeaf, e.getRole()));
                    edgesToRemove.add(e);
                }
        );

        edgesToRemove.forEach(this::removeEdge);
        edgesToAdd.forEach(this::addEdge);
        parentVertices.forEach(this::removeVertex);

        ListIterator<Variable> iterator = answerVariables.listIterator();
        while(iterator.hasNext()){
            Variable variable = iterator.next();
            if(parentVertices.contains(variable)){
                //iterator.remove();
                iterator.set(newLeaf.asVariable());
            }
        }
    }

    /**
     * For each atom r(y,x), replaces it by two atoms r(y, u), r(u, x),
     * where u is a fresh variable and r is a transitive role with r ⊑∗T s.
     */
    private void replaceEdgesByTransitiveSubEdgePairs(Collection<CQGraphEdge> edges, //
                                                      Map<CQGraphEdge, Integer> map) {
        ArrayList<CQGraphEdge> copyOfEdges = Lists.newArrayList(edges);

        for (CQGraphEdge edge : copyOfEdges) {
            if(map.containsKey(edge)) {
                Variable freshVar = getFreshVariable();
                this.addEdge(new CQGraphEdge(edge.getSource(), freshVar, map.get(edge)));
                this.addEdge(new CQGraphEdge(freshVar, edge.getDest(), map.get(edge)));
                this.removeEdge(edge);
            }
        }

    }

    /**
     * gets a constant source from the edges
     * <p/>
     * if there are more than one such edges, an exception will be thrown
     *
     * @param map
     */
    private Term computeNewLeaf(Collection<CQGraphEdge> edges, Map<CQGraphEdge, Integer> map) {
        Term ret = null;
        for (CQGraphEdge edge : edges) {
            if (!map.containsKey(edge)) {
                if (edge.getSource().isConstant()) {
                    if (ret == null) {
                        ret = edge.getSource().asConstant();
                    } else {
                        // not the first one
                        throw new IllegalArgumentException("more than one constants to merge!");
                    }
                }
            }
        }

        if (ret == null)
            ret = edges.iterator().next().getDest();

        return ret;
    }

    private void mergeLeafVertices(Term vertex0, Collection<Variable> leafVertices, List<Integer> type,
                                   boolean distinguished) {
        Collection<CQGraphEdge> interLeafEdges = this.getInterEdges(leafVertices);
        for (CQGraphEdge edge : interLeafEdges) {
            this.removeEdge(edge);
        }
        for (Variable vertex : leafVertices) {
            if (!vertex.equals(vertex0)) {
                this.removeVertex(vertex);
            }
        }

        getConcepts(vertex0).clear();
        getConcepts(vertex0).addAll(type);
        if (distinguished)
            answerVariables.add(vertex0.asVariable());
    }

    /**
     * @param parentVertex
     * @return
     */
    public Collection<Integer> getConcepts(Term parentVertex) {
        return termToConceptsMap.get(parentVertex);
    }

    /**
     * @param variable
     * @return
     */
    public boolean isAnswerVariable(Term variable) {

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
    public boolean removeVertex(Term vertex) {
        if (vertex.isVariable()) {
            Variable v = (Variable) vertex;
            // checkState(!isAnswerVariable(v));
            // TODO: check
            // this.answerVariables.remove(v);
            this.termToConceptsMap.removeAll(v);
        }

        return super.removeVertex(vertex);
    }

    private CQGraphEdge inverseEdge(CQGraphEdge inEdge) {

        Integer role = inEdge.getRole();

        int invRole;
        if (role % 2 == 0) {
            invRole = role + 1;
        } else {
            invRole = role - 1;
        }

        //noinspection UnnecessaryLocalVariable
        CQGraphEdge outEdge = new CQGraphEdge(inEdge.getDest(), inEdge.getSource(), invRole);

        return outEdge;
    }

    /**
     * Gets all the outcoming edges of the <code>vertices</code>, excluding the edges between <code>vertices</code>
     */
    public List<CQGraphEdge> getOutEdges(Collection<? extends Term> vertices) {
        List<CQGraphEdge> outEdges = new ArrayList<>();
        for (Term vertex : vertices) {
            Collection<CQGraphEdge> possibleOutEdges = this.getOutEdges(vertex);

            for (CQGraphEdge edge : possibleOutEdges) {
                Term second = edge.getDest();
                if (!vertices.contains(second)) {
                    outEdges.add(edge);
                }
            }

        }
        return outEdges;
    }

    /**
     * Gets all the incoming edges of the <code>vertices</code>, excluding the edges between <code>vertices</code>
     */
    public List<CQGraphEdge> getInEdges(Collection<? extends Term> vertices) {
        List<CQGraphEdge> inEdges = new ArrayList<>();
        for (Term vertex : vertices) {
            Collection<CQGraphEdge> possibleInEdges = this.getInEdges(vertex);
            for (CQGraphEdge edge : possibleInEdges) {
                Term first = edge.getSource();
                if (!vertices.contains(first)) {
                    inEdges.add(edge);
                }
            }
        }
        return inEdges;
    }

    /**
     * get the edges between the vertices,
     * <p/>
     * NOTE that self loop edges (e=(v,v)) are excluded
     *
     * @param vertices
     * @return
     */
    public Collection<CQGraphEdge> getInterEdges(Collection<? extends Term> vertices) {
        Set<CQGraphEdge> interEdges = Sets.newHashSet();
        for (Term vertex : vertices) {
            Preconditions.checkState(this.containsVertex(vertex), "the vertex is not in the graph");
            for (CQGraphEdge edge : this.getInEdges(vertex)) {
                Term first = this.getSource(edge);
                if (vertices.contains(first) && !edge.getSource().equals(edge.getDest())) {
                    interEdges.add(edge);
                }
            }
            for (CQGraphEdge edge : this.getOutEdges(vertex)) {
                Term second = this.getDest(edge);
                if (vertices.contains(second) && !edge.getSource().equals(edge.getDest())) {
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
        for (Term var : getVertices()) {
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
        return Objects.hashCode(vertices, edges, answerVariables, termToConceptsMap);
    }

    public CQ toCQ() {
        List<Atom> bodyAtoms = new ArrayList<Atom>();
        for (CQGraphEdge e : this.getEdges()) {
            Term firstVar = e.getSource();
            Term secondVar = e.getDest();
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

        for (Term vertex : this.getVertices()) {
            for (int concept : getConcepts(vertex)) {
                if (concept != 0) {
                    DLPredicate c = new DLPredicate(concept, 1);
                    bodyAtoms.add(new Atom(c, vertex));
                }
            }
        }

        List<Term> answerVars = new ArrayList<Term>(answerVariables);
        Predicate headPredicate = new NonDLPredicate(this.headPredicateName);
        Atom head = new Atom(headPredicate, answerVars);
        CQ cq = new CQ();
        cq.setHead(head);
        cq.setBody(bodyAtoms);

        return cq;
    }

    public List<Variable> getAnswerVariables() {
        return answerVariables;
    }

    public Set<Variable> getNondistinguishedVariables() {

        Set<Variable> ret = Sets.newHashSet();

        for (Term term : this.getVertices()) {
            if (term.isVariable() && !isAnswerVariable(term.asVariable())) {
                ret.add(term.asVariable());
            }
        }

        return ret;
    }


    public Variable getFreshVariable() {


        if (maxVariableIndex < 0) {
            /*
             * initialize
             */
            this.maxVariableIndex = this.getVertices().stream().filter(Term::isVariable)
                    .map(term -> term.asVariable().getIndex())
                    .max(Comparator.naturalOrder())
                    .orElseGet(() -> 0);
        }

        maxVariableIndex++;

        return new Variable(maxVariableIndex);
    }
}

/**
 * Immutable
 */
class CQGraphEdge {

    public CQGraphEdge(Term source, Term dest, Integer role) {
        this.source = source;
        this.dest = dest;
        this.role = role;
    }

    @Override
    public String toString() {
        return "<" + source + ", " + dest + ">[" + role + "]";
    }


    private Term source, dest;


    private Integer role;


    public Term getSource() {
        return this.source;
    }


    public Term getDest() {
        return this.dest;
    }


    public Integer getRole() {
        return this.role;
    }


    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CQGraphEdge)) return false;
        final CQGraphEdge other = (CQGraphEdge) o;
        if (!other.canEqual(this)) return false;
        final Object this$source = this.source;
        final Object other$source = other.source;
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$dest = this.dest;
        final Object other$dest = other.dest;
        if (this$dest == null ? other$dest != null : !this$dest.equals(other$dest)) return false;
        final Object this$role = this.role;
        final Object other$role = other.role;
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $source = this.source;
        result = result * PRIME + ($source == null ? 0 : $source.hashCode());
        final Object $dest = this.dest;
        result = result * PRIME + ($dest == null ? 0 : $dest.hashCode());
        final Object $role = this.role;
        result = result * PRIME + ($role == null ? 0 : $role.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CQGraphEdge;
    }
}