package org.semanticweb.clipper.hornshiq.queryanswering;

public class ClipperReport {
	private long reasoningTime;
	private long queryRewritingTime;
	private long datalogRunTime;
	private long normalizationTime;
	private long outputAnswerTime;
	private long coutingRealtedRulesTime;
	private int numberOfRewrittenQueries;
	private int numberOfRewrittenQueriesAndRules;

	public ClipperReport() {
	}

	public long getReasoningTime() {
		return reasoningTime;
	}

	public void setReasoningTime(long reasoningTime) {
		this.reasoningTime = reasoningTime;
	}

	public long getQueryRewritingTime() {
		return queryRewritingTime;
	}

	public void setQueryRewritingTime(long queryRewritingTime) {
		this.queryRewritingTime = queryRewritingTime;
	}

	public long getDatalogRunTime() {
		return datalogRunTime;
	}

	public void setDatalogRunTime(long datalogRunTime) {
		this.datalogRunTime = datalogRunTime;
	}

	public long getNormalizationTime() {
		return normalizationTime;
	}

	public void setNormalizationTime(long normalizationTime) {
		this.normalizationTime = normalizationTime;
	}

	public long getOutputAnswerTime() {
		return outputAnswerTime;
	}

	public void setOutputAnswerTime(long outputAnswerTime) {
		this.outputAnswerTime = outputAnswerTime;
	}

	public long getCoutingRealtedRulesTime() {
		return coutingRealtedRulesTime;
	}

	public void setCoutingRealtedRulesTime(long coutingRealtedRulesTime) {
		this.coutingRealtedRulesTime = coutingRealtedRulesTime;
	}

	public int getNumberOfRewrittenQueries() {
		return numberOfRewrittenQueries;
	}

	public void setNumberOfRewrittenQueries(int numberOfRewrittenQueries) {
		this.numberOfRewrittenQueries = numberOfRewrittenQueries;
	}

	public int getNumberOfRewrittenQueriesAndRules() {
		return numberOfRewrittenQueriesAndRules;
	}

	public void setNumberOfRewrittenQueriesAndRules(int numberOfRewrittenQueriesAndRules) {
		this.numberOfRewrittenQueriesAndRules = numberOfRewrittenQueriesAndRules;
	}
}