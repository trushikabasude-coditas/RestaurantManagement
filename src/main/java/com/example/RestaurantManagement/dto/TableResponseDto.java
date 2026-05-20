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
    private String tableName;
    private Integer capacity;
    private TableStatus status;
    private  Long branchId;
    private String  BranchName;
}
