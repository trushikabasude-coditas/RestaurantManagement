package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.superadmin.email:superadmin@restaurant.com}")
    private String superAdminEmail;

    @Value("${app.superadmin.password:SuperAdmin@123}")
    private String superAdminPassword;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail(superAdminEmail)) {
            userRepository.save(User.builder()
                    .name("Super Admin")
                    .email(superAdminEmail)
                    .passwordHash(passwordEncoder.encode(superAdminPassword))
                    .role(Role.SUPER_ADMIN)
                    .active(true)
                    .build());
            log.info("Super Admin created: {}", superAdminEmail);
        }
}
}

