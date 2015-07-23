package org.semanticweb.clipper.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

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

	@Test
	public void testEquals(){
		Set<Set<String>> setOfSet1 = new HashSet<>();
		setOfSet1.add(new HashSet<>(Arrays.asList("a", "b")));
		setOfSet1.add(new HashSet<>(Arrays.asList("d", "c")));

		Set<Set<String>> setOfSet2 = new HashSet<>();
		setOfSet2.add(new HashSet<>(Arrays.asList("c", "d")));
		setOfSet2.add(new HashSet<>(Arrays.asList("b", "a")));

		assertEquals(setOfSet1, setOfSet2);
	}


}
