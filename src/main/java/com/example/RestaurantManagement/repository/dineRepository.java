package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Dine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface dineRepository extends JpaRepository<Dine,Long> {
}
