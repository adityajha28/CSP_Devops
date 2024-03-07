package com.example.promact_customer_success.Controller;


import com.example.promact_customer_success.Entity.FinancialEscalationMatrix;
import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Repository.FinancialEscalationMatrixRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/financial-escalation-matrix")
public class FinancialEscalationMatrixController {

    @Autowired
    private FinancialEscalationMatrixRepository financialEscalationMatrixRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    @PostMapping
    public ResponseEntity<FinancialEscalationMatrix> createFinancialEscalationMatrix(@Valid @RequestBody FinancialEscalationMatrix financialEscalationMatrix) {

        // Fetch the Project entity corresponding to the given projectId
        Integer projectId = financialEscalationMatrix.getProject().getId();
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            financialEscalationMatrix.setProject(project);
            FinancialEscalationMatrix savedFinancialEscalationMatrix = financialEscalationMatrixRepository.save(financialEscalationMatrix);
            return new ResponseEntity<>(savedFinancialEscalationMatrix, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<FinancialEscalationMatrix>> getAllFinancialEscalationMatrices() {
        List<FinancialEscalationMatrix> financialEscalationMatrices = (List<FinancialEscalationMatrix>) financialEscalationMatrixRepository.findAll();
        return new ResponseEntity<>(financialEscalationMatrices, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<FinancialEscalationMatrix> getFinancialEscalationMatrixById(@PathVariable Integer id) {
        Optional<FinancialEscalationMatrix> optionalFinancialEscalationMatrix = financialEscalationMatrixRepository.findById(id);
        if (optionalFinancialEscalationMatrix.isPresent()) {
            return new ResponseEntity<>(optionalFinancialEscalationMatrix.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<FinancialEscalationMatrix> updateFinancialEscalationMatrix(@PathVariable Integer id,
                                                                                     @RequestBody FinancialEscalationMatrix financialEscalationMatrix) {
        if (!financialEscalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        financialEscalationMatrix.setId(id);
        FinancialEscalationMatrix updatedFinancialEscalationMatrix = financialEscalationMatrixRepository.save(financialEscalationMatrix);
        return new ResponseEntity<>(updatedFinancialEscalationMatrix, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFinancialEscalationMatrix(@PathVariable Integer id) {
        if (!financialEscalationMatrixRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        financialEscalationMatrixRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
