package com.example.hirelog.dto;

public class SummaryResponse {
    private long totalApplications;
    private long applied;
    private long interview;
    private long offer;
    private long rejected;
    private double replyRate;

    public SummaryResponse(long totalApplications, long applied, long interview, long offer, long rejected, double replyRate) {
        this.totalApplications = totalApplications;
        this.applied = applied;
        this.interview = interview;
        this.offer = offer;
        this.rejected = rejected;
        this.replyRate = replyRate;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getApplied() {
        return applied;
    }

    public void setApplied(long applied) {
        this.applied = applied;
    }

    public long getInterview() {
        return interview;
    }

    public void setInterview(long interview) {
        this.interview = interview;
    }

    public long getOffer() {
        return offer;
    }

    public void setOffer(long offer) {
        this.offer = offer;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }

    public double getReplyRate() {
        return replyRate;
    }

    public void setReplyRate(double replyRate) {
        this.replyRate = replyRate;
    }
}
