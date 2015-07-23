package org.semanticweb.clipper.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LUBMAnswerFileParser {

	private HashSet<List<String>> predictedAnswers;

	public LUBMAnswerFileParser() {
	}

	public Set<List<String>> getAnswers() {
		return predictedAnswers;
	}

	public Set<List<String>> readAnswers(String answerFile) {
		predictedAnswers = new HashSet<>();


		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(answerFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// read the first line
			String firstLine = br.readLine();
			if (!firstLine.contains("NO ANSWERS.")) {
				// String[] numberOfVar = firstLine.split(" ");

				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					// Print the content on the console
					List<String> answerTuple = new ArrayList<>();
					String[] answers = strLine.split("\\s+");
					for (String s : answers) {
						//answerTuple.add("<" + s + ">");
						answerTuple.add("\""+s+"\"");
					}

					predictedAnswers.add(answerTuple);
				}
			}
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return predictedAnswers;
	}
	// public static void main (String[] args){
	// CheckLUBMAnswer c = new CheckLUBMAnswer();
	// c.readAnswers("TestData/lubm/answers_query4.txt");
	// System.out.println(c.getPredictedAnswers());
	// }
}
