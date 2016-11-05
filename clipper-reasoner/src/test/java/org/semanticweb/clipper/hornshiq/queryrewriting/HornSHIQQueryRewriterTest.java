package org.semanticweb.clipper.hornshiq.queryrewriting;

import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import gnu.trove.set.hash.TIntHashSet;
import org.junit.Test;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAndSubAtomAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperAtomSubSomeAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperHornSHIQOntology;
import org.semanticweb.clipper.hornshiq.ontology.ClipperSubPropertyAxiom;
import org.semanticweb.clipper.hornshiq.ontology.ClipperTransitivityAxiom;
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.hornshiq.rule.CQ;
import org.semanticweb.clipper.hornshiq.rule.InternalCQParser;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HornSHIQQueryRewriterTest {

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

        HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

        /*
         * q(X1) :- A1(X1), A2(X2), A3(X3), A4(X4), r1(X1, X4), r2(X1, X2), r3(X2, X3), r4(X3, X4).
         */
        String s = "q(X1) :- c3(X1), c4(X2), c5(X3), c6(X4), r6(X1, X4), r8(X1, X2), r10(X2, X3), r12(X3, X4).";
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


        assertTrue(ucq1BodyString.contains("c3(X1)"));
        assertTrue(ucq1BodyString.contains("r6(X1,X3)"));
        assertTrue(ucq1BodyString.contains("r8(X1,X3)"));
        assertTrue(ucq1BodyString.contains("c2(X3)"));
        assertTrue(ucq1BodyString.contains("c4(X3)"));
        assertTrue(ucq1BodyString.contains("c6(X3)"));

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

        HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

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

        assertEquals(ucq.size(), 2);

        String ucq1BodyString = ucq.get(1).toCQ().toString();

        assertEquals(1, ucq.get(1).getVertexCount());
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
     * q(X1) :- C(X2), r1(X1, X2), A(X2)
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
                new TIntHashSet(new int[]{0, 2}), //
                // T^2, T^2, r and r1^- and r2^-
                new TIntHashSet(new int[]{0, 1, 6, 7, 9}),
                // B
                new TIntHashSet(new int[]{7})));

        HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

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
    }

    /**
     * Example 5 in TR
     *
     *
     * Assume T = {r ⊑ r−, trans(r), A ⊑ ∃r.B, B ⊑ ∃r.C, C ⊑ D}.
     *
     * B ⊑ ∃(r ⊓ r−).(C ⊓ D) ∈ Ξ(T).
     *
     * Let ρ : q(x) ← A(x), r(x, y), C(y), D(z), r(y, z).
     *
     * Then ρ can be rewritten to :
     *
     * ρ1 : q(x) ← A(x), r(x, y), B(y).
     *
     * ρ2 : q(x) ← A(x)
     *
     *
     * The following encodings are used in the test case
     *
     *  A -> c2, A1 -> c3, A2 -> c4, A3 -> c5, A4 -> c6, B -> c7, C -> c8, D -> c9
     *
     *  r -> 4, r1 -> 6, r2 -> 8, r3 -> 10, r4 -> 12
     *
     *  x -> X1, y -> X2, z -> X3
     *
     */
    @Test
    public void test_Example5() throws IOException {
        ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

        ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(4));

        IndexedEnfContainer enfs = new IndexedEnfContainer();

        enfs.add(new EnforcedRelation(
                // T, B
                new TIntHashSet(new int[] { 0, 7 }), //
                // T^2, T^2, r, r^-
                new TIntHashSet(new int[] { 0, 1, 4, 5 }),
                // C, D
                new TIntHashSet(new int[] { 8, 9 })));

        enfs.add(new EnforcedRelation(
                // T, B
                new TIntHashSet(new int[] { 0, 2 }), //
                // T^2, T^2, r, r^-
                new TIntHashSet(new int[] { 0, 1, 4 }),
                // C, D
                new TIntHashSet(new int[] { 7 })));

        HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

        /*
         * ρ : q(x) ← A(x), r(x, y), C(y), D(z), r(y, z).
         */
        String s = "q(X1) :- c2(X1), r4(X1, X2), c8(X2), r4(X2, X3), c9(X3).";
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

        assertEquals(1, ucq.size());
    }


    /**
     * Adapted Example 5 in TR for testing (S5).(c).ii
     *
     *
     * Assume T = {r ⊑ r−, trans(r), A ⊑ ∃(r2 ⊓ r3).(B ⊓ C), B ⊓ C ⊑ ∃r.T}.
     *
     * B ⊓ C ⊑ ∃(r ⊓ r−).T
     *
     * Let ρ : q(x) ← A(x), r2(x, y), B(y), r3(x, z), C(z), r(y, z).
     *
     * Then ρ can be rewritten to :
     *
     * ρ1 : q(x) ← A(x).
     *
     *
     *
     * The following encodings are used in the test case
     *
     *  A -> c2, A1 -> c3, A2 -> c4, A3 -> c5, A4 -> c6, B -> c7, C -> c8, D -> c9
     *
     *  r -> 4, r1 -> 6, r2 -> 8, r3 -> 10, r4 -> 12
     *
     *  x -> X1, y -> X2, z -> X3
     *
     */
    @Test
    public void test_Example5_1() throws IOException {
        ClipperHornSHIQOntology ontology = new ClipperHornSHIQOntology();

        ontology.getTransitivityAxioms().add(new ClipperTransitivityAxiom(4));

        ontology.getSubPropertyAxioms().add(new ClipperSubPropertyAxiom(4, 5));

        IndexedEnfContainer enfs = new IndexedEnfContainer();

        enfs.add(new EnforcedRelation(
                // T, A
                new TIntHashSet(new int[] { 0, 2 }), //
                // T^2, T^2, r2, r3
                new TIntHashSet(new int[] { 0, 1, 8, 10 }),
                // B, C
                new TIntHashSet(new int[] { 7, 8 })));

        enfs.add(new EnforcedRelation(
                // T, B, C
                new TIntHashSet(new int[] { 0, 7, 8 }), //
                // T^2, T^2, r, r^-
                new TIntHashSet(new int[] { 0, 1, 4, 5 }),
                // T
                new TIntHashSet(new int[] { 0 })));

         HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

        /*
         * ρ : q(x) ← A(x), r2(x, y), B(y), r3(x, z), C(z), r(y, z).
         */
        String s = "q(X1) :- c2(X1), r8(X1, X2), c7(X2), r10(X1, X3), c8(X3), r4(X2, X3).";
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

        assertEquals(1, ucq.size());
        assertEquals(1, ucq.get(0).getVertexCount());
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

		HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

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

		HornSHIQQueryRewriter rewriter = new HornSHIQQueryRewriter(ontology, enfs);

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
		new HornSHIQQueryRewriterTest().test();
	}

	@Test
	public void testGraph() {
		DirectedOrderedSparseMultigraph<Integer, String> graph = new DirectedOrderedSparseMultigraph<Integer, String>();
		graph.addEdge("e1", 1, 2);
		graph.addEdge("e2", 1, 2);
		graph.addEdge("e3", 1, 1);
		System.out.println(graph);
	}
}
