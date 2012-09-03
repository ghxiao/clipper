package org.semanticweb.clipper.hornshiq.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.beust.jcommander.JCommander;
<<<<<<< HEAD
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
=======
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e

public class ClipperOptionTest {

	@Test
<<<<<<< HEAD
	public void testUsage() {
=======
	public void test() {
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e
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

<<<<<<< HEAD
		String[] args = { "query", "ontology.owl", "abox.owl", "-sparql", "query.sparql" };
=======
		String[] args = { "query", "ontology.owl", "cq.sparql" };
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");

<<<<<<< HEAD
		assertEquals(Arrays.asList("ontology.owl", "abox.owl"), commandQuery.getOntologyFiles());
		
		assertEquals("query.sparql", commandQuery.getSparqlFile());
=======
		assertEquals(Arrays.asList("ontology.owl", "cq.sparql"), commandQuery.getFiles());
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e
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

<<<<<<< HEAD
		String[] args = { "query", "-dlv=/usr/bin/dlv", "ontology.owl", "-cq", "query.cq" };
=======
		String[] args = { "query", "-dlv=/usr/bin/dlv", "ontology.owl", "cq.sparql" };
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "query");

<<<<<<< HEAD
		assertEquals(Arrays.asList("ontology.owl"), commandQuery.getOntologyFiles());
		assertEquals("query.cq", commandQuery.getCqFile());
=======
		assertEquals(Arrays.asList("ontology.owl", "cq.sparql"), commandQuery.getFiles());
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e
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

<<<<<<< HEAD
		String[] args = { "rewrite", "-tbox-only", "ontology.owl" };
=======
		String[] args = { "rewrite", "--tbox-only", "ontology.owl" };
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e

		jc.parse(args);

		assertEquals(jc.getParsedCommand(), "rewrite");
		assertTrue(commandRewrite.isRewritingTBoxOnly());
		// assertEquals(Arrays.asList("ontology.owl"), commandSaturate.files);
	}
<<<<<<< HEAD
	
	@Test(expected=MissingCommandException.class)
	public void testBadCommand() {
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

		String[] args = { "blabla", "-tbox-only", "ontology.owl" };

		jc.parse(args);
	}
	
	@Test(expected=ParameterException.class)
	public void testBadParameter() {
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

		String[] args = { "query", "-tbox-only", "ontology.owl" };

		jc.parse(args);
	}
=======
>>>>>>> 2ba085c6629a2ffb4be77fa1b35001a97ef9952e
}
