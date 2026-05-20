package com.example.RestaurantManagement.controller;

import com.example.RestaurantManagement.dto.ApiResponse;
import com.example.RestaurantManagement.dto.TableRequestDto;
import com.example.RestaurantManagement.dto.TableResponseDto;
import com.example.RestaurantManagement.entity.DineTable;
import com.example.RestaurantManagement.service.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {
private final TableService tableService;
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<TableResponseDto>> create(@Valid @RequestBody TableRequestDto dto,
                                                                @AuthenticationPrincipal String managerEmail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Table created",
                tableService.create(dto, managerEmail)));
    }
//,manager can delete the table onbly
@DeleteMapping("{/id}")
@PreAuthorize("hasRole('MANAGER')")
public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,
                                                @AuthenticationPrincipal String managerEmail) {
tableService.delete(id, managerEmail);
return  ResponseEntity.ok(ApiResponse.success("Table deleted"));

}
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<TableResponseDto>>> getByBranch(@PathVariable Long branchId,
                                                                           @AuthenticationPrincipal String requesterEmail) {
        return ResponseEntity.ok(ApiResponse.success("Tables fetched",
                tableService.getByBranch(branchId, requesterEmail)));
    }
    @GetMapping("/{id}")//single table with id
    public ResponseEntity<ApiResponse<TableResponseDto>> getById(@PathVariable Long id,
                                                                @AuthenticationPrincipal String requesterEmail) {
        return ResponseEntity.ok(ApiResponse.success("Table fetched",
                tableService.getById(id, requesterEmail)));
    }
}


