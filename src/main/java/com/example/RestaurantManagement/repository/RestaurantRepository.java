package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurants, Long> {
    List<Restaurants> findByOwnerId(Long ownerId);
    boolean existsByNameAndOwnerId(String name, Long ownerId);
}
