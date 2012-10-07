package org.semanticweb.clipper.hornshiq.cli;


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