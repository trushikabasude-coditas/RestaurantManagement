package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByBranchIdAndRole(Long branchId, Role role);
}
