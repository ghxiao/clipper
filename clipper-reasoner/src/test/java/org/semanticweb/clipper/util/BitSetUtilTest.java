package org.semanticweb.clipper.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.clipper.util.BitSetUtil;

public class BitSetUtilTest {
	BitSet s1;
	BitSet s2;
	BitSet s3;
	BitSet s4;

	long T1 = 0, T2 = 0;

	@Before
	public void setup() {
		s1 = new BitSet();
		s1.set(1);
		s1.set(3);

		s2 = new BitSet();
		s2.set(1);
		s2.set(2);
		s2.set(3);

		s3 = new BitSet();
		s3.set(3);
		s4 = new BitSet();
		s4.set(4);

	}

	@Test
	public void test01() {

		BitSetUtil bu = new BitSetUtil();
		assertTrue(bu.isSubset(s1, s2));
		System.out.print(s1);

		assertTrue(bu.isSubset(1, 1));
		assertTrue(bu.isSubset(1, s2));
		assertTrue(bu.isSubset(3, s2));
		assertFalse(bu.isSubset(s3, s4));
		// BitSet set = new BitSet();
		// set.set(1);
		// set.set(2);
		// set.set(3);
		// set.set(4);
		// set.set(5);
		// System.out.println("set:" + set);
		// BitSet set2 = new BitSet();
		// set2.set(1);
		// set2.set(3);
		// set2.set(5);
		// set2.set(7);
		// System.out.println("set2:" + set2);
		// BitSet set3 = (BitSet) set.clone();
		// System.out.println("set3 = set.clonce():" + set3);
		// set3.and(set2);
		// System.out.println("set.and(set2) :" + set3);
		// set3 = (BitSet) set.clone();
		// System.out.println("set = set.clonce():" + set3);
		// set3.or(set2);
		// System.out.println("set.or(set2);" + set3);
		// set3 = (BitSet) set.clone();
		// set3.xor(set2);
		// System.out.println("set.xor(set2);" + set3);
		// set3 = (BitSet) set.clone();
		// set3.andNot(set2);
		// System.out.println("set.andNot(set2);" + set3);
		// // set3.andNot(null);
		// BitSet bs3 = new BitSet();
		// bs3.set(3);
		// BitSet bs4 = new BitSet();
		// bs4.set(4);
		// bs3.andNot(bs4);
		// System.out.println(bs3.isEmpty());

	}

	@Test
	public void test02() throws SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {

		BitSetUtil bu = new BitSetUtil();

		s1 = new BitSet();
		s1.set(1);
		s1.set(100);
		s2 = new BitSet();
		s2.set(1);
		s2.set(100);
		s2.set(1000);

		assertTrue(bu.isSubset2(s1, s2));
		assertFalse(bu.isSubset2(s2, s1));

		// System.out.print(s1);
		//
		// assertTrue(bu.isSubset(1, 1));
		// assertTrue(bu.isSubset(1, s2));
		// assertTrue(bu.isSubset(3, s2));
		// assertFalse(bu.isSubset(s3, s4));
	}

	@Test
	public void testRandom() throws SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {
		

		List<BitSet> testCases = new ArrayList<BitSet>();

		BitSetUtil bu = new BitSetUtil();

		for (int count = 0; count <= 100000; count++) {
			Random rand = new Random();
			int length;

			length = rand.nextInt(20);

			BitSet s1 = new BitSet();
			for (int i = 0; i < length; i++) {
				s1.set(rand.nextInt(1000));
			}

			length = rand.nextInt(10);

			BitSet s2 = new BitSet();
			for (int i = 0; i < length; i++) {
				s2.set(rand.nextInt(1000));
			}

			testCases.add(s1);
			testCases.add(s2);

		}

		// System.out.println(s1 + " vs " + s2);
		boolean r1, r2;
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < testCases.size(); i += 2) {
			BitSet s1 = testCases.get(i);
			BitSet s2 = testCases.get(i + 1);
			 r1 = BitSetUtil.isSubset(s1, s2);
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < testCases.size(); i += 2) {
			BitSet s1 = testCases.get(i);
			BitSet s2 = testCases.get(i + 1);
			r2 = BitSetUtil.isSubset2(s1, s2);
		}
		//assertEquals(r1,  r2);
		long t2 = System.currentTimeMillis();
		System.out.println((t1 - t0) + " vs " + (t2 - t1));

		T1 += t1 - t0;
		T2 += t2 - t1;

	}

	@Test
	public void testRandomLoop() throws SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {
		for (int i = 0; i < 20; i++) {
			testRandom();
		}
		System.out.println(T1 + " vs " + T2);
	}
}
