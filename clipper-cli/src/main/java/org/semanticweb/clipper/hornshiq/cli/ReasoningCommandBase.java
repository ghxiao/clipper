package org.semanticweb.clipper.hornshiq.cli;

import java.util.List;

import lombok.Getter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Common base class for ComamndQuery and CommandRewrite
 * 
 * @author xiao
 * 
 */
@Getter
public abstract class ReasoningCommandBase extends CommandBase {

	@Parameter(description = "<ontology1.owl> ... <ontologyk.owl>")
	protected List<String> ontologyFiles;

	@Parameter(names = "-cq", description = "<query.cq> query file in CQ format")
	protected String cqFile;

	@Parameter(names = "-sparql", description = "<query.sparql> query file in SPARQL format")
	protected String sparqlFile;

	@Parameter(names = { "-r", "-rewriter" }, description = "rewriter", hidden = true)
	protected String rewriter = "new";
	
	public ReasoningCommandBase(JCommander jc) {
		super(jc);
		jc.addCommand(this);
	}

	protected String errorMessage;

}
