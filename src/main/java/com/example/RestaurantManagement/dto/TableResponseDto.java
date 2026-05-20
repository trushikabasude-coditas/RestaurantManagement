package com.example.RestaurantManagement.dto;

import com.example.RestaurantManagement.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class TableResponseDto {
private Long id;
    private Integer tableNumber;
    private Integer capacity;
    private String status;
    private  Long branchId;
    private String  branchName;
}
