package org.semanticweb.clipper.util;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.clipper.hornshiq.ontology.AtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.AtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.NormalHornALCHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ConceptAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.InversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ObjectPropertyAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.SubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.Constraint;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplication;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplicationRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedHornImpContainer;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.Rule;
import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.owlapi.model.IRI;



/**
 * @author kien
 * 
 */
public class QueriesRelatedRules {
	private IndexedHornImpContainer indexImps;
	private IndexedEnfContainer indexEnfs;

	private Collection<HornImplication> coreImps;
	private Collection<EnforcedRelation> coreEnfs;
	private List<AtomSubAllAxiom> allValuesFromAxioms;
	private List<SubPropertyAxiom> subObjectPropertyAxioms;
	private List<InversePropertyOfAxiom> inverseObjectPropertyAxioms;
	private List<AtomSubMaxOneAxiom> maxOneCardinalityAxioms;
	private List<ConceptAssertionAxiom> conceptAssertionAxioms;
	private List<ObjectPropertyAssertionAxiom> roleAssertionAxioms;
	private Set<CQ> ucq;
	private Set<Rule> ucqRelatedDatalogRules; // contain datalog rules related
												// to ucq
	private Set<Predicate> ucqRelatedBodyPredicates;
	public int numberOfRelatedDatalogRules = 0;
	protected final int NOTHING = 1;
	protected final int THING = 0;
	protected final int TOP_PROPERTY = 0;
	protected final int BOTTOM_PROPERTY = 2;

	/*
	 * Constructor
	 * 
	 * @param: input Ontology
	 * 
	 * @param: Name of generated datalog file
	 */
	public QueriesRelatedRules() {

		// this.namingStrategy = NamingStrategy.IntEncoding;
	}

	public Set<CQ> getUcq() {
		return ucq;
	}

	public void setUcq(Set<CQ> ucq) {
		this.ucq = ucq;
	}

	public Set<Rule> getUcqRelatedDatalogRules() {
		return ucqRelatedDatalogRules;
	}

	public void setUcqRelatedDatalogRules(Set<Rule> ucqRelatedDatalogRules) {
		this.ucqRelatedDatalogRules = ucqRelatedDatalogRules;
	}

	public int getNumberOfRelatedDatalogRules() {
		return numberOfRelatedDatalogRules;
	}

	public void setNumberOfRelatedDatalogRules(int numberOfRelatedDatalogRules) {
		this.numberOfRelatedDatalogRules = numberOfRelatedDatalogRules;
	}

	public void setNamingStrategy(NamingStrategy strategy) {
		// this.namingStrategy = strategy;
	}

