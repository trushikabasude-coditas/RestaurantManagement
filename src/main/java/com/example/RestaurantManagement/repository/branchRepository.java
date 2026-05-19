package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.RestaurantBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface branchRepository extends JpaRepository<RestaurantBranch,Long> {
    List<RestaurantBranch> findByRestaurantId(Long restaurantId);
    Optional<RestaurantBranch> findByRestaurantIdAndHeadBranchTrue(Long restaurantId);
    boolean existsByRestaurantIdAndBranchName(Long restaurantId, String branchName);

}
