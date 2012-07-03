package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.iterator.TIntIterator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
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
import org.semanticweb.clipper.util.BitSetUtil;
import org.semanticweb.clipper.util.PrintingUtilities;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;


/*
 * This class is used to create datalog program from the representation of KB
 */
@Deprecated
public class ReductionToDatalog {

	private String datalogFileName;
	private Set<ImplicationRelation> coreImps;
	private Set<ImplicationRelation> coreImpsForDataLog;
	private Set<EnforcedRelation> coreEnfs;
	private List<AtomSubAllAxiom> allValuesFromAxioms;
	private List<AtomSubMaxOneAxiom> maxOneCardinalityAxioms;
	private List<SubPropertyAxiom> subObjectPropertyAxioms;
	private Set<ReachBottom> reachBottoms;
	private List<InversePropertyOfAxiom> inverseRoleAxioms;
	protected int nothing = 1;
	protected int thing = 0;
	protected int toProperty = 0;
	protected int bottomProperty = 2;

	private List<ConceptAssertionAxiom> conceptAssertionAxioms;

	private List<ObjectPropertyAssertionAxiom> roleAssertionAxioms;

	private List<SubPropertyAxiom> subPropertyAxioms;

	private List<InversePropertyOfAxiom> inversePropertyOfAxioms;

	/*
	 * Constructor
	 * 
	 * @param: input Ontology
	 * 
	 * @param: Name of generated datalog file
	 */
	public ReductionToDatalog(NormalHornALCHIQOntology ont_bs) {

		this.coreImps = new HashSet<ImplicationRelation>();
		this.coreEnfs = new HashSet<EnforcedRelation>();
		// initializing allValuesFromAxioms
		allValuesFromAxioms = new ArrayList<AtomSubAllAxiom>();
		allValuesFromAxioms = ont_bs.getAtomSubAllAxioms();

		// initializing maxOneCardinalityAxioms;
		maxOneCardinalityAxioms = new ArrayList<AtomSubMaxOneAxiom>();
		maxOneCardinalityAxioms = ont_bs.getAtomSubMaxOneAxioms();

		// initializing subObjectPropertyAxioms
		subObjectPropertyAxioms = new ArrayList<SubPropertyAxiom>();
		subObjectPropertyAxioms = ont_bs.getSubPropertyAxioms();

		// initializing private List<InversePropertyOfAxiom> inverseRoleAxioms;
		inverseRoleAxioms = new ArrayList<InversePropertyOfAxiom>();
		inverseRoleAxioms = ont_bs.getInversePropertyOfAxioms();

		this.reachBottoms = new HashSet<ReachBottom>();

		conceptAssertionAxioms = ont_bs.getConceptAssertionAxioms();

		roleAssertionAxioms = ont_bs.getRoleAssertionAxioms();

		subPropertyAxioms = ont_bs.getSubPropertyAxioms();

		inversePropertyOfAxioms = ont_bs.getInversePropertyOfAxioms();

	}

	public List<AtomSubAllAxiom> getAllValuesFromAxioms() {
		return allValuesFromAxioms;
	}

	public void setAllValuesFromAxioms(List<AtomSubAllAxiom> allValuesFromAxioms) {
		this.allValuesFromAxioms = allValuesFromAxioms;
	}

	public Set<ReachBottom> getReachBottoms() {
		return reachBottoms;
	}

	public void setReachBottoms(Set<ReachBottom> reachBottoms) {
		this.reachBottoms = reachBottoms;
	}

	public void setCoreImps(Set<ImplicationRelation> coreImps) {
		this.coreImps = coreImps;
	}

	public void setCoreEnfs(Set<EnforcedRelation> coreEnfs) {
		this.coreEnfs = coreEnfs;
	}

	public void ruleR1(PrintStream program) {
		Constraint cons = new Constraint();
		// PrintingUtilities printer = new PrintingUtilities();
		// printer.printReachBottom(reachBottoms);

		for (ReachBottom rb : reachBottoms) {
			cons.clear();
			for (int index = rb.getType().nextSetBit(0); index >= 0; index = rb
					.getType().nextSetBit(index + 1)) {
				OWLClass owlClass = ClipperManager.getInstance()
						.getOwlClassEncoder().getSymbolByValue(index);
				cons.addAtomToBody(owlClass.getIRI().getFragment() + "(X)");
			}
			System.out.println(cons);
			program.println(cons);
		}

	}

