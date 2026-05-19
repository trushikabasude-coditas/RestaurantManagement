package com.example.RestaurantManagement.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.aspectj.lang.annotation.Aspect;
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
        String body = """
  <p> You have been invited


}
