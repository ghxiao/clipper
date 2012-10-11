package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Test;

public class CommandRewriteTest {

	@Test
	public void testSparql() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-sparql=src/test/resources/lubm-ex-20/q5.sparql" };

		ClipperApp.main(args);
	}
	
	@Test
	public void testCQ() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-cq=src/test/resources/lubm-ex-20/q5.cq" };

		ClipperApp.main(args);
	}
}
