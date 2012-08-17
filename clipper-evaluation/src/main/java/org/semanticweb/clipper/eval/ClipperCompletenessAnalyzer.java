package org.semanticweb.clipper.eval;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import org.oxford.comlab.compass.CompletenessAnalyser;
import org.oxford.comlab.compass.SystemInterface;
import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;

public class ClipperCompletenessAnalyzer {
	public static void main(String[] args) throws Exception {
		runAllTests();
//		for(int j = 0; j < 10; j++){
//		 runOneTest(13, j);
//		}
		 //runOneTest(3, 12);
		 //runOneTest(9, 0);
	}

	public static void runOneTest(int queryIndex, int patternIndex) throws Exception {
		String root = (new File(".").getAbsolutePath().toString());
		root = root + "/src/main/resources/ontologies/LUBM";
		String ontologyFile = root + "/univ-bench.owl";
		String datasetFolder = root + "/univ-bench_TB-C_s";

		ClipperManager.getInstance().setVerboseLevel(1);
		ClipperSygeniaInterfaceImp cqSystem = new ClipperSygeniaInterfaceImp();

		cqSystem.setDataSetRoot(root);
		// cqSystem.setDlvPath("/Users/xiao/bin/dlv");
		cqSystem.initializeSystem();
		ArrayList<String> allQueryFolders = loadQueryFolders(datasetFolder);
		// long timeStart = System.currentTimeMillis();
		// for ( int i = 0; i<allQueryFolders.size() ; i++ ) {

		long timeQueryStart = System.currentTimeMillis();
		final String queryDatasetFolder = allQueryFolders.get(queryIndex);
		System.out.println("***  Examining query " + (queryIndex) + " *** " + queryDatasetFolder);

		cqSystem.loadQuery(queryIndex);

		// String queryDatasetFolder = allQueryFolders.get(queryIndex);
		// String queryDatasetFolder = allQueryFolders.get(queryIndex - 1);
		File testingUnitsDir = new File(queryDatasetFolder);
		File[] testingUnits = testingUnitsDir.listFiles();
		int failedABoxes = 0;
		long totalNumberOfABoxes = 0;
		// for (int k = 0; k < testingUnits.length; k++) {
		System.out.println("Examining ABoxes for inference pattern " + testingUnits[patternIndex].getName());

		File[] aBoxesForTU = testingUnits[patternIndex].listFiles();
		if (aBoxesForTU == null) {
			// Either dir does not exist or is not a directory
			System.out.println("Cannot open load directory: " + queryDatasetFolder);
			return;
		} else {
			for (int j = 0; j < aBoxesForTU.length; j++) {

				totalNumberOfABoxes++;
				cqSystem.loadTestToSystem(ontologyFile, aBoxesForTU[j]);
				long certainAnswers = cqSystem.runLoadedQuery();
				if (certainAnswers < 1) {
					// System.err.println( "Failed at ABox: " +
					// aBoxesForTU[j].getName() + " of inference pattern " +
					// testingUnits[k].getName());
					System.out.println("Failed at ABox: " + aBoxesForTU[j].getName());
					failedABoxes++;
				} else if (certainAnswers > 1)
					System.err.println("A STRANGE CASE HAPPENED ON " + aBoxesForTU[j].getName());

				cqSystem.clearRepository();
			}
		}
		// }
		float percentage = (float) (totalNumberOfABoxes - failedABoxes) / totalNumberOfABoxes;
		System.out.println("\nFailed in " + failedABoxes + " out of " + totalNumberOfABoxes + " testing units for "
				+ testingUnits.length + " inference patterns");
		System.out.println("Completeness: " + percentage);
		System.out.println("Analysis of query " + (queryIndex + 1) + " finished in "
				+ (System.currentTimeMillis() - timeQueryStart) + " ms\n\n");
		// }

	}

	private static ArrayList<String> loadQueryFolders(String datasetFolder) {

		File dir = new File(datasetFolder);
		File[] queryFolders = dir.listFiles();
		TreeSet<String> orderedListOfFiles = new TreeSet<String>();
		if (queryFolders == null) {
			// Either dir does not exist or is not a directory
			System.out.println("Cannot open pre-load directory: " + datasetFolder);
			System.exit(0);
		} else {
			for (int i = 0; i < queryFolders.length; i++) {
				// Get filename of file or directory
				if (queryFolders[i].isFile())
					continue;
				try {
					orderedListOfFiles.add(queryFolders[i].getAbsolutePath());
				} catch (OutOfMemoryError e) {
					System.out.println("System run out of memory. Exiting...");
					System.exit(0);
				}
			}
		}
		ArrayList<String> allQueryFolders = new ArrayList<String>(orderedListOfFiles);
		return allQueryFolders;
	}

	/**
	 * 
	 */
	private static void runAllTests() {
		String root = (new File(".").getAbsolutePath().toString());
		root = root + "/src/main/resources/ontologies/LUBM";
		String ontologyFile = root + "/univ-bench.owl";
		String datasetFolder = root + "/univ-bench_TB-C_s";

		CompletenessAnalyser complAnalyzer = new CompletenessAnalyser();
		ClipperSygeniaInterfaceImp mySystem = new ClipperSygeniaInterfaceImp();
		mySystem.setDataSetRoot(root);
		mySystem.setDlvPath("/Users/xiao/bin/dlv");

		complAnalyzer.doCompletenessAnalysisExperimentAllMappings(mySystem, ontologyFile, datasetFolder);
	}

}
