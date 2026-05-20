package com.example.RestaurantManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String email;
    private String name;
}
