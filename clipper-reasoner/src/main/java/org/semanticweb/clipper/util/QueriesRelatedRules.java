package org.semanticweb.clipper.util;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.CQFormatter;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplication;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedHornImpContainer;
import org.semanticweb.clipper.hornshiq.queryanswering.Rule;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kien
 * 
 */
public class QueriesRelatedRules {
	private IndexedHornImpContainer indexImps;
	private IndexedEnfContainer indexEnfs;

	private Collection<HornImplication> coreImps;
	private Collection<EnforcedRelation> coreEnfs;
	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
	private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
	private List<ClipperInversePropertyOfAxiom> inverseObjectPropertyAxioms;
	private List<ClipperAtomSubMaxOneAxiom> maxOneCardinalityAxioms;

	private Set<CQ> ucq;

	private Set<Rule> ucqRelatedDatalogRules; // datalog rules related

	private Set<Predicate> ucqRelatedBodyPredicates;
	public int numberOfRelatedDatalogRules = 0;
	protected final int NOTHING = 1;
	protected final int THING = 0;
	protected final int TOP_PROPERTY = 0;
	protected final int BOTTOM_PROPERTY = 2;

	CQFormatter cqFormatter;

	public QueriesRelatedRules(ClipperHornSHIQOntology ont_bs, Set<CQ> rewrittenUcq, CQFormatter cqFormatter) {
		this.cqFormatter = cqFormatter;
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();
		inverseObjectPropertyAxioms = ont_bs.getInversePropertyOfAxioms();
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		ucq = rewrittenUcq;
		ucqRelatedDatalogRules = new HashSet<Rule>();
		ucqRelatedBodyPredicates = new HashSet<Predicate>();
	}

	public IndexedHornImpContainer getIndexImps() {
		return indexImps;
	}

	public void setIndexImps(IndexedHornImpContainer indexImps) {
		this.indexImps = indexImps;
	}

	public IndexedEnfContainer getIndexEnfs() {
		return indexEnfs;
	}

	public void setIndexEnfs(IndexedEnfContainer indexEnfs) {
		this.indexEnfs = indexEnfs;
	}

	public Collection<HornImplication> getCoreImps() {
		return coreImps;
	}

	public void setCoreImps(Collection<HornImplication> coreImps) {
		this.coreImps = coreImps;
	}

	public Collection<EnforcedRelation> getCoreEnfs() {
		return coreEnfs;
	}

	public void setCoreEnfs(Collection<EnforcedRelation> coreEnfs) {
		this.coreEnfs = coreEnfs;
	}

	// end of constructor and getters and setters
	// Algorithm:
	// 1: Get all predicates (with code and arity) from body of conjunctive
	// queries, put it in the set named bodyPredicate
	// 2: Read through all axioms
	// If head of generated rules match with one of predicate in bodyPredicate
	// set
	// 2.0 Add this generated rule to relatedRules set
	// 2.1 Add all body of that rule to bodyPredicate set
	// 2.2 Delete this axiom
	// Repeat this process if there is nothing added to bodyPredicate
	// Complexity: Quadratic of number of axioms since each time at most rule
	// will be removed

	// ========================
	/**
	 * Return true if there is an update in ucqRealatedDatalogRules
	 */

	private Set<String> getEncodedBodyOfImp(TIntHashSet body) {
		Set<String> strBody = new HashSet<String>();
		TIntIterator iterator = body.iterator();
		while (iterator.hasNext()) {
			int index = iterator.next();
			if (index != THING) {
				strBody.add(cqFormatter.getUnaryPredicate(index) + "(X)");
				// strBody.add("c" + index + "(X)");
			}
		}
		return strBody;
	}

