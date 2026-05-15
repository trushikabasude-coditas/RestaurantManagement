package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Integer> {
}
