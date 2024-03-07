package com.example.promact_customer_success.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "risk_profiling")
public class RiskProfiling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @Column(name = "risk_type", nullable = false)
    private String riskType;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "severity", nullable = false)
    private String severity;

    @Column(name = "impact", nullable = false)
    private String impact;

    @Column(name = "remedial_steps", columnDefinition = "TEXT")
    private String remedialSteps;

    @Column(name = "status")
    private String status;

    @Column(name = "closure_date")
    private Date closureDate;

    public RiskProfiling() {
        // Default constructor required by JPA
    }

    public RiskProfiling(Project project, String riskType, String description, String severity,
                         String impact, String remedialSteps, String status, Date closureDate) {
        this.project = project;
        this.riskType = riskType;
        this.description = description;
        this.severity = severity;
        this.impact = impact;
        this.remedialSteps = remedialSteps;
        this.status = status;
        this.closureDate = closureDate;
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

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getRemedialSteps() {
        return remedialSteps;
    }

    public void setRemedialSteps(String remedialSteps) {
        this.remedialSteps = remedialSteps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }
}
