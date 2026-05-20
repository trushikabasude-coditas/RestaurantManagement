package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dishRepository  extends JpaRepository<Dish,Long> {
}
