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
    public void testRewriteQ10() {

        String[] args = { "rewrite" ,"-tq", "/Users/xiao/Development/clipper/clipper-cli/src/test/resources/kikot_bug_report/exp1.owl",
                //"-cq","/Users/xiao/Downloads/Q10.cq",
                "-cq","/Users/xiao/Development/clipper/clipper-cli/src/test/resources/kikot_bug_report/Q10.cq",
                "-d","/Users/xiao/Development/clipper/clipper-cli/src/test/resources/kikot_bug_report/Q10.rew.out"};

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
