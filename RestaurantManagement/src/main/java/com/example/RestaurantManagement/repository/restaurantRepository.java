package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface restaurantRepository extends JpaRepository<Restaurants, Long> {
}
