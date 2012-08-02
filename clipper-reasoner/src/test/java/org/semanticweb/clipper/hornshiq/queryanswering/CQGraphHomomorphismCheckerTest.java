package org.semanticweb.clipper.hornshiq.queryanswering;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.CQParser;

public class CQGraphHomomorphismCheckerTest {

	@Test
	public void test() {
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		
		CQGraphHomomorphismChecker checker = new CQGraphHomomorphismChecker();
		
		checker.deepthTraverse(g);
		
		//CQGraphHomomorphismChecker.isHomomorphism(g, g);
	}

}
