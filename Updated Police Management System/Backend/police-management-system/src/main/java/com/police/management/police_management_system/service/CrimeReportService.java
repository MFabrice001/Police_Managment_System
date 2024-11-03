package com.police.management.police_management_system.service;

import com.police.management.police_management_system.entity.CrimeReport;
import com.police.management.police_management_system.repository.CrimeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CrimeReportService {

    @Autowired
    private CrimeReportRepository crimeReportRepository;

    // Retrieve all crime reports
    public List<CrimeReport> getAllCrimes() {
        return crimeReportRepository.findAll();
    }

    // Save a new crime report
    @Transactional
    public CrimeReport saveCrimeReport(CrimeReport crimeReport) {
        return crimeReportRepository.save(crimeReport);
    }

    // Retrieve a crime report by ID
    public CrimeReport getCrimeById(Long id) {
        return crimeReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crime report not found"));
    }

    // Update an existing crime report by ID
    @Transactional
    public CrimeReport updateCrimeReport(Long id, CrimeReport crimeReport) {
        CrimeReport existingCrime = getCrimeById(id);

        // Update fields with values from the provided crimeReport
        existingCrime.setUser(crimeReport.getUser());
        existingCrime.setDescription(crimeReport.getDescription());
        existingCrime.setLocation(crimeReport.getLocation());
        existingCrime.setDateReported(crimeReport.getDateReported());


        return crimeReportRepository.save(existingCrime);
    }

    // Delete a crime report by ID
    @Transactional
    public void deleteCrimeReport(Long id) {
        crimeReportRepository.deleteById(id);
    }
}
