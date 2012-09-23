package org.semanticweb.clipper.hornshiq.cli;

import static org.junit.Assert.*;

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
				.main("-v=8 -rewriter=old query src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
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

		int j = 0;
		int i = 0;
		ClipperApp.main(String.format(
				"-v=8 -rewriter=old rewrite src/test/resources/lubm-ex-20/University%d_%d.owl -a", j, i).split("\\ "));

	}

	public static void main(String[] args) {
		new ClipperAppTest().testRewriteAboxes();
	}

}
