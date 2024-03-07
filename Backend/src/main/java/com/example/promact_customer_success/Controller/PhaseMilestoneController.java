package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.PhaseMilestone;
import com.example.promact_customer_success.Repository.PhaseMilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/phasemilestones")
public class PhaseMilestoneController {

    @Autowired
    private PhaseMilestoneRepository phaseMilestoneRepository;

    // Create
    @PostMapping
    public ResponseEntity<PhaseMilestone> createPhaseMilestone(@Valid @RequestBody PhaseMilestone phaseMilestone) {
        PhaseMilestone savedPhaseMilestone = phaseMilestoneRepository.save(phaseMilestone);
        return new ResponseEntity<>(savedPhaseMilestone, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<PhaseMilestone>> getAllPhaseMilestones() {
        List<PhaseMilestone> PhaseMilestones =(List<PhaseMilestone>) phaseMilestoneRepository.findAll();
        return new ResponseEntity<>(PhaseMilestones, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<PhaseMilestone> getPhaseMilestoneById(@PathVariable Integer id) {
        Optional<PhaseMilestone> optionalPhaseMilestone = phaseMilestoneRepository.findById(id);
        return optionalPhaseMilestone.map(phaseMilestone ->
                        new ResponseEntity<>(phaseMilestone, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<PhaseMilestone> updatePhaseMilestone(@PathVariable Integer id,
                                                               @Valid @RequestBody PhaseMilestone phaseMilestone) {
        if (!phaseMilestoneRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        phaseMilestone.setId(id);
        PhaseMilestone updatedPhaseMilestone = phaseMilestoneRepository.save(phaseMilestone);
        return new ResponseEntity<>(updatedPhaseMilestone, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhaseMilestone(@PathVariable Integer id) {
        if (!phaseMilestoneRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        phaseMilestoneRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

