package org.semanticweb.clipper.hornshiq.queryanswering;

import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import gnu.trove.set.hash.TIntHashSet;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAndSubAtomAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubSomeAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperTransitivityAxiom;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.InternalCQParser;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CQGraphRewriterTest {

	// @Test
	// public void testMatch() {
	// String s =
	// "q()  :- r2(X2, X1), r2(X2, X3), r2(X4, X1), r2(X5, X4), r4(X6, X1), r2(X7, X6), r2(X8, X7), r2(X7, X9).";
	// System.out.println(s);
	// CQParser parser = new CQParser();
	// parser.setQueryString(s);
	// CQ cq = parser.getCq();
	// CQGraph g = new CQGraph(cq);
	// System.out.println(g);
	// System.out.println();
	//
	// Vertex vertex = g.findVertex(1);
	// g.focus(vertex);
	// System.out.println("Select " + vertex);
	// System.out.println(g);
	// System.out.println();
	//
	// // transitive sub role r4 \sqsubseteq^* r2
	// Edge edge = g.findEdge(6, 1);
	// // edge.transitiveSubRole = 4;
	// // edge.selfLoop = true;
	//
	// TIntHashSet type1 = new TIntHashSet(new int[] { 3, 4 });
	// TIntHashSet roles = new TIntHashSet(new int[] { 2, 4 });
	// TIntHashSet type2 = new TIntHashSet(new int[] {});
	// EnforcedRelation enf = new EnforcedRelation(type1, roles, type2);
	// IndexedEnfContainer enfs = new IndexedEnfContainer();
	// enfs.add(enf);
	//
	// CQGraphRewriter rewriter = new CQGraphRewriter();
	// rewriter.enfs = enfs;
	//
	// rewriter.rewrite(g, vertex);
	//
	// System.out.println(g);
	//
	// assertEquals(5, g.getVertexCount());
	// assertEquals(4, g.getEdgeCount());
	// }
	//
	// @Test
	// public void test() {
	// String s =
	// "q()  :- r2(X2, X1), r2(X2, X3), r2(X4, X1), r2(X5, X4), r4(X6, X1), r2(X7, X6), r2(X8, X7), r2(X7, X9).";
	// System.out.println(s);
	// CQParser parser = new CQParser();
	// parser.setQueryString(s);
	// CQ cq = parser.getCq();
	// CQGraph g = new CQGraph(cq);
	// System.out.println(g);
	// System.out.println();
	//
	// TIntHashSet type1 = new TIntHashSet(new int[] { 3, 4 });
	// TIntHashSet roles = new TIntHashSet(new int[] { 2, 4 });
	// TIntHashSet type2 = new TIntHashSet(new int[] {});
	// EnforcedRelation enf = new EnforcedRelation(type1, roles, type2);
	// IndexedEnfContainer enfs = new IndexedEnfContainer();
	// enfs.add(enf);
	//
	// CQGraphRewriter rewriter = new CQGraphRewriter();
	// rewriter.enfs = enfs;
	//
	// rewriter.rewrite_recursive(g);
	// List<CQGraph> result = rewriter.getResult();
	//
	// System.out.println(result);
	//
	// // assertEquals(5, g.getVertexCount());
	// // assertEquals(4, g.getEdgeCount());
	//
	// }

    /**
     * Example 2 in TR
     *
     *
     * T = {  A subclassOf (r and r3 and r4^-) some (B and A3) }
     *
     * q(X1) :- A1(X1), A2(X2), A3(X3), A4(X4), r1(X1, X4), r2(X1, X2), r3(X2, X3), r4(X3, X4).
     *
     * q can be rewritten to :
     *
     * q(X1) :- A1(X1), A(X3), A2(X3), A4(X3), r1(X1, X3), r2(X1, X3)
     *
     *
     *
     * The following encodings are used in the test case
     *
     *  A -> 2, A1 -> 3, A2 -> 4, A3 -> 5, A4 -> 6, B -> 7
     *
     *  r -> 4, r1 -> 6, r2 -> 8, r3 -> 10, r4 -> 12
     *
     */
    @Test
    public void test_Example2() throws IOException {
        ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

        IndexedEnfContainer enfs = new IndexedEnfContainer();

        enfs.add(new EnforcedRelation(
                // T, A
                new TIntHashSet(new int[] { 0, 2 }), //
                // T2, T2, r, r3, r4-
                new TIntHashSet(new int[] { 0, 1, 4, 10, 13 }),
                // B, A3
                new TIntHashSet(new int[] { 5, 7 })));

        CQGraphRewriter rewriter = new CQGraphRewriter(ontology, enfs);

        /*
         * q(X1) :- A1(X1), A2(X2), A3(X3), A4(X4), r1(X1, X4), r2(X1, X2), r3(X2, X3), r4(X3, X4).
         */
        String s = "q(X1) :- c3(X1), c4(X2), c5(X3), c6(X4), r4(X1, X4), r8(X1, X2), r10(X2, X3), r12(X3, X4).";
        System.out.println(s);
        InternalCQParser parser = new InternalCQParser();
        parser.setQueryString(s);
        CQ cq = parser.getCq();
        CQGraph g = new CQGraph(cq);

        List<CQGraph> ucq = rewriter.rewrite(g);

        int i = 0;
        for (CQGraph rg : ucq) {
            System.out.print(i + ": ");
            System.out.println(rg.toCQ());
            i++;
        }


        assertEquals(ucq.size(), 2);

        String ucq1BodyString = ucq.get(1).toCQ().toString();

        assertTrue(ucq1BodyString.contains("r4(X1,X3)"));
        assertTrue(ucq1BodyString.contains("c6(X3)"));
        assertTrue(ucq1BodyString.contains("c2(X3)"));
        assertTrue(ucq1BodyString.contains("c3(X1)"));
        assertTrue(ucq1BodyString.contains("r8(X1,X3)"));
        assertTrue(ucq1BodyString.contains("c4(X3)"));

    }
    /**
     * Example 3 in TR
     *
     *
     * <pre>
     * T = {
     *   A subClassOf (r and r1^- and r2^-) some B
     * }
     *
     *  q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
     * </pre>
     *
     * q can be rewritten to :
     *
     * q(X2) :- C(X2), A(X2)
     *
     *
     * The following encodings are used in the test case
     *
     *  A -> 2, A1 -> 3, A2 -> 4, A3 -> 5, A4 -> 6, B -> 7, C -> 8
     *
     *  r -> 4, r1 -> 6, r2 -> 8, r3 -> 10, r4 -> 12
     *
     */
    @Test
    public void test_Example3() throws IOException {
        ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

        IndexedEnfContainer enfs = new IndexedEnfContainer();

        enfs.add(new EnforcedRelation(
                // T, A
                new TIntHashSet(new int[] { 0, 2 }), //
                // T^2, T^2, r and r1^- and r2^-
                new TIntHashSet(new int[] { 0, 1, 6, 7, 9 }),
                // B
                new TIntHashSet(new int[] { 7 })));

        CQGraphRewriter rewriter = new CQGraphRewriter(ontology, enfs);

        /*
         * q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
         */
        String s = "q(X1) :- c8(X1), c7(X2), r6(X1, X2), r6(X3, X2), r8(X2, X4).";
        System.out.println(s);
        InternalCQParser parser = new InternalCQParser();
        parser.setQueryString(s);
        CQ cq = parser.getCq();
        CQGraph g = new CQGraph(cq);

        List<CQGraph> ucq = rewriter.rewrite(g);

        int i = 0;
        for (CQGraph rg : ucq) {
            System.out.print(i + ": ");
            System.out.println(rg.toCQ());
            i++;
        }


        assertEquals(ucq.size(), 3);

        /**
         * TODO: the order of the rewrting results might change
         */

        assertEquals(3, ucq.get(1).getVertexCount());
        String ucq1BodyString = ucq.get(1).toCQ().toString();
        assertTrue(ucq1BodyString.contains("r8(X3,X4)"));
        assertTrue(ucq1BodyString.contains("r6(X1,X3)"));
        assertTrue(ucq1BodyString.contains("c8(X1)"));
        assertTrue(ucq1BodyString.contains("c2(X3)"));
        assertTrue(ucq1BodyString.contains("c7(X3)"));


        ucq1BodyString = ucq.get(2).toCQ().toString();

        assertEquals(1, ucq.get(2).getVertexCount());
        assertTrue(ucq1BodyString.contains("c8(X2)"));
        assertTrue(ucq1BodyString.contains("c2(X2)"));

    }
    /**
     * Example 4 in TR
     *
     *
     * <pre>
     * T = {
     *   r1 o r1 subPropertyOf r1,
     *   A subClassOf (r and r1^- and r2^-) some B
     * }
     *
     *  q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
     * </pre>
     *
     * q can be rewritten to :
     *
     * q(X2) :- C(X2), A(X2)
     *
     *
     * The following encodings are used in the test case
     *
     *  A -> 2, A1 -> 3, A2 -> 4, A3 -> 5, A4 -> 6, B -> 7, C -> 8
     *
     *  r -> 4, r1 -> 6, r2 -> 8, r3 -> 10, r4 -> 12
     *
     */
    @Test
    public void test_Example4() throws IOException {
        ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

        ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(6));

        IndexedEnfContainer enfs = new IndexedEnfContainer();

        enfs.add(new EnforcedRelation(
                // T, A
                new TIntHashSet(new int[] { 0, 2 }), //
                // T^2, T^2, r and r1^- and r2^-
                new TIntHashSet(new int[] { 0, 1, 6, 7, 9 }),
                // B
                new TIntHashSet(new int[] { 7 })));

        CQGraphRewriter rewriter = new CQGraphRewriter(ontology, enfs);

        /*
         * q(X1) :- C(X1), B(X2), r1(X1, X2), r1(X3, X2), r2(X2, X4).
         */
        String s = "q(X1) :- c8(X1), c7(X2), r6(X1, X2), r6(X3, X2), r8(X2, X4).";
        System.out.println(s);
        InternalCQParser parser = new InternalCQParser();
        parser.setQueryString(s);
        CQ cq = parser.getCq();
        CQGraph g = new CQGraph(cq);

        List<CQGraph> ucq = rewriter.rewrite(g);

        int i = 0;
        for (CQGraph rg : ucq) {
            System.out.print(i + ": ");
            System.out.println(rg.toCQ());
            i++;
        }


//        assertEquals(ucq.size(), 3);
//
//        /**
//         * TODO: the order of the rewriting results might change
//         */
//
//        assertEquals(3, ucq.get(1).getVertexCount());
//        String ucq1BodyString = ucq.get(1).toCQ().toString();
//        assertTrue(ucq1BodyString.contains("r8(X3,X4)"));
//        assertTrue(ucq1BodyString.contains("r6(X1,X3)"));
//        assertTrue(ucq1BodyString.contains("c8(X1)"));
//        assertTrue(ucq1BodyString.contains("c2(X3)"));
//        assertTrue(ucq1BodyString.contains("c7(X3)"));
//
//
//        ucq1BodyString = ucq.get(2).toCQ().toString();
//
//        assertEquals(1, ucq.get(2).getVertexCount());
//        assertTrue(ucq1BodyString.contains("c8(X2)"));
//        assertTrue(ucq1BodyString.contains("c2(X2)"));

    }

	@Test
	public void test() throws IOException {
		ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();
		ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(2, 3));

		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(2));

		ontology.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(2, 2, 3));
		ontology.getAtomSubSomeAxioms().add(new ClipperAtomSubSomeAxiom(3, 2, 4));

		ontology.getAndSubAtomAxioms().add(new ClipperAndSubAtomAxiom(4, 5));

		IndexedEnfContainer enfs = new IndexedEnfContainer();

		enfs.add(new EnforcedRelation(new TIntHashSet(new int[] { 0, 3 }), //
				// 0,1 are top roles
				new TIntHashSet(new int[] { 0, 1, 2, 3 }), //
				new TIntHashSet(new int[] { 0, 4, 5 })));

		CQGraphRewriter rewriter = new CQGraphRewriter(ontology, enfs);

		String s = "q(X1)  :- c2(X1), r2(X1, X2), c4(X2), c5(X3), r2(X2, X3).";
		System.out.println(s);
		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);

		List<CQGraph> ucq = rewriter.rewrite(g);

		int i = 0;
		for (CQGraph rg : ucq) {
			System.out.print(i + ": ");
			System.out.println(rg.toCQ());
			i++;
		}

		// Joiner.on("\n").appendTo(System.out, ucq);
	}

	/**
	 * star shape
	 * @throws IOException
	 */
	@Test
	public void test02() throws IOException {
		ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(2));
		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(4));
		ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(6));

		//ontology.getAndSubAtomAxioms().add(new AndSubAtomAxiom(4, 5));

		IndexedEnfContainer enfs = new IndexedEnfContainer();

		enfs.add(new EnforcedRelation(new TIntHashSet(new int[] { 0, 2 }), //
				new TIntHashSet(new int[] { 0, 1, 2 }), //
				new TIntHashSet(new int[] { 0, 3 })));
		enfs.add(new EnforcedRelation(new TIntHashSet(new int[]{0, 4}), //
                new TIntHashSet(new int[]{0, 1, 4}), //
                new TIntHashSet(new int[]{0, 5 })));
		enfs.add(new EnforcedRelation(new TIntHashSet(new int[]{0, 6}), //
                new TIntHashSet(new int[]{0, 1, 6}), //
                new TIntHashSet(new int[]{0, 7 })));

		CQGraphRewriter rewriter = new CQGraphRewriter(ontology, enfs);

		String s = "q(X1)  :-  r2(X1, X2),  r4(X1, X3), r6(X1, X4).";
		System.out.println(s);
		InternalCQParser parser = new InternalCQParser();
		parser.setQueryString(s);
		CQ cq = parser.getCq();
		CQGraph g = new CQGraph(cq);

		List<CQGraph> ucq = rewriter.rewrite(g);

		int i = 0;
		for (CQGraph rg : ucq) {
			System.out.print(i + ": ");
			System.out.println(rg.toCQ());
			i++;
		}

		// Joiner.on("\n").appendTo(System.out, ucq);
	}

	public static void main(String[] args) throws IOException {
		new CQGraphRewriterTest().test();
	}

	// @Test
	// public void testTransMap() {
	// Multimap<Tuple<Pair<Variable>, Integer>, Integer> mmap =
	// HashMultimap.create();
	// Variable x1 = new Variable(1);
	// Variable x2 = new Variable(2);
	// Variable x3 = new Variable(3);
	//
	// putAll(mmap, x1, x2, 2, ImmutableList.of(2, 4));
	// putAll(mmap, x1, x2, 4, ImmutableList.of(4, 6, 8));
	// putAll(mmap, x1, x3, 6, ImmutableList.of(6, 8, 10));
	//
	// Set<Tuple<Pair<Variable>, Integer>> allKeys = mmap.keySet();
	//
	// int i = 0;
	//
	// for (Set<Tuple<Pair<Variable>, Integer>> keys : Sets.powerSet(allKeys)) {
	// Multimap<Tuple<Pair<Variable>, Integer>, Integer> filteredMap =
	// Multimaps.filterKeys(mmap,
	// Predicates.in(keys));
	// List<Tuple<Pair<Variable>, Integer>> lstKeys = Lists.newArrayList();
	// List<Set<Integer>> candidates = Lists.newArrayList();
	// for (Tuple<Pair<Variable>, Integer> key : filteredMap.keySet()) {
	// lstKeys.add(key);
	// candidates.add(Sets.newHashSet(filteredMap.get(key)));
	// }
	// Set<List<Integer>> cartesianProduct = Sets.cartesianProduct(candidates);
	// int t = 0;
	// // System.out.print(lstKeys);
	// for (List<Integer> replacement : cartesianProduct) {
	// System.out.println(lstKeys + " -> " + replacement);
	// t++;
	// }
	// }
	//
	// }

	@Test
	public void testGraph() {
		DirectedOrderedSparseMultigraph<Integer, String> graph = new DirectedOrderedSparseMultigraph<Integer, String>();
		graph.addEdge("e1", 1, 2);
		graph.addEdge("e2", 1, 2);
		graph.addEdge("e3", 1, 1);
		System.out.println(graph);
	}
}
