package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.CQParser;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.base.Joiner;

public class SelfLoopComponentClusterTest {

	@Test
	public void test() throws IOException {
		String s = "q() :- r2(X1, X2), r2(X3, X2), r4(X1, X3), r8(X4, X3), r4(X4, X5), r2(X4, X6)";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		List<Integer> selfLoopRoles = Arrays.asList(2, 4);

		SelfLoopComponentCluster slcc = new SelfLoopComponentCluster(selfLoopRoles);

		Set<Set<Variable>> clusters = slcc.transform(g);
		Joiner.on("\n").appendTo(System.out, clusters);

		assertEquals(2, clusters.size());

	}

}
