
package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.Stakeholder;
import com.example.promact_customer_success.Repository.StakeholderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stakeholders")
public class StakeholderController {

    @Autowired
    private StakeholderRepository stakeholderRepository;

    // Create
    @PostMapping
    public ResponseEntity<Stakeholder> createStakeholder(@Valid @RequestBody Stakeholder stakeholder) {
        Stakeholder savedStakeholder = stakeholderRepository.save(stakeholder);
        return new ResponseEntity<>(savedStakeholder, HttpStatus.CREATED);
    }
    // Read all
    @GetMapping
    public ResponseEntity<List<Stakeholder>> getAllStakeholders() {
        List<Stakeholder> stakeholders = (List<Stakeholder>) stakeholderRepository.findAll();
        return new ResponseEntity<>(stakeholders, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<Stakeholder> getStakeholderById(@PathVariable Integer id) {
        Optional<Stakeholder> optionalStakeholder = stakeholderRepository.findById(id);
        if (optionalStakeholder.isPresent()) {
            Stakeholder stakeholder = optionalStakeholder.get();
            return new ResponseEntity<>(stakeholder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Stakeholder> updateStakeholder(@PathVariable Integer id,
                                                         @RequestBody Stakeholder stakeholder) {
        if (!stakeholderRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        stakeholder.setId(id);
        Stakeholder updatedStakeholder = stakeholderRepository.save(stakeholder);
        return new ResponseEntity<>(updatedStakeholder, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStakeholder(@PathVariable Integer id) {
        if (!stakeholderRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        stakeholderRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/project/{id}")
//    public ResponseEntity<List<Stakeholder>> getStakeholderByProjectId(@PathVariable Integer id){
//        List<Stakeholder> stakeholders =(List<Stakeholder>) stakeholderRepository.findByProjectId(id);
//        if (!stakeholders.isEmpty()) {
//            return new ResponseEntity<>(stakeholders, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
