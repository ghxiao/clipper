package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.set.hash.TIntHashSet;

import java.util.*;

import lombok.Getter;

import org.semanticweb.clipper.hornshiq.ontology.AtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.InversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Variable;

@Getter
public class QueryRewriting implements QueryRewriter {

	private IndexedEnfContainer enfContainer;
	private List<AtomSubAllAxiom> allValuesFromAxioms;
	private List<InversePropertyOfAxiom> inversePropertyOfAxioms;

	private Set<CQ> ucq;

	public QueryRewriting(IndexedEnfContainer indexEnfs, List<InversePropertyOfAxiom> inversePropertyAxioms,
			List<AtomSubAllAxiom> allValuesFromAxs) {
		this.enfContainer = indexEnfs;
		this.inversePropertyOfAxioms = inversePropertyAxioms;
		this.allValuesFromAxioms = allValuesFromAxs;
		ucq = new HashSet<CQ>();
	}

	public QueryRewriting(Set<EnforcedRelation> enfs, List<InversePropertyOfAxiom> inversePropertyOfAxioms) {
		this.enfContainer = new IndexedEnfContainer();
		this.enfContainer.addAll(enfs);
		this.inversePropertyOfAxioms = inversePropertyOfAxioms;
		this.allValuesFromAxioms = new ArrayList<AtomSubAllAxiom>();
		ucq = new HashSet<CQ>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.semanticweb.clipper.hornshiq.queryanswering.QueryRewriter#rewrite
	 * (org.semanticweb.clipper.hornshiq.rule.CQ)
	 */
	@Override
	public Set<CQ> rewrite(CQ query) {
		rewrite_recursive(query);
		return this.getUcq();
	}

	public void rewrite_recursive(CQ q1) {

		// UPDATE 12 March 2012
		ucq.add(q1);
		CQ q = preprocessQuery(q1);
		if (!q1.equals(q))
			ucq.add(q);
		// END OF UPDATE
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
		Set<Atom> bodyAtoms = q.getBody();
		Set<Atom> copyBodyAtoms = new HashSet<Atom>();
		copyBodyAtoms.addAll(bodyAtoms);

		Set<Atom> newBodyAtoms = new HashSet<Atom>();
		newBodyAtoms.addAll(bodyAtoms);

		Iterator<Atom> iter = bodyAtoms.iterator();
		while (iter.hasNext()) {
			Atom bodyAtom = (Atom) iter.next();
			if (bodyAtom.getPredicate().getArity() > 1) {
				for (AtomSubAllAxiom ax : this.allValuesFromAxioms) {
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
}
