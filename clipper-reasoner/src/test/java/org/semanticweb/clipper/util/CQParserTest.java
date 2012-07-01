package org.semanticweb.clipper.util;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQParser;


public class CQParserTest {
	@Test
	public void test01() {
		String s= "q0() :- r6(X1,d1), c3(d1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1).";
		CQParser parser= new CQParser();
		parser.setQueryString(s);
	//	parser.parse();
		System.out.println(parser.getCq());
	}
}
