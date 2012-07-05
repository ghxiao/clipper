package org.semanticweb.clipper.hornshiq.queryanswering;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.CQParser;
import org.semanticweb.clipper.hornshiq.rule.Variable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class CQGraphTest {

	@Test
	public void test() {
		String s = "q( X0 )  :- r6(X1, X2), r8(X1, X2), c3 (X1), c3(X1), r8(X0,X2), c2(X0), r4(X0,X1). ";

		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);

		Set<Variable> v0 = Collections.singleton(new Variable(0));

		g.focus(v0);
		System.out.println("Select " + v0);
		System.out.println(g);
	}

	@Test
	public void testToCQ01() {
		String s = "q()  :- r2(X1, X0), r4(X2, X1), r6(X3, X1).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		CQ g2cq = g.toCQ();
		System.out.println(g2cq);
		assertEquals(3, g2cq.getBody().size());
	}

	@Test
	public void testToCQ02() {
		String s = "q(X1, X2)  :- r2(X1, X0), r4(X2, X1), r5(X3, X1), c1(X1), c2(X1), c2(X0).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		CQ g2cq = g.toCQ();
		System.out.println(g2cq);
	}

	// individual
	@Test
	public void testToCQ03() {
		String s = "q(X1, X2)  :- r2(X1, X0), r4(X2, X1), r5(d3, X1), c1(X1), c2(X1), c2(X0).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		CQ g2cq = g.toCQ();
		System.out.println(g2cq);
	}

	@Test
	public void testClip01() {
		String s = " q()  :- r2(X1, X0), r4(X2, X1), r6(X3, X1). ";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);

		// Vertex vertex = g.findVertex(0);
		Variable vertex = new Variable(0);
		ImmutableSet<Variable> vs = ImmutableSet.of(vertex);
		g.focus(vs);
		System.out.println("Select " + vertex);
		System.out.println(g);

		// TIntHashSet type = new TIntHashSet(new int[] { 3, 4 });
		ImmutableList<Integer> type = ImmutableList.of(3, 4);

		Map<CQGraphEdge, Integer> emptyMap = Maps.newHashMap();
		g.clip(vs, g.getInEdges(vertex), emptyMap, type);
		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	// with constant
	@Test
	public void testClip01c() {
		String s = " q()  :- r2(X1, X0), r4(d2, X1), r6(X3, X1), c3(d2). ";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);

		// Vertex vertex = g.findVertex(0);
		Variable vertex = new Variable(0);
		ImmutableSet<Variable> vs = ImmutableSet.of(vertex);
		g.focus(vs);
		System.out.println("Select " + vertex);
		System.out.println(g);

		// TIntHashSet type = new TIntHashSet(new int[] { 3, 4 });
		ImmutableList<Integer> type = ImmutableList.of(3, 4);

		Map<CQGraphEdge, Integer> emptyMap = Maps.newHashMap();
		g.clip(vs, g.getInEdges(vertex), emptyMap, type);
		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}
	

	// with constant
	@Test
	public void testClip015() {
		String s = " q()  :- r2(X0, d0), r4(X0, X1), r6(d0, X2), r8(X1, X2), c2(X0), c3(d0), c4(X1), c5(X2). ";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);

		// Vertex vertex = g.findVertex(0);
		Variable vertex = new Variable(2);
		ImmutableSet<Variable> vs = ImmutableSet.of(vertex);
		g.focus(vs);
		System.out.println("Select " + vertex);
		System.out.println(g);

		// TIntHashSet type = new TIntHashSet(new int[] { 3, 4 });
		ImmutableList<Integer> type = ImmutableList.of(6);

		Map<CQGraphEdge, Integer> emptyMap = Maps.newHashMap();
		g.clip(vs, g.getInEdges(vertex), emptyMap, type);
		System.out.println("Clip off " + vertex);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(2, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}
	
	@Test
	public void testClip02() {
		String s = " q()  :- r2(X1, X0), r4(X2, X1), r6(X1, X3). ";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);

		Variable vertex = new Variable(0);
		ImmutableList<Variable> vs = ImmutableList.of(vertex);
		g.focus(vs);
		System.out.println("Select " + vertex);
		System.out.println(g);

		List<Integer> type = Lists.newArrayList(3, 4);
		Map<CQGraphEdge, Integer> emptyMap = Maps.newHashMap();
		g.clip(vs, g.getInEdges(vs), emptyMap, type);
		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip03() {
		String s = " q()  :- r2(X1, X0), r4(X2, X1), r6(X1, X3), r4(X0, X4), r4(X4, X3).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		Set<Variable> vertex = ImmutableSet.of(new Variable(0));
		g.focus(vertex);
		System.out.println("Select " + vertex);
		System.out.println(g);
		System.out.println();

		List<Integer> type = Lists.newArrayList(3, 4);
		Map<CQGraphEdge, Integer> emptyMap = Maps.newHashMap();
		g.clip(vertex, g.getInEdges(vertex), emptyMap, type);

		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(3, g.getEdgeCount());
	}

	@Test
	public void testClip04() {
		String s = "q()  :- r2(X1, X2), r2(X2, X3), c1(X1), c2(X2), c3(X3).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		Set<Variable> vertex = ImmutableSet.of(new Variable(2));
		g.focus(vertex);
		System.out.println("Select " + vertex);
		System.out.println(g);
		System.out.println();

		List<Integer> type = Lists.newArrayList(3, 4);
		CQGraphEdge edge = new CQGraphEdge(new Variable(1), new Variable(2), 2);

		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge, 4);
		g.clip(vertex, g.getInEdges(vertex), map, type);

		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(2, g.getVertexCount());
		assertEquals(1, g.getEdgeCount());
	}

	@Test
	public void testClip05() {
		String s = " q()  :- r2(X1, X2), r2(X2, X3), r3(X3, X4), c1(X1), c2(X2), c3(X3), c4(X4).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		Set<Variable> vertex = ImmutableSet.of(new Variable(2));
		g.focus(vertex);
		System.out.println("Select " + vertex);
		System.out.println(g);
		System.out.println();

		// transitive sub role r4 \sqsubseteq^* r2
		//
		CQGraphEdge edge = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge, 4);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertex, g.getInEdges(vertex), map, type);

		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip06() {
		String s = " q()  :- r2(X1, X2), r2(X2, X3), r3(X3, X4), c1(X1), c2(X2), c3(X3), c4(X4), r6(X1, X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		Set<Variable> vertex = ImmutableSet.of(new Variable(2));
		g.focus(vertex);
		System.out.println("Select " + vertex);
		System.out.println(g);
		System.out.println();

		// transitive sub role r4 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2), 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 4, edge2, 8);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertex, g.getInEdges(vertex), map, type);

		System.out.println("Clip off " + vertex);
		System.out.println(g);

		assertEquals(3, g.getVertexCount());
		assertEquals(3, g.getEdgeCount());
	}

	@Test
	public void testClip07() {
		String s = "q()  :- r2(X1, X2), r2(X2, X3), r3(X3, X4), c1(X1), c2(X2), c3(X3), c4(X4), r6(X1, X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		Set<Variable> vertices = ImmutableSet.of(new Variable(2), new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r4 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2), 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 4, edge2, 8);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);

		assertEquals(2, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip08() {
		String s = "q()  :- r2(X1, X2), r2(X2, X3), r3(X3, X4), c1(X1), c2(X2), c3(X3), c4(X4), r6(X1, X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(2));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r4 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 4);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);

		assertEquals(2, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip09() {
		String s = "q()  :- r2(X1, X3), r4(X1, X2), r6(X2, X3).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r4 \sqsubseteq^* r2
		//
		// CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of();
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);

		assertEquals(1, g.getVertexCount());
		assertEquals(1, g.getEdgeCount());
	}

	@Test
	public void testClip10() {
		String s = "q(X1) :- c2(X1), c5(X3), r2(X2,X3), c4(X2), r2(X1,X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r2 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(2), new Variable(3), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 2);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip11() {
		String s = "q(X1) :- c2(X1), c5(X3), r2(X2,X3), c4(X2), r2(X1,X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r2 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(2), new Variable(3), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 4);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(3, g.getVertexCount());
		assertEquals(2, g.getEdgeCount());
	}

	@Test
	public void testClip12() {
		String s = "q(X1) :- c2(X1), c5(X3), r2(X2,X3), c4(X2), r2(X1,X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3), new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r2 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 2);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(2, g.getVertexCount());
		assertEquals(1, g.getEdgeCount());
	}

	@Test
	public void testClip13() {
		String s = "q(X1) :- c2(X1), c5(X3), r2(X2,X3), c4(X2), r2(X1,X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3), new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r2 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 2);
		List<Integer> type = Lists.newArrayList(3, 4);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(2, g.getVertexCount());
		assertEquals(1, g.getEdgeCount());
	}

	/*
	 * DEBUG (CQGraphRewriter.java:175) - cq(g) = q(X1) :- c2(X1), c5(X3),
	 * r2(X2,X3), c4(X2), r2(X1,X2).
	 * 
	 * DEBUG (CQGraphRewriter.java:176) - edges = [<X1, X2>[2]]; map = {<X1,
	 * X2>[2]=2}
	 * 
	 * DEBUG (CQGraphRewriter.java:177) - type = [3, 0]
	 * 
	 * DEBUG (CQGraphRewriter.java:183) - -- new cq = q(X1) :- c2(X1), c3(X3).
	 */
	@Test
	public void testClip14() {
		String s = "q(X1) :- c2(X1), c5(X3), r2(X2,X3), c4(X2), r2(X1,X2).";
		System.out.println(s);
		CQParser parser = new CQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);
		System.out.println(g);
		System.out.println();

		// Set<Variable> vertices = ImmutableSet.of(new Variable(2), new
		// Variable(3));
		Set<Variable> vertices = ImmutableSet.of(new Variable(3), new Variable(3));
		g.focus(vertices);
		System.out.println("Select " + vertices);
		System.out.println(g);
		System.out.println();

		// transitive sub role r2 \sqsubseteq^* r2
		//
		CQGraphEdge edge1 = new CQGraphEdge(new Variable(1), new Variable(2), 2);
		// CQGraphEdge edge2 = new CQGraphEdge(new Variable(1), new Variable(2),
		// 6);
		Map<CQGraphEdge, Integer> map = ImmutableMap.of(edge1, 2);
		List<Integer> type = Lists.newArrayList(3, 0);
		g.clip(vertices, g.getInEdges(vertices), map, type);

		System.out.println("Clip off " + vertices);
		System.out.println(g);
		System.out.println(g.toCQ());

		assertEquals(2, g.getVertexCount());
		assertEquals(1, g.getEdgeCount());
	}

}
