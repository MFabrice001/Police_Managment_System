package com.police.management.police_management_system.service;

import com.police.management.police_management_system.entity.CrimeReport;
import com.police.management.police_management_system.repository.CrimeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private CrimeReportRepository crimeReportRepository;

    /**
     * Retrieves all crime reports.
     *
     * @return a list of all crime reports
     */
    public List<CrimeReport> getAllCrimeReports() {
        return crimeReportRepository.findAll();
    }

    /**
     * Deletes a specific crime report by its ID.
     *
     * @param id the ID of the crime report to delete
     */
    public void deleteCrimeReport(Long id) {
        crimeReportRepository.deleteById(id);
    }

    /**
     * Retrieves a specific crime report by its ID.
     *
     * @param id the ID of the crime report to retrieve
     * @return an Optional containing the crime report if found, or empty if not found
     */
    public Optional<CrimeReport> getCrimeReportById(Long id) {
        return crimeReportRepository.findById(id);
    }
}
