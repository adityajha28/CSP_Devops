package com.example.promact_customer_success.Controller;

import com.example.promact_customer_success.Entity.VersionHistory;
import com.example.promact_customer_success.Repository.VersionHistoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/versionhistory")
public class VersionHistoryController {

    @Autowired
    private VersionHistoryRepository versionHistoryRepository;

    // Create
    @PostMapping
    public ResponseEntity<VersionHistory> createVersionHistory(@Valid @RequestBody VersionHistory versionHistory) {
        VersionHistory savedVersionHistory = versionHistoryRepository.save(versionHistory);
        return new ResponseEntity<>(savedVersionHistory, HttpStatus.CREATED);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<VersionHistory>> getAllVersionHistories() {
        List<VersionHistory> versionHistories = (List<VersionHistory>) versionHistoryRepository.findAll();
        return new ResponseEntity<>(versionHistories, HttpStatus.OK);
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<VersionHistory> getVersionHistoryById(@PathVariable Integer id) {
        Optional<VersionHistory> optionalVersionHistory = versionHistoryRepository.findById(id);
        if (optionalVersionHistory.isPresent()) {
            VersionHistory versionHistory = optionalVersionHistory.get();
            return new ResponseEntity<>(versionHistory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<VersionHistory> updateVersionHistory(@PathVariable Integer id,
                                                               @RequestBody VersionHistory versionHistory) {
        if (!versionHistoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionHistory.setId(id);
        VersionHistory updatedVersionHistory = versionHistoryRepository.save(versionHistory);
        return new ResponseEntity<>(updatedVersionHistory, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteVersionHistory(@PathVariable Integer id) {
        if (!versionHistoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionHistoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
