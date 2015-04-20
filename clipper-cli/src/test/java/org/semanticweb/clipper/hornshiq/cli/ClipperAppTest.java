package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Test;

public class ClipperAppTest {

	@Test
	public void test() {
		ClipperApp
				.main("-v=2 rewrite -o /Users/xiao/Dropbox/krrepos/xiao/drafts/idmus/sa/data/BacteriaGeneInteractions.owl"
						.split("\\ "));
	}

	@Test
	public void testQuery() {

		ClipperApp
				.main(("-v=8 query -rewriter=old  src/test/resources/university.owl " +
                        "-sparql src/test/resources/university-q1.sparql")
						.split("\\ "));
	}
	
	@Test
	public void testRewriteTboxAndQuery() {

		ClipperApp
				.main("-v=8 -rewriter=old rewrite -tq src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
						.split("\\ "));
	}
	
	@Test
	public void testRewriteAll() {

		ClipperApp
				.main("-v=8 -rewriter=old rewrite -oq src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
						.split("\\ "));
	}
	
	@Test
	public void testRewriteABoxOnly() {

		ClipperApp
				.main("-v=8 -rewriter=old rewrite -a src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
						.split("\\ "));
	}
	
	@Test
	public void testRewriteTBoxOnly() {

		ClipperApp
				.main("-v=8 -rewriter=old rewrite -t src/test/resources/university.owl"
						.split("\\ "));
	}
	
	@Test
	public void testRewriteOntologyOnly() {

		ClipperApp
				.main("-v=8 -rewriter=old rewrite -o src/test/resources/university.owl"
						.split("\\ "));
	}


	@Test
	public void testCompletion() {

		ClipperApp.main("-v=8 -rewriter=old rewrite src/test/resources/lubm-ex-20/LUBM-ex-20.owl -o".split("\\ "));
	}

	@Test
	public void testRewriteAboxes() {
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j < 1; j++) {
				System.out.println(String.format("%d, %d", i, j));
				ClipperApp.main(String.format(
						"-v=0 -rewriter=old rewrite src/test/resources/lubm-ex-20/University%d_%d.owl -a", j, i).split(
						"\\ "));
			}
		}
	}

	@Test
	public void testRewriteAbox() {
		long t1 = System.currentTimeMillis();
		int j = 0;
		int i = 0;
		ClipperApp.main(String.format(
				"-v=0 rewrite -rewriter=old src/test/resources/lubm-ex-20/University%d_%d.owl -a", j, i).split("\\ "));
		long t2 = System.currentTimeMillis();
		System.out.println("TIME: " + (t2 - t1));


	}

	@Test
	public void testRewriteNPD() {
		ClipperApp
				.main("-v=8 rewrite -o /Users/xiao/npd-v2.owl -d /Users/xiao/npd-v2.dl"
						.split(" "));
	}

	public static void main(String[] args) {
		new ClipperAppTest().testQuery();
		//new ClipperAppTest().testRewriteAboxes();
		//new ClipperAppTest().testRewriteTboxAndQuery();
		//new ClipperAppTest().testRewriteAbox();
		//new ClipperAppTest().testRewriteTBoxOnly();
		//new ClipperAppTest().testRewriteOntologyOnly();
		//new ClipperAppTest().testRewriteAll();
	}

}
