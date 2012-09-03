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

		ClipperApp.main("-v=8 -rewriter=old query src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
				.split("\\ "));
	}

	public static void main(String[] args) {
		new ClipperAppTest().testQuery();
	}

}
