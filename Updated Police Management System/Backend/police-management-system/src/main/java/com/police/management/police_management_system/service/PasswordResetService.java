package com.police.management.police_management_system.service;

import com.police.management.police_management_system.Model.EPasswordResetToken;
import com.police.management.police_management_system.entity.User;
import com.police.management.police_management_system.repository.PasswordResetTokenRepository;
import com.police.management.police_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
    }

    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); // Token expires in 1 hour

        // Create a new PasswordResetToken with the generated token, user, and expiry date
        EPasswordResetToken passwordResetToken = new EPasswordResetToken(token, user, expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        return token;
    }

    public boolean validatePasswordResetToken(String token) {
        EPasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        // Check if the token is null or doesn't exist
        if (passwordResetToken == null) {
            return false; // Token does not exist
        }

        // Check if the token has expired
        if (passwordResetToken.getExpiryDate() == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false; // Token is expired
        }

        return true; // Token is valid
    }
}
