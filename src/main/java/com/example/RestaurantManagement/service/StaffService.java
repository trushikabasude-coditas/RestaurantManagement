package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.dto.StaffResponseDto;
import com.example.RestaurantManagement.entity.RestaurantBranch;
import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.exception.BadRequestException;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.repository.BranchRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;

    // get all staffranch
    public List<StaffResponseDto> getStaffByBranch(Long branchId, String requesterEmail) {
        RestaurantBranch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        validateAccess(requester, branch);
        return userRepository.findByBranchId(branchId)
                .stream()
                .filter(u -> isStaffRole(u.getRole()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // get staff by role in a branc
    public List<StaffResponseDto> getStaffByBranchAndRole(Long branchId, String role,
                                                          String requesterEmail) {
        RestaurantBranch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateAccess(requester, branch);

        Role staffRole;
        try {
            staffRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + role);
        }
        if (!isStaffRole(staffRole)) {
            throw new BadRequestException("Role must be WAITER, CHEF or CLEANER");
        }
        return userRepository.findByBranchIdAndRole(branchId, staffRole)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StaffResponseDto getStaffById(Long userId, String requesterEmail) {
        User staff = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        if (!isStaffRole(staff.getRole())) {
            throw new BadRequestException("User is not a staff member");
        }
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (staff.getBranch() != null) {
            validateAccess(requester, staff.getBranch());
        }
        return toDto(staff);
    }

    @Transactional
    public void deactivateStaff(Long userId, String requesterEmail) {
        User staff = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        if (!isStaffRole(staff.getRole())) {
            throw new BadRequestException("User is not a staff member");
        }
        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (staff.getBranch() != null) {
            validateAccess(requester, staff.getBranch());
        }
        staff.setActive(false);
        userRepository.save(staff);
    }
    @Transactional
    public void reactivateStaff(Long userId, String requesterEmail) {
        User staff = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
        if (!isStaffRole(staff.getRole())) {
            throw new BadRequestException("User is not a staff member");
        }

        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (staff.getBranch() != null) {
            validateAccess(requester, staff.getBranch());
        }

        staff.setActive(true);
        userRepository.save(staff);
    }

    // Own profile — any logged in user
    public StaffResponseDto getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toDto(user);
    }
    private void validateAccess(User requester, RestaurantBranch branch) {
        if (requester.getRole() == Role.OWNER) {
            // owner can access any branch of their restaurant
            if (!branch.getRestaurant().getOwner().getEmail().equals(requester.getEmail())) {
                throw new BadRequestException("You do not own this restaurant");
            }
        } else if (requester.getRole() == Role.MANAGER) {
            // manager can only access their own branch
            if (requester.getBranch() == null ||
                    !requester.getBranch().getId().equals(branch.getId())) {
                throw new BadRequestException("You do not manage this branch");
            }
            // extra check — if branch has no manager, only owner should access
            if (branch.getManager() == null) {
                throw new BadRequestException("This branch has no manager assigned");
            }
        } else {
            throw new BadRequestException("Access denied");
        }
    }
    private boolean isStaffRole(Role role) {
        return role == Role.WAITER || role == Role.CHEF || role == Role.CLEANER;
    }

    private StaffResponseDto toDto(User u) {
        return StaffResponseDto.builder()
             .id(u.getId())
            .name(u.getName())
             .email(u.getEmail()).phone(u.getPhone())
                .role(u.getRole().name()).active(u.isActive())
                .branchId(u.getBranch() != null ? u.getBranch().getId() : null)
                .branchName(u.getBranch() != null ? u.getBranch().getBranchName() : null)
                .restaurantId(u.getRestaurant() != null ? u.getRestaurant().getId() : null)
                .restaurantName(u.getRestaurant() != null ? u.getRestaurant().getName() : null)
                .build();
    }
}