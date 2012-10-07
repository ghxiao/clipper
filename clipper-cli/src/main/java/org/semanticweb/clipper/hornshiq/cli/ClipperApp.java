package org.semanticweb.clipper.hornshiq.cli;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;

import com.beust.jcommander.JCommander;

public class ClipperApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ClipperApp(args);
	}

	public ClipperApp(String[] args) {
		CommandLineArgs co = new CommandLineArgs();
		JCommander jc = new JCommander(co);

		CommandQuery commandQuery = new CommandQuery(jc);
		jc.addCommand(commandQuery);
		CommandRewrite commandRewrite = new CommandRewrite(jc);
		jc.addCommand(commandRewrite);
		CommandHelp commandHelp = new CommandHelp(jc);
		jc.addCommand(commandHelp);

		jc.setProgramName("clipper.sh");

		jc.parse(args);

		ClipperManager.getInstance().setVerboseLevel(co.getVerbose());

		String cmd = null;
		try {
			cmd = jc.getParsedCommand();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			commandHelp.exec();
		}

		if (cmd == null) {
			//help(jc);
			commandHelp.exec();
		} else if (cmd.equals("query")) {
			//query(co, commandQuery);
			commandQuery.exec();
		} else if (cmd.equals("rewrite")) {
			//rewrite(co, commandRewrite);
			commandRewrite.exec();
		} else if (cmd.equals("help")) {
			//help(jc);
			commandHelp.exec();
		}
	}

}
