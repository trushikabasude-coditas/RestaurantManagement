package com.example.RestaurantManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Restaurants {
    @NotBlank(message = "Restaurant name is required")
    @Size(max = 200, message = "Name must be at most 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotBlank(message = "Restaurant name is required")
    @Size(max = 200, message = "Name must be at most 200 characters")
    @Column(name = "name", nullable = false, length = 200)
  private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @Column(name = "gst_number", length = 20)
    private String gstNumber;

    @Column(name = "pan_number", length = 20)
    private String panNumber;

    // All branches under this restaurant (including main)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantBranch> branches = new ArrayList<>();


}
