package org.semanticweb.clipper.cqparser;

import org.antlr.runtime.tree.CommonTree;
import org.semanticweb.clipper.hornshiq.rule.Atom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.Constant;
import org.semanticweb.clipper.hornshiq.rule.DLPredicate;
import org.semanticweb.clipper.hornshiq.rule.NonDLPredicate;
import org.semanticweb.clipper.hornshiq.rule.Predicate;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CQTreeWalker {
	private BidirectionalShortFormProvider bsfp;

	public CQTreeWalker(BidirectionalShortFormProvider bidirectionalShortFormProvider) {
		this.bsfp = bidirectionalShortFormProvider;
	}

	@SuppressWarnings("unchecked")
	private List<CommonTree> childrenOf(CommonTree node) {
		return node.getChildren();
	}

	public List<CQ> walkUCQNode(CommonTree root) {

		List<CQ> rules = new ArrayList<CQ>();

		List<CommonTree> children = childrenOf(root);

		for (CommonTree node : children) {
			rules.add(walkRuleNode(node));
		}

		return rules;
	}

	public CQ walkRuleNode(CommonTree ruleNode) {

		assert (ruleNode.getType() == CQLexer.RULE);

		Iterator<CommonTree> iterator = childrenOf(ruleNode).iterator();

		List<Atom> headDLAtoms = new ArrayList<Atom>();
		List<Atom> headNonDLAtoms = new ArrayList<Atom>();

		List<Atom> bodyPositiveDLAtoms = new ArrayList<Atom>();

		List<Atom> bodyPositiveNonDLAtoms = new ArrayList<Atom>();

		// walk for head
		walkAtomList(iterator.next(), headDLAtoms, headNonDLAtoms);

		// walk for positive body
		walkAtomList(iterator.next(), bodyPositiveDLAtoms, bodyPositiveNonDLAtoms);

		// TODO: negative body

		return new CQ(headNonDLAtoms.iterator().next(), bodyPositiveDLAtoms);

	}

	/**
	 * 
	 * walk a node with list of atoms.
	 * 
	 * @param node
	 *            node to walk
	 * @param dlAtoms
	 *            DL atoms are added to dlAtoms
	 * @param nonDLAtoms
	 *            non-DL atoms are added to nonDLAtoms
	 */
	public void walkAtomList(CommonTree node, List<Atom> dlAtoms, List<Atom> nonDLAtoms) {
		assert (node.getType() == CQLexer.ATOM_LIST);
		for (CommonTree atomNode : childrenOf(node)) {
			Atom atom = walkAtomNode(atomNode);
			if (atom.getPredicate().isDLPredicate()) {
				dlAtoms.add(atom);
			} else {
				nonDLAtoms.add(atom);
			}
		}

	}

	private Atom walkAtomNode(CommonTree node) {
		String predicateName = node.getText();

		Predicate predicate;
		int arity = 0;
		List<Term> terms = new ArrayList<Term>();
		Set<OWLEntity> entities = bsfp.getEntities(predicateName);

		// Non DL Predicate
		if (entities.size() == 0) {
			predicate = new NonDLPredicate(predicateName);
		} else {
			if (entities.size() > 1)
				System.err.println(//
						"Warning: More than one DL predicates with the same short name " + predicateName + " -> "
								+ entities);
			OWLEntity entity = entities.iterator().next();
			predicate = new DLPredicate(entity);
		}

		for (CommonTree termNode : childrenOf(node)) {
			Term term = walkTermNode(termNode);
			arity++;
			terms.add(term);
		}
		predicate.setArity(arity);

		return new Atom(predicate, terms);

	}

	private Term walkTermNode(CommonTree termNode) {
		CommonTree subNode = childrenOf(termNode).iterator().next();
		String text = subNode.getText();

		switch (termNode.getType()) {
		case CQLexer.VARIABLE:
			return new Variable(Integer.parseInt(text));
		case CQLexer.CONSTANT:
			return new Constant(text);
		}
		throw new IllegalArgumentException();
	}

}
