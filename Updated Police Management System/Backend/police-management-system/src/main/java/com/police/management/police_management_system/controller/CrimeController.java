package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.model.Crime;
import com.police.management.police_management_system.service.CrimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crimes")
public class CrimeController {

    private final CrimeService crimeService;

    public CrimeController(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @PostMapping
    public ResponseEntity<Crime> reportCrime(@RequestBody Crime crime) {
        Crime savedCrime = crimeService.reportCrime(crime);
        return ResponseEntity.ok(savedCrime);
    }

    @GetMapping
    public ResponseEntity<List<Crime>> getAllCrimes() {
        return ResponseEntity.ok(crimeService.getAllCrimes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crime> getCrimeById(@PathVariable Long id) {
        return ResponseEntity.ok(crimeService.getCrimeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrime(@PathVariable Long id) {
        crimeService.deleteCrime(id);
        return ResponseEntity.noContent().build();
    }
}
