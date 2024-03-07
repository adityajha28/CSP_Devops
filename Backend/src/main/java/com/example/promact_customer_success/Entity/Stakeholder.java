package com.example.promact_customer_success.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "stakeholders")
public class Stakeholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "contact", length = 255, nullable = false)
    private String contact;

    // Constructors
    public Stakeholder() {
    }

    public Stakeholder(Project project, String title, String name, String contact) {
        this.project = project;
        this.title = title;
        this.name = name;
        this.contact = contact;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
