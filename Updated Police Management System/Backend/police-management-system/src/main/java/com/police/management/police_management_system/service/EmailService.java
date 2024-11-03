package com.police.management.police_management_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetPasswordEmail(String to, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(
                    "<p>Click the following link to reset your password:</p>" +
                            "<a href=\"" + resetLink + "\">Reset Password</a>",
                    true
            );

            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception (logging, rethrowing, etc.)
            e.printStackTrace();
        }
    }
}
