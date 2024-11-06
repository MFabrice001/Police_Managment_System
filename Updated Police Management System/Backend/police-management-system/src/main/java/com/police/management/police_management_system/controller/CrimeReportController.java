package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.entity.CrimeReport;
import com.police.management.police_management_system.service.CrimeReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/crimes")
public class CrimeReportController {

    private final CrimeReportService crimeReportService;
    private static final Logger logger = LoggerFactory.getLogger(CrimeReportController.class);

    public CrimeReportController(CrimeReportService crimeReportService) {
        this.crimeReportService = crimeReportService;
    }

    @GetMapping
    public ResponseEntity<List<CrimeReport>> getAllCrimes() {
        List<CrimeReport> crimes = crimeReportService.getAllCrimes();
        logger.info("Fetched all crime reports");
        return new ResponseEntity<>(crimes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CrimeReport> createCrime(@RequestBody CrimeReport crimeReport) {
        CrimeReport savedCrime = crimeReportService.saveCrimeReport(crimeReport);
        logger.info("Created new crime report with ID: {}", savedCrime.getId());
        return new ResponseEntity<>(savedCrime, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrimeReport> getCrimeById(@PathVariable Long id) {
        try {
            CrimeReport crime = crimeReportService.getCrimeById(id);
            logger.info("Fetched crime report with ID: {}", id);
            return new ResponseEntity<>(crime, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.warn("Crime report with ID: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrimeReport> updateCrime(@PathVariable Long id, @RequestBody CrimeReport crimeReport) {
        try {
            CrimeReport updatedCrime = crimeReportService.updateCrimeReport(id, crimeReport);
            logger.info("Updated crime report with ID: {}", id);
            return new ResponseEntity<>(updatedCrime, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.warn("Attempted to update non-existent crime report with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrime(@PathVariable Long id) {
        try {
            crimeReportService.deleteCrimeReport(id);
            logger.info("Deleted crime report with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.warn("Attempted to delete non-existent crime report with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
