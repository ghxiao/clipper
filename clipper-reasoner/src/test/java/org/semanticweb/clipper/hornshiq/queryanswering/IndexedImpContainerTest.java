package org.semanticweb.clipper.hornshiq.queryanswering;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplication;
import org.semanticweb.clipper.hornshiq.queryanswering.HornImplicationRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedHornImpContainer;
import org.semanticweb.clipper.util.BitSetUtil;
import org.semanticweb.clipper.util.BitSetUtilOpt;


public class IndexedImpContainerTest {

	final int MATCH = 100000;
	
	@Test
	public void test001() {
		TIntHashSet T0 = new TIntHashSet();
		T0.add(0);
		T0.add(1);
		T0.add(3);
		//
		// TIntIterator tI = T0.iterator();
		// while(tI.hasNext()){
		// int i = tI.next();
		// System.out.println(i);
		// }

		TIntHashSet T1 = new TIntHashSet();
		T1.add(1);
		T1.add(2);
		T1.add(3);
		TIntHashSet T2 = new TIntHashSet();
		T2.add(1);
		T2.add(4);
		HornImplication imp0 = new HornImplication(T1, 0);
		HornImplication imp1 = new HornImplication(T2, 1);
		HornImplication imp2 = new HornImplication(T1, 1);
		HornImplication imp3 = new HornImplication(T0, 2);

		IndexedHornImpContainer indexedImpContainer = new IndexedHornImpContainer();
		indexedImpContainer.add(imp0);
		indexedImpContainer.add(imp1);
		indexedImpContainer.add(imp2);
		indexedImpContainer.add(imp3);

		TIntHashSet candidateBody = new TIntHashSet();
		candidateBody.add(1);
		candidateBody.add(3);

		// Collection<HornImplicationRelation> result =
		// indexedImpContainer.matchHead(1);

		Collection<HornImplication> result = indexedImpContainer.matchBody(candidateBody);

		for (HornImplication imp : result) {
			System.out.println(imp);
		}
		System.out.println();

	}

	@Test
	public void testIndexedContainerSearch001() {

		long t1 = System.currentTimeMillis();

		IndexedHornImpContainer imps = new IndexedHornImpContainer();
		for (int i = 0; i < 10000; i++) {
			int size = 6 + i % 5;
			TIntHashSet body = new TIntHashSet();
			for (int k = 0; k < size; k++) {
				body.add((i + 3571 * k) % 97);
			}
			int head = i % 97;
			HornImplication imp = new HornImplication(body, head);
			imps.add(imp);
		}

		TIntHashSet candidateBody;

		Collection<HornImplication> result;

		long ms = System.currentTimeMillis();

		for (int t = 0; t < MATCH; t++) {
			// 1
			candidateBody = new TIntHashSet();

			for (int k = 0; k < t % 3 + 2; k++) {
				candidateBody.add(k * t % 97);
			}
			result = imps.matchBody(candidateBody);
		//	System.out.println("Indexed: " + t + " / size = " + result.size());
		}

		System.out.println("Pre Processing:" + (ms - t1));

		System.out.println("Matching:" + (System.currentTimeMillis() - ms));
		
		System.out.println("Total:" + (System.currentTimeMillis() - t1));
		

		// // 2
		// candidateBody = new BitSet();
		// candidateBody.set(1);
		// candidateBody.set(7);
		// result = imps.matchBody(candidateBody);
		// System.out.println(result.size());
		//
		// // 3
		// result = imps.matchHead(1);
		// System.out.println(result.size());

	}

	@Test
	public void testNaiveSearch001() {
		
		long t1 = System.currentTimeMillis();
		
		Set<HornImplicationRelation> imps = new HashSet<HornImplicationRelation>();
		for (int i = 0; i < 10000; i++) {
			int size = 6 + i % 5;
			TIntHashSet body = new TIntHashSet();
			for (int k = 0; k < size; k++) {
				body.add((i + 3571 * k) % 97);
			}
			int head = i % 97;
			HornImplicationRelation imp = new HornImplicationRelation(body, head);
			imps.add(imp);
		}

		BitSetUtil bu = new BitSetUtil();
		Collection<HornImplicationRelation> result;
		long ms = System.currentTimeMillis();
		for (int t = 0; t < MATCH; t++) {
			TIntHashSet candidateBody = new TIntHashSet();

			for (int k = 0; k < t % 3 + 2; k++) {
				candidateBody.add(k * t % 97); 
			}
			result = new ArrayList<HornImplicationRelation>();
			for (HornImplicationRelation imp : imps) {

				if (imp.getBody().containsAll(candidateBody)) {
					result.add(imp);
				}

			}

			//System.out.println("Naive: " + t + " / size = " + result.size());
		}
		System.out.println("Pre Processing:" + (ms - t1));

		System.out.println("matching:" + (System.currentTimeMillis() - ms));

		System.out.println("Total:" + (System.currentTimeMillis() - t1));
		
	}
}
