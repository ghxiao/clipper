package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClipperOptionTest {

	@Test

	public void testUsage() {

		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
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

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");


		String[] args = { "query", "ontology.owl", "abox.owl", "-sparql", "query.sparql" };


		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");


		assertEquals(Arrays.asList("ontology.owl", "abox.owl"), commandQuery.getOntologyFiles());
		
		assertEquals("query.sparql", commandQuery.getSparqlFile());

	}
	
	@Test
	public void testCommandQuery002() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");


		String[] args = { "query", "-dlv=/usr/bin/dlv", "ontology.owl", "-cq", "query.cq" };


		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");


		assertEquals(Arrays.asList("ontology.owl"), commandQuery.getOntologyFiles());
		assertEquals("query.cq", commandQuery.getCqFile());

		assertEquals(commandQuery.getDlvPath(), "/usr/bin/dlv");
	}


	@Test
	public void testCommandRewrite() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);
		// CommandSaturate commandSaturate = new CommandSaturate();
		// jc.addCommand(commandSaturate);

		jc.setProgramName("clipper.sh");


		String[] args = { "rewrite", "-tbox-only", "ontology.owl" };


		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "rewrite");
		assertTrue(commandRewrite.isRewritingTBoxOnly());
		// assertEquals(Arrays.asList("ontology.owl"), commandSaturate.files);
	}
	
	@Test(expected=MissingCommandException.class)
	public void testBadCommand() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);
		// CommandSaturate commandSaturate = new CommandSaturate();
		// jc.addCommand(commandSaturate);

		jc.setProgramName("clipper.sh");

		String[] args = { "blabla", "-tbox-only", "ontology.owl" };

		jc.parse(args);
	}
	
	@Test(expected=ParameterException.class)
	public void testBadParameter() {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);
		// CommandSaturate commandSaturate = new CommandSaturate();
		// jc.addCommand(commandSaturate);

		jc.setProgramName("clipper.sh");

		String[] args = { "query", "-tbox-only", "ontology.owl" };

		jc.parse(args);
	}

}
