package com.police.management.police_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.ArrayList; // Import for example lists

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Add a welcome message for the dashboard
        model.addAttribute("welcomeMessage", "Welcome to the Admin Dashboard!");
        return "admin/dashboard"; // Ensure this matches your JSP file path
    }

    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        // Example user list; replace this with actual user retrieval logic
        ArrayList<String> userList = new ArrayList<>();
        userList.add("User1");
        userList.add("User2");
        userList.add("User3");

        model.addAttribute("userList", userList); // Add user list to the model
        return "admin/manage-users"; // Ensure this matches your JSP file path
    }

    @GetMapping("/reports")
    public String showReports(Model model) {
        // Example report list; replace this with actual report retrieval logic
        ArrayList<String> reportList = new ArrayList<>();
        reportList.add("Report1");
        reportList.add("Report2");
        reportList.add("Report3");

        model.addAttribute("reportList", reportList); // Add report list to the model
        return "admin/reports"; // Ensure this matches your JSP file path
    }
}
