package com.example.RestaurantManagement.controller;

import com.example.RestaurantManagement.dto.ApiResponse;
import com.example.RestaurantManagement.dto.StaffResponseDto;
import com.example.RestaurantManagement.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    // Any logged in user — get own profile
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<StaffResponseDto>> getMyProfile(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(ApiResponse.success("Profile fetched",
                staffService.getMyProfile(email)));
    }

    // OWNER or MANAGER — get all staff of a branch
    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<StaffResponseDto>>> getStaffByBranch(
            @PathVariable Long branchId,
            @AuthenticationPrincipal String requesterEmail) {
        return ResponseEntity.ok(ApiResponse.success("Staff fetched",
                staffService.getStaffByBranch(branchId, requesterEmail)));
    }

    // OWNER or MANAGER — get staff by role in a branch
    // e.g. GET /api/staff/branch/1/role/WAITER
    @GetMapping("/branch/{branchId}/role/{role}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<StaffResponseDto>>> getStaffByRole(
            @PathVariable Long branchId,
            @PathVariable String role,
            @AuthenticationPrincipal String requesterEmail) {
        return ResponseEntity.ok(ApiResponse.success("Staff fetched",
                staffService.getStaffByBranchAndRole(branchId, role, requesterEmail)));
    }

    // OWNER or MANAGER — get single staff
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<StaffResponseDto>> getById(
            @PathVariable Long userId,
            @AuthenticationPrincipal String requesterEmail) {
        return ResponseEntity.ok(ApiResponse.success("Staff fetched",
                staffService.getStaffById(userId, requesterEmail)));
    }

    // OWNER or MANAGER — deactivate staff
    // if branch has a manager → only MANAGER can deactivate/reactivate
// if branch has no manager → only OWNER can
    @PatchMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<Void>> deactivate(
            @PathVariable Long userId,
            @AuthenticationPrincipal String requesterEmail) {
        staffService.deactivateStaff(userId, requesterEmail);
        return ResponseEntity.ok(ApiResponse.success("Staff deactivated"));
    }

    // OWNER or MANAGER — reactivate staff
    @PatchMapping("/{userId}/reactivate")
    @PreAuthorize("hasRole('OWNER') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<Void>> reactivate(
            @PathVariable Long userId,
            @AuthenticationPrincipal String requesterEmail) {
        staffService.reactivateStaff(userId, requesterEmail);
        return ResponseEntity.ok(ApiResponse.success("Staff reactivated"));
    }
}

