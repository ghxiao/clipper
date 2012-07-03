package org.semanticweb.clipper.hornshiq.queryanswering;

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
import org.semanticweb.owlapi.model.IRI;


/*
 * This class is used to create datalog program from the representation of KB
 */
/**
 * 
 * @author xiao
 *
 * FIXME:
 */
public class ReductionToDatalogOpt {

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

	protected final int NOTHING = 1;
	protected final int THING = 0;
	protected final int TOP_PROPERTY = 0;
	protected final int BOTTOM_PROPERTY = 2;

	// private NamingStrategy namingStrategy;

	public enum NamingStrategy {
		// OriginalName,
		/** Using fragment for predicate and constants, with lower case ininital */
		LowerCaseFragment,
		/** Everything has an integer encoding */
		IntEncoding,
	}

	/*
	 * Constructor
	 * 
	 * @param: input Ontology
	 * 
	 * @param: Name of generated datalog file
	 */
	public ReductionToDatalogOpt() {

		// this.namingStrategy = NamingStrategy.IntEncoding;
	}

//	public void setNamingStrategy(NamingStrategy strategy) {
//		// this.namingStrategy = strategy;
//	}

	public ReductionToDatalogOpt(NormalHornALCHIQOntology ont_bs) {
		// this.namingStrategy = NamingStrategy.IntEncoding;

		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.IntEncoding);
		// KaosManager.getInstance().setNamingStrategy(NamingStrategy.LowerCaseFragment);

		indexImps = new IndexedHornImpContainer();
		indexEnfs = new IndexedEnfContainer();
		// initializing allValuesFromAxioms
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();

		// initializing maxOneCardinalityAxioms;
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();

		// initializing subObjectPropertyAxioms
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		// initializing private List<InversePropertyOfAxiom> inverseRoleAxioms;
		inverseObjectPropertyAxioms = ont_bs.getInversePropertyOfAxioms();

		conceptAssertionAxioms = ont_bs.getConceptAssertionAxioms();

		roleAssertionAxioms = ont_bs.getRoleAssertionAxioms();

		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();
	}

	// getters and setters

	// end of getters and setters

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

