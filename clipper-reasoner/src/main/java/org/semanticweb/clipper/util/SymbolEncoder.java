package org.semanticweb.clipper.util;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolEncoder<K> {

	int currentMax = 0;

	private int start;

	private Class<K> cls;

	/**
	 * @param start
	 *            the start value of encoding start >= 0
	 */
	public SymbolEncoder(int start) {
		if (start < 0) {
			throw new IllegalArgumentException("start >= 0 expected, but start = " + start);
		}

		this.start = start;
		this.currentMax = start - 1;
	}

	public SymbolEncoder(Class<K> cls) {
		this(0);
		this.cls = cls;
	}

	Map<K, Integer> symbolToInt_Map = new HashMap<K, Integer>();

	List<K> intToSymbol_List = new ArrayList<K>();

	@SuppressWarnings("unchecked")
	public K getSymbolByValue(int value) {

		if (cls == OWLPropertyExpression.class) {
			@SuppressWarnings("rawtypes")
			OWLPropertyExpression p = (OWLPropertyExpression) intToSymbol_List.get(value / 2 - start);
			if (p.isObjectPropertyExpression()) {
				if (value % 2 == 0) {
					return (K) p;
				} else {
					return (K) ((OWLObjectPropertyExpression) p).getInverseProperty();
				}
			} else {
				return (K) p;
			}
		} else {
			return intToSymbol_List.get(value - start);
		}

	}

	@SuppressWarnings("unchecked")
	public int getValueBySymbol(K symbol) {

		if (OWLObjectPropertyExpression.class.isAssignableFrom(symbol.getClass())) {
			OWLObjectPropertyExpression property = (OWLObjectPropertyExpression) symbol;

			OWLObjectPropertyExpression simplified = property.getSimplified();
			OWLObjectProperty namedProperty = simplified.getNamedProperty();
			symbol = (K) namedProperty;

			if (simplified instanceof OWLObjectInverseOf) {
				return 2 * lookupOrInsert((K) namedProperty) + 1;
			} else {
				return 2 * lookupOrInsert((K) namedProperty);
			}
		} else if (OWLDataProperty.class.isAssignableFrom(symbol.getClass())) {
			return 2 * lookupOrInsert((K) symbol);
		}

		else {
			return lookupOrInsert(symbol);
		}
	}

	protected int lookupOrInsert(K symbol) {
		if (symbolToInt_Map.containsKey(symbol)) {
			return symbolToInt_Map.get(symbol);
		} else {
			encodeSybmol(symbol);
			return currentMax;
		}
	}

	private void encodeSybmol(K symbol) {

		if (!symbolToInt_Map.containsKey(symbol)) {
			currentMax++;
			symbolToInt_Map.put(symbol, currentMax);
			intToSymbol_List.add(symbol);
		}
	}

	public int getMax() {
		if (cls == OWLObjectPropertyExpression.class) {
			return currentMax * 2 + 1;
		} else {
			return currentMax;
		}
	}

	// public int getValueBySymbol(OWLObjectPropertyExpression property) {
	// OWLObjectPropertyExpression simplified = property.getSimplified();
	// OWLObjectProperty namedProperty = simplified.getNamedProperty();
	//
	// if (simplified instanceof OWLObjectInverseOf) {
	// return 2 * getValueBySymbol(namedProperty) - 1;
	// } else {
	// return 2 * getValueBySymbol(namedProperty);
	// }
	// }

}
