package org.semanticweb.clipper.hornshiq.rule;

public interface CQVisitor {

	public void visit(CQ cq);
	
	public void visitHead(Atom atom);
	
	public void visitBody(Atom atom);
	
	public void visit(Atom atom);

	public void visit(Predicate predicate);

	public void visit(DLPredicate dlPredicate);

	public void visit(NonDLPredicate nonDLPredicate);

	public void visit(Constant constant);

	public void visit(Variable variable);
	
}
