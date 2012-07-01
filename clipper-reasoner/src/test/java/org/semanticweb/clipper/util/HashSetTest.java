package org.semanticweb.clipper.util;

import java.util.*;

import org.junit.Test;

public class HashSetTest {

	@Test
	public void test001() {
		Set<String> hs = new HashSet<String>();
		hs.add("String 1");
		hs.add("String 2");
		// hs.add("String 3");
		// hs.add("String 4");
		// hs.add("String 5");
		for (String s : hs) {
			for (String t : hs) {
				System.out.println(s + ", " + t);
			}
		}
	}

	@Test
	public void testNavigableMap() {
		Calendar now = Calendar.getInstance();
		Locale locale = Locale.getDefault();

		Map<String, Integer> names = now.getDisplayNames(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
		NavigableMap<String, Integer> nav = new TreeMap<String, Integer>(names);
		System.out.printf("Whole list:%n%s%n", nav);
		System.out.printf("Key floor before Sunday: %s%n", nav.floorKey("Sunday"));
	}

}
