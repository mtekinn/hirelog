package com.example.hirelog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String jobName;
    private String status;
    private String companyName;
    private String companyWeb;
    private LocalDate appliedDate;
    private String notes;
    private boolean didReply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Application() {}

    public Application(long id, String jobName, String status, String companyName, String companyWeb, LocalDate appliedDate, String notes, boolean didReply) {
        this.id = id;
        this.jobName = jobName;
        this.status = status;
        this.companyName = companyName;
        this.companyWeb = companyWeb;
        this.appliedDate = appliedDate;
        this.notes = notes;
        this.didReply = didReply;
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
}
