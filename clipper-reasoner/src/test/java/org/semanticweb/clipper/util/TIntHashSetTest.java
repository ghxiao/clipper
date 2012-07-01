package org.semanticweb.clipper.util;

import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

import org.junit.Test;

public class TIntHashSetTest {
	@Test
	public void test01(){
		TIntHashSet h= new TIntHashSet();
		h.add(1);
		h.add(2);
		h.add(5);
		System.out.println(h.size());
		TIntIterator iterator= h.iterator();
		while(iterator.hasNext())
		System.out.println(iterator.next());
	
	}
}
