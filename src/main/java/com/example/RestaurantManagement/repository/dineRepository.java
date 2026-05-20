package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.DineTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dineRepository extends JpaRepository<DineTable,Long> {
}
