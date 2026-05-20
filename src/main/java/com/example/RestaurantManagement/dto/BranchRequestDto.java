package com.example.RestaurantManagement.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchRequestDto {

    @NotBlank(message = "Branch name is required")
    @Size(max = 200)
    private String branchName;

    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 20)
    private String phone;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    private boolean headBranch = false;
}
