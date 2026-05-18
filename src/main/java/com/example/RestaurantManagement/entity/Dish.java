package com.example.RestaurantManagement.entity;

import jakarta.persistence.Column;

public class Dish {
    private Long id;
    private Long brand_id;
    private String name;
    private  String description;
    private String special_note;
    private boolean isVeg;
    private Integer price;
    @Column(name = "photo_url")
    private String photoUrl;

    private String

}
