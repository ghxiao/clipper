package org.semanticweb.clipper.eval;

import java.io.File;

import org.oxford.comlab.compass.CompletenessAnalyser;
import org.oxford.comlab.compass.SystemInterface;

public class ClipperCompletenessAnalyzer {
	public static void main(String[] args) throws Exception {
		String root = (new File(".").getAbsolutePath().toString());
		root = root + "/src/main/resources/ontologies/LUBM";
		String ontologyFile = root + "/univ-bench.owl";
		String datasetFolder = root + "/univ-bench_TB-C_s";

		CompletenessAnalyser complAnalyzer = new CompletenessAnalyser();
		ClipperSygeniaInterfaceImp mySystem = new ClipperSygeniaInterfaceImp();
		mySystem.setDataSetRoot(root);
		mySystem.setDlvPath("/Users/xiao/bin/dlv");
		
		complAnalyzer.doCompletenessAnalysisExperimentAllMappings(mySystem, ontologyFile, datasetFolder);

		// String ontologyFile = root +
		// "/src/main/resources/ontologies/LUBM/univ-bench.owl";
		//
		// String datasetFolder = root + "/src/main/resources/ontologies/LUBM";
		//
		// CompletenessAnalyser analyzer = new CompletenessAnalyser();
		// SystemInterface cs = new ClipperSygenia();
		// analyzer.doCompletenessAnalysisExperimentInjective(cs, ontologyFile,
		// datasetFolder);
	}
}
