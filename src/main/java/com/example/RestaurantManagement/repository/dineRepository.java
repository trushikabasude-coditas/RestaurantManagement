package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.DineTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface dineRepository extends JpaRepository<DineTable,Long> {
}
