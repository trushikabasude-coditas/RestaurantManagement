package com.example.RestaurantManagement.dto;

import com.example.RestaurantManagement.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class inviteRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Role is required")
    private Role role;

    // Required when inviting MANAGER, WAITER, CHEF, CASHIER
    private Long branchId;
}
