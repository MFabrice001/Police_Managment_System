package com.PoliceManagementSystem.Controller;

import com.PoliceManagementSystem.Model.Signup;
import com.PoliceManagementSystem.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@Controller
public class EditController {

    @Autowired
    private SignupService signupService;
    @GetMapping("/editPolice/{id}")
    public String editSignupForm(@PathVariable("id") UUID id, Model model) {
        Optional<Signup> optionalSignup = signupService.findById(id);
        if (optionalSignup.isPresent()) {
            model.addAttribute("signupModel", optionalSignup.get());
            return "edit_police"; // Replace with your actual edit form template name
        } else {
            // Handle soldier not found
            return "redirect:/police";
        }
    }
}
