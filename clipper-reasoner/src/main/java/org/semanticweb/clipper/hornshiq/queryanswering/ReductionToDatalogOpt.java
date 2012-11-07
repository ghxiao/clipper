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

import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperConceptAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperPropertyAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperTransitivityAxiom;
import org.semanticweb.owlapi.model.IRI;

/*
 * This class is used to create datalog program from the representation of KB
 */
/**
 * 
 * @author xiao
 * 
 *         FIXME:
 */
public class ReductionToDatalogOpt {

	private IndexedHornImpContainer indexImps;
	private IndexedEnfContainer indexEnfs;

	private Collection<HornImplication> coreImps;
	private Collection<EnforcedRelation> coreEnfs;
	private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
	private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
	private List<ClipperInversePropertyOfAxiom> inverseObjectPropertyAxioms;
	private List<ClipperAtomSubMaxOneAxiom> maxOneCardinalityAxioms;
	private List<ClipperConceptAssertionAxiom> conceptAssertionAxioms;
	private List<ClipperPropertyAssertionAxiom> roleAssertionAxioms;
	private List<ClipperTransitivityAxiom> transAxioms;

	protected final int NOTHING = 1;
	protected final int THING = 0;
	protected final int TOP_PROPERTY = 0;
	protected final int BOTTOM_PROPERTY = 2;

	CQFormater cqFormater = new CQFormater();

	// private NamingStrategy namingStrategy;

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

	// public void setNamingStrategy(NamingStrategy strategy) {
	// // this.namingStrategy = strategy;
	// }

	public ReductionToDatalogOpt(ClipperHornSHIQOntology ont_bs) {
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

		roleAssertionAxioms = ont_bs.getPropertyAssertionAxioms();

		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		transAxioms = ont_bs.getTransitivityAxioms();
	}

	// getters and setters

	// end of getters and setters

