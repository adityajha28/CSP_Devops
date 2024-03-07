package com.example.promact_customer_success.Controller;
import com.example.promact_customer_success.Entity.SprintDetail;
import com.example.promact_customer_success.Repository.SprintDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sprintdetails")
public class SprintDetailController {

    @Autowired
    private SprintDetailRepository sprintDetailRepository;

    // Create
    @PostMapping
    public ResponseEntity<SprintDetail> createSprintDetail(@Valid @RequestBody SprintDetail sprintDetail) {
        SprintDetail savedSprintDetail = sprintDetailRepository.save(sprintDetail);
        return new ResponseEntity<>(savedSprintDetail, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<SprintDetail>> getAllSprintDetails() {
        List<SprintDetail> sprintDetails = (List<SprintDetail>) sprintDetailRepository.findAll();
        return new ResponseEntity<>(sprintDetails, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<SprintDetail> getSprintDetailById(@PathVariable Integer id) {
        Optional<SprintDetail> optionalSprintDetail = sprintDetailRepository.findById(id);
        if (optionalSprintDetail.isPresent()) {
            SprintDetail sprintDetail = optionalSprintDetail.get();
            return new ResponseEntity<>(sprintDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<SprintDetail> updateSprintDetail(@PathVariable Integer id, @Valid @RequestBody SprintDetail sprintDetail) {
        if (!sprintDetailRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sprintDetail.setId(id);
        SprintDetail updatedSprintDetail = sprintDetailRepository.save(sprintDetail);
        return new ResponseEntity<>(updatedSprintDetail, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprintDetail(@PathVariable Integer id) {
        if (!sprintDetailRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sprintDetailRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

