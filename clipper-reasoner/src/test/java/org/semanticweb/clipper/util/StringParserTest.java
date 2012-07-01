package org.semanticweb.clipper.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.Constant;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;


public class StringParserTest {

	@Test
	public void test01() {
		String s0 = "q0(X2) :- r6(X1,d1), c2(X2), c3(X1), c3(d1), r4(X2,X1), c5(X2).";
		String s00 = s0.replaceAll(" ", "");
		String s = s00.replaceAll("\\.", "");
		System.out.println(s);
		String delims = "[:-]+";
		String[] tokens = s.split(delims);
		String strHead = tokens[0];
		Atom head = getAtomFromAtomString(strHead);
//		System.out.println("head:  " + head);
		String strBody = tokens[1];
		System.out.println(strBody);
		String strBody1 = strBody.replaceAll("\\),", "\\);");
		delims = "[;]+";
		String[] tokensBody = strBody1.split(delims);
		// for (int i = 0; i < tokensBody.length; i++)
		// System.out.println(tokensBody[i]);
		// //
		for (String atomString : tokensBody)
			System.out.println(getAtomFromAtomString(atomString));
	}

	// input: c1, d2, q3, X4 r5 will return 1,2,3,4,5 respectively
	private int getCode(String string) {
		String code;
		String delims = "[cqdXr]+";
		String[] tokens = string.split(delims);
		// for (String s: tokens) System.out.println(s);
		code = tokens[1];
		return Integer.parseInt(code);
	}

	// //input: c1,d2,q3,X4 will return : c, d, q, X, respectively
	// private int getIndicater(String string){
	//
	// }
	private Atom createRoleAssertion(DLPredicate r, Term x1, Term x2) {
		List<Term> terms = new ArrayList<Term>();
		terms.add(x1);
		terms.add(x2);
		return new Atom(r, terms);
	}

	private Atom createConceptAssertion(DLPredicate c, Term x1) {
		List<Term> terms = new ArrayList<Term>();
		terms.add(x1);
		return new Atom(c, terms);
	}

	private Atom createHead(NonDLPredicate q, Term x1) {
		List<Term> terms = new ArrayList<Term>();
		terms.add(x1);
		return new Atom(q, terms);
	}

	private Atom getAtomFromAtomString(String atomString) {
		String s = atomString;
		String delims = "[\\(\\),]+";
		String[] tokens = s.split(delims);
		Atom atom = new Atom();
		// System.out.println(tokens.length);
		// if (tokens.length==1)
		// for (int i = 0; i < tokens.length; i++)
		// System.out.println(tokens[i]);
		Predicate predicate = null;
		if (tokens.length == 1) {
			String qString = tokens[0];
			predicate = new NonDLPredicate(getCode(qString), 0);
			atom = new Atom(predicate);
			// System.out.println(atom);
		}
		
		if (tokens.length == 2) {
			String qString = tokens[0];
			if (qString.contains("c"))
				predicate = new DLPredicate(getCode(qString), 1);
			else if (qString.contains("q"))
				predicate = new NonDLPredicate(getCode(qString), 1);

			String x1String = tokens[1];
			Term term = null;
			if (x1String.contains("X")) {
				term = new Variable(getCode(x1String));
			}
			if (x1String.contains("d")) {
				term = new Constant(getCode(x1String));
			}
			atom = new Atom(predicate, term);
			// System.out.println(atom);
		}

		if (tokens.length == 3) {
			String predicateString = tokens[0];
			predicate = new DLPredicate(getCode(predicateString), 2);
			String x1String = tokens[1];
			Term term1 = null;
			if (x1String.contains("X")) {
				term1 = new Variable(getCode(x1String));
			} else if (x1String.contains("d")) {
				term1 = new Constant(getCode(x1String));
			}

			String x2String = tokens[2];
			Term term2 = null;
			if (x2String.contains("X")) {
				term2 = new Variable(getCode(x2String));
			}
			if (x2String.contains("d")) {
				term2 = new Constant(getCode(x2String));
			}
			List<Term> terms = new ArrayList<Term>();
			terms.add(term1);
			terms.add(term2);
			atom = new Atom(predicate, terms);
			// System.out.println(atom);
		}
		return atom;
	}

}
