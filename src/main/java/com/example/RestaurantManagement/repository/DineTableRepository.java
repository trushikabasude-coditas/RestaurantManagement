package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.DineTable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DineTableRepository extends JpaRepository<DineTable,Long> {
    List<DineTable> findByBranchId(Long branchId);
    List<DineTable> findByTableNumber(Integer tableNumber);
    List<DineTable> exitsByBranchIdAndTableNumber(Integer branchId, Integer tableNumber);

    boolean existsByBranchIdAndTableNumber(@NotNull(message = "Branch Id is required") Long branchId, @NotNull(message = "Table number is required field") @Min(value=1,message = "Positive Value is Required") Integer tableNumber);
}
