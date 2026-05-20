package com.example.RestaurantManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BranchResponseDto {
    private Long id;
    private String branchName;
    private String address;
    private String city;
    private String phone;
    private boolean headBranch;
    private boolean active;
    private Long restaurantId;
    private String restaurantName;
    private String managerName;
    private String managerEmail;
}