package com.example.RestaurantManagement.service;

public class mail {
    package com.example.RestaurantManagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

    @Service
    @RequiredArgsConstructor
    public class EmailService {

        private final JavaMailSender mailSender;

        @Value("${spring.mail.username}")
        private String fromEmail;

        @Value("${app.invite.base-url}")
        private String baseUrl;

        @Async
        public void sendInvitationEmail(String toEmail, String role, String token) {
            String acceptLink = baseUrl + "/api/auth/accept-invite?token=" + token;

            String subject = "You're invited to join RestaurantManagement as " + role;
            String body = """
                <h2>You have been invited!</h2>
                <p>You've been invited to join the platform as <strong>%s</strong>.</p>
                <p>Click the link below to accept your invitation and set up your account:</p>
                <a href="%s" style="padding:10px 20px;background:#4CAF50;color:white;text-decoration:none;border-radius:4px;">
                    Accept Invitation
                </a>
                <p>This link expires in <strong>48 hours</strong>.</p>
                <p>If you did not expect this invitation, you can safely ignore this email.</p>
                """.formatted(role, acceptLink);

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
}
