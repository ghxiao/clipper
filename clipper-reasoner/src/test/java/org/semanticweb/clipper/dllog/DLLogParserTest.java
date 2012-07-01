package org.semanticweb.clipper.dllog;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;
import org.semanticweb.clipper.dllog.DLLogParser.dl_log_rules_return;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import com.google.common.base.Joiner;

public class DLLogParserTest {

	@Test
	public void test() throws IOException, RecognitionException {
		// Create input stream from standard input

		String text = "p(X) v PERSON(Z) :- q(Y), q1(a, b)."
				+ "p(X)  :- q(Y), q1(a, b), PERSON(Z).";

		ByteArrayInputStream ss = new ByteArrayInputStream(text.getBytes());

		ANTLRInputStream input = new ANTLRInputStream(ss);
		// Create a lexer attached to that input stream
		DLLogLexer lexer = new DLLogLexer(input);
		// Create a stream of tokens pulled from the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// Create a parser attached to the token stream
		DLLogParser parser = new DLLogParser(tokens);
		// Invoke the program rule in get return value
		dl_log_rules_return r = parser.dl_log_rules();
		CommonTree t = (CommonTree) r.getTree();

		// If -dot option then generate DOT diagram for AST
		// if (args.length > 0 && args[0].equals("-dot")) {
		// DOTTreeGenerator gen = new DOTTreeGenerator();
		// StringTemplate st = gen.toDOT(t);
		// System.out.println(st);
		// } else {
		System.out.println(t.toStringTree());
		// }
		// Walk resulting tree; create treenode stream first
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
		// AST nodes have payloads that point into token stream
		nodes.setTokenStream(tokens);

		ShortFormProvider sfp = new SimpleShortFormProvider();
		BidirectionalShortFormProvider bsfp = new BidirectionalShortFormProviderAdapter(sfp);
		
		DLLogTreeWalker walker = new DLLogTreeWalker(bsfp);

		List<DLLogRule> rules = walker.walkRootNode(t);

		Joiner.on("\n").appendTo(System.out, rules);
		
		//System.out.println(rules);

		// Create a tree Walker attached to the nodes stream
		// DLLogWalker walker = new DLLogWalker(nodes);
		// Invoke the start symbol, rule program
		// walker.program();
	}

	@Test
	public void testShortFormatProvider() throws OWLOntologyCreationException {
		 ShortFormProvider sfp = new SimpleShortFormProvider();
		System.setProperty("entityExpansionLimit", "640000");
		//ShortFormProvider sfp = new QNameShortFormProvider();

		IRI iri = IRI.create("http://semantics.crl.ibm.com/univ-bench-dl.owl#enrollIn");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

//		OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(
//				"ontology/testBI_no_nominals.owl"));

		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLEntity entity = factory.getOWLObjectProperty(iri);
		OWLDeclarationAxiom axiom = factory.getOWLDeclarationAxiom(entity);
		OWLOntology ontology = manager.createOntology(Collections.<OWLAxiom>singleton(axiom));

		BidirectionalShortFormProvider bsfp = new BidirectionalShortFormProviderAdapter(
				Collections.singleton(ontology), sfp);
		
		String shortForm = bsfp.getShortForm(entity);
		System.out.println(shortForm);
		assertEquals(shortForm, "enrollIn");
		
		OWLEntity entity2 = bsfp.getEntity(shortForm);
		assertEquals(entity, entity2);
		
	}
}
