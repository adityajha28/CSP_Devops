package com.example.promact_customer_success.Entity;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "phases_milestones")
public class PhaseMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "status")
    private String status;

    @Column(name = "revised_completion_date")
    private Date revisedCompletionDate;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    // Constructors

    public PhaseMilestone() {
    }

    public PhaseMilestone(Project project, String title) {
        this.project = project;
        this.title = title;
    }

    // Getters and Setters

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRevisedCompletionDate() {
        return revisedCompletionDate;
    }

    public void setRevisedCompletionDate(Date revisedCompletionDate) {
        this.revisedCompletionDate = revisedCompletionDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
