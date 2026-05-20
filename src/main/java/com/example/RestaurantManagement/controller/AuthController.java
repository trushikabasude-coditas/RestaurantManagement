package com.example.RestaurantManagement.controller;

import com.example.RestaurantManagement.dto.*;
import com.example.RestaurantManagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;

    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<InviteResponseDto>> invite(
            @Valid @RequestBody InviteRequestDto dto,
            @AuthenticationPrincipal String senderEmail) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invitation sent successfully",
                        authService.sendInvitation(dto, senderEmail)));
    }

    @PostMapping("/accept-invite")
    public ResponseEntity<ApiResponse<AuthResponseDto>> acceptInvite(
            @Valid @RequestBody AcceptInviteRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully",
                        authService.acceptInvitation(dto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody LoginRequestDto dto) {

        return ResponseEntity.ok(ApiResponse.success("Login successful",
                authService.login(dto)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refresh(
            @Valid @RequestBody RefreshTokenRequestDto dto) {

        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully",
                authService.refreshToken(dto)));
    }

}
