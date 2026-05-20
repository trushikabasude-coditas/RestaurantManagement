package com.example.RestaurantManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StaffResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private boolean active;
    private Long branchId;
    private String branchName;
    private Long restaurantId;
    private String restaurantName;
}