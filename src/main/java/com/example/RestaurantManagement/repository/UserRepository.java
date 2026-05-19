package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByBranchIdAndRole(Long branchId, Role role);
}