	public void ruleR1BitSet(PrintStream program) {
		Constraint cons = new Constraint();
		// PrintingUtilities printer = new PrintingUtilities();
		// printer.printReachBottom(reachBottoms);

		for (ReachBottom rb : reachBottoms) {
			cons.clear();
			for (int index = rb.getType().nextSetBit(0); index >= 0; index = rb
					.getType().nextSetBit(index + 1)) {
				cons.addAtomToBody("c" + index + "(X)");
			}
			System.out.println(cons);
			program.println(cons);
		}

	}

	/*
	 * each imp(T1,T2) is reduced to the following rules: C1(X):-
	 * B1(X),B2(X),...,Bn(X). C2(X):- B1(X),B2(X),...,Bn(X). ... Cm(X):-
	 * B1(X),B2(X),...,Bn(X). Where T2={C1,...Cm} T1={B1,B2,...Bn}
	 */
	public void ruleR2(PrintStream program) {
		Rule rule = new Rule();
		// for (ImplicationRelation imp:coreImp){
		for (ImplicationRelation imp : coreImps) {
			if (!reachBottoms.contains(new ReachBottom(imp.getType2()))) {

				for (int index = imp.getType2().nextSetBit(0); index >= 0; index = imp
						.getType2().nextSetBit(index + 1)) {
					OWLClass owlClass = ClipperManager.getInstance()
							.getOwlClassEncoder().getSymbolByValue(index);
					rule.clear();
					rule.setHead(owlClass.getIRI().getFragment() + "(X)");
					for (int index1 = imp.getType1().nextSetBit(0); index1 >= 0; index1 = imp
							.getType1().nextSetBit(index1 + 1)) {
						OWLClass owlClass1 = ClipperManager.getInstance()
								.getOwlClassEncoder().getSymbolByValue(index1);
						if (!owlClass1.isOWLThing())
							rule.addAtomToBody(owlClass1.getIRI().getFragment()
									+ "(X)");
					}
					if (rule.isNotTrivial()) {
						System.out.println(rule);
						program.println(rule);
					}

				}

			}
		}
	}

	public void ruleR2BitSet(PrintStream program) {
		Rule rule = new Rule();
		// for (ImplicationRelation imp:coreImp){
		for (ImplicationRelation imp : coreImps) {
			if (!reachBottoms.contains(new ReachBottom(imp.getType2()))) {

				for (int index = imp.getType2().nextSetBit(0); index >= 0; index = imp
						.getType2().nextSetBit(index + 1)) {
					OWLClass owlClass = ClipperManager.getInstance()
							.getOwlClassEncoder().getSymbolByValue(index);
					rule.clear();
					rule.setHead("c" + index + "(X)");
					for (int index1 = imp.getType1().nextSetBit(0); index1 >= 0; index1 = imp
							.getType1().nextSetBit(index1 + 1)) {
						OWLClass owlClass1 = ClipperManager.getInstance()
								.getOwlClassEncoder().getSymbolByValue(index1);
						if (index1 != thing)
							rule.addAtomToBody("c" + index1 + "(X)");
					}
					if (rule.isNotTrivial()) {
						System.out.println(rule);
						program.println(rule);
					}

				}

			}
		}
	}

	/*
	 * Adding rule triggered by : A \sqsubset VR.C Rule: C(Y) :- A(X), R(X,Y)
	 */
	public void ruleR3(PrintStream program) {

		Rule rule = new Rule();
		for (AtomSubAllAxiom axiom : allValuesFromAxioms) {
			rule.clear();
			int ic = axiom.getConcept2();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);

			int ir = axiom.getRole();
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			int ia = axiom.getConcept1();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);

			rule.setHead(c.getIRI().getFragment() + "(Y)");

			rule.addAtomToBody(a.getIRI().getFragment() + "(X)");
			rule.addAtomToBody(r.getIRI().getFragment() + "(X,Y)");

