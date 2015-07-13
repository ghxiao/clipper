package org.semanticweb.clipper.hornshiq.queryanswering;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.InternalCQParser;
import org.semanticweb.clipper.hornshiq.rule.Term;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OntopCQContainmentCheckUnderLIDsTest {

	@Test
	public void test() {
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g1 = new CQGraph(cq);

		String s2 = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		parser.setQueryString(s2);
		CQ cq2 = parser.getCq();
		CQGraph g2 = new CQGraph(cq2);

		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();

		Variable x0 = new Variable(0);
		Variable x1 = new Variable(1);
		Variable x2 = new Variable(2);

		Map<Term, Term> idMap = ImmutableMap.<Term, Term> of(x0, x0, x1, x1, x2, x2);

		//boolean b = checker.check(idMap, g1, g2);
		//System.out.println(b);

		// CQContainmentCheckUnderLIDs.isContainedIn(g, g);
	}

	@Test
	public void test02() {
		// String s =
		// "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g1 = new CQGraph(cq);

		String s2 = "q( X1 )  :- r6(X0, X2), r8(X0, X2), c3 (X0), c3(X0), r8(X1,X2), c2(X1), r4(X1,X0). ";

		parser.setQueryString(s2);
		CQ cq2 = parser.getCq();
		CQGraph g2 = new CQGraph(cq2);

		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();

		Variable x0 = new Variable(0);
		Variable x1 = new Variable(1);
		Variable x2 = new Variable(2);

		Map<Term, Term> idMap = ImmutableMap.<Term, Term> of(x0, x1, x1, x0, x2, x2);

		//boolean b = checker.check(idMap, g1, g2);
		//assertTrue(b);
		//System.out.println(b);

		// CQContainmentCheckUnderLIDs.isContainedIn(g, g);
	}

	@Test
	public void test03() {
		// String s =
		// "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g1 = new CQGraph(cq);

		String s2 = "q( X1 )  :- r6(X0, X2), r8(X0, X2), c3 (X0), c3(X0), r8(X1,X2), c2(X1), r4(X1,X0). ";

		parser.setQueryString(s2);
		CQ cq2 = parser.getCq();
		CQGraph g2 = new CQGraph(cq2);

		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();

		// Map<Term, Term> idMap = ImmutableMap.<Term, Term> of(x0, x1, x1, x0,
		// x2, x2);

		assertTrue(checker.isContainedIn(g1, g2));
	}

	@Test
	public void test04() {
		// String s =
		// "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g1 = new CQGraph(cq);

		String s2 = "q( X1 )  :- r6(X0, X2), r8(X0, X2), c3 (X0), c3(X0), r8(X1,X2), c2(X1), r4(X1,X0), r8(X2, X2). ";

		parser.setQueryString(s2);
		CQ cq2 = parser.getCq();
		CQGraph g2 = new CQGraph(cq2);

		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();

		// Map<Term, Term> idMap = ImmutableMap.<Term, Term> of(x0, x1, x1, x0,
		// x2, x2);

		assertTrue(checker.isContainedIn(g1, g2));
	}

	@Test
	public void test05() {

		// String s =
		// "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";
		String s = "q(X1) :- r6(X1,X4), r2(X1,X2), r4(X1,X3).";

		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g1 = new CQGraph(cq);

		String s2 = "q(X1) :- c2(X2), r6(X1,X4), r2(X1,X2), r4(X1,X3).";

		parser.setQueryString(s2);
		CQ cq2 = parser.getCq();
		CQGraph g2 = new CQGraph(cq2);

		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();

		// Map<Term, Term> idMap = ImmutableMap.<Term, Term> of(x0, x1, x1, x0,
		// x2, x2);

		assertTrue(checker.isContainedIn(g1, g2));
		//System.out.println(checker.getMap());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubset01() {
		List<Collection<Integer>> lst1 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1), ImmutableList.of(2, 3));
		List<Collection<Integer>> lst2 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1, 2), ImmutableList.of(2, 3));
		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();
		//assertTrue(checker.subset(lst1, lst2));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubset02() {
		List<Collection<Integer>> lst1 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1), ImmutableList.of(2, 3));
		List<Collection<Integer>> lst2 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1, 2), ImmutableList.of(2));
		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();
		//assertFalse(checker.subset(lst1, lst2));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubset03() {
		List<Collection<Integer>> lst1 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1), ImmutableList.of(2, 3), ImmutableList.of(2));
		List<Collection<Integer>> lst2 = Lists.<Collection<Integer>> newArrayList(//
				ImmutableList.of(1, 2), ImmutableList.of(2, 3, 4));
		CQContainmentCheckUnderLIDs checker = new CQContainmentCheckUnderLIDs();
		//assertTrue(checker.subset(lst1, lst2));
	}
}
