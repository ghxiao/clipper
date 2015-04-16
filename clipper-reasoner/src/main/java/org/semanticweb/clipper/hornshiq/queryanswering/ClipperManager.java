package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

public class ClipperManager {
	private SymbolEncoder<OWLClass> owlClassEncoder;

	/**
	 * encoder for both OWLObjectPropertyExpression and
	 * OWLDataPropertyExpression
	 */
	@SuppressWarnings("rawtypes")
	private SymbolEncoder<OWLPropertyExpression> owlPropertyExpressionEncoder;

	/**
	 * encoder for both OWLIndividual and OWLLiteral
	 */
	private SymbolEncoder<OWLPropertyAssertionObject> owlIndividualAndLiteralEncoder;

	private int thing;
	private int nothing;
	private int topProperty;
	private int bottomProperty;

	private int verboseLevel;

	@Deprecated // should not use this as global variable
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

	public SymbolEncoder<OWLClass> getOwlClassEncoder() {
		return this.owlClassEncoder;
	}

	public SymbolEncoder<OWLPropertyExpression> getOwlPropertyExpressionEncoder() {
		return this.owlPropertyExpressionEncoder;
	}

	public SymbolEncoder<OWLPropertyAssertionObject> getOwlIndividualAndLiteralEncoder() {
		return this.owlIndividualAndLiteralEncoder;
	}

	public int getThing() {
		return this.thing;
	}

	public int getNothing() {
		return this.nothing;
	}

	public int getTopProperty() {
		return this.topProperty;
	}

	public int getBottomProperty() {
		return this.bottomProperty;
	}

	public int getVerboseLevel() {
		return this.verboseLevel;
	}

	@Deprecated
	public NamingStrategy getNamingStrategy() {
		return this.namingStrategy;
	}

	public void setVerboseLevel(int verboseLevel) {
		this.verboseLevel = verboseLevel;
	}

	@Deprecated
	public void setNamingStrategy(NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}
}
