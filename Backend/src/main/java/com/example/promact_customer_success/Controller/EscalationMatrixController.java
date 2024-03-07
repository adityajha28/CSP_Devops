package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.EscalationMatrix;
import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Repository.EscalationMatrixRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/escalation-matrix")
public class EscalationMatrixController {

    @Autowired
    private EscalationMatrixRepository escalationMatrixRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    @PostMapping
    public ResponseEntity<EscalationMatrix> createEscalationMatrix(@Valid @RequestBody EscalationMatrix escalationMatrix) {
        System.out.println("hit");
        Integer projectId = escalationMatrix.getProject().getId();
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            escalationMatrix.setProject(project);
            EscalationMatrix savedEscalationMatrix = escalationMatrixRepository.save(escalationMatrix);
            return new ResponseEntity<>(savedEscalationMatrix, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<EscalationMatrix>> getAllEscalationMatrix() {
        List<EscalationMatrix> escalationMatrices = (List<EscalationMatrix>)  escalationMatrixRepository.findAll();
        return new ResponseEntity<>(escalationMatrices, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<EscalationMatrix> getEscalationMatrixById(@PathVariable Integer id) {

        Optional<EscalationMatrix> optionalEscalationMatrix = escalationMatrixRepository.findById(id);
        if (optionalEscalationMatrix.isPresent()) {
            EscalationMatrix escalationMatrix = optionalEscalationMatrix.get();
            return new ResponseEntity<>(escalationMatrix, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<EscalationMatrix> updateEscalation(@PathVariable Integer id, @RequestBody EscalationMatrix escalationMatrix) {

        if (!escalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        escalationMatrix.setId(id);
        EscalationMatrix updatedEscalation = escalationMatrixRepository.save(escalationMatrix);
        return new ResponseEntity<>(updatedEscalation, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEscalationMatrix(@PathVariable Integer id) {
        System.out.println(id);
        if (!escalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        escalationMatrixRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
