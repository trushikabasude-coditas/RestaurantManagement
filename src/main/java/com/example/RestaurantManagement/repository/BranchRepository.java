package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.RestaurantBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BranchRepository extends JpaRepository<RestaurantBranch,Long> {
    List<RestaurantBranch> findByRestaurantId(Long restaurantId);
    Optional<RestaurantBranch> findByRestaurantIdAndHeadBranchTrue(Long restaurantId);
    boolean existsByRestaurantIdAndBranchName(Long restaurantId, String branchName);

}
