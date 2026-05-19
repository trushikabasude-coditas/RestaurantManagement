package com.example.RestaurantManagement.dto;

import com.example.RestaurantManagement.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Role is required")
    private Role role;

    // Required for MANAGER, WAITER, CHEF, CLEANER — not needed for OWNER
    private Long branchId;

    // Required for OWNER and branch-level roles
    private Long restaurantId;
}
