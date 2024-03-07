package com.example.promact_customer_success.Controller;


import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.TechnicalEscalationMatrix;
import com.example.promact_customer_success.Repository.TechnicalEscalationMatrixRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/technical-escalation-matrix")
public class TechnicalEscalationMatrixController {

    @Autowired
    private TechnicalEscalationMatrixRepository technicalEscalationMatrixRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    @PostMapping
    public ResponseEntity<TechnicalEscalationMatrix> createTechnicalEscalationMatrix(@RequestBody TechnicalEscalationMatrix technicalEscalationMatrix) {
        // Fetch the Project entity corresponding to the given projectId
        Integer projectId = technicalEscalationMatrix.getProject().getId();
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            technicalEscalationMatrix.setProject(project);
            TechnicalEscalationMatrix savedTechnicalEscalationMatrix = technicalEscalationMatrixRepository.save(technicalEscalationMatrix);
            return new ResponseEntity<>(savedTechnicalEscalationMatrix, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<TechnicalEscalationMatrix>> getAllTechnicalEscalationMatrices() {
        List<TechnicalEscalationMatrix> technicalEscalationMatrices = (List<TechnicalEscalationMatrix> )technicalEscalationMatrixRepository.findAll();
        return new ResponseEntity<>(technicalEscalationMatrices, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<TechnicalEscalationMatrix> getTechnicalEscalationMatrixById(@PathVariable Integer id) {
        Optional<TechnicalEscalationMatrix> optionalTechnicalEscalationMatrix = technicalEscalationMatrixRepository.findById(id);
        return optionalTechnicalEscalationMatrix.map(technicalEscalationMatrix -> new ResponseEntity<>(technicalEscalationMatrix, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TechnicalEscalationMatrix> updateTechnicalEscalationMatrix(@PathVariable Integer id,
                                                                                     @RequestBody TechnicalEscalationMatrix technicalEscalationMatrix) {
        if (!technicalEscalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        technicalEscalationMatrix.setId(id);
        TechnicalEscalationMatrix updatedTechnicalEscalationMatrix = technicalEscalationMatrixRepository.save(technicalEscalationMatrix);
        return new ResponseEntity<>(updatedTechnicalEscalationMatrix, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnicalEscalationMatrix(@PathVariable Integer id) {
        if (!technicalEscalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        technicalEscalationMatrixRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
