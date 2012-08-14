package org.semanticweb.clipper.hornshiq.rule;

import lombok.Getter;
import lombok.Setter;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
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

		// TODO distinguish objectProperty and dataProperty
		if (entity.isBottomEntity()){
			this.arity = 0;
			this.encoding = ClipperManager.getInstance().getNothing();
		} else if (entity.isTopEntity()){
			this.arity = 0;
			this.encoding = ClipperManager.getInstance().getThing();
		} else if (entity.isOWLClass()) {
			this.arity = 1;
			this.encoding = ClipperManager.getInstance().getOwlClassEncoder().getValueBySymbol(entity.asOWLClass());
		} else if (entity.isOWLObjectProperty()) {
			this.arity = 2;
			this.encoding = ClipperManager.getInstance().getOwlObjectPropertyExpressionEncoder()
					.getValueBySymbol(entity.asOWLObjectProperty());
		} else if (entity.isOWLDataProperty()) {
			this.arity = 2;
			this.encoding = ClipperManager.getInstance().getOwlDataPropertyEncoder()
					.getValueBySymbol(entity.asOWLDataProperty());
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

	@Override
	public boolean isDLPredicate() {
		return true;
	}

	@Override
	public String getName() {
		return sfp.getShortForm(owlEntity);
	}
}
