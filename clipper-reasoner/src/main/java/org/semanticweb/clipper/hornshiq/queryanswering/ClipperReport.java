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
        return this.reasoningTime;
    }

    public long getQueryRewritingTime() {
        return this.queryRewritingTime;
    }

    public long getDatalogRunTime() {
        return this.datalogRunTime;
    }

    public long getNormalizationTime() {
        return this.normalizationTime;
    }

    public long getOutputAnswerTime() {
        return this.outputAnswerTime;
    }

    public long getCoutingRealtedRulesTime() {
        return this.coutingRealtedRulesTime;
    }

    public int getNumberOfRewrittenQueries() {
        return this.numberOfRewrittenQueries;
    }

    public int getNumberOfRewrittenQueriesAndRules() {
        return this.numberOfRewrittenQueriesAndRules;
    }

    public void setReasoningTime(long reasoningTime) {
        this.reasoningTime = reasoningTime;
    }

    public void setQueryRewritingTime(long queryRewritingTime) {
        this.queryRewritingTime = queryRewritingTime;
    }

    public void setDatalogRunTime(long datalogRunTime) {
        this.datalogRunTime = datalogRunTime;
    }

    public void setNormalizationTime(long normalizationTime) {
        this.normalizationTime = normalizationTime;
    }

    public void setOutputAnswerTime(long outputAnswerTime) {
        this.outputAnswerTime = outputAnswerTime;
    }

    public void setCoutingRealtedRulesTime(long coutingRealtedRulesTime) {
        this.coutingRealtedRulesTime = coutingRealtedRulesTime;
    }

    public void setNumberOfRewrittenQueries(int numberOfRewrittenQueries) {
        this.numberOfRewrittenQueries = numberOfRewrittenQueries;
    }

    public void setNumberOfRewrittenQueriesAndRules(int numberOfRewrittenQueriesAndRules) {
        this.numberOfRewrittenQueriesAndRules = numberOfRewrittenQueriesAndRules;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ClipperReport)) return false;
        final ClipperReport other = (ClipperReport) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.reasoningTime != other.reasoningTime) return false;
        if (this.queryRewritingTime != other.queryRewritingTime) return false;
        if (this.datalogRunTime != other.datalogRunTime) return false;
        if (this.normalizationTime != other.normalizationTime) return false;
        if (this.outputAnswerTime != other.outputAnswerTime) return false;
        if (this.coutingRealtedRulesTime != other.coutingRealtedRulesTime) return false;
        if (this.numberOfRewrittenQueries != other.numberOfRewrittenQueries) return false;
        if (this.numberOfRewrittenQueriesAndRules != other.numberOfRewrittenQueriesAndRules) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $reasoningTime = this.reasoningTime;
        result = result * PRIME + (int) ($reasoningTime >>> 32 ^ $reasoningTime);
        final long $queryRewritingTime = this.queryRewritingTime;
        result = result * PRIME + (int) ($queryRewritingTime >>> 32 ^ $queryRewritingTime);
        final long $datalogRunTime = this.datalogRunTime;
        result = result * PRIME + (int) ($datalogRunTime >>> 32 ^ $datalogRunTime);
        final long $normalizationTime = this.normalizationTime;
        result = result * PRIME + (int) ($normalizationTime >>> 32 ^ $normalizationTime);
        final long $outputAnswerTime = this.outputAnswerTime;
        result = result * PRIME + (int) ($outputAnswerTime >>> 32 ^ $outputAnswerTime);
        final long $coutingRealtedRulesTime = this.coutingRealtedRulesTime;
        result = result * PRIME + (int) ($coutingRealtedRulesTime >>> 32 ^ $coutingRealtedRulesTime);
        result = result * PRIME + this.numberOfRewrittenQueries;
        result = result * PRIME + this.numberOfRewrittenQueriesAndRules;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof ClipperReport;
    }

    public String toString() {
        return "org.semanticweb.clipper.hornshiq.queryanswering.ClipperReport(reasoningTime=" + this.reasoningTime + ", queryRewritingTime=" + this.queryRewritingTime + ", datalogRunTime=" + this.datalogRunTime + ", normalizationTime=" + this.normalizationTime + ", outputAnswerTime=" + this.outputAnswerTime + ", coutingRealtedRulesTime=" + this.coutingRealtedRulesTime + ", numberOfRewrittenQueries=" + this.numberOfRewrittenQueries + ", numberOfRewrittenQueriesAndRules=" + this.numberOfRewrittenQueriesAndRules + ")";
    }
}