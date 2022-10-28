package com.example.the_bugtracker_mark_2.Models;

import com.example.the_bugtracker_mark_2.Enums.Severity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bug_information")
@EnableJpaAuditing
public class Bug {

    @Column
    @CreatedDate
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    public final LocalDate reportDate = LocalDate.now(); //DATE OF REPORT
    @Column
    public String assignedTo;
    @Column
    @CreatedDate
    @JsonIgnore
    public LocalDate lastUpdate;

    @OneToOne
    @JoinColumn(name = "user_bug_id")
    public User userAssignedToBug;
    @Id
    @Column(name = "bug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bugId; //ID OF BUG
    @Column(name = "bug_name", nullable = false)
    private String label; //BUG NAME

    @Column
    private Integer createdBy;
    @Column(name = "approved_by")
    @JsonIgnore
    private String approvedBy;
    @Column
    @JsonIgnore
    private Date approvedDate;
    @Column
    @JsonIgnore
    private String assignedDate;
    @Column
    private String severity; //SEVERITY LEVEL
    @Column
    private Severity enumSeverity;
    @Column
    private boolean assigned;

    @Column
    private final String ticketId = "ZB"+LocalDate.now()+String.valueOf(bugId);

    @Column
    private String bugTreatmentStage; //ALL BUGS, OPEN BUGS, TREATED, PENDING

    @Column
    private String progressStatus;//INITIATED, APPROVED, ASSIGNED TO, REASSIGNED TO, CORRECTION COMPLETED

    @Column
    private String bugReview; //USERS REVIEW ON BUG TREATMENT

    @OneToOne
    @JoinColumn(name = "bug_platform_id")
    private Platforms platformses;

    public Bug() {
    }

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String bugName) {
        this.label = bugName;
    }

    public Integer getCreatedBy(String bugName) {
        return createdBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getBugTreatmentStage() {
        return bugTreatmentStage;
    }

    public void setBugTreatmentStage(String bugTreatmentStage) {
        this.bugTreatmentStage = bugTreatmentStage;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }
    public String getBugReview() {
        return bugReview;
    }

    public void setBugReview(String bugReview) {
        this.bugReview = bugReview;
    }

    public Platforms getPlatformses() {
        return platformses;
    }

    public void setPlatformses(Platforms platformses) {
        this.platformses = platformses;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public User getUserAssignedToBug() {
        return userAssignedToBug;
    }

    public void setUserAssignedToBug(User userAssignedToBug) {
        this.userAssignedToBug = userAssignedToBug;
    }


    public Severity getEnumSeverity() {
        return enumSeverity;
    }

    public void setEnumSeverity(Severity enumSeverity) {
        this.enumSeverity = enumSeverity;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "reportDate=" + reportDate +
                ", assignedTo='" + assignedTo + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", userAssignedToBug=" + userAssignedToBug +
                ", bugId=" + bugId +
                ", label='" + label + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", assignedDate='" + assignedDate + '\'' +
                ", severity='" + severity + '\'' +
                ", enumSeverity=" + enumSeverity +
                ", assigned=" + assigned +
                ", ticketId='" + ticketId + '\'' +
                ", bugTreatmentStage='" + bugTreatmentStage + '\'' +
                ", progressStatus='" + progressStatus + '\'' +
                ", bugReview='" + bugReview + '\'' +
                ", platformses=" + platformses +
                '}';
    }

    public String getTicketId() {
        return ticketId;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

}