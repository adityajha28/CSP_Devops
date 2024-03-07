package com.example.promact_customer_success.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "projectbudget")
public class ProjectBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
        private String projectType;

    @NotNull
    private Integer durationMonths;

    @NotNull
    private Integer budgetedHours;

    // Constructors, getters, and setters

    public ProjectBudget() {
        // Default constructor required by JPA
    }

    // Constructors

    public ProjectBudget(Project project, String projectType, Integer durationMonths, Integer budgetedHours) {
        this.project = project;
        this.projectType = projectType;
        this.durationMonths = durationMonths;
        this.budgetedHours = budgetedHours;
    }

    // Getters and setters

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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public Integer getBudgetedHours() {
        return budgetedHours;
    }

    public void setBudgetedHours(Integer budgetedHours) {
        this.budgetedHours = budgetedHours;
    }
}

