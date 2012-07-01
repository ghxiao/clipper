package org.semanticweb.clipper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

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

	public K getSymbolByValue(int value) {

		if (cls == OWLObjectPropertyExpression.class) {
			OWLObjectPropertyExpression p = (OWLObjectPropertyExpression) intToSymbol_List.get(value / 2 - start);

			if (value % 2 == 0) {
				return (K) p;
			} else {
				return (K) p.getInverseProperty();
			}
		} else {

			// if (0 <= value && value <= currentMax - 1)
			return intToSymbol_List.get(value - start);
		}
		// else {
		// throw new IllegalArgumentException();
		// }
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
		} else {

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
