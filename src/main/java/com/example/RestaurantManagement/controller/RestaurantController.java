package com.example.RestaurantManagement.controller;

import com.example.RestaurantManagement.dto.*;
import com.example.RestaurantManagement.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// rest stays same as before
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.RestaurantManagement.dto.*;
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // OWNER — create
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> create(
            @Valid @RequestBody RestaurantRequestDto dto,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Restaurant created successfully",
                        restaurantService.create(dto, ownerEmail)));
    }

    // OWNER — get their own restaurants
    @GetMapping("/my")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<List<RestaurantResponseDto>>> getMyRestaurants(
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.ok(
                ApiResponse.success("Restaurants fetched",
                        restaurantService.getMyRestaurants(ownerEmail)));
    }

    // OWNER or SUPER_ADMIN — get one by id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal String requesterEmail) {

        return ResponseEntity.ok(
                ApiResponse.success("Restaurant fetched",
                        restaurantService.getById(id, requesterEmail)));
    }

    // SUPER_ADMIN — view all
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<RestaurantResponseDto>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.success("All restaurants fetched",
                        restaurantService.getAll()));
    }

    // OWNER — full update (PUT)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<RestaurantResponseDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequestDto dto,
            @AuthenticationPrincipal String ownerEmail) {

        return ResponseEntity.ok(
                ApiResponse.success("Restaurant updated",
                        restaurantService.update(id, dto, ownerEmail)));
    }

    // OWNER — partial update (PATCH)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal String ownerEmail) {

        restaurantService.delete(id, ownerEmail);
        return ResponseEntity.ok(ApiResponse.success("Restaurant deleted"));
    }

    // SUPER_ADMIN
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteByAdmin(@PathVariable Long id) {

        restaurantService.deleteByAdmin(id);
        return ResponseEntity.ok(ApiResponse.success("Restaurant deleted by admin"));
    }
}


