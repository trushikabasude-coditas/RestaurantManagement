package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.DineTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DineTableRepository extends JpaRepository<DineTable,Long> {
    List<DineTable> findByBranchId(Long branchId);
    List<DineTable> findByTableNumber(Integer tableNumber);
    List<DineTable> findByBranchIdAndTableNumber(Integer branchId, Integer tableNumber);
}
