package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.TableStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "dining_tables")
public class DineTable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private Long dishId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private RestaurantBranch branch;

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 150)
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Size(max = 30, message = "Unit max 30 chars")
    @Column(name = "unit", length = 30)
    private String unit;
    // Current stock quantity
    @Column(name = "stock_quantity")
    private Double stockQuantity;


}
