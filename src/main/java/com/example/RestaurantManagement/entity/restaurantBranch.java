package com.example.RestaurantManagement.entity;

import jakarta.persistence.Column;

public class restaurantBranch {
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable=false)
    private String branch_name;
    private Long owner_id;
    private Long manager_id;
    private String address;
    private String type;

}
