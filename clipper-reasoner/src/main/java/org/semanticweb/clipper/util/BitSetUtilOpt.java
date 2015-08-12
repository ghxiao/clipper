package org.semanticweb.clipper.util;

import gnu.trove.set.hash.TIntHashSet;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;

import java.util.BitSet;


public class BitSetUtilOpt {

    public static TIntHashSet getInstanceWithTop(TIntHashSet in) {
        TIntHashSet ret = new TIntHashSet(in);
        ret.add(ClipperManager.getInstance().getThing());
        return ret;
    }

    public static TIntHashSet getInstanceWithTop(int in) {
        TIntHashSet ret = new TIntHashSet(in);
        ret.add(ClipperManager.getInstance().getThing());
        return ret;
    }

    public static TIntHashSet getInstanceWithTop() {
        TIntHashSet ret = new TIntHashSet();
        ret.add(ClipperManager.getInstance().getThing());
        return ret;
    }


    public static boolean isSubset(BitSet s1, BitSet s2) {

//		for (int i=0 ; i < s1.size();i++) {
//			if (s1.get(i)&& !s2.get(i))  return false;
//		}
//		return true;
//		
        BitSet s1_copy = new BitSet();//(BitSet) s1;//.clone();
        s1_copy.or(s1);
        s1_copy.andNot(s2);
        return s1_copy.isEmpty();
    }

    public static int inverseRole(int r) {
        if (r % 2 == 1) {
            return r - 1;
        } else {
            return r + 1;
        }
    }


}
