package org.semanticweb.clipper.hornshiq.rule;

public class Variable implements Term {

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
	public Variable asVariable() {
		return this;
	}

	@Override
	public Constant asConstant() {
		throw new IllegalArgumentException("not a constant!");
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Variable)) return false;
		final Variable other = (Variable) o;
		if (!other.canEqual((Object) this)) return false;
		if (this.index != other.index) return false;
		final Object this$name = this.name;
		final Object other$name = other.name;
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + this.index;
		final Object $name = this.name;
		result = result * PRIME + ($name == null ? 0 : $name.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof Variable;
	}
}
