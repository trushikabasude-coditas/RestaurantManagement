package com.example.RestaurantManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRequestDto {
    @NotNull(message = "Branch Id is required")
    private Long branchId;
    @NotNull(message = "Branch name is required")
    private String branchName;
    @NotNull(message = "Table number is required field")
    @Min(value=1,message = "Positive Value is Required")
    private Integer tableNumber;
   @Min(value=1,message = "There should be atleast capacity of 1")
   private Integer Capacity;

}
