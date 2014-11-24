package org.semanticweb.clipper.hornshiq.cli;


import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {
	@Parameter(names = { "-v", "-verbose" }, description = "Level of verbosity")
	private Integer verbose = 1;

	public Integer getVerbose() {
		return this.verbose;
	}
}