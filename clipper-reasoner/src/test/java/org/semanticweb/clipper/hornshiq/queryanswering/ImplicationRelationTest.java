package org.semanticweb.clipper.hornshiq.queryanswering;

import java.util.BitSet;

import org.junit.Test;
import org.semanticweb.clipper.hornshiq.queryanswering.ImplicationRelation;


public class ImplicationRelationTest {

	@Test
	public void test001(){
		
		int a = 0;
		int b = 1;
		int c = 2;
		int d = 3;
		
		ImplicationRelation imp = new ImplicationRelation();
		BitSet t1 = new BitSet();
		t1.set(a);
		t1.set(d);
		BitSet t2 = new BitSet();
		t2.set(c);
		
		imp.setType1(t1);
		imp.setType2(t2);
		
		System.out.println(imp);
	}
}
