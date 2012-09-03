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

<<<<<<< HEAD
		ClipperApp.main("-v=8 -rewriter=old query src/test/resources/university.owl -sparql src/test/resources/university-q1.sparql"
=======
		ClipperApp.main("-v=8 -rewriter=new query src/test/resources/university.owl src/test/resources/university-q1.sparql"
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e
				.split("\\ "));
	}

	public static void main(String[] args) {
		new ClipperAppTest().testQuery();
	}

}
