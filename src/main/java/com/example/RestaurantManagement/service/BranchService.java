package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.dto.BranchRequestDto;
import com.example.RestaurantManagement.dto.BranchResponseDto;
import com.example.RestaurantManagement.entity.RestaurantBranch;
import com.example.RestaurantManagement.entity.Restaurants;
import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.exception.BadRequestException;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.repository.BranchRepository;
import com.example.RestaurantManagement.repository.RestaurantRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public BranchResponseDto create(BranchRequestDto dto, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Restaurants restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (!restaurant.getOwner().getEmail().equals(ownerEmail)) {
            throw new BadRequestException("You do not own this restaurant");
        }

        if (dto.isHeadBranch() && branchRepository
                .findByRestaurantIdAndHeadBranchTrue(dto.getRestaurantId()).isPresent()) {
            throw new BadRequestException("Head branch already exists for this restaurant");
        }

        if (branchRepository.existsByRestaurantIdAndBranchName(
                dto.getRestaurantId(), dto.getBranchName())) {
            throw new BadRequestException("Branch with this name already exists");
        }
        RestaurantBranch branch = RestaurantBranch.builder()
                .branchName(dto.getBranchName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .phone(dto.getPhone())
                .restaurant(restaurant)
                .headBranch(dto.isHeadBranch())
                .active(true)
                .build();
        // head branch — owner is manager by default
        if (dto.isHeadBranch()) {
            branch.setManager(owner);
        }

        return toDto(branchRepository.save(branch));
    }
    public List<BranchResponseDto> getByRestaurant(Long restaurantId, String ownerEmail) {
        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (!restaurant.getOwner().getEmail().equals(ownerEmail)) {
            throw new BadRequestException("You do not own this restaurant");
        }

        return branchRepository.findByRestaurantId(restaurantId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }
    public BranchResponseDto getById(Long id, String requesterEmail) {
        RestaurantBranch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (requester.getRole() != Role.SUPER_ADMIN) {
            if (!branch.getRestaurant().getOwner().getEmail().equals(requesterEmail)) {
                throw new BadRequestException("You do not have access to this branch");
            }
        }

        return toDto(branch);
    }
    @Transactional
    public BranchResponseDto update(Long id, BranchRequestDto dto, String ownerEmail) {
        RestaurantBranch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        validateOwnerAccess(branch, ownerEmail);

        branch.setBranchName(dto.getBranchName());
        branch.setAddress(dto.getAddress());
        branch.setCity(dto.getCity());
        branch.setPhone(dto.getPhone());

        return toDto(branchRepository.save(branch));
    }

    @Transactional
    public void deleteByAdmin(Long id) {
        RestaurantBranch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        branchRepository.delete(branch);
    }

    public List<BranchResponseDto> getManagersOfRestaurant(Long restaurantId, String ownerEmail) {
        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (!restaurant.getOwner().getEmail().equals(ownerEmail)) {
            throw new BadRequestException("You do not own this restaurant");
        }

        return branchRepository.findByRestaurantId(restaurantId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    private void validateOwnerAccess(RestaurantBranch branch, String ownerEmail) {
        if (!branch.getRestaurant().getOwner().getEmail().equals(ownerEmail)) {
            throw new BadRequestException("You do not have access to this branch");
        }
    }

    private BranchResponseDto toDto(RestaurantBranch b) {

        String managerName = Objects.requireNonNullElse(
                b.getManager() != null ? b.getManager().getName() : null,
                b.getRestaurant().getOwner().getName() + " (Owner)");

        String managerEmail = Objects.requireNonNullElse(
                b.getManager() != null ? b.getManager().getEmail() : null,
                b.getRestaurant().getOwner().getEmail());

        return BranchResponseDto.builder()
                .id(b.getId())
                .branchName(b.getBranchName())
                .address(b.getAddress())
                .city(b.getCity())
                .phone(b.getPhone())
                .headBranch(b.isHeadBranch())
                .active(b.isActive())
                .restaurantId(b.getRestaurant().getId())
                .restaurantName(b.getRestaurant().getName())
                .managerName(managerName)
                .managerEmail(managerEmail)
                .build();
    }
}