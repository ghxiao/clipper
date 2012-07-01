package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.HashSet;
import java.util.Set;

/* A rule contains HEAD and BODY. The rule in HORN form.
 * e.g  A :- A1, A2, ..., An.
 * Where Ai are atoms.  
 * 
 * */
public class Rule {
	private String head;
	private Set<String> body;
	private int headPredicate = -1; // default =-1; means that this value is not
									// set
	private int headPredicatArity = -1;// default =-1; means that this value is
										// not set

	// ================
	public Rule() {
		head = "";
		body = new HashSet<String>();
	}

	// =================
	public String getHead() {
		return head;
	}

	public Set<String> getBody() {
		return body;
	}

	public void setBody(Set<String> body) {
		this.body = body;
	}

	public void setHead(String h) {
		head = h;
	}

	public int getHeadPredicate() {
		return headPredicate;
	}

	public void setHeadPredicate(int headPredicate) {
		this.headPredicate = headPredicate;
	}

	public int getHeadPredicatArity() {
		return headPredicatArity;
	}

	public void setHeadPredicatArity(int headPredicatArity) {
		this.headPredicatArity = headPredicatArity;
	}

	public void addAtomToBody(String s) {
		if (!s.equals("Thing(X)") && !s.equals("Thing(Y)")
				&& !s.equals("Thing(Z)") && !s.equals("c0(X)"))
			body.add(s);
	}

	// Empty the rule;
	public void clear() {
		head = "";
		body.clear();
	}

	// Rule only makes sense if the read is not contained in the body.
	public boolean isNotTrivial() {
		return (!body.contains(head));
	}

	@Override
	public String toString() {
		String rule = new String();
		rule = rule + head + " :- ";
		int i = 0;
		int n = body.size();
		for (String s : body) {
			if (i < n - 1) {
				rule = rule + s + ", ";
				i++;
			} else {
				rule = rule + s + ".";
			}
			;
		}
		return rule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((head == null) ? 0 : head.hashCode());
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
		Rule other = (Rule) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		return true;
	}

}
