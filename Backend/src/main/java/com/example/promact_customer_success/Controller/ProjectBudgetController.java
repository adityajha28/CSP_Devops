package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.ProjectBudget;
import com.example.promact_customer_success.Repository.ProjectBudgetRepository;
import com.example.promact_customer_success.Repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projectbudgets")
public class ProjectBudgetController {

    @Autowired
    private ProjectBudgetRepository projectBudgetRepository;

    @Autowired
    private ProjectRepository projectRepository;
    // Create
    @PostMapping
    public ResponseEntity<ProjectBudget> createProjectBudget(@Valid @RequestBody ProjectBudget projectBudget) {

        System.out.println(projectBudget);

        // Fetch the Project entity corresponding to the given projectId
        Integer projectId = projectBudget.getProject().getId();

        // Set the Project entity in the ProjectBudget
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        System.out.println(optionalProject);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            projectBudget.setProject(project);
            ProjectBudget savedProjectBudget = projectBudgetRepository.save(projectBudget);
            return new ResponseEntity<>(savedProjectBudget, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<ProjectBudget>> getAllProjectBudgets() {
        List<ProjectBudget> projectBudgets = (List<ProjectBudget>) projectBudgetRepository.findAll();
        return new ResponseEntity<>(projectBudgets, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<ProjectBudget> getProjectBudgetsById(@PathVariable Integer id) {
        System.out.println(id);
        Optional<ProjectBudget> optionalProjectBudget = projectBudgetRepository.findById(id);
        System.out.println(optionalProjectBudget);
        if (optionalProjectBudget.isPresent()) {
            ProjectBudget project = optionalProjectBudget.get();

        return new ResponseEntity<>(project, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ProjectBudget> updateProjectBudget(@PathVariable Integer id,
                                                             @RequestBody ProjectBudget projectBudget) {
        if (!projectBudgetRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectBudget.setId(id);
        ProjectBudget updatedProjectBudget = projectBudgetRepository.save(projectBudget);
        return new ResponseEntity<>(updatedProjectBudget, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProjectBudget(@PathVariable Integer id) {
        if (!projectBudgetRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectBudgetRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

