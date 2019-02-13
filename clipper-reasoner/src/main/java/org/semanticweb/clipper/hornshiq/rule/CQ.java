package org.semanticweb.clipper.hornshiq.rule;

import com.google.common.collect.Lists;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CQ {
    Atom head;
    List<Atom> body;

    @Deprecated
    public CQ() {
        head = new Atom();
        body = new ArrayList<>();
    }

    // Fact
    public CQ(Atom fact) {
        this.head = fact;
        body = new ArrayList<>();
    }

    public CQ(Atom head, List<Atom> body) {
        this.head = head;
        this.body = body;
    }

    public CQ(Atom head, Atom body) {
        this.head = head;
        this.body = Lists.newArrayList(body);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(head);
        sb.append(" :- ");
        boolean first = true;
        for (Atom b : body) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(b);
        }
        sb.append(".");
        return sb.toString();
    }

    public boolean isNotTrivial() {
        return (!body.contains(head));
    }

    public Set<Term> getDistinguishedVariables() {
        return this.head.getTerms().stream().filter(Term::isVariable).collect(Collectors.toSet());
    }

    /*
     * Check if there is r(x,x) for non-distinguished variable x in the query
     */
    public boolean hasNoLoop(Variable x) {

        for (Atom atom : body) {
            if (atom.getPredicate().getArity() == 2) {
                List<Term> terms = atom.getTerms();
                if (terms.get(0).isVariable() && terms.get(0).getIndex() == x.getIndex() && terms.get(1).isVariable()
                        && terms.get(1).getIndex() == x.getIndex())
                    return false;

            }
        }
        return true;
    }

    // TODO: update it for rules with non-DL prediciates in the body
    public Set<Variable> getNonDistinguishedVars() {
        Set<Variable> nonDistinguishedVars = new HashSet<>();
        Set<Term> distinguishedVars = this.getDistinguishedVariables();
        for (Atom atom : body) {
            nonDistinguishedVars.addAll(atom.getTerms().stream()
                    .filter(term -> (term.isVariable()) && !distinguishedVars.contains(term))
                    .map(term -> (Variable) term).collect(Collectors.toList()));

        }

        return nonDistinguishedVars;
    }

    /**
     * Get inverse role of r. If there is no defined inverse role of r, then
     * return anonymous inverseOf(r) Note: We assume that for each role r, there
     * is only one inverse role of r.
     */
    public int getInverseOf(int r, List<ClipperInversePropertyOfAxiom> inverseAxioms) {
        int inverseOfR = -1;
        for (ClipperInversePropertyOfAxiom invAxiom : inverseAxioms) {
            if (invAxiom.getRole1() == r)
                inverseOfR = invAxiom.getRole2();
            if (invAxiom.getRole2() == r)
                inverseOfR = invAxiom.getRole1();
        }
        if (inverseOfR == -1) { // there is no predefined inverse role of r
            if (r % 2 == 0)
                inverseOfR = r + 1;
            else
                inverseOfR = r - 1;
        }
        return inverseOfR;
    }

    public TIntHashSet computeRho(Variable x, List<ClipperInversePropertyOfAxiom> inverseAxioms) {

        TIntHashSet rho = new TIntHashSet();

        for (Atom atom : body) {
            List<Term> terms = atom.getTerms();
            if (atom.getPredicate().getArity() == 2) {
                if (terms.get(1).equals(x))
                    rho.add(atom.getPredicate().getEncoding());
                    // else add Inverse of atom.predicate to Rho
                else if (terms.get(0).equals(x)) {
                    int rBar = getInverseOf(atom.getPredicate().getEncoding(), inverseAxioms);
                    rho.add(rBar);
                }
            }
        }
        return rho;
    }

    public List<Term> computeRenamingVars(Variable x) {
        List<Term> vars = new ArrayList<>();
        for (Atom atom : body) {
            // Need to deal with inverse role
            if (atom.getTerms().contains(x) && atom.getPredicate().getArity() == 2) {
                for (Term term : atom.getTerms())
                    if (!term.equals(x))
                        vars.add(term);
            }
        }
        return vars;
    }

    public TIntHashSet computeTypeOfX(Variable x) {
        TIntHashSet bs = new TIntHashSet();
        body.stream().filter(atom -> atom.getPredicate().getArity() == 1 && atom.getTerms().contains(x))
                .forEach(atom -> {
                    int typeOfX = atom.getPredicate().getEncoding();
                    bs.add(typeOfX);
                });

        return bs;
    }

    // If there is enf(T1,R,T2) such that R contains Rho, T2 contain type of X
    // Then substitute all role which contain X by T1(X)
    private List<Atom> addTypeOfXToBody(Term x, TIntHashSet type, List<Atom> body) {
        List<Atom> newAtoms = new ArrayList<>();
        TIntIterator iterator = type.iterator();
        while (iterator.hasNext()) {
            int i = iterator.next();
            if (i != ClipperManager.getInstance().getThing()) {
                Predicate predicate = new DLPredicate(i, 1);
                List<Term> terms = new ArrayList<>();
                terms.add(x);
                Atom newAtom = new Atom(predicate, terms);
                if (!body.contains(newAtom) && !newAtoms.contains(newAtom)) {
                    newAtoms.add(newAtom);
                }
            }
        }
        return newAtoms;
    }

    // compute new Head for the qPrime
    private Atom computeNewHead(Variable x, Atom oldHead) {
        Atom newHead = oldHead;
        List<Term> newTermsForHead = new ArrayList<Term>();
        for (Term termOfHead : oldHead.getTerms()) {
            if (this.computeRenamingVars(x).contains(termOfHead))
                newTermsForHead.add(x);
            else
                newTermsForHead.add(termOfHead);

        }
        newHead = new Atom(oldHead.getPredicate(), newTermsForHead);
        return newHead;
    }

    // compute new Head for the qPrime if there is an individual in the
    // renamingVars
    private Atom computeNewHead(Variable x, Constant d, Atom oldHead) {
        Atom newHead = oldHead;
        for (Term termOfHead : oldHead.getTerms()) {
            List<Term> newTermsForHead = new ArrayList<Term>();
            if (this.computeRenamingVars(x).contains(termOfHead))
                newTermsForHead.add(d);
            else
                newTermsForHead.add(termOfHead);
            newHead = new Atom(oldHead.getPredicate(), newTermsForHead);

        }
        return newHead;
    }

    public CQ computeQprime(TIntHashSet type, Variable x) {
        CQ qPrime = new CQ();
        qPrime.setHead(head);
        int numberOfIndividual = 0;
        Constant individual = new Constant();
        for (Term term : this.computeRenamingVars(x)) {
            if (!term.isVariable()) {
                numberOfIndividual++;
                individual = (Constant) term;
            }
        }
        if (numberOfIndividual > 1) // cannot merge two individuals
            return null;
        // if there is no individual in the variables which should be renamed
        if (numberOfIndividual == 0) {
            for (Atom atom : body) { // compute body
                if (!(atom.getTerms().contains(x))) {
                    // atom that contains x will disappear in the rewrite query
                    List<Term> newTerms = new ArrayList<Term>();
                    for (Term term : atom.getTerms()) {
                        if (this.computeRenamingVars(x).contains(term))
                            newTerms.add(x);
                        else
                            newTerms.add(term);
                    }
                    Atom renamedVarAtom = new Atom(atom.getPredicate(), newTerms);
                    if (!qPrime.body.contains(renamedVarAtom)) {
                        qPrime.body.add(renamedVarAtom);
                    }
                }
            }
            // add type(x) to the body
            List<Atom> currentBody = qPrime.getBody();
            qPrime.body.addAll(addTypeOfXToBody(x, type, currentBody));
            // compute head
            Atom newHead = computeNewHead(x, this.head);
            qPrime.setHead(newHead);
        }
        // if there is an individual in the variables which should be renamed
        // then all these variables should be renamed as the individual
        if (numberOfIndividual == 1) {
            // compute body
            for (Atom atom : body) {
                if (!(atom.getTerms().contains(x))) {
                    // atom that contains x will disappear in the rewrite query
                    List<Term> newTerms = new ArrayList<Term>();
                    for (Term term : atom.getTerms()) {
                        if (this.computeRenamingVars(x).contains(term))
                            // this term should be renamed
                            newTerms.add(individual);
                        else
                            newTerms.add(term);
                    }
                    Atom renamedVarAtom = new Atom(atom.getPredicate(), newTerms);
                    if (!qPrime.body.contains(renamedVarAtom)) {
                        qPrime.body.add(renamedVarAtom);
                    }
                }
            }
            // at type(x) to the body
            List<Atom> currentBody = qPrime.getBody();
            qPrime.body.addAll(addTypeOfXToBody(individual, type, currentBody));
            // compute head
            Atom newHead = computeNewHead(x, individual, this.head);
            qPrime.setHead(newHead);
        }
        return qPrime;
    }

    public Atom getHead() {
        return this.head;
    }

    public List<Atom> getBody() {
        return this.body;
    }

    public void setHead(Atom head) {
        this.head = head;
    }

    public void setBody(List<Atom> body) {
        this.body = body;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CQ)) return false;
        final CQ other = (CQ) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$head = this.head;
        final Object other$head = other.head;
        if (this$head == null ? other$head != null : !this$head.equals(other$head)) return false;
        final Object this$body = this.body;
        final Object other$body = other.body;
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $head = this.head;
        result = result * PRIME + ($head == null ? 0 : $head.hashCode());
        final Object $body = this.body;
        result = result * PRIME + ($body == null ? 0 : $body.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof CQ;
    }

}
