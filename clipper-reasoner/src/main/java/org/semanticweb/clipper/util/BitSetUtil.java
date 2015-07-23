package org.semanticweb.clipper.util;

import java.lang.reflect.Field;
import java.util.BitSet;

public class BitSetUtil {
	static Field wordsField = null;

	public BitSetUtil() {
		try {
			wordsField = BitSet.class.getDeclaredField("words");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wordsField.setAccessible(true);
	}

	/*
	 * Test if s1 is subset of s2
	 */
	public static boolean isSubset(BitSet s1, BitSet s2) {
		// BitSet s1_copy = new BitSet();
		// s1_copy = s1;
		BitSet s1_copy = (BitSet) s1.clone();
		// s1_copy.or(s1);
		s1_copy.andNot(s2);

		return s1_copy.isEmpty();
	}

	public static boolean isSubset2(BitSet s1, BitSet s2)  {

		
		try {
			long[] left = (long[]) wordsField.get(s1);
			long[] right = (long[]) wordsField.get(s2);

			for (int i = 0; i < left.length; i++) {
				long x = left[i];
				if (i >= right.length) {
					if (x != 0)
						return false;
				} else {
					long y = right[i];
					if ((x | y) != y) {
						return false;
					}
				}
			}
		} catch (IllegalArgumentException cannotHappen) {
		} catch (IllegalAccessException cannotHappen) {
		}
		

		// System.out.println(Arrays.toString(left) + " vs " +
		// Arrays.toString(right));

		return true;
		// return false;
	}

	public boolean isSubset(int i, BitSet s2) {
		BitSet s1 = new BitSet();
		s1.set(i);
		s1.andNot(s2);
		return s1.isEmpty();

	}

	public boolean isSubset(int i1, int i2) {
		return (i1 == i2);
	}
	
	
	
}
