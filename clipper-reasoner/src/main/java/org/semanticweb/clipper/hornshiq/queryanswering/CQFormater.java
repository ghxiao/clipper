package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class CQFormater {

	public CQFormater() {
	}

	String getBinaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			OWLObjectPropertyExpression owlExpression = ClipperManager.getInstance()
					.getOwlObjectPropertyExpressionEncoder().getSymbolByValue(value);
			if (owlExpression.isAnonymous())
				return "INVERSEOF(" + normalizeIRI(owlExpression.getNamedProperty().getIRI()) + ")";
			else {
				IRI iri = ClipperManager.getInstance().getOwlObjectPropertyExpressionEncoder().getSymbolByValue(value)
						.asOWLObjectProperty().getIRI();
				return normalizeIRI(iri);
			}

		case IntEncoding:
			return "r" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	String getUnaryPredicate(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlClassEncoder().getSymbolByValue(value).getIRI();

			return normalizeIRI(iri);
		case IntEncoding:
			return "c" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	private String normalizeIRI(IRI iri) {
		String fragment = iri.getFragment();
		if (fragment != null) {
			return fragment.replaceAll("[_-]", "").toLowerCase();
		} else {
			final String iriString = iri.toString();
			int i = iriString.lastIndexOf('/') + 1;
			return iriString.substring(i).replace("_-", "").toLowerCase();

		}

	}

	/**
	 * Print return a conjunctive query in lowercase, not in form of encoded
	 * number.
	 * */
	String formatQuery(CQ cq) {
		StringBuilder sb = new StringBuilder();
		// if
		// (ClipperManager.getInstance().getNamingStrategy().equals(NamingStrategy.IntEncoding))
		// {
		sb.append(cq.getHead());
		sb.append(" :- ");
		boolean first = true;
		for (Atom b : cq.getBody()) {
			if (b.getPredicate().getEncoding() != ClipperManager.getInstance().getThing()) {
				if (!first) {
					sb.append(", ");
				}
				first = false;
				sb.append(formatAtom(b));
			}
		}
		sb.append(".");
		return sb.toString();

		// } else {
		// sb.append(cq.getHead());
		// sb.append(" :- ");
		// boolean first = true;
		// for (Atom b : cq.getBody()) {
		// if (b.getPredicate().getEncoding() !=
		// ClipperManager.getInstance().getThing()) {
		// if (!first) {
		// sb.append(", ");
		// }
		// first = false;
		// sb.append(lowerCaseFormOfAtom(b));
		// }
		// }
		// sb.append(".");
		// return sb.toString();
		//
		// }

	}

	// ============================================
	private String formatAtom(Atom atom) {
		StringBuilder sb = new StringBuilder();
		if (atom.getPredicate().getArity() == 1) {
			String predicateStr = getUnaryPredicate(atom.getPredicate().getEncoding());

			sb.append(predicateStr);
		} else if (atom.getPredicate().getArity() == 2) {
			String predicateStr = getBinaryPredicate(atom.getPredicate().getEncoding());

			sb.append(predicateStr);
		} else
			sb.append(atom.getPredicate());
		sb.append("(");
		boolean first = true;
		for (Term t : atom.getTerms()) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			if (!t.isVariable()) {
				sb.append(getConstant(t.getIndex()));
			} else
				sb.append(t);
		}
		sb.append(")");
		return sb.toString();
	}

	// // ============================================
	// private String lowerCaseFormOfAtom(Atom atom) {
	// StringBuilder sb = new StringBuilder();
	// if (atom.getPredicate().getArity() == 1) {
	// String predicateStr =
	// getUnaryPredicate(atom.getPredicate().getEncoding());
	// sb.append(predicateStr);
	// } else if (atom.getPredicate().getArity() == 2) {
	// String predicateStr =
	// getBinaryPredicate(atom.getPredicate().getEncoding());
	// sb.append(predicateStr);
	// } else
	// sb.append(atom.getPredicate());
	// sb.append("(");
	// boolean first = true;
	// for (Term t : atom.getTerms()) {
	// if (!first) {
	// sb.append(",");
	// }
	// first = false;
	// if (!t.isVariable()) {
	// sb.append(getConstant(t.getIndex()));
	// } else
	// sb.append(t);
	// }
	// sb.append(")");
	// return sb.toString();
	// }

	// convert term to lower case format
	private String getConstant(int value) {
		switch (ClipperManager.getInstance().getNamingStrategy()) {
		case LowerCaseFragment:
			IRI iri = ClipperManager.getInstance().getOwlIndividualEncoder().getSymbolByValue(value)
					.asOWLNamedIndividual().getIRI();
			return "\"" + normalizeIRI(iri) + "\"";
		case IntEncoding:
			return "d" + value;
		}
		throw new IllegalStateException("Not possible");
	}

}
