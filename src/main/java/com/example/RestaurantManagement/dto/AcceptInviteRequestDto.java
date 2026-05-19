package com.example.RestaurantManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptInviteRequestDto {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "Name is required")
    @Size(max = 120)
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Size(max = 20)
    private String phone;}
