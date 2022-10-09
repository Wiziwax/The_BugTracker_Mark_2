package com.example.the_bugtracker_mark_2.Models;


import com.example.the_bugtracker_mark_2.Enums.Action;
import com.example.the_bugtracker_mark_2.Models.Bug;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Integer activityId;

    @Column
    public String createdBy;//User who sent the complaint

    @Column(name = "bug_action")
    public Action action;

    @Column
    public LocalDate createdDt;//Date he sent it


    @Column
    public String description;//The platform he sent it on and his complaint


    @Column
    public String approvedBy;

    @Column
    public String assignedTo;

    @Column
    public String treatmentStage;

    @Column
    public String bugTicketId;

    @Column
    public String progressStatus;

    @Column(name = "reassigned_to")
    public String reassignedTo;

    @Column(name = "reassignedBy")
    public String reassignedBy;

    @Column(name = "action_date")
    public Date actionDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_bug_id")
    @JsonIgnore
    public Bug bugActivity;

    public Activity() {
    }

    public Activity(String description){
        this.description=description;
    }





    //GETTERS AND SETTERS

    //FOR BUG CREATION
    public Activity(String description, String createdBy, LocalDate createdDt, String approvedBy,
                    Date actionDate, String assignedTo, String bugTicketId, String treatmentStage, String progressStatus) {
        this.description=description;
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.approvedBy = approvedBy;
        this.actionDate=actionDate;
        this.assignedTo=assignedTo;
        this.bugTicketId = bugTicketId;
        this.treatmentStage = treatmentStage;
        this.progressStatus = progressStatus;
    }

    //FOR BUG DELETION
    public Activity(String description, String treatmentStage, Date actionDate) {
        this.description = description;

        this.treatmentStage = treatmentStage;
        this.actionDate = actionDate;
    }

    //FOR BUG UPDATE/ASSIGNMENT


    public Activity(String createdBy,
                    LocalDate createdDate,
                    String reassignedTo,
                    String description,
                    String approvedBy,
                    String assignedTo, String treatmentStage,
                    Date actionDate) {

        this.createdBy=createdBy;
        this.createdDt=createdDate;
        this.reassignedTo=reassignedTo;
        this.description = description;
        this.approvedBy=approvedBy;
        this.assignedTo = assignedTo;
        this.treatmentStage = treatmentStage;
        this.actionDate = actionDate;
    }

    //FOR REASSIGNMENT

    public Activity(String createdBy, LocalDate createdDt,
                    String assignedTo, String description,
                    Date actionDate, String reassignedBy,
                    String reassignedTo) {
        this.createdBy = createdBy;
        this.createdDt = createdDt;
        this.assignedTo=assignedTo;
        this.description = description;
        this.actionDate = actionDate;
        this.reassignedBy = reassignedBy;
        this.reassignedTo = reassignedTo;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDate createdDt) {
        this.createdDt = createdDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTreatmentStage() {
        return treatmentStage;
    }

    public void setTreatmentStage(String treatmentStage) {
        this.treatmentStage = treatmentStage;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getReassignedTo() {
        return reassignedTo;
    }

    public void setReassignedTo(String reassignedTo) {
        this.reassignedTo = reassignedTo;
    }

    public String getReassignedBy() {
        return reassignedBy;
    }

    public void setReassignedBy(String reassignedBy) {
        this.reassignedBy = reassignedBy;
    }


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }


    public Bug getBugActivity() {
        return bugActivity;
    }

    public void setBugActivity(Bug bugActivity) {
        this.bugActivity = bugActivity;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "bugActivity=" + bugActivity +
                '}';
    }
}
