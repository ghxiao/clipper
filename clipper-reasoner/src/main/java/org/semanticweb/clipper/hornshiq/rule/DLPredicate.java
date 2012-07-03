package org.semanticweb.clipper.hornshiq.rule;

import lombok.Getter;
import lombok.Setter;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

public class DLPredicate implements Predicate {

	OWLEntity owlEntity;

	ShortFormProvider sfp = new SimpleShortFormProvider();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arity;
		result = prime * result + encoding;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DLPredicate other = (DLPredicate) obj;
		if (arity != other.arity)
			return false;
		if (encoding != other.encoding)
			return false;
		return true;
	}

	@Getter
	@Setter
	int encoding;

	@Getter
	@Setter
	int arity;

	public DLPredicate(int encoding, int arity) {
		this.encoding = encoding;
		this.arity = arity;
	}

	public DLPredicate(OWLEntity entity) {
		this.owlEntity = entity;
		// TODO compute encoding
		if (entity.isBottomEntity() || entity.isTopEntity()) {
			this.arity = 0;
		} else if (entity.isOWLClass()) {
			this.arity = 1;
		} else if (entity.isOWLObjectProperty() || entity.isOWLDataProperty()) {
			this.arity = 2;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String toString() {

		if (owlEntity != null) {
			return getName();
		}

		if (arity == 1) {
			return "c" + encoding;
		} else if (arity == 2) {
			return "r" + encoding;
		}

		return null;

	}

	public boolean isDLPredicate() {
		return true;
	}

	@Override
	public String getName() {
		return sfp.getShortForm(owlEntity);
	}
}
