package com.example.RestaurantManagement.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "dine")
public class Dine {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private Long dishId;
    private  Long branch_id;
    @Column(nullable = false)
    private Integer dinning_no;

    private  Integer capacity;
    private  Long assigned_staff_id;

}
