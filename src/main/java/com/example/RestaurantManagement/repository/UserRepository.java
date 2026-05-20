package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByBranchIdAndRole(Long branchId, Role role);
    List<User> findByBranchId(Long branchId);
}