			System.out.println(rule);
			program.println(rule);

		}
	}

	public void ruleR3BitSet(PrintStream program) {

		Rule rule = new Rule();
		for (AtomSubAllAxiom axiom : allValuesFromAxioms) {
			rule.clear();
			int ic = axiom.getConcept2();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);

			int ir = axiom.getRole();
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			int ia = axiom.getConcept1();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);

			rule.setHead("c" + ic + "(Y)");

			rule.addAtomToBody("c" + ia + "(X)");
			rule.addAtomToBody("r" + ir + "(X,Y)");

			System.out.println(rule);
			program.println(rule);

		}
	}

	/*
	 * Adding rule triggered by: r \subseteq s Rule: s(X,Y):- r(X,Y).
	 */
	public void ruleR4(PrintStream program) {

		for (SubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int sup = subRoleAxiom.getRole2();
			OWLObjectProperty superRole = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(sup).asOWLObjectProperty();

			int sub = subRoleAxiom.getRole1();
			OWLObjectProperty subRole = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(sub).asOWLObjectProperty();

			// if(!superRole.getIRI().getFragment().contains("_inv_fresh_") &&
			// !subRole.getIRI().getFragment().contains("_inv_fresh_") ){
			String sRole = new String();
			sRole = sRole + superRole.getIRI().getFragment() + "(X,Y):-";
			sRole = sRole + subRole.getIRI().getFragment() + "(X,Y).";
			System.out.println(sRole);
			program.println(sRole);
		}
	}

	public void ruleR4BitSet(PrintStream program) {

		for (SubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
			int sup = subRoleAxiom.getRole2();
			OWLObjectProperty superRole = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(sup).asOWLObjectProperty();

			int sub = subRoleAxiom.getRole1();
			OWLObjectProperty subRole = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(sub).asOWLObjectProperty();

			// if(!superRole.getIRI().getFragment().contains("_inv_fresh_") &&
			// !subRole.getIRI().getFragment().contains("_inv_fresh_") ){
			String sRole = new String();
			sRole = sRole + "r" + sup + "(X,Y):-";
			sRole = sRole + "r" + sub + "(X,Y).";
			System.out.println(sRole);
			program.println(sRole);
		}
	}

	public void ruleR5(PrintStream program) {
		Rule rule = new Rule();
		for (InversePropertyOfAxiom ax : inverseRoleAxioms) {
			rule.clear();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();

			OWLObjectProperty owlR1 = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(r1).asOWLObjectProperty();
			OWLObjectProperty owlR2 = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(r2).asOWLObjectProperty();

			rule.setHead(owlR1.getIRI().getFragment() + "(X,Y)");
			rule.addAtomToBody(owlR2.getIRI().getFragment() + "(Y,X)");
			System.out.println(rule);
			program.println(rule);

			rule.clear();
			rule.setHead(owlR2.getIRI().getFragment() + "(Y,X)");
			rule.addAtomToBody(owlR1.getIRI().getFragment() + "(X,Y)");
			System.out.println(rule);
			program.println(rule);

		}
	}

	public void ruleR5BitSet(PrintStream program) {
		Rule rule = new Rule();
		for (InversePropertyOfAxiom ax : inverseRoleAxioms) {
			rule.clear();
			int r1 = ax.getRole1();
			int r2 = ax.getRole2();

			OWLObjectProperty owlR1 = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(r1).asOWLObjectProperty();
			OWLObjectProperty owlR2 = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(r2).asOWLObjectProperty();

			rule.setHead("r" + r1 + "(X,Y)");
			rule.addAtomToBody("r" + r2 + "(Y,X)");
			System.out.println(rule);
			program.println(rule);

			rule.clear();
			rule.setHead("r" + r2 + "(Y,X)");
			rule.addAtomToBody("r" + r1 + "(X,Y)");
			System.out.println(rule);
			program.println(rule);

		}
	}

	/* 
	 * */
	public void ruleR6(PrintStream program) {
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);
			Constraint constraint = new Constraint();
			if (!a.isOWLThing())
				constraint.addAtomToBody(a.getIRI().getFragment() + "(X)");
			if (!c.isOWLThing()) {
				constraint.addAtomToBody(c.getIRI().getFragment() + "(Y)");
				constraint.addAtomToBody(c.getIRI().getFragment() + "(Z)");
			}
			constraint.addAtomToBody(r.getIRI().getFragment() + "(X,Y)");
			constraint.addAtomToBody(r.getIRI().getFragment() + "(X,Z)");
			constraint.addAtomToBody("Y !=Z");

			System.out.println(constraint);
			program.println(constraint);

		}
	}

	public void ruleR6BitSet(PrintStream program) {
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);
			Constraint constraint = new Constraint();
			if (ia != thing)
				constraint.addAtomToBody("c" + ia + "(X)");
			if (ic != thing) {
				constraint.addAtomToBody("c" + ic + "(Y)");
				constraint.addAtomToBody("c" + ic + "(Z)");
			}
			constraint.addAtomToBody("r" + ir + "(X,Y)");
			constraint.addAtomToBody("r" + ir + "(X,Z)");
			constraint.addAtomToBody("Y !=Z");

			System.out.println(constraint);
			program.println(constraint);

		}
	}

	/*
	 * Adding rule triggered by: A \sqsubseteq <= 1R.C, enf(T1,R,T2), r \in R, C
	 * * \in T2. Rule: r1(X,Y),r2(X,Y),....rk(X,Y),C1(Y),...,Cm(Y) :-
	 * A(x),B1(X),...Bn(X),r(x,Y),C(Y).
	 */
	public void ruleR7(PrintStream program) {
		BitSetUtil bu = new BitSetUtil();
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);

			for (EnforcedRelation enfRelation : coreEnfs) {
				if (enfRelation.getRoles().contains(ir)
						&& (enfRelation.getRoles().size() > 1 || enfRelation
								.getType2().size() > 1)
						&& enfRelation.getType2().contains(ic)) {
					System.out
							.println("%====These following rules are from:====");
					System.out.println("%   " + subClassAxiom);
					System.out.println("%   " + enfRelation);
					Rule rule = new Rule();
					// =========Each element of Type2 is a head of a rule ======
					TIntIterator iterator = enfRelation.getType2().iterator();
					while (iterator.hasNext()) {
						// for (int index =
						// enfRelation.getType2().nextSetBit(0); index >= 0;
						// index = enfRelation
						// .getType2().nextSetBit(index + 1)) {
						int index = iterator.next();
						OWLClass owlClass = ClipperManager.getInstance()
								.getOwlClassEncoder().getSymbolByValue(index);
						// create head of the rule. Each element of Type2 is a
						// head.
						rule.setHead(owlClass.getIRI().getFragment() + "(Y)");

						// create body of the rule
						TIntIterator iterator1 = enfRelation.getType1()
								.iterator();
						while (iterator.hasNext()) {
							// for (int index1 =
							// enfRelation.getType1().nextSetBit(0); index1 >=
							// 0; index1 = enfRelation
							// .getType1().nextSetBit(index1 + 1)) {
							int index1 = iterator1.next();
							OWLClass owlClass1 = ClipperManager.getInstance()
									.getOwlClassEncoder()
									.getSymbolByValue(index1);
							rule.addAtomToBody(owlClass1.getIRI().getFragment()
									+ "(X)");
						}
						rule.addAtomToBody(a.getIRI().getFragment() + "(X)");
						rule.addAtomToBody(r.getIRI().getFragment() + "(X,Y)");
						rule.addAtomToBody(c.getIRI().getFragment() + "(Y)");
						// Rule only makes sense if the read is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							System.out.println(rule);
							program.println(rule);
						}
					}
					// =======Each role in enf.roles is a head of the rule
					// =======

					TIntIterator iterator2 = enfRelation.getRoles().iterator();
					while (iterator.hasNext()) {
						// for (int index2 =
						// enfRelation.getRoles().nextSetBit(0); index2 >= 0;
						// index2 = enfRelation
						// .getRoles().nextSetBit(index2 + 1)) {
						int index2 = iterator.next();
						rule.clear();
						OWLObjectProperty ri = ClipperManager.getInstance()
								.getOwlObjectPropertyExpressionEncoder()
								.getSymbolByValue(index2).asOWLObjectProperty();
						rule.setHead(ri.getIRI().getFragment() + "(X,Y)");

						TIntIterator iterator1 = enfRelation.getType1()
								.iterator();
						while (iterator.hasNext()) {
							// for (int index1 =
							// enfRelation.getType1().nextSetBit(0); index1 >=
							// 0; index1 = enfRelation
							// .getType1().nextSetBit(index1 + 1)) {
							int index1 = iterator1.next();
							OWLClass owlClass1 = ClipperManager.getInstance()
									.getOwlClassEncoder()
									.getSymbolByValue(index1);
							rule.addAtomToBody(owlClass1.getIRI().getFragment()
									+ "(X)");
						}
						rule.addAtomToBody(a.getIRI().getFragment() + "(X)");
						rule.addAtomToBody(r.getIRI().getFragment() + "(X,Y)");
						rule.addAtomToBody(c.getIRI().getFragment() + "(Y)");
						// Rule only makes sense if its Head is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							System.out.println(rule);
							program.println(rule);
						}
					}
					System.out.println("%=============================");
				}
			}
		}
	}

	public void ruleR7BitSet(PrintStream program) {
		BitSetUtil bu = new BitSetUtil();
		for (AtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			OWLClass a = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ia);
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);

			for (EnforcedRelation enfRelation : coreEnfs) {
				// if (bu.isSubset(ir, enfRelation.getRoles())
				// && (enfRelation.getRoles().cardinality() > 1 || enfRelation
				// .getType2().cardinality() > 1)
				// && bu.isSubset(ic, enfRelation.getType2())) {
				if (enfRelation.getRoles().contains(ir)
						&& (enfRelation.getRoles().size() > 1 || enfRelation
								.getType2().size() > 1)
						&& enfRelation.getType2().contains(ic)) {
					System.out
							.println("%====These following rules are from:====");
					System.out.println("%   " + subClassAxiom);
					System.out.println("%   " + enfRelation);
					Rule rule = new Rule();
					// =========Each element of Type2 is a head of a rule ======

					// for (int index = enfRelation.getType2().nextSetBit(0);
					// index >= 0; index = enfRelation
					// .getType2().nextSetBit(index + 1)) {
					TIntIterator iterator = enfRelation.getType2().iterator();
					while (iterator.hasNext()) {
						int index = iterator.next();
						OWLClass owlClass = ClipperManager.getInstance()
								.getOwlClassEncoder().getSymbolByValue(index);
						// create head of the rule. Each element of Type2 is a
						// head.
						rule.setHead("c" + index + "(Y)");

						// create body of the rule
						// for (int index1 =
						// enfRelation.getType1().nextSetBit(0); index1 >= 0;
						// index1 = enfRelation
						// .getType1().nextSetBit(index1 + 1)) {
						TIntIterator iterator1 = enfRelation.getType1()
								.iterator();
						while (iterator1.hasNext()) {
							int index1 = iterator.next();
							rule.addAtomToBody("c" + index1 + "(X)");
						}
						rule.addAtomToBody("c" + ia + "(X)");
						rule.addAtomToBody("r" + ir + "(X,Y)");
						rule.addAtomToBody("c" + ic + "(Y)");
						// Rule only makes sense if the read is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							System.out.println(rule);
							program.println(rule);
						}
					}
					// =======Each role in enf.roles is a head of the rule
					// =======
					TIntIterator iterator2 = enfRelation.getRoles().iterator();
					// while(iterator.hasNext()){
					// System.out.println(iterator.next());
					// }

					// for (int index2 = enfRelation.getRoles().nextSetBit(0);
					// index2 >= 0; index2 = enfRelation
					// .getRoles().nextSetBit(index2 + 1)) {
					while (iterator2.hasNext()) {
						int index2 = iterator2.next();
						rule.clear();
						OWLObjectProperty ri = ClipperManager.getInstance()
								.getOwlObjectPropertyExpressionEncoder()
								.getSymbolByValue(index2).asOWLObjectProperty();
						rule.setHead("r" + index2 + "(X,Y)");

						TIntIterator iterator1 = enfRelation.getType1()
								.iterator();
						// for (int index1 =
						// enfRelation.getType1().nextSetBit(0); index1 >= 0;
						// index1 = enfRelation
						// .getType1().nextSetBit(index1 + 1)) {
						while (iterator1.hasNext()) {
							int index1 = iterator1.next();
							OWLClass owlClass1 = ClipperManager.getInstance()
									.getOwlClassEncoder()
									.getSymbolByValue(index1);
							rule.addAtomToBody("c" + index1 + "(X)");
						}
						rule.addAtomToBody("c" + ia + "(X)");
						rule.addAtomToBody("r" + ir + "(X,Y)");
						rule.addAtomToBody("c" + ic + "(Y)");
						// Rule only makes sense if its Head is not contained in
						// the body.
						if (rule.isNotTrivial()) {
							System.out.println(rule);
							program.println(rule);
						}
					}
					System.out.println("%=============================");
				}
			}
		}
	}

	public void ruleR8R9(PrintStream program) {
		for (ObjectPropertyAssertionAxiom a : roleAssertionAxioms) {

			int ir = a.getRole();
			int ind1 = a.getIndividual1();
			int ind2 = a.getIndividual2();

			OWLNamedIndividual subject = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(ind1)
					.asOWLNamedIndividual();
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();

			OWLNamedIndividual object = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(ind2)
					.asOWLNamedIndividual();

			System.out.println(r.getIRI().getFragment() + "(\""
					+ subject.getIRI() + "\", \"" + object.getIRI() + "\").");
			program.println(r.getIRI().getFragment() + "(\"" + subject.getIRI()
					+ "\", \"" + object.getIRI() + "\").");

		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Class assertions: ");
		for (ConceptAssertionAxiom ca : conceptAssertionAxioms) {
			int ic = ca.getConcept();
			int iInd = ca.getIndividual();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);
			OWLNamedIndividual ind = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(iInd)
					.asOWLNamedIndividual();
			// System.out.println(c.getIRI().getFragment() +
			// "("+ind.asOWLNamedIndividual().getIRI().getFragment()+").");

			System.out.println(c.getIRI().getFragment() + "(\""
					+ ind.asOWLNamedIndividual().getIRI() + "\").");
			program.println(c.getIRI().getFragment() + "(\""
					+ ind.asOWLNamedIndividual().getIRI() + "\").");
			// pro.println(c.getIRI().getFragment() +
			// "("+ind.asOWLNamedIndividual().getIRI().getFragment()+").");
		}
	}

	public void ruleR8R9BitSet(PrintStream program) {
		for (ObjectPropertyAssertionAxiom a : roleAssertionAxioms) {

			int ir = a.getRole();
			int ind1 = a.getIndividual1();
			int ind2 = a.getIndividual2();

			OWLNamedIndividual subject = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(ind1)
					.asOWLNamedIndividual();
			OWLObjectProperty r = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder()
					.getSymbolByValue(ir).asOWLObjectProperty();

			OWLNamedIndividual object = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(ind2)
					.asOWLNamedIndividual();

			System.out.println("r" + ir + "(" + ind1 + "," + ind2 + ").");
			program.println("r" + ir + "(" + ind1 + "," + ind2 + ").");
			// System.out.println(r.getIRI().getFragment() + "(\""
			// + subject.getIRI() + "\", \"" + object.getIRI() + "\").");
			// program.println(r.getIRI().getFragment() + "(\"" +
			// subject.getIRI()
			// + "\", \"" + object.getIRI() + "\").");

		}
		// System.out.println("======================================== ");
		// System.out.println("Facts from Class assertions: ");
		for (ConceptAssertionAxiom ca : conceptAssertionAxioms) {
			int ic = ca.getConcept();
			int iInd = ca.getIndividual();
			OWLClass c = ClipperManager.getInstance().getOwlClassEncoder()
					.getSymbolByValue(ic);
			OWLNamedIndividual ind = ClipperManager.getInstance()
					.getOwlIndividualEncoder().getSymbolByValue(iInd)
					.asOWLNamedIndividual();
			// System.out.println(c.getIRI().getFragment() +
			// "("+ind.asOWLNamedIndividual().getIRI().getFragment()+").");

			System.out.println("c" + ic + "(" + iInd + ").");
			program.println("c" + ic + "(" + iInd + ").");
			// System.out.println(c.getIRI().getFragment() + "(\""
			// + ind.asOWLNamedIndividual().getIRI() + "\").");
			// program.println(c.getIRI().getFragment() + "(\""
			// + ind.asOWLNamedIndividual().getIRI() + "\").");
			// pro.println(c.getIRI().getFragment() +
			// "("+ind.asOWLNamedIndividual().getIRI().getFragment()+").");
		}
	}

	public void getDataLogcProgram(String generatedDataLogFile) {
		System.out.println();
		System.out
				.println("==============  DATALOG PROGRAM :================ ");
		try {
			PrintStream program = new PrintStream(new FileOutputStream(
					generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

			ruleR1(program);
			ruleR2(program);
			ruleR3(program);
			ruleR4(program);
			ruleR5(program);
			ruleR6(program);
			ruleR7(program);
			ruleR8R9(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void getBitSetDataLogProgram(String generatedDataLogFile) {
		System.out.println();
		System.out
				.println("============== BitSet DATALOG PROGRAM :================ ");
		try {
			PrintStream program = new PrintStream(new FileOutputStream(
					generatedDataLogFile));
			// System.out.println("======================================== ");
			// System.out.println("Facts from Role assertions: ");

			ruleR1BitSet(program);
			ruleR2BitSet(program);
			ruleR3BitSet(program);
			ruleR4BitSet(program);
			ruleR5BitSet(program);
			ruleR6BitSet(program);
			ruleR7BitSet(program);
			ruleR8R9BitSet(program);
			program.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
