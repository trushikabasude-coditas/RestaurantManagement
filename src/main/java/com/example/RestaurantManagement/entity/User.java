package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.Role;
import jakarta.persistence.*;

public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private  Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    private String phone;
    private String address;
    private String photo_id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
