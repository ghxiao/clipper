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
import org.semanticweb.clipper.hornshiq.queryanswering.EnforcedRelation;
import org.semanticweb.clipper.hornshiq.queryanswering.IndexedEnfContainer;
import org.semanticweb.clipper.util.BitSetUtil;
import org.semanticweb.clipper.util.BitSetUtilOpt;


public class IndexedEnfContainerTest {

	final int MATCHES = 100000;

	@Test
	public void test001() {
		TIntHashSet T0 = new TIntHashSet();
		T0.add(0);
		T0.add(1);
		T0.add(3);
		TIntHashSet T1 = new TIntHashSet();
		T1.add(1);
		T1.add(2);
		T1.add(3);
		TIntHashSet T2 = new TIntHashSet();
		T2.add(1);
		T2.add(4);

		TIntHashSet R0 = new TIntHashSet();
		R0.add(0);
		R0.add(2);
		R0.add(3);
		R0.add(1);

		TIntHashSet R1 = new TIntHashSet();
		R0.add(0);
		R1.add(1);
		R1.add(4);

		EnforcedRelation enf0 = new EnforcedRelation(T1, R0, T2);
		EnforcedRelation enf1 = new EnforcedRelation(T1, R1, T1);
		EnforcedRelation enf2 = new EnforcedRelation(T1, R0, T0);
		EnforcedRelation enf3 = new EnforcedRelation(T0, R1, T1);

		IndexedEnfContainer indexedEnfContainer = new IndexedEnfContainer();
		indexedEnfContainer.add(enf0);
		indexedEnfContainer.add(enf1);
		indexedEnfContainer.add(enf2);
		indexedEnfContainer.add(enf3);

		TIntHashSet candidateRole = new TIntHashSet();
		candidateRole.add(1);
		candidateRole.add(0);

		TIntHashSet candidateType = new TIntHashSet();
		candidateType.add(1);
		candidateType.add(3);

		// Collection<HornImplicationRelation> result =
		// indexedImpContainer.matchHead(1);

		Collection<EnforcedRelation> result;
		// result= indexedEnfContainer.matchType2(candidateBody);

		// for (EnforcedRelation enf : result) {
		// System.out.println(enf);
		// }
		// System.out.println();

		result = indexedEnfContainer.matchRolesAndType2(candidateRole, candidateType);
		for (EnforcedRelation enf : result) {
			System.out.println(enf);
		}
		System.out.println();
	}

	@Test
	public void testIndexedContainerSearch001() {

		long t1 = System.currentTimeMillis();

		IndexedEnfContainer enfs = new IndexedEnfContainer();
		for (int i = 0; i < 10000; i++) {
			int size = 6 + i % 5;
			TIntHashSet type1 = new TIntHashSet();
			for (int k = 0; k < size; k++) {
				type1.add((i + 3571 * k) % 97);
			}

			int head = i % 97;
			EnforcedRelation enf = new EnforcedRelation(type1, type1, type1);
			enfs.add(enf);
		}

		TIntHashSet candidateBody;

		Collection<EnforcedRelation> result;

		long ms = System.currentTimeMillis();

		for (int t = 0; t < MATCHES; t++) {
			// 1
			candidateBody = new TIntHashSet();

			for (int k = 0; k < t % 3 + 2; k++) {
				candidateBody.add(k * t % 97);
			}
			result = enfs.matchType1(candidateBody);
			System.out.println("Indexed: " + t + " / size = " + result.size());
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

		Set<EnforcedRelation> enfs = new HashSet<EnforcedRelation>();
		for (int i = 0; i < 10000; i++) {
			int size = 6 + i % 5;
			TIntHashSet type1 = new TIntHashSet();
			for (int k = 0; k < size; k++) {
				type1.add((i + 3571 * k) % 97);
			}
			EnforcedRelation enf = new EnforcedRelation(type1, type1, type1);
			enfs.add(enf);
		}

		Collection<EnforcedRelation> result;
		long ms = System.currentTimeMillis();
		for (int t = 0; t < MATCHES; t++) {
			TIntHashSet candidateBody = new TIntHashSet();

			for (int k = 0; k < t % 3 + 2; k++) {
				candidateBody.add(k * t % 97);
			}
			result = new ArrayList<EnforcedRelation>();
			for (EnforcedRelation enf : enfs) {

				if (enf.getType1().containsAll(candidateBody)) {
					result.add(enf);
				}

			}

			System.out.println("Naive: " + t + " / size = " + result.size());
		}
		System.out.println("Pre Processing:" + (ms - t1));

		System.out.println("matching:" + (System.currentTimeMillis() - ms));

		System.out.println("Total:" + (System.currentTimeMillis() - t1));

	}
}
