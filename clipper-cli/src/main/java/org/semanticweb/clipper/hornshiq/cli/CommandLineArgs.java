package org.semanticweb.clipper.hornshiq.cli;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {
	@Parameter(names = { "-v", "-verbose" }, description = "Level of verbosity")
	private Integer verbose = 1;

	public Integer getVerbose() {
		return verbose;
	}
}

@Parameters(commandNames = { "query" }, separators = "=", commandDescription = "answerting conjunctive query")
class CommandQuery {
	@Parameter(description = "<ontology.owl> <cq.sparql>")
	private List<String> files;

	@Parameter(names = "-dlv", description = "the path to dlv")
	private String dlvPath;

	@Parameter(names = "-clingo", description = "the path to clingo", hidden = true)
	private String clingoPath;

	public List<String> getFiles() {
		return files;
	}

	public String getDlvPath() {
		return dlvPath;
	}

	public String getClingoPath() {
		return clingoPath;
	}

	public void validate() {
		// TODO
	}

}

@Parameters(commandNames = { "rewrite" }, separators = "=", commandDescription = "rewrite the query w.r.t. the ontology, and generate a datalog program")
class CommandRewrite {

	@Parameter(description = "<ontology.owl> [ <cq.sparql> ] ")
	private List<String> files;

	@Parameter(names = { "--tbox-only", "-t" }, description = "only rewrite TBox")
	private boolean rewritingTBoxOnly;

	@Parameter(names = { "--abox-only", "-a" }, description = "only rewrite ABox")
	private boolean rewritingABoxOnly;

	@Parameter(names = { "--ontology-only", "-o" }, description = "only rewrite ontology (= TBox + ABox)")
	private boolean rewritingOntologyOnly;

	@Parameter(names = { "--tbox-and-query", "-tq" }, description = "only rewrite TBox and query")
	private boolean rewritingTBoxAndQuery;

	@Parameter(names = { "--ontology-and-query", "-oq" }, description = "rewrite ontology (= TBox + ABox) and query")
	private boolean rewritingOntologyAndQuery;

	@Parameter(names = { "--remove-redundancy", "-r" }, description = "remove redundancy rules w.r.t the query")
	private boolean removingRedundancyRules;

	@Parameter(names = { "--output-directory", "-d" }, description = "output directory")
	private String outputDirectory = ".";

	public List<String> getFiles() {
		return files;
	}

	public boolean isRewritingTBoxOnly() {
		return rewritingTBoxOnly;
	}

	public boolean isRewritingABoxOnly() {
		return rewritingABoxOnly;
	}

	public boolean isRewritingOntologyOnly() {
		return rewritingOntologyOnly;
	}

	public boolean isRewritingTBoxAndQuery() {
		return rewritingTBoxAndQuery;
	}

	public boolean isRewritingOntologyAndQuery() {
		return rewritingOntologyAndQuery;
	}

	public boolean isRemovingRedundancyRules() {
		return removingRedundancyRules;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void validate() {
		// TODO
	}

}

@Parameters(commandNames = { "help" }, commandDescription = "Print the usage")
class CommandHelp {

}