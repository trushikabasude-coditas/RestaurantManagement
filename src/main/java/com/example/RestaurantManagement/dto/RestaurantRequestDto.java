package com.example.RestaurantManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestDto {

    @NotBlank(message = "Restaurant name is required")
    @Size(max = 200)
    private String name;

    private String description;

    @Size(max = 20)
    private String gstNumber;

    @Size(max = 20)
    private String panNumber;

    private String logoUrl;
}