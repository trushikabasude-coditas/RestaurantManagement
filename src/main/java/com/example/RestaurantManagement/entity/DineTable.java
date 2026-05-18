package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.TableStatus;
import jakarta.persistence.*;

@Entity
@Table (name = "dining_tables")
public class DineTable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private Long dishId;
    private  Long branch_id;
    @Column(nullable = false)
    private Integer dinningTable_no;
    private  Integer capacity;
    private  Long assigned_staff_id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TableStatus tableStatus = TableStatus.AVAILABLE;


}
