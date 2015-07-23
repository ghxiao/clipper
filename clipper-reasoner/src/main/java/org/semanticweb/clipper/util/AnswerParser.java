package org.semanticweb.clipper.util;

import org.semanticweb.clipper.hornshiq.queryanswering.ClipperManager;
import org.semanticweb.clipper.hornshiq.queryanswering.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class AnswerParser {
	private List<String> answers;
	private List<List<String>> decodedAnswers;
	private String prefix = null;
	NamingStrategy namingStrategy;

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public List<List<String>> getDecodedAnswers() {
		return decodedAnswers;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public AnswerParser(NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
		answers = new ArrayList<String>();
		decodedAnswers = new ArrayList<List<String>>();
	}

	// input: c1, d2, q3, X4 r5 will return 1,2,3,4,5 respectively
	private int getCode(String string) {
		String code;
		String delims = "[cqdXr]+";
		String[] tokens = string.split(delims);
		// for (String s: tokens) System.out.println(s);
		if (tokens.length > 1) {
			code = tokens[1];
			return Integer.parseInt(code);
		} else
			return -1;
	}

	private String getDecodedIndividual(String indiString) {
		int code = getCode(indiString);
		// OWLIndividual individual =
		// ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(code);
		return ClipperManager.getInstance().getOwlIndividualAndLiteralEncoder().getSymbolByValue(code).toString();
		// return individual.toString();

	}

	private List<String> getDecodedAnswer(String answer) {
		List<String> decodedAnswers = new ArrayList<String>();
		String delims = "[\\(\\),]+\\s*";
		String s = answer;
		String[] tokens = s.split(delims);

		for (int i = 1; i < tokens.length; i++) {
			String indiString = tokens[i];
			if (namingStrategy == NamingStrategy.INT_ENCODING) {
				String decodedIndividual = getDecodedIndividual(indiString);
				decodedAnswers.add(decodedIndividual);
			} else {
				decodedAnswers.add(indiString);
			}
		}
		return decodedAnswers;
	}

	public void parse() {
		for (String answer : answers) {
			List<String> decodedAnswer = getDecodedAnswer(answer);
			decodedAnswers.add(decodedAnswer);
		}
	}

}
