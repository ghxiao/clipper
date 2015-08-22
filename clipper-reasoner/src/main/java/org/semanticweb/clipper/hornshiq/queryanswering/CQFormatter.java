package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

import static java.util.Objects.requireNonNull;

public class CQFormatter {

	NamingStrategy namingStrategy;

	public CQFormatter(NamingStrategy namingStrategy) {
		requireNonNull(namingStrategy);
		this.namingStrategy = namingStrategy;
	}

	public String getBinaryPredicate(int value) {
		OWLPropertyExpression owlExpression = ClipperManager.getInstance()
				.getOwlPropertyExpressionEncoder().getSymbolByValue(value);
		switch (namingStrategy) {
		case LOWER_CASE_FRAGMENT:
			if (owlExpression.isObjectPropertyExpression()) {
				OWLObjectPropertyExpression ope = (OWLObjectPropertyExpression) owlExpression;
				if (owlExpression.isAnonymous()) {
					// should not happen
					return "INVERSEOF("
							+ normalizeIRI(ope.getInverseProperty()
									.getNamedProperty().getIRI()) + ")";
				} else {
					OWLProperty property = (OWLProperty) owlExpression;
					IRI iri = property.getIRI();
					return normalizeIRI(iri);
				}
			} else {
				OWLProperty property = (OWLProperty) owlExpression;
				IRI iri = property.getIRI();
				return normalizeIRI(iri);
			}

		case INT_ENCODING:
			return "r" + value;
		case FRAGMENT:
			if (owlExpression.isObjectPropertyExpression()) {
				OWLObjectPropertyExpression ope = (OWLObjectPropertyExpression) owlExpression;
				if (owlExpression.isAnonymous()) {
					// should not happen
					return "INVERSEOF("
							+ normalizeIRI(ope.getInverseProperty()
									.getNamedProperty().getIRI()) + ")";
				} else {
					OWLProperty property = (OWLProperty) owlExpression;
					IRI iri = property.getIRI();
					return fragmentIRI(iri);
				}
			} else {
				OWLProperty property = (OWLProperty) owlExpression;
				IRI iri = property.getIRI();
				return fragmentIRI(iri);
			}
		default:
			throw new IllegalStateException("Not possible");
		}

	}

	public String getUnaryPredicate(int value) {
		IRI iri = ClipperManager.getInstance().getOwlClassEncoder()
				.getSymbolByValue(value).getIRI();
		switch (namingStrategy) {
		case LOWER_CASE_FRAGMENT:
			return normalizeIRI(iri);
		case INT_ENCODING:
			return "c" + value;
		case FRAGMENT:
			return fragmentIRI(iri);
		default:
			throw new IllegalStateException("Not possible");
		}

	}

	public String normalizeIRI(IRI iri) {
		String fragment = iri.getFragment();
		if (fragment != null) {
			fragment = replaceUmlauts(fragment);
			return fragment.replaceAll("[_-]", "").toLowerCase();
		} else {
			final String iriString = iri.toString();
			int i = iriString.lastIndexOf('/') + 1;
			return iriString.substring(i).replace("_-", "").toLowerCase();

		}
	}

	static String replaceUmlauts(String string) {
		string = string.replaceAll("Ä", "Ae");
		string = string.replaceAll("Ö", "Oe");
		string = string.replaceAll("Ü", "Ue");
		string = string.replaceAll("ä", "ae");
		string = string.replaceAll("ö", "oe");
		string = string.replaceAll("ü", "ue");
		string = string.replaceAll("ß", "ss");
		return string;
	}

	public String fragmentIRI(IRI iri) {
		String fragment = iri.getFragment();
		if (fragment != null) {
			return fragment;
		} else {
			final String iriString = iri.toString();
			int i = iriString.lastIndexOf('/') + 1;
			return iriString.substring(i);

		}
	}

	/**
	 * Print return a conjunctive query in lowercase, not in form of encoded
	 * number.
	 * */
	String formatQuery(CQ cq) {
		StringBuilder sb = new StringBuilder();

		sb.append(cq.getHead());
		sb.append(" :- ");
		boolean first = true;
		for (Atom b : cq.getBody()) {
			if (b.getPredicate().getEncoding() != ClipperManager.getInstance()
					.getThing()) {
				if (!first) {
					sb.append(", ");
				}
				first = false;
				sb.append(formatAtom(b));
			}
		}
		sb.append(".");
		return sb.toString();

	}

	// ============================================
	private String formatAtom(Atom atom) {
		StringBuilder sb = new StringBuilder();
		if (atom.getPredicate().getArity() == 1) {
			String predicateStr = getUnaryPredicate(atom.getPredicate()
					.getEncoding());

			sb.append(predicateStr);
		} else if (atom.getPredicate().getArity() == 2) {
			String predicateStr = getBinaryPredicate(atom.getPredicate()
					.getEncoding());

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
				// TODO: check again
				if (t.getIndex() == -1) {
					String name = t.getName();
					if (name.startsWith("<") && name.endsWith(">")) {
						name = name.substring(1, name.length() - 1);
					} else if (name.startsWith("\"") && name.endsWith("\"")) {
						name = name.substring(1, name.length() - 1);
					}
					sb.append('\"' + name + '\"');
				} else
					sb.append(getConstant(t.getIndex()));
			} else
				sb.append(t);
		}
		sb.append(")");
		return sb.toString();
	}

	// convert term to lower case format
	public String getConstant(int value) {
		switch (namingStrategy) {
		case LOWER_CASE_FRAGMENT:
		case FRAGMENT:
			final OWLPropertyAssertionObject symbol = ClipperManager
					.getInstance().getOwlIndividualAndLiteralEncoder()
					.getSymbolByValue(value);

			if (symbol instanceof OWLLiteral) {
				OWLLiteral owlLiteral = (OWLLiteral) symbol;
				return "\"" + owlLiteral.getLiteral() + "\"";

			} else if (symbol instanceof OWLNamedIndividual) {
				OWLNamedIndividual owlNamedIndividual = (OWLNamedIndividual) symbol;
				return "\"" + (owlNamedIndividual.getIRI()) + "\"";
				// XXX
				// TODO: add another option
				// return "\"" + normalizeIRI(owlNamedIndividual.getIRI()) +
				// "\"";
			} else {
				throw new IllegalArgumentException(symbol.toString());
			}

			// IRI iri = symbol.getIRI();
		case INT_ENCODING:
			return "d" + value;
		}
		throw new IllegalStateException("Not possible");
	}

	public String getBinaryAtomWithoutInverse(int v, String var1, String var2) {
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
}
