package com.example.promact_customer_success.Controller;


import com.example.promact_customer_success.Entity.OperationalEscalationMatrix;
import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Repository.OperationalEscalationMatrixRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/operational-escalation-matrix")
public class OperationalEscalationMatrixController {

    @Autowired
    private OperationalEscalationMatrixRepository escalationMatrixRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    @PostMapping
    public ResponseEntity<OperationalEscalationMatrix> createEscalationMatrix(@RequestBody OperationalEscalationMatrix escalationMatrix) {
        // Fetch the Project entity corresponding to the given projectId
        Integer projectId = escalationMatrix.getProject().getId();
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            escalationMatrix.setProject(project);
            OperationalEscalationMatrix savedMatrix = escalationMatrixRepository.save(escalationMatrix);
            return new ResponseEntity<>(savedMatrix, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<OperationalEscalationMatrix>> getAllEscalationMatrix() {
        List<OperationalEscalationMatrix> escalationMatrices = (List<OperationalEscalationMatrix>) escalationMatrixRepository.findAll();
        return new ResponseEntity<>(escalationMatrices, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<OperationalEscalationMatrix> getEscalationMatrixById(@PathVariable Integer id) {
        Optional<OperationalEscalationMatrix> optionalEscalationMatrix = escalationMatrixRepository.findById(id);
        if (optionalEscalationMatrix.isPresent()) {
            OperationalEscalationMatrix matrix = optionalEscalationMatrix.get();
            return new ResponseEntity<>(matrix, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<OperationalEscalationMatrix> updateEscalationMatrix(@PathVariable Integer id,
                                                                              @RequestBody OperationalEscalationMatrix escalationMatrix) {
        if (!escalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        escalationMatrix.setId(id);
        OperationalEscalationMatrix updatedMatrix = escalationMatrixRepository.save(escalationMatrix);
        return new ResponseEntity<>(updatedMatrix, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEscalationMatrix(@PathVariable Integer id) {
        if (!escalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        escalationMatrixRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
