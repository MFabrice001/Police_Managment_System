package com.police.management.police_management_system.controller;

import com.police.management.police_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public PasswordResetController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    // Endpoint to request password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, HttpServletRequest request) {
        Locale locale = request.getLocale();
        try {
            userService.processForgotPassword(email);
            String successMessage = messageSource.getMessage("password.reset.success", null, locale);
            logger.info("Password reset request successful for email: {}", email);
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            logger.error("Error processing password reset for email: {}", email, e);
            String failureMessage = messageSource.getMessage("error.not.found", null, locale);
            return ResponseEntity.status(400).body(failureMessage);
        }
    }

    // Endpoint to confirm password reset token
    @GetMapping("/confirm-reset")
    public ResponseEntity<String> confirmPasswordReset(@RequestParam String token, HttpServletRequest request) {
        Locale locale = request.getLocale();
        try {
            boolean isValid = userService.validatePasswordResetToken(token);
            if (isValid) {
                String validMessage = messageSource.getMessage("token.valid", null, locale);
                logger.info("Token validation successful for token: {}", token);
                return ResponseEntity.ok(validMessage);
            } else {
                String invalidMessage = messageSource.getMessage("token.invalid.or.expired", null, locale);
                logger.warn("Invalid or expired token: {}", token);
                return ResponseEntity.status(404).body(invalidMessage);
            }
        } catch (Exception e) {
            logger.error("Error validating token: {}", token, e);
            String errorMessage = messageSource.getMessage("token.validation.error", null, locale);
            return ResponseEntity.status(500).body(errorMessage);
        }
    }

    // Endpoint to reset password after token validation
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword,
            HttpServletRequest request) {

        Locale locale = request.getLocale();
        try {
            boolean isValid = userService.validatePasswordResetToken(token);
            if (isValid) {
                userService.updatePassword(token, newPassword);
                String successMessage = messageSource.getMessage("password.reset.success", null, locale);
                logger.info("Password reset successful for token: {}", token);
                return ResponseEntity.ok(successMessage);
            } else {
                String failureMessage = messageSource.getMessage("token.invalid.or.expired", null, locale);
                logger.warn("Attempt to reset password with invalid or expired token: {}", token);
                return ResponseEntity.status(404).body(failureMessage);
            }
        } catch (Exception e) {
            logger.error("Error resetting password for token: {}", token, e);
            String errorMessage = messageSource.getMessage("password.reset.error", null, locale);
            return ResponseEntity.status(500).body(errorMessage);
        }
    }
}
