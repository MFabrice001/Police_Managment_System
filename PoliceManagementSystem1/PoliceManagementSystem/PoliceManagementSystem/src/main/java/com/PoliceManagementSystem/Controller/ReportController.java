package com.PoliceManagementSystem.Controller;


import com.PoliceManagementSystem.Model.Gun;
import com.PoliceManagementSystem.Model.Request;
import com.PoliceManagementSystem.Model.Returns;
import com.PoliceManagementSystem.Model.Signup;
import com.PoliceManagementSystem.Service.GunService;
import com.PoliceManagementSystem.Service.RequestService;
import com.PoliceManagementSystem.Service.ReturnsService;
import com.PoliceManagementSystem.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

@Controller
public class ReportController {
    @Autowired
    SignupService signupService;
    @Autowired
    RequestService requestService;
    @Autowired
    ReturnsService returnsService;
    @Autowired
    GunService gunService;


    @GetMapping("/report")
    public String report(@RequestParam(name = "page", defaultValue = "0") int currentPage,
                         @RequestParam(name = "size", defaultValue = "10") int size, Model model) {
        model.addAttribute("policeModel", new Signup());

        // Police
        Page<Signup> signupPage = signupService.getAllPolicePageable(currentPage, size);
        model.addAttribute("allPolice", signupPage.getContent());
        model.addAttribute("currentPagePolice", currentPage);
        model.addAttribute("totalPagesPolice", signupPage.getTotalPages());
        model.addAttribute("totalItemsPolice", signupPage.getTotalElements());

        // Requests
        Page<Request> requestsPage = requestService.getAllRequestsPageable(currentPage, size);
        model.addAttribute("allRequests", requestsPage.getContent());
        model.addAttribute("currentPageRequests", currentPage);
        model.addAttribute("totalPagesRequests", requestsPage.getTotalPages());
        model.addAttribute("totalItemsRequests", requestsPage.getTotalElements());

        // Guns
        Page<Gun> gunsPage = gunService.getAllGunsPageable(currentPage, size);
        model.addAttribute("allGuns", gunsPage.getContent());
        model.addAttribute("currentPageGuns", currentPage);
        model.addAttribute("totalPagesGuns", gunsPage.getTotalPages());
        model.addAttribute("totalItemsGuns", gunsPage.getTotalElements());

        // Returns
        Page<Returns> returnsPage = returnsService.getAllReturnsPageable(currentPage, size);
        model.addAttribute("allReturns", returnsPage.getContent());
        model.addAttribute("currentPageReturns", currentPage);
        model.addAttribute("totalPagesReturns", returnsPage.getTotalPages());
        model.addAttribute("totalItemsReturns", returnsPage.getTotalElements());

        // other model attributes...
        model.addAttribute("size", size);

        return "View";
    }


    @GetMapping("/report/delete-police/{id}")
    public String deletePolice(@PathVariable("id") UUID id, Model model) {
        try {
            signupService.deleteSignup(id);
            return "redirect:/report";
        } catch (DataIntegrityViolationException e) {
            // Log the exception if needed
            e.printStackTrace();

            // Add custom error message to the model
            model.addAttribute("errorMessage", "Cannot delete police due to existing dependencies.");

            // Return the error view
            return "error"; // Create an "error.html" Thymeleaf template for displaying error messages
        }
    }

    @GetMapping("/report/delete-request/{id}")
    public String deleteRequest(@PathVariable("id") UUID id, Model model){
        try {
            requestService.deleteRequest(id);
        } catch (Exception e) {
            // Handle the exception (check if it's a foreign key constraint violation)
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                String errorMessage = "Cannot delete request due to existing dependencies.";
                model.addAttribute("errorMessage", errorMessage);
            } else {
                // Handle other exceptions if needed
            }
        }
        return "redirect:/report";
    }

}
