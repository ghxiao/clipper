package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubAllAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubMaxOneAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperConceptAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperInversePropertyOfAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperPropertyAssertionAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperTransitivityAxiom;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * This class is used to create datalog program from the representation of KB
 */
public class ReductionToDatalog {

    private static final SymbolEncoder<OWLClass> owlClassEncoder = ClipperManager.getInstance().getOwlClassEncoder();

    private static final SymbolEncoder<OWLPropertyExpression> owlPropertyExpressionEncoder
            = ClipperManager.getInstance().getOwlPropertyExpressionEncoder();

    private static final Variable varX = new Variable("X");
    private static final Variable varY = new Variable("Y");
    private static final Variable varZ = new Variable("Z");

    private Collection<HornImplication> coreImps;
    private Collection<EnforcedRelation> coreEnfs;
    private List<ClipperAtomSubAllAxiom> allValuesFromAxioms;
    private List<ClipperSubPropertyAxiom> subObjectPropertyAxioms;
    private List<ClipperInversePropertyOfAxiom> inverseObjectPropertyAxioms;
    private List<ClipperAtomSubMaxOneAxiom> maxOneCardinalityAxioms;
    private List<ClipperConceptAssertionAxiom> conceptAssertionAxioms;
    private List<ClipperPropertyAssertionAxiom> roleAssertionAxioms;
    private List<ClipperTransitivityAxiom> transAxioms;

    protected static final int THING = 0;

    CQFormatter cqFormatter;

    public ReductionToDatalog(CQFormatter cqFormatter) {
        this.cqFormatter = cqFormatter;
    }

    public ReductionToDatalog(ClipperHornSHIQOntology ontology, CQFormatter cqFormatter) {
        this.cqFormatter = cqFormatter;

        // initializing allValuesFromAxioms
        allValuesFromAxioms = ontology.getAtomSubAllAxioms();

        // initializing maxOneCardinalityAxioms;
        maxOneCardinalityAxioms = ontology.getAtomSubMaxOneAxioms();

        // initializing subObjectPropertyAxioms
        subObjectPropertyAxioms = ontology.getSubPropertyAxioms();

        // initializing private List<InversePropertyOfAxiom> inverseRoleAxioms;
        inverseObjectPropertyAxioms = ontology.getInversePropertyOfAxioms();

        conceptAssertionAxioms = ontology.getConceptAssertionAxioms();

        roleAssertionAxioms = ontology.getPropertyAssertionAxioms();

        subObjectPropertyAxioms = ontology.getSubPropertyAxioms();

        transAxioms = ontology.getTransitivityAxioms();
    }

    private static DLPredicate getClassPredicate(int index) {
        return new DLPredicate(owlClassEncoder.getSymbolByValue(index));
    }

    private static DLPredicate getPropertyPredicate(int index) {
        // v % 2 == 0
        return new DLPredicate((OWLEntity) owlPropertyExpressionEncoder.getSymbolByValue(index));
    }

    // no inverse
    private static Atom getBinaryAtom(int index, Variable var1, Variable var2) {
        if (index % 2 == 0) {
            return new Atom(getPropertyPredicate(index), var1, var2);
        } else {
            return new Atom(getPropertyPredicate(index - 1), var2, var1);
        }
    }

