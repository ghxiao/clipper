package org.semanticweb.clipper.hornshiq.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.rule.CQParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;


public class CQParserTest {
	@Test
	public void test01() {
		String s="     q( X0 )     :-       r6(   X1,   d1),             c3 (d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		String s1="     q()     :-       r6(X1,d1),             c3(d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		String s2="     q(X0,  X1)     :-       r6(X1,d1),             c3(d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		System.out.println(parser.getCq());
		parser.setQueryString(s1);
		System.out.println(parser.getCq());
		parser.setQueryString(s2);
		System.out.println(parser.getCq());
	}
	
}
