package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Ignore;
import org.junit.Test;

public class ClipperAppTest {

	@Test
	public void testQuery() {

//		ClipperApp
//				.main(("-v=8 query  src/test/resources/university.owl " +
//                        "-sparql src/test/resources/university-q1.sparql")
//						.split("\\ "));
	}
	
	@Test
	public void testRewriteTboxAndQuery() {

//		ClipperApp
//				.main("-v=8 rewrite -tq src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
//						.split("\\ "));
	}
	
	@Test
	public void testRewriteAll() {

//		ClipperApp
//				.main("-v=8 rewrite -oq src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
//						.split("\\ "));
	}
	
	@Test
	public void testRewriteABoxOnly() {

//		ClipperApp
//				.main("-v=8 rewrite -a src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
//						.split("\\ "));
	}
	
	@Test
	public void testRewriteTBoxOnly() {

//		ClipperApp
//				.main("-v=8 rewrite -t src/test/resources/university.owl"
//						.split("\\ "));
	}
	
	@Test
	public void testRewriteOntologyOnly() {

//		ClipperApp
//				.main("-v=8 rewrite -o src/test/resources/university.owl"
//						.split("\\ "));
	}


	@Test
	public void testCompletion() {

//		ClipperApp.main("-v=8 rewrite src/test/resources/lubm-ex-20/LUBM-ex-20.owl -o".split("\\ "));
	}

	@Test
    @Ignore
	public void testRewriteAboxes() {
		for (int i = 0; i <= 14; i++) {
			for (int j = 0; j < 1; j++) {
				System.out.println(String.format("%d, %d", i, j));
//				ClipperApp.main(String.format(
//						"-v=0 rewrite src/test/resources/lubm-ex-20/University%d_%d.owl -a", j, i).split(
//						"\\ "));
			}
		}
	}

	@Test
    @Ignore
	public void testRewriteAbox() {
		long t1 = System.currentTimeMillis();
		int j = 0;
		int i = 0;
//		ClipperApp.main(String.format(
//				"-v=0 rewrite src/test/resources/lubm-ex-20/University%d_%d.owl -a", j, i).split("\\ "));
		long t2 = System.currentTimeMillis();
		System.out.println("TIME: " + (t2 - t1));


	}

    @Ignore
	@Test
	public void testRewriteNPD() {
//		ClipperApp
//				.main("-v=8 rewrite -o /Users/xiao/npd-v2.owl -d /Users/xiao/npd-v2.dl"
//						.split(" "));
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

	@Test
	public void test_new() throws Exception {

//		ClipperApp
//				.main(("-v=9 testOptimisation /home/bato/data/ISWC-Benchmarks/OxfordRepo/succeded_under_2_min_optimised_version/00794.owl")
//						.split("\\ "));


		ClipperApp
				.main(("-v -3 testOptimisation -x /home/bato/data/ISWC-Benchmarks/OxfordRepo/test/00725.owl")
						.split("\\ "));

//		ClipperApp
//				.main((" testOptimisation -optimised src/test/resources/testActivators/testActivatorExtractio.owl")
//						.split("\\ "));


//		ClipperApp
//				.main(("-v -1 testOptimisation -optimised src/test/resources/testActivators/testActivatorExtraction2.owl")
//						.split("\\ "));
//
//		ClipperApp
//				.main(("-v -1 testOptimisation src/test/resources/testActivators/testActivatorExtraction2.owl")
//						.split("\\ "));

	}

}
