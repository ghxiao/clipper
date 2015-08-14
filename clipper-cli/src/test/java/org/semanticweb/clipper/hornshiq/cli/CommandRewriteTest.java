package org.semanticweb.clipper.hornshiq.cli;

import org.junit.Ignore;
import org.junit.Test;


public class CommandRewriteTest {

	@Test
    @Ignore
	public void testSparql() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
		//		"-sparql=src/test/resources/lubm-ex-20/q5.sparql"
				"-sparql=src/test/resources/lubm-ex-20/query4.sparql"
		};

		ClipperApp.main(args);
	}
	
	@Test
	public void testName() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-sparql=src/test/resources/lubm-ex-20/query/q5.sparql" , "-name=Fragment"};

		ClipperApp.main(args);
	}

	@Test
	public void testRewriteQ1() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-sparql=src/test/resources/lubm-ex-20/query/q1.sparql",
				"-output-datalog=src/test/resources/lubm-ex-20/query/q1.rew.dlv" };

		ClipperApp.main(args);
	}
	
	@Test
	public void testRewriteQ2() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-sparql=src/test/resources/lubm-ex-20/query/q2.sparql",
				"-output-datalog=src/test/resources/lubm-ex-20/query/q2.rew.dlv" };

		ClipperApp.main(args);
	}
	
	@Test
    @Ignore
	public void testCQ() {

		String[] args = { "rewrite", "src/test/resources/lubm-ex-20/LUBM-ex-20.owl",
				"-cq=src/test/resources/lubm-ex-20/q5.cq", "-output-datalog=src/test/resources/lubm-ex-20/q5-rew.dlv" };

		ClipperApp.main(args);
	}
	
	public static void main(String[] args){
		new CommandRewriteTest().testName();
	}
}
