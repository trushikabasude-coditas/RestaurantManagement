package com.example.RestaurantManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String description;

    private String gstNumber;
    private String logoUrl;
    private String ownerName;
    private String ownerEmail;
}