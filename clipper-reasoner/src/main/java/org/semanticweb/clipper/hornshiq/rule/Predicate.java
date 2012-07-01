package org.semanticweb.clipper.hornshiq.rule;

public interface Predicate {

	public boolean isDLPredicate();

	public int getEncoding();

	public void setArity(int arity);

	public int getArity();

	public String getName();

}