	private Set<String> getEncodedBodyOfImp(TIntHashSet body) {
		Set<String> strBody = new HashSet<String>();
		TIntIterator iterator = body.iterator();
		while (iterator.hasNext()) {
			int index = iterator.next();
			if (index != THING) {
				strBody.add(cqFormater.getUnaryPredicate(index) + "(X)");
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
			rule.setHead(cqFormater.getUnaryPredicate(imp.getHead()) + "(X)");
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
					.getOwlClassEncoder().getSymbolByValue(p).getIRI().toString();
			if (classIRI.startsWith("http://www.example.org/fresh#SOME_fresh"))
				return true;
		}
		return false;
	}

	// subclassOf( A, R only C)
	public void rulesFromValueRestrictions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Value Restrictions ============");
		}
		Rule rule = new Rule();
		for (ClipperAtomSubAllAxiom axiom : allValuesFromAxioms) {
			rule.clear();
			int ic = axiom.getConcept2();
			int ir = axiom.getRole();
			int ia = axiom.getConcept1();

			rule.setHead(cqFormater.getUnaryPredicate(ic) + "(Y)");
			if (ia != ClipperManager.getInstance().getThing())
				rule.addAtomToBody(cqFormater.getUnaryPredicate(ia) + "(X)");
			final String s;
			s = cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Y");
			rule.addAtomToBody(s);
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
		}
	}

	// SubRole (sup, super)
	public void rulesFromRoleInclusions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From Sub Roles Axioms =====");
		}
		for (ClipperSubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int sup = subRoleAxiom.getRole2();
			int sub = subRoleAxiom.getRole1();
			// don't care about subroleAxiom of anonymous roles
			// if (!(sup % 2 == 1 && sup % 2 == 1)) {
			Rule rule = new Rule();
			if (!(sub % 2 == 1 && sup % 2 == 1)) {
				rule.setHead(cqFormater.getBinaryAtomWithoutInverse(sup, "X", "Y"));
				rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(sub, "X", "Y"));
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
			System.out.println("%==========rules From inverse role axioms===================");
		}
		Rule rule = new Rule();
		for (ClipperInversePropertyOfAxiom ax : inverseObjectPropertyAxioms) {
			rule.clear();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();
			rule.setHead(cqFormater.getBinaryAtomWithoutInverse(r1, "X", "Y"));
			rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(r2, "Y", "X"));
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
			rule.clear();
			rule.setHead(cqFormater.getBinaryAtomWithoutInverse(r2, "Y", "X"));
			rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(r1, "X", "Y"));
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			program.println(rule);
		}
	}

	// SubclassOf( A, R max 1 C)
	public void rulesFromNumberRestrictions(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From NumberRestrictions ===================");
		}
		for (ClipperAtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			Constraint constraint = new Constraint();
			if (ia != THING)
				constraint.addAtomToBody(cqFormater.getUnaryPredicate(ia) + "(X)");
			if (ic != THING) {
				constraint.addAtomToBody(cqFormater.getUnaryPredicate(ic) + "(Y)");
				constraint.addAtomToBody(cqFormater.getUnaryPredicate(ic) + "(Z)");
			}
			constraint.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Y"));
			constraint.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Z"));
			constraint.addAtomToBody("Y !=Z");
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(constraint);
			}
			program.println(constraint);
		}
	}

	public void rulesFromNumberRestrictionAndEnfs(PrintStream program) {// 1
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
						rule.setHead(cqFormater.getUnaryPredicate(index) + "(Y)");
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormater.getUnaryPredicate(ia) + "(X)");
						rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Y"));
						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormater.getUnaryPredicate(ic) + "(Y)");
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
						rule.setHead(cqFormater.getBinaryAtomWithoutInverse(index2, "X", "Y"));
						rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
						if (ia != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormater.getUnaryPredicate(ia) + "(X)");
						rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Y"));
						if (ic != ClipperManager.getInstance().getThing())
							rule.addAtomToBody(cqFormater.getUnaryPredicate(ic) + "(Y)");
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
		for (ClipperPropertyAssertionAxiom a : roleAssertionAxioms) {

			int ir = a.getRole();
			int ind1 = a.getIndividual1();
			int ind2 = a.getIndividual2();
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(cqFormater.getBinaryPredicate(ir) + "(" + cqFormater.getConstant(ind1) + ","
						+ cqFormater.getConstant(ind2) + ").");
			}
			program.println(cqFormater.getBinaryPredicate(ir) + "(" + cqFormater.getConstant(ind1) + ","
					+ cqFormater.getConstant(ind2) + ").");

		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Class assertions: ");
		for (ClipperConceptAssertionAxiom ca : conceptAssertionAxioms) {
			int ic = ca.getConcept();
			int iInd = ca.getIndividual();
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(cqFormater.getUnaryPredicate(ic) + "(" + cqFormater.getConstant(iInd) + ").");
			}
			program.println(cqFormater.getUnaryPredicate(ic) + "(" + cqFormater.getConstant(iInd) + ").");

		}
	}

	/**
	 * @return DATALOG program that contains ABox completion rules and
	 *         Assertions in ABox
	 * */
	public void saveEncodedDataLogProgram(String generatedDataLogFile) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

			// ruleForBottomConcept(program);
			rulesFromImps(program);
			rulesFromValueRestrictions(program);
			rulesFromRoleInclusions(program);
			rulesFromInverseRoleAxioms(program);
			rulesFromNumberRestrictions(program);
			rulesFromNumberRestrictionAndEnfs(program);
			rulesFromTransitiveAxioms(program);
			rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void rulesFromTransitiveAxioms(PrintStream program) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%Rules From Transitive Assertions");
		}
		for (ClipperTransitivityAxiom a : transAxioms) {

			int ir = a.getRole();

			Rule rule = new Rule();
			rule.setHead(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Z"));
			rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "X", "Y"));
			rule.addAtomToBody(cqFormater.getBinaryAtomWithoutInverse(ir, "Y", "Z"));
			
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(rule);
			}
			
			program.println(rule);

		}

	}

	/**
	 * @return DATALOG program contain only ABox assertions
	 * */
	public void getABoxAssertionsDatalogProgram(String generatedDataLogFile) {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));
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
			System.out.println("============== Encoded DATALOG PROGRAM :================ ");
		}
		try {
			PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

			// ruleForBottomConcept(program);
			rulesFromImps(program);
			rulesFromValueRestrictions(program);
			rulesFromRoleInclusions(program);
			rulesFromInverseRoleAxioms(program);
			rulesFromNumberRestrictions(program);
			rulesFromNumberRestrictionAndEnfs(program);
			// rulesFromABoxAssertions(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// private String getConstant(int value) {
	// switch (ClipperManager.getInstance().getNamingStrategy()) {
	// case LowerCaseFragment:
	// IRI iri = ClipperManager.getInstance().getOwlIndividualEncoder()
	// .getSymbolByValue(value).asOWLNamedIndividual().getIRI();
	// return "\"" + normalize(iri) + "\"";
	// case IntEncoding:
	// return "d" + value;
	// }
	// throw new IllegalStateException("Not possible");
	// }
	//
	// private String getBinaryPredicate(int value) {
	// switch (ClipperManager.getInstance().getNamingStrategy()) {
	// case LowerCaseFragment:
	// IRI iri = ClipperManager.getInstance()
	// .getOwlObjectPropertyExpressionEncoder()
	// .getSymbolByValue(value).asOWLObjectProperty().getIRI();
	// return normalize(iri);
	// case IntEncoding:
	// return "r" + value;
	// }
	// throw new IllegalStateException("Not possible");
	// }
	//
	// private String getUnaryPredicate(int value) {
	// switch (ClipperManager.getInstance().getNamingStrategy()) {
	// case LowerCaseFragment:
	// IRI iri = ClipperManager.getInstance().getOwlClassEncoder()
	// .getSymbolByValue(value).getIRI();
	//
	// return normalize(iri);
	// case IntEncoding:
	// return "c" + value;
	// }
	// throw new IllegalStateException("Not possible");
	// }
	//
	// private String normalize(IRI iri) {
	// String fragment = iri.getFragment();
	// if (fragment != null) {
	// return fragment.replaceAll("[_-]", "").toLowerCase();
	// } else {
	// final String iriString = iri.toString();
	// int i = iriString.lastIndexOf('/') + 1;
	// return iriString.substring(i).replace("_-", "").toLowerCase();
	//
	// }
	//
	// }
}
