package com.example.RestaurantManagement.controller;

import com.example.RestaurantManagement.dto.ApiResponse;
import com.example.RestaurantManagement.dto.BranchRequestDto;
import com.example.RestaurantManagement.dto.BranchResponseDto;
import com.example.RestaurantManagement.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor

public class BranchController {
    private final BranchService  branchService;
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<BranchResponseDto>> create(
            @Valid @RequestBody BranchRequestDto dto,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Branch created successfully",
                        branchService.create(dto, ownerEmail)));
    }
    // OWNER — get all branches of a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<List<BranchResponseDto>>> getByRestaurant(
            @PathVariable Long restaurantId,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.ok(ApiResponse.success("Branches fetched",
                branchService.getByRestaurant(restaurantId, ownerEmail)));
    }
    // OWNER or SUPER_ADMIN — get one branch
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<BranchResponseDto>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal String requesterEmail) {

        return ResponseEntity.ok(ApiResponse.success("Branch fetched",
                branchService.getById(id, requesterEmail)));
    }

    // OWNER — view managers of all branches of a restaurant
    @GetMapping("/restaurant/{restaurantId}/managers")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<List<BranchResponseDto>>> getManagers(
            @PathVariable Long restaurantId,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.ok(ApiResponse.success("Managers fetched",
                branchService.getManagersOfRestaurant(restaurantId, ownerEmail)));
    }

    // OWNER — full update
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<BranchResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequestDto dto,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.ok(ApiResponse.success("Branch updated",
                branchService.update(id, dto, ownerEmail)));
    }
    // SUPER_ADMIN — delete any
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteByAdmin(@PathVariable Long id) {

        branchService.deleteByAdmin(id);
        return ResponseEntity.ok(ApiResponse.success("Branch deleted by admin"));
    }
}