    private Set<String> getEncodedBodyOfImp(TIntHashSet body) {
        Set<String> strBody = new HashSet<>();
        TIntIterator iterator = body.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            if (index != THING) {
                strBody.add(cqFormatter.getUnaryPredicate(index) + "(X)");

            }
        }
        return strBody;
    }

    private static List<Atom> getClassAtoms(TIntHashSet body, Variable var) {
        List<Atom> atoms = new ArrayList<>();
        TIntIterator iterator = body.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            if (index != THING) {
                atoms.add(new Atom(getClassPredicate(index), var));
            }
        }
        return atoms;
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
            rule.setHead(cqFormatter.getUnaryPredicate(imp.getHead()) + "(X)");
            rule.setBody(getEncodedBodyOfImp(imp.getBody()));
            if (rule.isNotTrivial()) {
                if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                    System.out.println(rule);
                }
                program.println(rule);
            }
        }
    }

    public List<CQ> rulesFromImps() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%==========rules From Imps=============");
        }

        List<CQ> result = new ArrayList<>();

        for (HornImplication imp : coreImps) {

            if (existentialInTheBodby(imp)) {
                continue;
            }

            int impHead = imp.getHead();
            DLPredicate headPredicate = getClassPredicate(impHead);
            Atom headAtom = new Atom(headPredicate, varX);
            List<Atom> bodyAtoms = getClassAtoms(imp.getBody(), varX);
            CQ cq = new CQ(headAtom, bodyAtoms);

            if (cq.isNotTrivial()) {
                result.add(cq);
            }
        }

        return result;
    }

    private boolean existentialInTheBodby(HornImplication imp) {
        TIntHashSet body = imp.getBody();
        TIntIterator iterator = body.iterator();
        while (iterator.hasNext()) {
            int p = iterator.next();
            String classIRI = ClipperManager.getInstance()
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

            rule.setHead(cqFormatter.getUnaryPredicate(ic) + "(Y)");
            if (ia != ClipperManager.getInstance().getThing())
                rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
            final String s;
            s = cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y");
            rule.addAtomToBody(s);
            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println(rule);
            }
            program.println(rule);
        }
    }

    // subclassOf( A, R only C)
    public List<CQ> rulesFromValueRestrictions() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%==========rules From Value Restrictions ============");
        }
        List<CQ> result = new ArrayList<>();
        for (ClipperAtomSubAllAxiom axiom : allValuesFromAxioms) {

            int ic = axiom.getConcept2();
            int ir = axiom.getRole();
            int ia = axiom.getConcept1();

            Atom headAtom = new Atom(getClassPredicate(ic), varY);
            List<Atom> bodyAtoms = new ArrayList<>();
            if (ia != ClipperManager.getInstance().getThing()) {
                bodyAtoms.add(new Atom(getClassPredicate(ia), varX));
            }

            bodyAtoms.add(getBinaryAtom(ir, varX, varY));
            CQ cq = new CQ(headAtom, bodyAtoms);
            result.add(cq);
        }
        return result;
    }


    // SubRole (sub, super)
    public void rulesFromRoleInclusions(PrintStream program) {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%==========rules From Sub Roles Axioms =====");
        }
        for (ClipperSubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
            int sup = subRoleAxiom.getRole2();
            int sub = subRoleAxiom.getRole1();
            Rule rule = new Rule();
            if (!(sub % 2 == 1 && sup % 2 == 1)) {
                rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(sup, "X", "Y"));
                rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(sub, "X", "Y"));
                if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                    System.out.println(rule);
                }
                program.println(rule);
            }
        }
    }

    // SubRole (sub, super)
    public List<CQ> rulesFromRoleInclusions() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%==========rules From Sub Roles Axioms =====");
        }
        List<CQ> result = new ArrayList<>();

        for (ClipperSubPropertyAxiom subRoleAxiom : subObjectPropertyAxioms) {
            int sup = subRoleAxiom.getRole2();
            int sub = subRoleAxiom.getRole1();
            if (!(sub % 2 == 1 && sup % 2 == 1)) {
                Atom head = getBinaryAtom(sup, varX, varY);
                Atom body = getBinaryAtom(sub, varX, varY);
                CQ cq = new CQ(head, body);
                result.add(cq);
            }
        }

        return result;
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
            rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(r1, "X", "Y"));
            rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(r2, "Y", "X"));
            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println(rule);
            }
            program.println(rule);
            rule.clear();
            rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(r2, "Y", "X"));
            rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(r1, "X", "Y"));
            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println(rule);
            }
            program.println(rule);
        }
    }

    // InverseRole(r1, r2)
    public List<CQ> rulesFromInverseRoleAxioms() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%==========rules From inverse role axioms===================");
        }

        List<CQ> result = new ArrayList<>();
        for (ClipperInversePropertyOfAxiom ax : inverseObjectPropertyAxioms) {
            CQ cq;

            int r1 = ax.getRole1();
            int r2 = ax.getRole2();

            cq = new CQ(getBinaryAtom(r1, varX, varY), getBinaryAtom(r2, varY, varX));
            result.add(cq);

            cq = new CQ(getBinaryAtom(r2, varY, varX), getBinaryAtom(r1, varX, varY));
            result.add(cq);

        }
        return result;
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
                constraint.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
            if (ic != THING) {
                constraint.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Y)");
                constraint.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Z)");
            }
            constraint.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
            constraint.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Z"));
            constraint.addAtomToBody("Y !=Z");
            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println(constraint);
            }
            program.println(constraint);
        }
    }

    // SubclassOf( A, R max 1 C)
    // TODO: handle empty head and `Y!=Z`
    /*public List<CQ> rulesFromNumberRestrictions() {
		if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
			System.out.println("%==========rules From NumberRestrictions ===================");
		}
		for (ClipperAtomSubMaxOneAxiom subClassAxiom : maxOneCardinalityAxioms) {
			int ia = subClassAxiom.getConcept1();
			int ir = subClassAxiom.getRole();
			int ic = subClassAxiom.getConcept2();
			Constraint constraint = new Constraint();
			List<Atom> bodyAtoms = new ArrayList<>();
			if (ia != THING) {
				//constraint.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");

				bodyAtoms.add(new Atom(getClassPredicate(ia), varX));
			}

			if (ic != THING) {
				bodyAtoms.add(new Atom(getClassPredicate(ic), varY));
				bodyAtoms.add(new Atom(getClassPredicate(ic), varZ));
			}

			bodyAtoms.add(getBinaryAtom(ir, varX, varY));
			bodyAtoms.add(getBinaryAtom(ir, varX, varZ));

			constraint.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
			constraint.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Z"));
			constraint.addAtomToBody("Y !=Z");
			if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
				System.out.println(constraint);
			}
			program.println(constraint);
		}
	}*/


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
                        rule.setHead(cqFormatter.getUnaryPredicate(index) + "(Y)");
                        rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
                        if (ia != ClipperManager.getInstance().getThing())
                            rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
                        rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
                        if (ic != ClipperManager.getInstance().getThing())
                            rule.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Y)");
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
                        rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(index2, "X", "Y"));
                        rule.setBody(getEncodedBodyOfImp(enfRelation.getType1()));
                        if (ia != ClipperManager.getInstance().getThing())
                            rule.addAtomToBody(cqFormatter.getUnaryPredicate(ia) + "(X)");
                        rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
                        if (ic != ClipperManager.getInstance().getThing())
                            rule.addAtomToBody(cqFormatter.getUnaryPredicate(ic) + "(Y)");
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


    // TODO
    //	public void rulesFromNumberRestrictionAndEnfs() {// 1
    //	}

    public void rulesFromABoxAssertions(PrintStream program) {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("% Facts From ABox Assertions");
            System.out.println("% (Skipped for saving spaces) ");
        }
        for (ClipperPropertyAssertionAxiom a : roleAssertionAxioms) {

            int ir = a.getRole();
            int ind1 = a.getIndividual1();
            int ind2 = a.getIndividual2();
//			if (ClipperManager.getInstance().getVerboseLevel() >= 4) {
//				System.out.println(cqFormatter.getBinaryPredicate(ir) + "(" + cqFormatter.getConstant(ind1) + ","
//						+ cqFormatter.getConstant(ind2) + ").");
//			}
            program.println(cqFormatter.getBinaryPredicate(ir) + "(" + cqFormatter.getConstant(ind1) + ","
                    + cqFormatter.getConstant(ind2) + ").");

        }
        for (ClipperConceptAssertionAxiom ca : conceptAssertionAxioms) {
            int ic = ca.getConcept();
            int iInd = ca.getIndividual();
//			if (ClipperManager.getInstance().getVerboseLevel() >= 4) {
//				System.out.println(cqFormatter.getUnaryPredicate(ic) + "(" + cqFormatter.getConstant(iInd) + ").");
//			}
            program.println(cqFormatter.getUnaryPredicate(ic) + "(" + cqFormatter.getConstant(iInd) + ").");

        }
    }

    /**
     * @return DATALOG program that contains ABox completion rules and
     * Assertions in ABox
     */
    public void saveEncodedDataLogProgram(String generatedDataLogFile) {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("============== Encoded DATALOG PROGRAM :================ ");
        }
        try {
            PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));

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
            rule.setHead(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Z"));
            rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "X", "Y"));
            rule.addAtomToBody(cqFormatter.getBinaryAtomWithoutInverse(ir, "Y", "Z"));

            if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
                System.out.println(rule);
            }

            program.println(rule);

        }

    }

    private List<CQ> rulesFromTransitiveAxioms() {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("%Rules From Transitive Assertions");
        }

        List<CQ> result = new ArrayList<>();

        for (ClipperTransitivityAxiom a : transAxioms) {

            int ir = a.getRole();

            Atom head = getBinaryAtom(ir, varX, varZ);
            List<Atom> body = Arrays.asList(getBinaryAtom(ir, varX, varY), getBinaryAtom(ir, varY, varZ));
            result.add(new CQ(head, body));

        }

        return result;

    }

    /**
     * generates DATALOG program contain only ABox assertions
     */
    public void getABoxAssertionsDatalogProgram(String generatedDataLogFile) {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("============== Encoded DATALOG PROGRAM :================ ");
        }
        try {
            PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));

            rulesFromABoxAssertions(program);
            program.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * generates DATALOG program contains only Completion rules.
     */
    public void getCompletionRulesDatalogProgram(String generatedDataLogFile) {
        if (ClipperManager.getInstance().getVerboseLevel() >= 2) {
            System.out.println("============== Encoded DATALOG PROGRAM :================ ");
        }
        try {
            PrintStream program = new PrintStream(new FileOutputStream(generatedDataLogFile));

            // ruleForBottomConcept(program);
            rulesFromImps(program);
            rulesFromValueRestrictions(program);
            rulesFromRoleInclusions(program);
            rulesFromInverseRoleAxioms(program);
            rulesFromNumberRestrictions(program);
            rulesFromNumberRestrictionAndEnfs(program);
            program.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<CQ> getCompletionRulesDatalogProgram() {
        List<CQ> program = new ArrayList<>();
        // ruleForBottomConcept(program);
        program.addAll(rulesFromImps());
        program.addAll(rulesFromValueRestrictions());
        program.addAll(rulesFromRoleInclusions());
        program.addAll(rulesFromInverseRoleAxioms());
        program.addAll(rulesFromTransitiveAxioms());
        // not implemented TODO
        // program.addAll(rulesFromNumberRestrictions());
        // not implemented TODO
        // program.addAll(rulesFromNumberRestrictionAndEnfs());
        return program;
    }
}
