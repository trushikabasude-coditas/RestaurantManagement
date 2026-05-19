package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