	public QueriesRelatedRules(NormalHornALCHIQOntology ont_bs,
			Set<CQ> rewrittenUcq) {
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();
		inverseObjectPropertyAxioms = ont_bs.getInversePropertyOfAxioms();
		conceptAssertionAxioms = ont_bs.getConceptAssertionAxioms();
		roleAssertionAxioms = ont_bs.getRoleAssertionAxioms();
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
				strBody.add(getUnaryPredicate(index) + "(X)");
				// strBody.add("c" + index + "(X)");
			}
		}
		return strBody;
	}

	private boolean addDatalogRule(Predicate headPredicate,
			Set<Predicate> bodyPredicates, Rule addedRule) {

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
			System.out
					.println("%==========rules From Imps in Counting Related rules=============");
		}
		// Rule rule = new Rule();
		Set<HornImplication> removedImps = new HashSet<HornImplication>();
		for (HornImplication imp : coreImps) {

			if (existentialInTheBodby(imp)) {
				continue;
			}

			Rule rule = new Rule();
			// rule.setHead("c" + imp.getHead() + "(X)");
			rule.setHead(getUnaryPredicate(imp.getHead()) + "(X)");
			rule.setBody(getEncodedBodyOfImp(imp.getBody()));
			if (rule.isNotTrivial()) {
				DLPredicate headPredicate = new DLPredicate(imp.getHead(), 1);
				Set<Predicate> bodyPredicates = getPredicatesFromSet(imp
						.getBody());
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
			System.out
					.println("%==========rules From Value Restrictions ============");
		}
		Set<AtomSubAllAxiom> removedAxioms = new HashSet<AtomSubAllAxiom>();
		for (AtomSubAllAxiom axiom : allValuesFromAxioms) {
			Rule rule = new Rule();
			int ic = axiom.getConcept2();
			int ir = axiom.getRole();
			int ia = axiom.getConcept1();
			rule.setHead(getUnaryPredicate(ic) + "(Y)");
			if (ia != ClipperManager.getInstance().getThing())
				rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");
			final String s;
			s = getBinaryAtomWithoutInverse(ir, "X", "Y");
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
		Set<SubPropertyAxiom> removedAxioms = new HashSet<SubPropertyAxiom>();
		for (SubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int superRole = subRoleAxiom.getRole2();
			int subRole = subRoleAxiom.getRole1();
			// don't care about subroleAxiom of anonymous roles
			// if (!(sup % 2 == 1 && sup % 2 == 1)) {
			Rule rule = new Rule();
			if (!(subRole % 2 == 1 && superRole % 2 == 1)) {
				rule.setHead(getBinaryAtomWithoutInverse(superRole, "X", "Y"));
				rule.addAtomToBody(getBinaryAtomWithoutInverse(subRole, "X",
						"Y"));

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
			System.out
					.println("%==========rules From inverse role axioms===================");
		}

		Set<InversePropertyOfAxiom> removedAxioms = new HashSet<InversePropertyOfAxiom>();

		for (InversePropertyOfAxiom ax : inverseObjectPropertyAxioms) {
			Rule rule = new Rule();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();
			rule.setHead(getBinaryAtomWithoutInverse(r1, "X", "Y"));
			rule.addAtomToBody(getBinaryAtomWithoutInverse(r2, "Y", "X"));

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
			rule2.setHead(getBinaryAtomWithoutInverse(r2, "Y", "X"));
			rule2.addAtomToBody(getBinaryAtomWithoutInverse(r1, "X", "Y"));
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
			System.out
					.println("%==========rules From NumberRestrictions And Enfs===================");
		}
		Set<Rule> generatedRules = new HashSet<Rule>();
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {// 2
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			for (EnforcedRelation enfRelation : coreEnfs) {
				if (enfRelation.getRoles().contains(ir)
						&& (enfRelation.getRoles().size() > 1 || enfRelation
								.getType2().size() > 1)
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
						rule.setHead(getUnaryPredicate(index) + "(Y)");

						Predicate headPredicate = new DLPredicate(index, 1);
						Set<Predicate> bodyPredicates = new HashSet<Predicate>();

						rule.setHeadPredicate(index);
						rule.setHeadPredicatArity(1);
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));

						bodyPredicates.addAll(getPredicatesFromSet(enfRelation
								.getType1()));

						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");

						Predicate iaPredicate = new DLPredicate(ia, 1);
						bodyPredicates.add(iaPredicate);

						rule.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X",
								"Y"));

						Predicate irPredicate = new DLPredicate(ir, 2);
						bodyPredicates.add(irPredicate);

						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(getUnaryPredicate(ic) + "(Y)");

						Predicate icPredicate = new DLPredicate(ic, 2);
						bodyPredicates.add(icPredicate);
						// Rule only makes sense if the read is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);
							if (addDatalogRule(headPredicate, bodyPredicates,
									rule))
								updated = true;

						}
					}
					// =======Each role in enf.roles is a head of the rule
					TIntIterator iterator2 = enfRelation.getRoles().iterator();
					while (iterator2.hasNext()) {
						int index2 = iterator2.next();
						Rule rule = new Rule();

						rule.setHead(getBinaryAtomWithoutInverse(index2, "X",
								"Y"));
						Predicate headPredicate = new DLPredicate(index2, 2);

						Set<Predicate> bodyPredicates = new HashSet<Predicate>();

						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						bodyPredicates.addAll(getPredicatesFromSet(enfRelation
								.getType1()));

						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");
						Predicate iaPredicate = new DLPredicate(ia, 1);
						bodyPredicates.add(iaPredicate);

						rule.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X",
								"Y"));
						Predicate irPredicate = new DLPredicate(ir, 2);
						bodyPredicates.add(irPredicate);

						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(getUnaryPredicate(ic) + "(Y)");
						Predicate icPredicate = new DLPredicate(ic, 1);
						bodyPredicates.add(icPredicate);

						// Rule only makes sense if its Head is not
						// contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);
							if (addDatalogRule(headPredicate, bodyPredicates,
									rule))
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
			System.out
					.println("==============Datalog rules which are related to UCQ :================ ");
		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Role assertions: ");
		// ruleForBottomConcept(program);
		initializeUcqRelatedBodyPredicates();
		boolean updated = true;
		while (updated) {
			updated = rulesFromImps() || rulesFromValueRestrictions()
					|| rulesFromRoleInclusions()
					|| rulesFromInverseRoleAxioms()
					|| rulesFromNumberRestrictionAndEnfs();
		}
	}

	private String getBinaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(value).asOWLObjectProperty().getIRI();
			return normalize(iri);
		case IntEncoding:
			return "r" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	private String getUnaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(value).getIRI();

			return normalize(iri);
		case IntEncoding:
			return "c" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	private String normalize(IRI iri) {
		String fragment = iri.getFragment();
		if (fragment != null) {
			return fragment.replaceAll("[_-]", "").toLowerCase();
		} else {
			final String iriString = iri.toString();
			int i = iriString.lastIndexOf('/') + 1;
			return iriString.substring(i).replace("_-", "").toLowerCase();

		}

	}

	// =====================================================
	protected String getBinaryAtomWithoutInverse(int v, String var1, String var2) {
		final String s;
		if (v % 2 == 0) {
			s = getBinaryPredicate(v) + "(" + var1 + "," + var2 + ")";
		} else {
			// int inverseOfr = ir + 1;
			int inverseOfr = v - 1;
			s = getBinaryPredicate(inverseOfr) + "(" + var2 + "," + var1 + ")";
		}
		return s;
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
					.getOwlClassEncoder().getSymbolByValue(p).getIRI()
					.toString();
			if (classIRI.startsWith("http://www.example.org/fresh#SOME_fresh"))
				return true;
		}
		return false;
	}
}
