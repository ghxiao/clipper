package org.semanticweb.clipper.hornshiq.rule;

public class Variable implements Term {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		Variable other = (Variable) obj;
		if (index != other.index)
			return false;
		return true;
	}

	int index;
	String name;

	public Variable(int index) {
		this.index = index;
	}

	public Variable(String text) {
		this.name = text;
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public String toString() {

		if (name != null)
			return name;

		return "X" + index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

}
