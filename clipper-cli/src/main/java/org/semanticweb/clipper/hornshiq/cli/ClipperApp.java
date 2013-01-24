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

		CommandRewrite commandRewrite = new CommandRewrite(jc);

		CommandHelp commandHelp = new CommandHelp(jc);

		CommandLoad commandLoad = new CommandLoad(jc);

		CommandInitDB commandInitDB = new CommandInitDB(jc);

		CommandGenerateMapFile commandGenerateMapFile = new CommandGenerateMapFile(
				jc);
		
		CommandSparqlToSQL commandSparqlToSQL = new CommandSparqlToSQL(jc);

		CommandPythonOntology commandPythonOntology = new CommandPythonOntology(jc);
		
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
			commandHelp.exec();
		} else if (cmd.equals("query")) {
			commandQuery.exec();
		} else if (cmd.equals("rewrite")) {
			commandRewrite.exec();
		} else if (cmd.equals("load")) {
			commandLoad.exec();
		} else if (cmd.equals("init")) {
			commandInitDB.exec();
		} else if (cmd.equals("help")) {
			commandHelp.exec();
		} else if (cmd.equals("gen")) {
			commandGenerateMapFile.exec();
		} else if (cmd.equals("pex")) {
			commandPythonOntology.exec();
		} else if (cmd.endsWith("sparql2sql")){
			commandSparqlToSQL.exec();
		}
	}

}
