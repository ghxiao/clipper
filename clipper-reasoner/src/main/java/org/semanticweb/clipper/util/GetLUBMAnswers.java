package org.semanticweb.clipper.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GetLUBMAnswers {
	private Set<Set<String>> predictedAnswers;

	public GetLUBMAnswers() {
		predictedAnswers = new HashSet<Set<String>>();
	}

	public Set<Set<String>> getAnswers() {
		return predictedAnswers;
	}

	public void readAnswers(String answerFilePath) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(answerFilePath);
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
					Set<String> answerTuple = new HashSet<String>();
					String[] answers = strLine.split("\\s+");
					// System.out.println(answers.length);
					for (String s : answers) {
						answerTuple.add("<" + s + ">");
					}
					// System.out.println(answerTuple.size());
					predictedAnswers.add(answerTuple);
					// System.out.println(strLine);
				}
			}
			// Close the input stream
			in.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	// public static void main (String[] args){
	// CheckLUBMAnswer c = new CheckLUBMAnswer();
	// c.readAnswers("TestData/lubm/answers_query4.txt");
	// System.out.println(c.getPredictedAnswers());
	// }
}
