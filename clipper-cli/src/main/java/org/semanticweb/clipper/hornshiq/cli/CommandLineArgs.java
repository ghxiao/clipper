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

@Getter
@Parameters(commandNames = { "query" }, separators = "=", commandDescription = "answerting conjunctive query")
class CommandQuery {

	@Parameter(description = "<ontology1.owl> ... <ontologyk.owl>")
	private List<String> ontologyFiles;

	@Parameter(names = "-cq", description = "<query.cq> query file in CQ format")
	private String cqFile;

	@Parameter(names = "-sparql", description = "<query.sparql> query file in SPARQL format")
	private String sparqlFile;

	@Parameter(names = "-dlv", description = "the path to dlv")
	private String dlvPath;

	@Parameter(names = { "-f", "-output-format" }, description = "output format, possible values: { table | csv | atoms | html }")
	private String outputFormat = "table";

	private String errorMessage;


	// TODO: will be supported in the future
	@Parameter(names = "-clingo", description = "the path to clingo", hidden = true)
	private String clingoPath;


	public boolean validate() {
		if (ontologyFiles.size() == 0) {
			errorMessage = "At least one ontology file should be specified";
			return false;
		}

		if ((sparqlFile != null && cqFile == null) || (sparqlFile == null && cqFile != null)) {
			errorMessage = "One and only one query file (either in CQ or in SPARQL format) must be provided";
		}

		return true;
	}

}

@Getter
@Parameters(commandNames = { "rewrite" }, separators = "=", commandDescription = "rewrite the query w.r.t. the ontology, and generate a datalog program")
class CommandRewrite {

	@Parameter(description = "<ontology.owl> [ <cq.sparql> ] ")
	private List<String> files;

	@Parameter(names = { "-tbox-only", "-t" }, description = "only rewrite TBox")
	private boolean rewritingTBoxOnly;

	@Parameter(names = { "-abox-only", "-a" }, description = "only rewrite ABox")
	private boolean rewritingABoxOnly;

	@Parameter(names = { "-ontology-only", "-o" }, description = "only rewrite ontology (= TBox + ABox)")
	private boolean rewritingOntologyOnly;

	@Parameter(names = { "-tbox-and-query", "-tq" }, description = "only rewrite TBox and query")
	private boolean rewritingTBoxAndQuery;

	@Parameter(names = { "-ontology-and-query", "-oq" }, description = "rewrite ontology (= TBox + ABox) and query")
	private boolean rewritingOntologyAndQuery;

//	@Parameter(names = { "--remove-redundancy", "-r" }, description = "remove redundancy rules w.r.t the query")
//	private boolean removingRedundancyRules;
	@Parameter(names = { "--output-directory", "-d" }, description = "output directory")
	private String outputDirectory = ".";

	public void validate() {
		// TODO
	}

}

@Parameters(commandNames = { "help" }, commandDescription = "Print the usage")
class CommandHelp {

}