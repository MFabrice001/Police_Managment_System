package com.PoliceManagementSystem.Controller;

import com.PoliceManagementSystem.Model.Rank;
import com.PoliceManagementSystem.Model.Signup;
import com.PoliceManagementSystem.Service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    SignupService signupService;

    @GetMapping("/polices")
    public String users(Model model){
        model.addAttribute("signupModel",new Signup());
        model.addAttribute("rank", Rank.values());
        return "Login";
    }

    @PostMapping("loginPolice")
    public String loginPolice(@ModelAttribute("signupModel")Signup signup, Model model){
        Signup loggedInPolice = signupService.login(signup);

        if (loggedInPolice != null) {
            // Soldier found, proceed with login
            return "redirect:/";
        } else {
            // Soldier not found, add an error message to the model and redirect to login page
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/polices";
        }
    }
}