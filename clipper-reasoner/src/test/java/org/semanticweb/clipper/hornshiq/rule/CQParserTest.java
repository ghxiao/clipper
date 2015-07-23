package org.semanticweb.clipper.hornshiq.rule;

import org.junit.Test;


public class CQParserTest {
	@Test
	public void test01() {
		String s="     q( X0 )     :-       r6(   X1,   d1),             c3 (d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		String s1="     q()     :-       r6(X1,d1),             c3(d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		String s2="     q(X0,  X1)     :-       r6(X1,d1),             c3(d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).         ";
		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		System.out.println(parser.getCq());
		parser.setQueryString(s1);
		System.out.println(parser.getCq());
		parser.setQueryString(s2);
		System.out.println(parser.getCq());
	}
	
}
