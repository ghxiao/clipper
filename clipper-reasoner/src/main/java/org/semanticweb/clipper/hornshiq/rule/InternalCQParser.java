package org.semanticweb.clipper.hornshiq.rule;

import com.google.common.collect.Lists;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * only for some internal test
 */
public class InternalCQParser {
	private CQ cq;
	private String queryString;
	private String prefix= null;

	public InternalCQParser() {
		cq = new CQ();
		queryString = new String();
	}

	public InternalCQParser(String queryString) {
		this.cq = new CQ();
		this.queryString = queryString;
	}

	public CQ getCq() {
		if(prefix==null)parseIntQuery();
		else parseOWLQuery();
		return cq;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setCq(CQ cq) {
		this.cq = cq;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String string) {
		this.queryString = string;
	}

	private void parseIntQuery() {
		// String s0 =
		// "q0(X2) :- r6(X1,d1), c2(X2), c3(X1), c3(d1), r4(X2,X1), c5(X2).";
		String s0 = this.queryString;
		String s1 = s0.replaceAll(" ", "");
		String s = s1.replaceAll("\\.", "");
		// System.out.println(s);
		String delims = "[:-]+";
		String[] tokens = s.split(delims);
		String strHead = tokens[0];
		Atom head = getHeadAtomFromString(strHead);
		this.cq.setHead(head);
		// System.out.println("head:  " + head);
		String strBody = tokens[1];
		// System.out.println(strBody);
		String strBody1 = strBody.replaceAll("\\),", "\\);");
		delims = "[;]+";
		String[] tokensBody = strBody1.split(delims);
		// for (int i = 0; i < tokensBody.length; i++)
		// System.out.println(tokensBody[i]);
		// //
		List<Atom> body = new ArrayList<>();
		for (String atomString : tokensBody)
			body.add(getAtomFromAtomString(atomString));
		this.cq.setBody(body);
	}

	// input: c1, d2, q3, X4 r5 will return 1,2,3,4,5 respectively
	private int getCode(String string) {
		String code;
		String delims = "[cqdXr]+";
		String[] tokens = string.split(delims);
		// for (String s: tokens) System.out.println(s);
		if (tokens.length > 1) {
			code = tokens[1];
			return Integer.parseInt(code);
		} else
			return -1;
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
	private Atom getHeadAtomFromString(String atomString) {
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
			predicate = new NonDLPredicate(getCode(predicateString), 2);
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

	// ========================================Parsing
	// OWLQuery===================================

	private int getEncodedIndividual(String indiString) {
		OWLIndividual individual = OWLManager.getOWLDataFactory()
				.getOWLNamedIndividual(IRI.create(this.prefix + indiString));
		int intIndividual = ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder()
				.getValueBySymbol(individual);
		return intIndividual;
	}

	private Predicate getEncodedOWLObjectProperty(String predicateString,
			int arity) {
		OWLObjectProperty owlPredicate = OWLManager
				.getOWLDataFactory()
				.getOWLObjectProperty(IRI.create(this.prefix + predicateString));
		int encodedPredicate = ClipperManager.getInstance()
				.getOwlPropertyExpressionEncoder()
				.getValueBySymbol(owlPredicate);
		Predicate predicate = new DLPredicate(encodedPredicate, arity);
		return predicate;
	}

	private Atom getBodyAtomFromString(String bodyAtomString) {
		Atom atom = null;
		String delims = "[\\(\\),]+";
		String s = bodyAtomString;
		String[] tokens = s.split(delims);
		for (String s1 : tokens)
			System.out.println(s1);

		if (tokens.length == 2) {
			String predicateString = tokens[0];
			Predicate predicate = getEncodedOWLObjectProperty(predicateString,
					1);
			String termString = tokens[1];
			String x1StringUperCase = termString.toUpperCase();
			Term term = null;
			if (termString.equals(x1StringUperCase)) {
				term = new Variable(getCode(termString));
			} else {
				int intIndividual = getEncodedIndividual(termString);
				term = new Constant(intIndividual);
			}
			atom = new Atom(predicate, term);

		}

		if (tokens.length == 3) {
			String predicateString = tokens[0];
			Predicate predicate = getEncodedOWLObjectProperty(predicateString,
					2);
			String term1String = tokens[1];
			String term1StringUpperCase = term1String.toUpperCase();
			Term term1 = null;
			if (term1String.equals(term1StringUpperCase)) {
				term1 = new Variable(getCode(term1String));
			} else {
				int intIndividual1 = getEncodedIndividual(term1String);
				term1 = new Constant(intIndividual1);
			}
			String term2String = tokens[2];
			String term2UperCase = term2String.toUpperCase();
			Term term2 = null;
			if (term2String.equals(term2UperCase)) {
				term2 = new Variable(getCode(term2String));
			} else {
				int intIndividual2 = getEncodedIndividual(term2String);
				term2 = new Constant(intIndividual2);
			}
			List<Term> terms = new ArrayList<Term>();
			terms.add(term1);
			terms.add(term2);
			atom = new Atom(predicate, terms);
		}
		return atom;
	}
	private void parseOWLQuery() {
		// String s0 =
		// "q0(X2) :- r6(X1,d1), c2(X2), c3(X1), c3(d1), r4(X2,X1), c5(X2).";
		String s0 = this.queryString;
		String s1 = s0.replaceAll(" ", "");
		String s = s1.replaceAll("\\.", "");
		// System.out.println(s);
		String delims = "[:-]+";
		String[] tokens = s.split(delims);
		String strHead = tokens[0];
		Atom head = getHeadAtomFromString(strHead);
		this.cq.setHead(head);
		// System.out.println("head:  " + head);
		String strBody = tokens[1];
		// System.out.println(strBody);
		String strBody1 = strBody.replaceAll("\\),", "\\);");
		delims = "[;]+";
		String[] tokensBody = strBody1.split(delims);
		// for (int i = 0; i < tokensBody.length; i++)
		// System.out.println(tokensBody[i]);
		// //
		List<Atom> body = new ArrayList<>();
        for (String atomString : tokensBody)
			body.add(getBodyAtomFromString(atomString));
		this.cq.setBody(body);
	}

}
