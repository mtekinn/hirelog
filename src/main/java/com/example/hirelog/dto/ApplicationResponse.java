package com.example.hirelog.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ApplicationResponse implements Serializable {
    private long id;
    private String jobName;
    private String status;
    private String companyName;
    private String companyWeb;
    private LocalDate appliedDate;
    private String notes;
    private boolean didReply;
    private long userId;

    public ApplicationResponse() {}

    public ApplicationResponse(long id, String jobName, String status, String companyName, String companyWeb, LocalDate appliedDate, String notes, boolean didReply, long userId) {
        this.id = id;
        this.jobName = jobName;
        this.status = status;
        this.companyName = companyName;
        this.companyWeb = companyWeb;
        this.appliedDate = appliedDate;
        this.notes = notes;
        this.didReply = didReply;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isDidReply() {
        return didReply;
    }

    public void setDidReply(boolean didReply) {
        this.didReply = didReply;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
