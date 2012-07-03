package org.semanticweb.clipper.hornshiq.queryanswering;

import org.semanticweb.clipper.hornshiq.queryanswering.ReductionToDatalogOpt.NamingStrategy;
import org.semanticweb.clipper.util.SymbolEncoder;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


public class ClipperManager {

	private SymbolEncoder<OWLClass> owlClassEncoder;
	private SymbolEncoder<OWLObjectPropertyExpression> owlObjectPropertyExpressionEncoder;
	private SymbolEncoder<OWLDataProperty> owlDataPropertyEncoder;
	private SymbolEncoder<OWLIndividual> owlIndividualEncoder;
	private int thing;
	private int nothing;
	private int topProperty;
	private int bottomProperty;

	private int verboseLevel;
	
	private NamingStrategy namingStrategy;

	private static ClipperManager instance = new ClipperManager();

	private ClipperManager() {
		this.owlClassEncoder = new SymbolEncoder<OWLClass>(OWLClass.class);
		this.owlObjectPropertyExpressionEncoder = new SymbolEncoder<OWLObjectPropertyExpression>(
				OWLObjectPropertyExpression.class);
		this.owlDataPropertyEncoder = new SymbolEncoder<OWLDataProperty>(OWLDataProperty.class);
		this.owlIndividualEncoder = new SymbolEncoder<OWLIndividual>(OWLIndividual.class);
		this.thing = owlClassEncoder.getValueBySymbol(OWLManager.getOWLDataFactory().getOWLThing());
		this.nothing = owlClassEncoder.getValueBySymbol(OWLManager.getOWLDataFactory().getOWLNothing());
		this.topProperty = owlObjectPropertyExpressionEncoder.getValueBySymbol(OWLManager.getOWLDataFactory()
				.getOWLTopObjectProperty());
		this.bottomProperty = owlObjectPropertyExpressionEncoder.getValueBySymbol(OWLManager.getOWLDataFactory()
				.getOWLBottomObjectProperty());
	}

	public static ClipperManager getInstance() {
		return instance;
	}

	public SymbolEncoder<OWLClass> getOwlClassEncoder() {
		return owlClassEncoder;
	}

	public SymbolEncoder<OWLDataProperty> getOwlDataPropertyEncoder() {
		return owlDataPropertyEncoder;
	}

	public SymbolEncoder<OWLIndividual> getOwlIndividualEncoder() {
		return owlIndividualEncoder;
	}

	public SymbolEncoder<OWLObjectPropertyExpression> getOwlObjectPropertyExpressionEncoder() {
		return owlObjectPropertyExpressionEncoder;
	}

	public int getThing() {
		return thing;
	}

	public int getNothing() {
		return nothing;
	}

	public int getTopProperty() {
		return topProperty;
	}

	public int getBottomProperty() {
		return bottomProperty;
	}

	public int getVerboseLevel() {
		return verboseLevel;
	}

	public void setVerboseLevel(int verboseLevel) {
		this.verboseLevel = verboseLevel;
	}

	public NamingStrategy getNamingStrategy() {
		return namingStrategy;
	}

	public void setNamingStrategy(NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	
}
