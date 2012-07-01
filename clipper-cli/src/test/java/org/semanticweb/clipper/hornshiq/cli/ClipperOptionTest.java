package org.semanticweb.clipper.hornshiq.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.beust.jcommander.JCommander;

public class ClipperOptionTest {

	@Test
	public void test() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery();
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite();
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp();
		jc.addCommand(commandHelp);
		// CommandSaturate commandSaturate = new CommandSaturate();
		// jc.addCommand(commandSaturate);

		jc.setProgramName("clipper.sh");

		// jc.parse(args);

		jc.usage();
	}

	@Test
	public void testCommandQuery001() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery();
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite();
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp();
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");

		String[] args = { "query", "ontology.owl", "cq.sparql" };

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");

		assertEquals(Arrays.asList("ontology.owl", "cq.sparql"), commandQuery.getFiles());
	}
	
	@Test
	public void testCommandQuery002() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery();
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite();
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp();
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");

		String[] args = { "query", "-dlv=/usr/bin/dlv", "ontology.owl", "cq.sparql" };

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");

		assertEquals(Arrays.asList("ontology.owl", "cq.sparql"), commandQuery.getFiles());
		assertEquals(commandQuery.getDlvPath(), "/usr/bin/dlv");
	}


	@Test
	public void testCommandRewrite() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery();
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite();
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp();
		jc.addCommand(commandHelp);
		// CommandSaturate commandSaturate = new CommandSaturate();
		// jc.addCommand(commandSaturate);

		jc.setProgramName("clipper.sh");

		String[] args = { "rewrite", "--tbox-only", "ontology.owl" };

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "rewrite");
		assertTrue(commandRewrite.isRewritingTBoxOnly());
		// assertEquals(Arrays.asList("ontology.owl"), commandSaturate.files);
	}
}
