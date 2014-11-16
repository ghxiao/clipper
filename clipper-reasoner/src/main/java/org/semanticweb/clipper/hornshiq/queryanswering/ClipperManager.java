package org.semanticweb.clipper.hornshiq.queryanswering;

import lombok.Getter;
import lombok.Setter;

import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

public class ClipperManager {
	@Getter
	private SymbolEncoder<OWLClass> owlClassEncoder;

	/**
	 * encoder for both OWLObjectPropertyExpression and
	 * OWLDataPropertyExpression
	 */
	@SuppressWarnings("rawtypes")
	@Getter
	private SymbolEncoder<OWLPropertyExpression> owlPropertyExpressionEncoder;

	/**
	 * encoder for both OWLIndividual and OWLLiteral
	 */
	@Getter
	private SymbolEncoder<OWLPropertyAssertionObject> owlIndividualAndLiteralEncoder;

	@Getter
	private int thing;
	@Getter
	private int nothing;
	@Getter
	private int topProperty;
	@Getter
	private int bottomProperty;

	@Getter
	@Setter
	private int verboseLevel;

	@Deprecated // should not use this as global variable
	@Getter
	@Setter
	private NamingStrategy namingStrategy;

	private static ClipperManager instance = new ClipperManager();

	private ClipperManager() {
		reset();
	}

	@SuppressWarnings("rawtypes")
	public void reset() {
		this.owlClassEncoder = new SymbolEncoder<OWLClass>(OWLClass.class);
		this.owlPropertyExpressionEncoder = new SymbolEncoder<OWLPropertyExpression>(OWLPropertyExpression.class);

		this.owlIndividualAndLiteralEncoder = new SymbolEncoder<OWLPropertyAssertionObject>(
				OWLPropertyAssertionObject.class);

		this.thing = owlClassEncoder.getValueBySymbol(OWLManager.getOWLDataFactory().getOWLThing());
		this.nothing = owlClassEncoder.getValueBySymbol(OWLManager.getOWLDataFactory().getOWLNothing());

		this.topProperty = owlPropertyExpressionEncoder.getValueBySymbol(OWLManager.getOWLDataFactory()
				.getOWLTopObjectProperty());

		this.bottomProperty = owlPropertyExpressionEncoder.getValueBySymbol(OWLManager.getOWLDataFactory()
				.getOWLBottomObjectProperty());
	}

	public static ClipperManager getInstance() {
		return instance;
	}

}
