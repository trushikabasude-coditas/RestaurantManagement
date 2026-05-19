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
    public ResponseEntity<ApiResponse<InviteResponseDto>> invite(@Valid @RequestBody InviteRequestDto dto,@AuthenticationPrincipal String senderEmail) {
        InviteResponseDto result = authService.sendInvitation(dto, senderEmail);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invitation sent successfully", result));
    }


    @PostMapping("/accept-invite")
    public ResponseEntity<ApiResponse<AuthResponseDto>> acceptInvite(@Valid @RequestBody AcceptInviteRequestDto dto) {

        AuthResponseDto result = authService.acceptInvitation(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", result));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody LoginRequestDto dto) {

        AuthResponseDto result = authService.login(dto);
        return ResponseEntity
                .ok(ApiResponse.success("Login successful", result));
    }
    public ResponseEntity<ApiResponse<AuthResponseDto>> refreshToken(@Valid @RequestBody RefreshTokenRequestDto dto) {
        AuthResponseDto result = authService.refreshToken(dto);
        return ResponseEntity
                .ok(ApiResponse.success("Refresh token successful", result));
    }
    public ResponseEntity<ApiResponse<RegisterResponseDto>> register(@Valid @RequestBody RegisterResquestDto  dto) {
        RegisterResponseDto reuslt = authService.registerUser(dto);
    return  ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("User registered successfully", reuslt));
    }


}
