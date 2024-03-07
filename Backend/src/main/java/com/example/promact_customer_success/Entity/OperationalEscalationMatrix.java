package com.example.promact_customer_success.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "operational_escalation_matrix")
public class OperationalEscalationMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name = "escalation_level")
    private Integer escalationLevel;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "role", nullable = false, length = 255)
    private String role;

    @Column(name="type", nullable = false)
    private String type;
    // Constructors

    public OperationalEscalationMatrix() {
    }



    public OperationalEscalationMatrix(Project project, Integer escalationLevel, String name, String role) {
        this.project = project;
        this.escalationLevel = escalationLevel;
        this.name = name;
        this.role = role;
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

    public Integer getEscalationLevel() {
        return escalationLevel;
    }

    public void setEscalationLevel(Integer escalationLevel) {
        this.escalationLevel = escalationLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

