package com.example.promact_customer_success.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit_history")
public class AuditHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @Column(name = "date_of_audit")
    private Date dateOfAudit;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "status", columnDefinition = "TEXT")
    private String status;

    @Column(name = "reviewed_section", columnDefinition = "TEXT")
    private String reviewedSection;

    @Column(name = "comment_queries", columnDefinition = "TEXT")
    private String commentQueries;

    @Column(name = "action_item", columnDefinition = "TEXT")
    private String actionItem;

    public AuditHistory() {
        // Default constructor required by JPA
    }

    public AuditHistory(Project project, Date dateOfAudit, String reviewedBy, String status,
                        String reviewedSection, String commentQueries, String actionItem) {
        this.project = project;
        this.dateOfAudit = dateOfAudit;
        this.reviewedBy = reviewedBy;
        this.status = status;
        this.reviewedSection = reviewedSection;
        this.commentQueries = commentQueries;
        this.actionItem = actionItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDateOfAudit() {
        return dateOfAudit;
    }

    public void setDateOfAudit(Date dateOfAudit) {
        this.dateOfAudit = dateOfAudit;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedSection() {
        return reviewedSection;
    }

    public void setReviewedSection(String reviewedSection) {
        this.reviewedSection = reviewedSection;
    }

    public String getCommentQueries() {
        return commentQueries;
    }

    public void setCommentQueries(String commentQueries) {
        this.commentQueries = commentQueries;
    }

    public String getActionItem() {
        return actionItem;
    }

    public void setActionItem(String actionItem) {
        this.actionItem = actionItem;
    }
}
