package org.semanticweb.clipper.hornshiq.queryanswering;

import lombok.Data;

@Data
public class ClipperReport {
	private long reasoningTime;
	private long queryRewritingTime;
	private long datalogRunTime;
	private long normalizationTime;
	private long outputAnswerTime;
	private long coutingRealtedRulesTime;
	private int numberOfRewrittenQueries;
	private int numberOfRewrittenQueriesAndRules;

}