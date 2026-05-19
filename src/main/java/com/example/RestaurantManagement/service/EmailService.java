package com.example.RestaurantManagement.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final mailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${app.invite.base-url}")
    private String baseUrl;

    @Async
    public void sendInvitationEmail(String toEmail, String role, String token) {
        String acceptLink = baseUrl + "/api/auth/accept-invite?token=" + token;

        String subject = "You're invited to join RestaurantManagement as " + role;
        String body = "You have been invited to onboard into the Great Restaurant MAnagement Platform!!-WElcome"
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send invitation email to " + toEmail, e);
        }
    }
}
