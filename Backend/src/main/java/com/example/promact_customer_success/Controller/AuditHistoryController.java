package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.AuditHistory;
import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.ProjectBudget;
import com.example.promact_customer_success.Repository.AuditHistoryRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import com.example.promact_customer_success.Service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/audithistory")
public class AuditHistoryController {

    @Autowired
    private AuditHistoryRepository auditHistoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmailService emailService;
    // Create
    @PostMapping
    public ResponseEntity<AuditHistory> createAuditHistory(@Valid @RequestBody AuditHistory auditHistory) {

        System.out.println("called added history");
        emailService.sendAuditHistoryNotification(auditHistory,"created");
        Integer projectId = auditHistory.getProject().getId();
        System.out.println(projectId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            auditHistory.setProject(project);
            AuditHistory savedAuditHistory = auditHistoryRepository.save(auditHistory);
            return new ResponseEntity<>(savedAuditHistory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<AuditHistory>> getAllAuditHistory() {
        List<AuditHistory> auditHistories = (List<AuditHistory>)  auditHistoryRepository.findAll();
        return new ResponseEntity<>(auditHistories, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<AuditHistory> getAuditHistoryById(@PathVariable Integer id) {
        Optional<AuditHistory> optionalAuditHistory = auditHistoryRepository.findById(id);
        if (optionalAuditHistory.isPresent()) {
            AuditHistory auditHistory = optionalAuditHistory.get();
            return new ResponseEntity<>(auditHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<AuditHistory> updateAuditHistory(@PathVariable Integer id, @RequestBody AuditHistory auditHistory) {
        if (!auditHistoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        auditHistory.setId(id);
        AuditHistory updatedAuditHistory = auditHistoryRepository.save(auditHistory);
        emailService.sendAuditHistoryNotification(updatedAuditHistory,"updated");
        return new ResponseEntity<>(updatedAuditHistory, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAuditHistory(@PathVariable Integer id) {
        if (!auditHistoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        auditHistoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
