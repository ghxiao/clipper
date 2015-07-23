package org.semanticweb.clipper.dllog;

import com.google.common.base.Joiner;
import org.semanticweb.clipper.hornshiq.rule.Atom;

import java.util.List;

public class DLLogRule {

	private List<Atom> headDLAtoms;

	private List<Atom> headNonDLAtoms;

	private List<Atom> bodyPositiveDLAtoms;

	private List<Atom> bodyPositiveNonDLAtoms;

	private List<Atom> bodyNegativeNonDLAtoms;

	public DLLogRule() {

	}

	public DLLogRule(List<Atom> headDLAtoms, List<Atom> headNonDLAtoms,
			List<Atom> bodyPositiveDLAtoms, List<Atom> bodyPositiveNonDLAtoms,
			List<Atom> bodyNegativeNonDLAtoms) {
		super();
		this.headDLAtoms = headDLAtoms;
		this.headNonDLAtoms = headNonDLAtoms;
		this.bodyPositiveDLAtoms = bodyPositiveDLAtoms;
		this.bodyPositiveNonDLAtoms = bodyPositiveNonDLAtoms;
		this.bodyNegativeNonDLAtoms = bodyNegativeNonDLAtoms;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean firstList = true;
		if (headDLAtoms.size() > 0) {
			Joiner.on(" v ").appendTo(sb, headDLAtoms);
			firstList = false;
		}
		if (!firstList) {
			sb.append(" v ");
		}
		Joiner.on(" v ").appendTo(sb, headNonDLAtoms);

		// body is not empty
		if (bodyPositiveDLAtoms.size() > 0 || bodyPositiveNonDLAtoms.size() > 0
				|| bodyNegativeNonDLAtoms.size() > 0) {
			sb.append(" :- ");

			firstList = true;

			if (bodyPositiveDLAtoms.size() > 0) {
				Joiner.on(", ").appendTo(sb, bodyPositiveDLAtoms);
				firstList = false;
			}

			if (!firstList) {
				sb.append(" v ");
			}

			if (bodyPositiveNonDLAtoms.size() > 0) {
				Joiner.on(", ").appendTo(sb, bodyPositiveNonDLAtoms);
			}
			// TODO: negative body
		}

		sb.append(".");
		return sb.toString();
	}

	public List<Atom> getHeadDLAtoms() {
		return headDLAtoms;
	}

	public void setHeadDLAtoms(List<Atom> headDLAtoms) {
		this.headDLAtoms = headDLAtoms;
	}

	public List<Atom> getHeadNonDLAtoms() {
		return headNonDLAtoms;
	}

	public void setHeadNonDLAtoms(List<Atom> headNonDLAtoms) {
		this.headNonDLAtoms = headNonDLAtoms;
	}

	public List<Atom> getBodyPositiveDLAtoms() {
		return bodyPositiveDLAtoms;
	}

	public void setBodyPositiveDLAtoms(List<Atom> bodyPositiveDLAtoms) {
		this.bodyPositiveDLAtoms = bodyPositiveDLAtoms;
	}

	public List<Atom> getBodyPositiveNonDLAtoms() {
		return bodyPositiveNonDLAtoms;
	}

	public void setBodyPositiveNonDLAtoms(List<Atom> bodyPositiveNonDLAtoms) {
		this.bodyPositiveNonDLAtoms = bodyPositiveNonDLAtoms;
	}

	public List<Atom> getBodyNegativeNonDLAtoms() {
		return bodyNegativeNonDLAtoms;
	}

	public void setBodyNegativeNonDLAtoms(List<Atom> bodyNegativeNonDLAtoms) {
		this.bodyNegativeNonDLAtoms = bodyNegativeNonDLAtoms;
	}
}
