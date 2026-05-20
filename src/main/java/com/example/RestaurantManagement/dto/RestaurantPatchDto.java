package com.example.RestaurantManagement.dto;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantPatchDto {

    @Size(max = 200)
    private String name;

    private String description;

    @Size(max = 20)
    private String gstNumber;

    @Size(max = 20)
    private String panNumber;

    private String logoUrl;
}
