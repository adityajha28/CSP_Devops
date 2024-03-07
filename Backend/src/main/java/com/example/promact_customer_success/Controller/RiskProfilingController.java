package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.RiskProfiling;
import com.example.promact_customer_success.Repository.RiskProfilingRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/riskprofilings")
public class RiskProfilingController {

    @Autowired
    private RiskProfilingRepository riskProfilingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create
    @PostMapping
    public ResponseEntity<RiskProfiling> createRiskProfiling(@Valid @RequestBody RiskProfiling riskProfiling) {

        // Check if the associated project exists
        Integer projectId = riskProfiling.getProject().getId();
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            riskProfiling.setProject(project);
            RiskProfiling savedRiskProfiling = riskProfilingRepository.save(riskProfiling);
            return new ResponseEntity<>(savedRiskProfiling, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<RiskProfiling>> getAllRiskProfilings() {
        List<RiskProfiling> riskProfilings = (List<RiskProfiling>) riskProfilingRepository.findAll();
        return new ResponseEntity<>(riskProfilings, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<RiskProfiling> getRiskProfilingById(@PathVariable Integer id) {
        Optional<RiskProfiling> optionalRiskProfiling = riskProfilingRepository.findById(id);
        if (optionalRiskProfiling.isPresent()) {
            RiskProfiling riskProfiling = optionalRiskProfiling.get();
            return new ResponseEntity<>(riskProfiling, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<RiskProfiling> updateRiskProfiling(@PathVariable Integer id,
                                                             @RequestBody RiskProfiling riskProfiling) {
        if (!riskProfilingRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        riskProfiling.setId(id);
        RiskProfiling updatedRiskProfiling = riskProfilingRepository.save(riskProfiling);
        return new ResponseEntity<>(updatedRiskProfiling, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRiskProfiling(@PathVariable Integer id) {
        if (!riskProfilingRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        riskProfilingRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
