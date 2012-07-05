package org.semanticweb.clipper.hornshiq.rule;

import lombok.Data;

@Data
public class Constant implements Term {

	int value;
	private String name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		Constant other = (Constant) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public Constant() {

	}

	public Constant(int value) {
		this.value = value;
	}

	public Constant(String text) {
		this.name = text;
		// TODO: compute value

	}

	@Override
	public boolean isVariable() {
		return false;
	}

	@Override
	public String toString() {

		if (name != null) {
			return name;
		}

		return String.valueOf("d" + value);
	}

	@Override
	public int getIndex() {
		return value;
	}

	@Override
	public Variable asVariable() {
		throw new IllegalArgumentException("not a variable!");
	}

	@Override
	public Constant asConstant() {
		return this;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

}
