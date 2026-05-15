package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepository extends JpaRepository<order,Long> {
}
