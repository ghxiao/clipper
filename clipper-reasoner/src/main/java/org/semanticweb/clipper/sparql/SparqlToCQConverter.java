package org.semanticweb.clipper.sparql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Constant;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;

import com.hp.hpl.jena.query.Query;

import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;

public class SparqlToCQConverter {

	/**
	 * @param query
	 */
	public CQ compileQuery(Query query) {

		List<Atom> body = Lists.newArrayList();
		List<Term> ansVars = new ArrayList<Term>();
		List<Var> projectVars = query.getProjectVars();
		for (Var var : projectVars) {
			ansVars.add(compileTerm(var));
		}

		NonDLPredicate ans = new NonDLPredicate("ans", ansVars.size());

		// NormalPredicate ans = CacheManager.getInstance().getPredicate("ans",
		// ansVars.size());

		Atom head = new Atom(ans, ansVars);

		Element queryPattern = query.getQueryPattern();

		if (queryPattern instanceof ElementGroup) {
			ElementGroup group = (ElementGroup) queryPattern;
			List<Element> elements = group.getElements();
			for (Element ele : elements) {
				// System.out.println(ele.getClass());
				if (ele instanceof ElementPathBlock) {
					ElementPathBlock block = (ElementPathBlock) ele;
					Iterator<TriplePath> patternElts = block.patternElts();
					while (patternElts.hasNext()) {
						TriplePath triplePath = patternElts.next();
						Triple triple = triplePath.asTriple();
						final Atom lit = complileTriple(triple);
						// System.out.println(triple + " ==> " + lit);
						body.add(lit);
					}
					return new CQ(head, body);
				} else {
					throw new UnsupportedOperationException();
				}

			}

		} else {
			throw new UnsupportedOperationException();
		}
		return null;
	}

	private Atom complileTriple(Triple triple) {

		if (triple.getPredicate().getURI()
				.equals(OWLRDFVocabulary.RDF_TYPE.toString())) {
			return new Atom(compileUnaryPredicate(triple.getObject(), 1), //
					compileTerm(triple.getSubject()));
		} else {
			return new Atom(compileBinaryPredicate(triple.getPredicate(), 2), //
					compileTerm(triple.getSubject()), //
					compileTerm(triple.getObject()));
		}

	}

	/**
	 * @param predicate
	 * @return
	 */
	private DLPredicate compileUnaryPredicate(Node predicate, int arity) {

		if (predicate.isURI()) {
			String uri = predicate.getURI();
			
			return new DLPredicate(OWLManager.getOWLDataFactory()
					.getOWLClass(IRI.create(uri)));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @param predicate
	 * @return
	 */
	private DLPredicate compileBinaryPredicate(Node predicate, int arity) {

		if (predicate.isURI()) {
			String uri = predicate.getURI();
			return new DLPredicate(OWLManager.getOWLDataFactory()
					.getOWLObjectProperty(IRI.create(uri)));
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Term compileTerm(Node node) {
		if (node.isURI()) {
			String uri = node.getURI();
			return new Constant(quote(uri));
		} else if (node.isLiteral()) {
			String uri = node.getLiteralDatatypeURI();
			if (uri != null)
				return new Constant(quote(uri));
			else
				return new Constant(node.toString());
		} else if (node.isVariable()) {
			String name = node.getName();
			return new Variable(name);
		} else {
			throw new IllegalArgumentException(node.toString());
		}
	}

	/**
	 * @param uri
	 * @return
	 */
	private String quote(String uri) {
		final String quotedIRI = "<" + uri + ">";
		return quotedIRI;
	}

}
