package com.police.management.police_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AdminController {

    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Displays a list of all crime reports.
     */
    @GetMapping("/admin/crime-reports")
    public String listCrimeReports(Model model) {
        List<CrimeReport> crimeReports = adminService.getAllCrimeReports();
        model.addAttribute("crimeReports", crimeReports);
        logger.info("Fetched all crime reports for admin view");
        return "admin/manage-crime-reports"; // Ensure this JSP page exists for managing crime reports
    }

    /**
     * Deletes a specific crime report by ID.
     */
    @PostMapping("/admin/crime-reports/delete/{id}")
    public String deleteCrimeReport(@PathVariable Long id, Model model) {
        Optional<CrimeReport> crimeReport = adminService.getCrimeReportById(id);

        if (crimeReport.isPresent()) {
            adminService.deleteCrimeReport(id);
            logger.info("Deleted crime report with ID: {}", id);
            return "redirect:/admin/crime-reports"; // Redirect back to the list of crime reports
        } else {
            model.addAttribute("error", "Crime report not found");
            logger.warn("Attempted to delete non-existent crime report with ID: {}", id);
            return "admin/manage-crime-reports"; // Return to page with error message
        }
    }
}
