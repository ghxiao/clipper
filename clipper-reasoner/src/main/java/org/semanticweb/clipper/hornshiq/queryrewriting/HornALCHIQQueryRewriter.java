package org.semanticweb.clipper.hornshiq.queryrewriting;

import com.google.common.collect.Lists;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.queryrewriting.QueryRewriter;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HornALCHIQQueryRewriter implements QueryRewriter {

	private IndexedEnfContainer enfContainer;
	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
	private List<ClipperInversePropertyOfAxiom> inversePropertyOfAxioms;

	private Set<CQ> ucq;

	public HornALCHIQQueryRewriter(IndexedEnfContainer indexEnfs, List<ClipperInversePropertyOfAxiom> inversePropertyAxioms,
                                   List<ClipperAtomSubAllAxiom> allValuesFromAxs) {
		this.enfContainer = indexEnfs;
		this.inversePropertyOfAxioms = inversePropertyAxioms;
		this.allValuesFromAxioms = allValuesFromAxs;
		ucq = new HashSet<CQ>();
	}

	public HornALCHIQQueryRewriter(Set<EnforcedRelation> enfs, List<ClipperInversePropertyOfAxiom> inversePropertyOfAxioms) {
		this.enfContainer = new IndexedEnfContainer();
		this.enfContainer.addAll(enfs);
		this.inversePropertyOfAxioms = inversePropertyOfAxioms;
		this.allValuesFromAxioms = new ArrayList<>();
		ucq = new HashSet<CQ>();
	}


	@Override
	public Set<CQ> rewrite(CQ query) {
		rewrite_recursive(query);
		return this.getUcq();
	}

	public void rewrite_recursive(CQ q1) {

		ucq.add(q1);
		CQ q = preprocessQuery(q1);
		if (!q1.equals(q)){
            ucq.add(q);
        }

		for (Variable x : q.getNonDistinguishedVars()) {
			if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
				System.out.println("% Choose variable:" + x);
			}
			TIntHashSet rho = q.computeRho(x, inversePropertyOfAxioms);
			if ((rho != null) && q.hasNoLoop(x)) {
				if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
					System.out.println("              Comptute S/rho wrt " + x + " : " + rho);
				}
				// Need method indexEnfs.matchRolesAndType2
				// Set<EnforcedRelation> machedEnfs =
				// indexEnfs.matchRolesAndType2
				Collection<EnforcedRelation> enfs = enfContainer.matchRolesAndType2(rho, q.computeTypeOfX(x));
				for (EnforcedRelation enf : enfs) {
					// if (enf.getType2().containsAll(q.computeTypeOfX(x))
					// && (enf.getRoles().containsAll(rho))) {
					if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
						System.out.println("              There is a rewriteable case :" + enf);
					}
					CQ qPrime = q.computeQprime(enf.getType1(), x);
					if (ClipperManager.getInstance().getVerboseLevel() >= 1) {
						System.out.println("               Rewrite query at this step:" + qPrime);
					}
					if ((qPrime != null) && (!ucq.contains(qPrime))) {
						ucq.add(qPrime);
						rewrite(qPrime);
					}
				}
			}
		}
	}

	/**
	 * rewrite a collection of queries, aka limited DL+log style rules
	 * 
	 * TODO: future work
	 * 
	 * @param queries
	 */
	public void rewrite(Collection<CQ> queries) {
		for (CQ cq : queries) {
			rewrite(cq);
		}
	}

	// remove body atoms that can be inferred from other body atoms based on
	// Domain and range constrain.
	private CQ preprocessQuery(CQ q) {
		CQ newQuery = new CQ();
		newQuery.setHead(q.getHead());
		List<Atom> bodyAtoms = q.getBody();
        List<Atom> copyBodyAtoms = new ArrayList<>();
		copyBodyAtoms.addAll(bodyAtoms);

		List<Atom> newBodyAtoms = new ArrayList<>();
		newBodyAtoms.addAll(bodyAtoms);

		Iterator<Atom> iter = bodyAtoms.iterator();
		while (iter.hasNext()) {
			Atom bodyAtom = (Atom) iter.next();
			if (bodyAtom.getPredicate().getArity() > 1) {
				for (ClipperAtomSubAllAxiom ax : this.allValuesFromAxioms) {
					// if meet axiom in the form of : Thing SubclassOf ForAll
					// Role . Concept2)
					// then check if concept2 is in the body atom. If yes,
					// remove it from body atoms.
					if (bodyAtom.getPredicate().getEncoding() == ax.getRole() && ax.getConcept1() == 0) {
						Iterator<Atom> iterator = copyBodyAtoms.iterator();
						while (iterator.hasNext()) {
							Atom checkedbodyAtom = (Atom) iterator.next();
							if (checkedbodyAtom.getPredicate().getEncoding() == ax.getConcept2()
									&& checkedbodyAtom.getPredicate().getArity() == 1) {
								newBodyAtoms.remove(checkedbodyAtom);
							}
						}

					}
				}
			}
		}
		newQuery.setBody(newBodyAtoms);
		// System.out.println("Strimed query: " + newQuery);
		return newQuery;
	}

    public IndexedEnfContainer getEnfContainer() {
        return this.enfContainer;
    }

    public List<ClipperAtomSubAllAxiom> getAllValuesFromAxioms() {
        return this.allValuesFromAxioms;
    }

    public List<ClipperInversePropertyOfAxiom> getInversePropertyOfAxioms() {
        return this.inversePropertyOfAxioms;
    }

    public Set<CQ> getUcq() {
        return this.ucq;
    }
}
