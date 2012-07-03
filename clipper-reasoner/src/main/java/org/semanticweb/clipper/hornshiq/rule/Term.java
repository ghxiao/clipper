package org.semanticweb.clipper.hornshiq.rule;

public interface Term {
	public boolean isVariable();

	public int getIndex();

	public String getName();

	public void setName(String name);
}
