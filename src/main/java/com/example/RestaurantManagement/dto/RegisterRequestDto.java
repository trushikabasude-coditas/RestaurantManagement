package com.example.RestaurantManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto (@NotBlank String username,
                               @NotBlank @Email String email,
                               @NotBlank
                               @Size(min=6)
                               String password,
                               @NotBlank
                               @Size(min=6)
                               String fullName,
                               @Pattern(regexp ="A|EMPLOYEE" ,message="Must be admin or employee")
                                  String role
)
{}
