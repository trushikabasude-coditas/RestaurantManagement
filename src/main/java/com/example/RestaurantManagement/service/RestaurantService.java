package com.example.RestaurantManagement.service;

import com.example.RestaurantManagement.dto.RestaurantPatchDto;
import com.example.RestaurantManagement.dto.RestaurantRequestDto;
import com.example.RestaurantManagement.dto.RestaurantResponseDto;
import com.example.RestaurantManagement.entity.Restaurants;
import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.exception.BadRequestException;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.repository.RestaurantRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public RestaurantResponseDto create(@Valid RestaurantRequestDto dto, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Owner Not FOund"));
        if (restaurantRepository.existsByNameAndOwnerId(dto.getName(), owner.getId())) {
            throw new BadRequestException("You already have a restaurant with this name");

        }
        Restaurants restaurant = Restaurants.builder()
                .name(dto.getName())
                .gstNumber(dto.getGstNumber())
                .owner(owner)
                .build();

        restaurantRepository.save(restaurant);
        return toDto(restaurant);
    }

    public List<RestaurantResponseDto> getMyRestaurants(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        return restaurantRepository.findByOwnerId(owner.getId())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantResponseDto update(Long id, RestaurantRequestDto dto, String ownerEmail) {
        Restaurants restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        validateAccess(restaurant, ownerEmail);

        restaurant.setName(dto.getName());
        restaurant.setGstNumber(dto.getGstNumber());

        restaurantRepository.save(restaurant);
        return toDto(restaurant);
    }

    // OWNER deletes their restaurant
    @Transactional
    public void delete(Long id, String ownerEmail) {
        Restaurants restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        validateAccess(restaurant, ownerEmail);
        restaurantRepository.delete(restaurant);
    }

    // SUPER_ADMIN gets all restaurants
    public List<RestaurantResponseDto> getAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private void validateAccess(Restaurants restaurant, String requesterEmail) {
        if (!restaurant.getOwner().getEmail().equals(requesterEmail)) {
            throw new BadRequestException("You do not have access to this restaurant");
        }
    }

    private RestaurantResponseDto toDto(Restaurants r) {
        return RestaurantResponseDto.builder()
                .id(r.getId())
                .name(r.getName())
                .gstNumber(r.getGstNumber())
                .ownerName(r.getOwner().getName())
                .ownerEmail(r.getOwner().getEmail())
                .build();
    }

    public void deleteByAdmin(Long id) {
        Restaurants restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }

    @Transactional
    public RestaurantResponseDto patch(Long id, RestaurantPatchDto dto, String ownerEmail) {
        Restaurants restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        validateAccess(restaurant, ownerEmail);

        if (dto.getName() != null) restaurant.setName(dto.getName());
        if (dto.getDescription() != null) restaurant.setDescription(dto.getDescription());
        if (dto.getGstNumber() != null) restaurant.setGstNumber(dto.getGstNumber());
        if (dto.getPanNumber() != null) restaurant.setPanNumber(dto.getPanNumber());
        if (dto.getLogoUrl() != null) restaurant.setLogoUrl(dto.getLogoUrl());

        return toDto(restaurantRepository.save(restaurant));
    }


    public RestaurantResponseDto getById(Long id, String requesterEmail) {
        Restaurants restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        User requester = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (requester.getRole() != Role.SUPER_ADMIN) {
            validateAccess(restaurant, requesterEmail);
        }

        return toDto(restaurant);
    }
}
