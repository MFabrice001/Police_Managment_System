package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.entity.User;
import com.police.management.police_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Show signup form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register"; // Updated to match project structure
    }

    // Process signup form
    @PostMapping("/signup")
    public String processSignUpForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }
        try {
            userService.saveUser(user, "User");
            model.addAttribute("successMessage", "Registration successful!");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            model.addAttribute("errorMessage", "An error occurred during registration. Please try again.");
            return "user/register";
        }
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    // Process login form
    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute("user") User user, Model model) {
        try {
            User authenticatedUser = userService.authenticate(user.getUsername(), user.getPassword());
            if (authenticatedUser != null) {
                if (authenticatedUser.hasRole("Admin")) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/user/home";
                }
            } else {
                model.addAttribute("errorMessage", "Invalid credentials, please try again.");
                return "user/login";
            }
        } catch (Exception e) {
            logger.error("Login error", e);
            model.addAttribute("errorMessage", "An error occurred during login. Please try again.");
            return "user/login";
        }
    }

    // Show user profile
    @GetMapping("/user/profile")
    public String showProfile(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "user/profile";
    }

    // Show home page
    @GetMapping("/user/home")
    public String showHomePage(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("message", "Welcome to the Police Management System");
        return "user/home";
    }

    // Show about-us page
    @GetMapping("/user/about-us")
    public String showAboutUsPage() {
        return "user/about-us";
    }

    // Show contact-us page
    @GetMapping("/user/contact-us")
    public String showContactUsPage() {
        return "user/contact-us";
    }

    // Profile update form (for editing profile)
    @GetMapping("/user/profile/edit")
    public String showProfileEditForm(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "user/edit-profile";
    }

    // Process profile update
    @PostMapping("/user/profile/edit")
    public String processProfileEditForm(@Valid @ModelAttribute("user") User updatedUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/edit-profile";
        }
        try {
            userService.updateUserProfile(updatedUser);
            model.addAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/user/profile";
        } catch (Exception e) {
            logger.error("Error updating profile", e);
            model.addAttribute("errorMessage", "An error occurred while updating your profile. Please try again.");
            return "user/edit-profile";
        }
    }
}
