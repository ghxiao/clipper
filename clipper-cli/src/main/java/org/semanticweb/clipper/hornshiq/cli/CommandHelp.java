package org.semanticweb.clipper.hornshiq.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = { "help", "-h", "--help", "-help" }, commandDescription = "Print the usage")
class CommandHelp extends CommandBase {

	public CommandHelp(JCommander jc) {
		super(jc);
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public String getErrorMessage() {
		return "";
	}

	@Override
	public void exec() {
		System.out.println("Clipper reasoner [v0.2]");
		System.out.println();
		jc.usage();
	}

}