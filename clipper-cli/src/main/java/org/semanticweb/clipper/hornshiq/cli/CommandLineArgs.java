package org.semanticweb.clipper.hornshiq.cli;

import java.util.List;

import lombok.Getter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {
	@Getter
	@Parameter(names = { "-v", "-verbose" }, description = "Level of verbosity")
	private Integer verbose = 1;

	@Getter
	@Parameter(names = { "-r", "-rewriter" }, description = "rewriter", hidden = true)
	private String rewriter = "new";
}

@Parameters(commandNames = { "query" }, separators = "=", commandDescription = "answerting conjunctive query")
class CommandQuery {
	@Getter
	@Parameter(description = "<ontology.owl> <cq.sparql>")
	private List<String> files;

	@Getter
	@Parameter(names = "-dlv", description = "the path to dlv")
	private String dlvPath;

	@Getter
	@Parameter(names = { "-f", "--output-format" }, description = "output format, possible values: { table | csv | atoms | html }")
	private String outputFormat = "table";

	// TODO: will be supported in the future
	@Getter
	@Parameter(names = "-clingo", description = "the path to clingo", hidden = true)
	private String clingoPath;

	public void validate() {
		// TODO
	}

}

@Parameters(commandNames = { "rewrite" }, separators = "=", commandDescription = "rewrite the query w.r.t. the ontology, and generate a datalog program")
class CommandRewrite {

	@Getter
	@Parameter(description = "<ontology.owl> [ <cq.sparql> ] ")
	private List<String> files;

	@Getter
	@Parameter(names = { "--tbox-only", "-t" }, description = "only rewrite TBox")
	private boolean rewritingTBoxOnly;

	@Getter
	@Parameter(names = { "--abox-only", "-a" }, description = "only rewrite ABox")
	private boolean rewritingABoxOnly;

	@Getter
	@Parameter(names = { "--ontology-only", "-o" }, description = "only rewrite ontology (= TBox + ABox)")
	private boolean rewritingOntologyOnly;

	@Getter
	@Parameter(names = { "--tbox-and-query", "-tq" }, description = "only rewrite TBox and query")
	private boolean rewritingTBoxAndQuery;

	@Getter
	@Parameter(names = { "--ontology-and-query", "-oq" }, description = "rewrite ontology (= TBox + ABox) and query")
	private boolean rewritingOntologyAndQuery;

	@Getter
	@Parameter(names = { "--remove-redundancy", "-r" }, description = "remove redundancy rules w.r.t the query")
	private boolean removingRedundancyRules;

	@Getter
	@Parameter(names = { "--output-directory", "-d" }, description = "output directory")
	private String outputDirectory = ".";

	public void validate() {
		// TODO
	}

}

@Parameters(commandNames = { "help" }, commandDescription = "Print the usage")
class CommandHelp {

}