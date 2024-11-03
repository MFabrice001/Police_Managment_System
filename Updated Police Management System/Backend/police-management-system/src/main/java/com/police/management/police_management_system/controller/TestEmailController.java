package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEmailController {

    private final EmailService emailService;

    @Autowired
    public TestEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String email) throws MessagingException {
        String resetLink = "http://example.com/reset-password"; // Replace with your actual reset password link
        emailService.sendResetPasswordEmail(email, resetLink);
        return "Email sent to " + email;
    }
}