	private boolean addDatalogRule(Predicate headPredicate, Set<Predicate> bodyPredicates, Rule addedRule) {

		// if Predicate in HEAD of datalog rule appears on body of
		// conjunctive
		// query, then this rule is considered to be
		// related to this conjunctive query
		if (headPredicate.getEncoding() != 0) {
			Set<Predicate> addedPredicate = new HashSet<Predicate>();

			for (Predicate predicate : ucqRelatedBodyPredicates) {
				if (headPredicate.equals(predicate)) {
					if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
						System.out.println(addedRule);
					}
					numberOfRelatedDatalogRules++;
					addedPredicate.addAll(bodyPredicates);
					ucqRelatedDatalogRules.add(addedRule);
					// addingHappens = true;
				}
			}
			return (ucqRelatedBodyPredicates.addAll(addedPredicate));
		} else
			return false;
	}

	private Set<Predicate> getPredicatesFromSet(TIntHashSet body) {
		TIntIterator iterator = body.iterator();
		Set<Predicate> bodyPredicates = new HashSet<Predicate>();
		while (iterator.hasNext()) {
			int encode = iterator.next();
			Predicate prediate = new DLPredicate(encode, 1);
			bodyPredicates.add(prediate);
		}
		return bodyPredicates;

	}

	// ==========================
	/**
	 * returns true if there is an update in ucqRelatedBodyPredicate
	 */
	public boolean rulesFromImps() {

		boolean updated = false;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Imps in Counting Related rules=============");
		}
		// Rule rule = new Rule();
		Set<HornImplication> removedImps = new HashSet<HornImplication>();
		for (HornImplication imp : coreImps) {

			if (existentialInTheBodby(imp)) {
				continue;
			}

			Rule rule = new Rule();
			// rule.setHead("c" + imp.getHead() + "(X)");
			rule.setHead(cqFormatter.getUnaryPredicate(imp.getHead()) + "(X)");
			rule.setBody(getEncodedBodyOfImp(imp.getBody()));
			if (rule.isNotTrivial()) {
				DLPredicate headPredicate = new DLPredicate(imp.getHead(), 1);
				Set<Predicate> bodyPredicates = getPredicatesFromSet(imp.getBody());
				if (addDatalogRule(headPredicate, bodyPredicates, rule)) {
					updated = true;
					removedImps.add(imp);
				}
			}
		}
		coreImps.removeAll(removedImps);
		return updated;
	}

	// subclassOf( A, R only C)
	public boolean rulesFromValueRestrictions() {
		boolean updated = false;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Value Restrictions ============");
		}
		Set<ClipperAtomSubAllAxiom> removedAxioms = new HashSet<ClipperAtomSubAllAxiom>();
		for (ClipperAtomSubAllAxiom axiom : allValuesFromAxioms) {
			Rule rule = new Rule();
			int ic = axiom.getConcept2();
			int ir = axiom.getRole();
			int ia = axiom.getConcept1();
			rule.setHead(cqFormatter.getUnaryPredicate(ic) + "(Y)");
			if (ia != ClipperManager.getInstance().getThing())
				rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
			final String s;
			s = cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y");
			rule.addAtomToBody(s);

			Predicate cPredicate = new DLPredicate(ic, 1);
			Set<Predicate> bodyPredicates = new HashSet<Predicate>();
			Predicate aPredicate = new DLPredicate(ia, 1);
			Predicate rPredicate = new DLPredicate(ir, 2);
			bodyPredicates.add(aPredicate);
			bodyPredicates.add(rPredicate);
			if (addDatalogRule(cPredicate, bodyPredicates, rule)) {

				updated = true;
				removedAxioms.add(axiom);
			}
		}
		allValuesFromAxioms.removeAll(removedAxioms);
		return updated;
	}

	/*
	 * Delaing with rule generated from axion of type: SubRole (sup, super)
	 * 
	 * @return true if there is an update
	 */
	public boolean rulesFromRoleInclusions() {
		boolean updated = false;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Sub Roles Axioms =====");
		}
		Set<ClipperSubPropertyAxiom> removedAxioms = new HashSet<ClipperSubPropertyAxiom>();
		for (ClipperSubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int superRole = subRoleAxiom.getRole2();
			int subRole = subRoleAxiom.getRole1();
			// don't care about subroleAxiom of anonymous roles
			// if (!(sup % 2 == 1 && sup % 2 == 1)) {
			Rule rule = new Rule();
			if (!(subRole % 2 == 1 && superRole % 2 == 1)) {
				rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(superRole, "X", "Y"));
				rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(subRole, "X", "Y"));

				Predicate headPredicate = new DLPredicate(superRole, 2);
				Set<Predicate> bodyPredicates = new HashSet<Predicate>();
				Predicate subPredicate = new DLPredicate(subRole, 2);
				bodyPredicates.add(subPredicate);

				if (addDatalogRule(headPredicate, bodyPredicates, rule)) {

					updated = true;
					removedAxioms.add(subRoleAxiom);
				}
				// if (KaosManager.getInstance().getVerboseLevel() >= 2) {
				// System.out.println(rule);
				// }
				// program.println(rule);
			}
		}
		this.subObjectPropertyAxioms.removeAll(removedAxioms);
		return updated;
	}

	/**
	 * InverseRole(r1, r2)
	 * 
	 * @return true if there is a update in ucqRelatedBodyPredicates
	 */
	public boolean rulesFromInverseRoleAxioms() {
		boolean updated = false;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From inverse role axioms===================");
		}

		Set<ClipperInversePropertyOfAxiom> removedAxioms = new HashSet<ClipperInversePropertyOfAxiom>();

		for (ClipperInversePropertyOfAxiom ax : inverseObjectPropertyAxioms) {
			Rule rule = new Rule();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();
			rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(r1, "X", "Y"));
			rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(r2, "Y", "X"));

			Predicate headPredicate = new DLPredicate(r1, 2);
			Set<Predicate> bodyPredicates = new HashSet<Predicate>();
			Predicate bodyPredicate = new DLPredicate(r2, 2);
			bodyPredicates.add(bodyPredicate);

			if (addDatalogRule(headPredicate, bodyPredicates, rule)) {
				updated = true;
				removedAxioms.add(ax);
			}
			// if (KaosManager.getInstance().getVerboseLevel() >= 2) {
			// System.out.println(rule);
			// }
			// program.println(rule);

			Rule rule2 = new Rule();
			rule2.setHead(cqFormatter.getBinaryAtomWithoutInverse(r2, "Y", "X"));
			rule2.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(r1, "X", "Y"));
			Set<Predicate> body2Predicates = new HashSet<Predicate>();
			body2Predicates.add(headPredicate);

			if (addDatalogRule(bodyPredicate, body2Predicates, rule2)) {
				updated = true;
				removedAxioms.add(ax);
			}
			// if (KaosManager.getInstance().getVerboseLevel() >= 2) {
			// System.out.println(rule2);
			// }
			// program.println(rule2);
		}
		this.inverseObjectPropertyAxioms.removeAll(removedAxioms);
		return updated;
	}

	/**
	 * @return true if there is a update in ucqRealatedBodyPredicates
	 */
	public boolean rulesFromNumberRestrictionAndEnfs() {
		boolean updated = false;
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From NumberRestrictions And Enfs===================");
		}
		Set<Rule> generatedRules = new HashSet<Rule>();
		for (ClipperAtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {// 2
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			for (EnforcedRelation enfRelation : coreEnfs) {
				if (enfRelation.getRoles().contains(ir)
						&& (enfRelation.getRoles().size() > 1 || enfRelation.getType2().size() > 1)
						&& enfRelation.getType2().contains(ic)) {// 3

					// =========Each element of Type2 is a head of a rule ======
					// System.out.println("enf:"+ enfRelation);
					TIntIterator iterator = enfRelation.getType2().iterator();
					while (iterator.hasNext()) {// 4
						int index = iterator.next();
						// System.out.println("index of Type2:" +index);
						// create head of the rule. Each element of Type2 is a
						// head.
						Rule rule = new Rule();
						rule.setHead(cqFormatter.getUnaryPredicate(index) + "(Y)");

						Predicate headPredicate = new DLPredicate(index, 1);
						Set<Predicate> bodyPredicates = new HashSet<Predicate>();

						rule.setHeadPredicate(index);
						rule.setHeadPredicatArity(1);
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));

						bodyPredicates.addAll(getPredicatesFromSet(enfRelation.getType1()));

						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");

						Predicate iaPredicate = new DLPredicate(ia, 1);
						bodyPredicates.add(iaPredicate);

						rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));

						Predicate irPredicate = new DLPredicate(ir, 2);
						bodyPredicates.add(irPredicate);

						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Y)");

						Predicate icPredicate = new DLPredicate(ic, 2);
						bodyPredicates.add(icPredicate);
						// Rule only makes sense if the read is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);
							if (addDatalogRule(headPredicate, bodyPredicates, rule))
								updated = true;

						}
					}
					// =======Each role in enf.roles is a head of the rule
					TIntIterator iterator2 = enfRelation.getRoles().iterator();
					while (iterator2.hasNext()) {
						int index2 = iterator2.next();
						Rule rule = new Rule();

						rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(index2, "X", "Y"));
						Predicate headPredicate = new DLPredicate(index2, 2);

						Set<Predicate> bodyPredicates = new HashSet<Predicate>();

						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						bodyPredicates.addAll(getPredicatesFromSet(enfRelation.getType1()));

						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
						Predicate iaPredicate = new DLPredicate(ia, 1);
						bodyPredicates.add(iaPredicate);

						rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
						Predicate irPredicate = new DLPredicate(ir, 2);
						bodyPredicates.add(irPredicate);

						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Y)");
						Predicate icPredicate = new DLPredicate(ic, 1);
						bodyPredicates.add(icPredicate);

						// Rule only makes sense if its Head is not
						// contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);
							if (addDatalogRule(headPredicate, bodyPredicates, rule))
								updated = true;

						}
					}
				}// 3
			}// 2
		}// 1
		return updated;

	}

	private void initializeUcqRelatedBodyPredicates() {
		for (CQ cq : ucq) {
			for (Atom atom : cq.getBody()) {
				ucqRelatedBodyPredicates.add(atom.getPredicate());
			}
		}
	}

	public void countUCQRelatedRules() {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("==============Datalog rules which are related to UCQ :================ ");
		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Role assertions: ");
		// ruleForBottomConcept(program);
		initializeUcqRelatedBodyPredicates();
		boolean updated = true;
		while (updated) {
			updated = rulesFromImps() || rulesFromValueRestrictions() || rulesFromRoleInclusions()
					|| rulesFromInverseRoleAxioms() || rulesFromNumberRestrictionAndEnfs();
		}
	}

	// ====================================================
	/*
	 * to eliminate some redundant rule in normalization
	 */
	private boolean existentialInTheBodby(HornImplication imp) {
		TIntHashSet body = imp.getBody();
		TIntIterator iterator = body.iterator();
		while (iterator.hasNext()) {
			int p = iterator.next();
			String classIRI = ClipperManager.getInstance()
			//
					.getOwlClassEncoder().getSymbolByValue(p).getIRI().toString();
			if (classIRI.startsWith("http://www.example.org/fresh#SOME_fresh"))
				return true;
		}
		return false;
	}

    public Set<Rule> getUcqRelatedDatalogRules() {
        return this.ucqRelatedDatalogRules;
    }
}