//	public void ruleForBottomConcept(PrintStream program) {
//		Constraint cons = new Constraint();
//		// cons.addAtomToBody("c" + NOTHING + "(X)");
//
//		cons.addAtomToBody(getUnaryPredicate(NOTHING) + "(X)");
//		if (KaosManager.getInstance().getVerboseLevel() >= 2) {
//			System.out.println(cons);
//		}
//		program.println(cons);
//
//	}
//	/**
//	 * enf(T1,R,T2), Bottom \in T2 ---> imps(T1,Bottom)
//	 * @param program
//	 */
//    public void ruleFromReachBottom(PrintStream program){
//    	
//    }
	public void rulesFromImps(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Imps=============");
		}
		Rule rule = new Rule();
		for (HornImplication imp : coreImps) {

			if (existentialInTheBodby(imp)) {
				continue;
			}

			rule.clear();
			// rule.setHead("c" + imp.getHead() + "(X)");
			rule.setHead(getUnaryPredicate(imp.getHead()) + "(X)");
			rule.setBody(getEncodedBodyOfImp(imp.getBody()));
			if (rule.isNotTrivial()) {
				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					System.out.println(rule);
				}
				program.println(rule);
			}
		}
	}

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

	// subclassOf( A, R only C)
	public void rulesFromValueRestrictions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("%==========rules From Value Restrictions ============");
		}
		Rule rule = new Rule();
		for (AtomSubAllAxiom axiom : allValuesFromAxioms) {
			rule.clear();
			int ic = axiom.getConcept2();
			int ir = axiom.getRole();
			int ia = axiom.getConcept1();

			rule.setHead(getUnaryPredicate(ic) + "(Y)");
			if (ia != ClipperManager.getInstance().getThing()) rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");
			final String s;
			s = getBinaryAtomWithoutInverse(ir, "X", "Y");
			rule.addAtomToBody(s);
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
		}
	}

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

	// SubRole (sup, super)
	public void rulesFromRoleInclusions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Sub Roles Axioms =====");
		}
		for (SubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int sup = subRoleAxiom.getRole2();
			int sub = subRoleAxiom.getRole1();
			// don't care about subroleAxiom of anonymous roles
			// if (!(sup % 2 == 1 && sup % 2 == 1)) {
			Rule rule = new Rule();
			if (!(sub % 2 == 1 && sup % 2 == 1)) {
				rule.setHead(getBinaryAtomWithoutInverse(sup, "X", "Y"));
				rule.addAtomToBody(getBinaryAtomWithoutInverse(sub, "X", "Y"));
				if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
					System.out.println(rule);
				}
				program.println(rule);
			}
		}
	}

	// InverseRole(r1, r2)
	public void rulesFromInverseRoleAxioms(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("%==========rules From inverse role axioms===================");
		}
		Rule rule = new Rule();
		for (InversePropertyOfAxiom ax : inverseObjectPropertyAxioms) {
			rule.clear();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();
			rule.setHead(getBinaryAtomWithoutInverse(r1, "X", "Y"));
			rule.addAtomToBody(getBinaryAtomWithoutInverse(r2, "Y", "X"));
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
			rule.clear();
			rule.setHead(getBinaryAtomWithoutInverse(r2, "Y", "X"));
			rule.addAtomToBody(getBinaryAtomWithoutInverse(r1, "X", "Y"));
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
		}
	}

	// SubclassOf( A, R max 1 C)
	public void rulesFromNumberRestrictions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("%==========rules From NumberRestrictions ===================");
		}
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			Constraint constraint = new Constraint();
			if (ia != THING)
				constraint.addAtomToBody(getUnaryPredicate(ia) + "(X)");
			if (ic != THING) {
				constraint.addAtomToBody(getUnaryPredicate(ic) + "(Y)");
				constraint.addAtomToBody(getUnaryPredicate(ic) + "(Z)");
			}
			constraint.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X", "Y"));
			constraint.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X", "Z"));
			constraint.addAtomToBody("Y !=Z");
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(constraint);
			}
			program.println(constraint);
		}
	}

	public void rulesFromNumberRestrictionAndEnfs(PrintStream program) {// 1
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
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						if (ia != ClipperManager.getInstance().getThing()) rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");
						rule.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X",
								"Y"));
						if (ic != ClipperManager.getInstance().getThing())rule.addAtomToBody(getUnaryPredicate(ic) + "(Y)");
						// Rule only makes sense if the read is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);

						}
					}
					// =======Each role in enf.roles is a head of the rule
					TIntIterator iterator2 = enfRelation.getRoles().iterator();
					while (iterator2.hasNext()) {
						int index2 = iterator2.next();
						Rule rule = new Rule();
						rule.setHead(getBinaryAtomWithoutInverse(index2, "X",
								"Y"));
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						if (ia != ClipperManager.getInstance().getThing()) rule.addAtomToBody(getUnaryPredicate(ia) + "(X)");
						rule.addAtomToBody(getBinaryAtomWithoutInverse(ir, "X",
								"Y"));
						if (ic != ClipperManager.getInstance().getThing()) rule.addAtomToBody(getUnaryPredicate(ic) + "(Y)");
						// Rule only makes sense if its Head is not
						// contained in
						// the body.
						if (rule.isNotTrivial()) {
							// System.out.println(rule);
							generatedRules.add(rule);

						}
					}
				}// 3
			}// 2
		}// 1

		for (Rule r : generatedRules) {
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(r);
			}
			program.println(r);
		}
	}

	public void rulesFromABoxAssertions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%Facts From ABox Assertions");
		}
		for (ObjectPropertyAssertionAxiom a : roleAssertionAxioms) {

			int ir = a.getRole();
			int ind1 = a.getIndividual1();
			int ind2 = a.getIndividual2();
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(getBinaryPredicate(ir) + "("
						+ getConstant(ind1) + "," + getConstant(ind2) + ").");
			}
			program.println(getBinaryPredicate(ir) + "(" + getConstant(ind1)
					+ "," + getConstant(ind2) + ").");

		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Class assertions: ");
		for (ConceptAssertionAxiom ca : conceptAssertionAxioms) {
			int ic = ca.getConcept();
			int iInd = ca.getIndividual();
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(getUnaryPredicate(ic) + "("
						+ getConstant(iInd) + ").");
			}
			program.println(getUnaryPredicate(ic) + "(" + getConstant(iInd)
					+ ").");

		}
	}
   
	/**
	 * @return DATALOG program that contains ABox completion rules and Assertions in ABox
	 * */
	public void getEncodedDataLogProgram(String generatedDataLogFile) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(
					generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

	//		ruleForBottomConcept(program);
			rulesFromImps(program);
			rulesFromValueRestrictions(program);
			rulesFromRoleInclusions(program);
			rulesFromInverseRoleAxioms(program);
			rulesFromNumberRestrictions(program);
			rulesFromNumberRestrictionAndEnfs(program);
			rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	/** 
	 * @return DATALOG program contain only ABox assertions
	 * */
	public void getABoxAssertionsDatalogProgram(String generatedDataLogFile) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(
					generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

			// ruleForBottomConcept(program);
			// rulesFromImps(program);
			// rulesFromValueRestrictions(program);
			// rulesFromRoleInclusions(program);
			// rulesFromInverseRoleAxioms(program);
			// rulesFromNumberRestrictions(program);
			// rulesFromNumberRestrictionAndEnfs(program);
			rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 
	 * @param generatedDataLogFile
	 * @return DATALOG program contains only Completion rules.
	 */
	public void getCompletionRulesDatalogProgram(String generatedDataLogFile) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out
					.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(
					generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

		//	 ruleForBottomConcept(program);
			 rulesFromImps(program);
			 rulesFromValueRestrictions(program);
			 rulesFromRoleInclusions(program);
			 rulesFromInverseRoleAxioms(program);
			 rulesFromNumberRestrictions(program);
			 rulesFromNumberRestrictionAndEnfs(program);
			//rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	private String getConstant(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlIndividualEncoder()
					.getSymbolByValue(value).asOWLNamedIndividual().getIRI();
			return "\"" + normalize(iri) + "\"";
		case IntEncoding:
			return "d" + value;
		}
		throw new IllegalStateException("Not possible");
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
}
