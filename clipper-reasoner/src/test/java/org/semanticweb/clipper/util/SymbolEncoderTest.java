package org.semanticweb.clipper.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

public class SymbolEncoderTest {

	@Test
	public void testPropertyEncoder() {
		ClipperManager km = ClipperManager.getInstance();
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		final SymbolEncoder<OWLPropertyExpression> encoder = km.getOwlPropertyExpressionEncoder();
		OWLDataFactory factory = ontologyManager.getOWLDataFactory();

		OWLObjectProperty op1 = factory.getOWLObjectProperty(IRI.create("#op1"));
		OWLObjectInverseOf op1_inv = factory.getOWLObjectInverseOf(op1);
		OWLObjectProperty op2 = factory.getOWLObjectProperty(IRI.create("#op2"));
		OWLDataProperty dp1 = factory.getOWLDataProperty(IRI.create("#dp1"));
		OWLDataProperty dp2 = factory.getOWLDataProperty(IRI.create("#dp2"));

		final int v_op1 = encoder.getValueBySymbol(op1);
		final int v_op1_inv = encoder.getValueBySymbol(op1_inv);
		final int v_op2 = encoder.getValueBySymbol(op2);
		final int v_dp1 = encoder.getValueBySymbol(dp1);
		final int v_dp2 = encoder.getValueBySymbol(dp2);

		System.out.println("op1 -> " + v_op1);
		System.out.println("inv(op1) -> " + v_op1_inv);
		System.out.println("op2 -> " + v_op2);
		System.out.println("dp1 -> " + v_dp1);
		System.out.println("dp2 -> " + v_dp2);

		assertEquals(op1, encoder.getSymbolByValue(v_op1));
		assertEquals(op2, encoder.getSymbolByValue(v_op2));
		assertEquals(dp1, encoder.getSymbolByValue(v_dp1));
		assertEquals(dp2, encoder.getSymbolByValue(v_dp2));
		assertEquals(op1_inv, encoder.getSymbolByValue(v_op1_inv));
	}

	@Test
	public void testIndividualLiteralEncoder() {
		ClipperManager km = ClipperManager.getInstance();
		OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		final SymbolEncoder<OWLPropertyAssertionObject> encoder = km.getOwlIndividualAndLiteralEncoder();
		OWLDataFactory factory = ontologyManager.getOWLDataFactory();

		OWLNamedIndividual op1 = factory.getOWLNamedIndividual(IRI.create("#op1"));
		OWLNamedIndividual op2 = factory.getOWLNamedIndividual(IRI.create("#op2"));
		OWLLiteral lit1 = factory.getOWLLiteral(true);
		OWLLiteral lit2 = factory.getOWLLiteral("ABC");
		OWLLiteral lit3 = factory.getOWLLiteral(123);


		final int v_op1 = encoder.getValueBySymbol(op1);
		final int v_op2 = encoder.getValueBySymbol(op2);
		final int v_lit1 = encoder.getValueBySymbol(lit1);
		final int v_lit2 = encoder.getValueBySymbol(lit2);
		final int v_lit3 = encoder.getValueBySymbol(lit3);

		System.out.println(op1 + " -> " + v_op1);
		System.out.println(op2 + " -> " + v_op2);
		System.out.println(lit1 + " -> " + v_lit1);
		System.out.println(lit2 + " -> " + v_lit2);
		System.out.println(lit3 + " -> " + v_lit3);

		assertEquals(op1, encoder.getSymbolByValue(v_op1));
		assertEquals(op2, encoder.getSymbolByValue(v_op2));
		assertEquals(lit1, encoder.getSymbolByValue(v_lit1));
		assertEquals(lit2, encoder.getSymbolByValue(v_lit2));
		assertEquals(lit3, encoder.getSymbolByValue(v_lit3));
	}

}
