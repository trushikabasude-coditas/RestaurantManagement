package com.example.RestaurantManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RestaurantRequestDTo {
    @NotBlank("Restaurant name is required field")
    @Size
    @Size(max=200)
    private String name;

}
