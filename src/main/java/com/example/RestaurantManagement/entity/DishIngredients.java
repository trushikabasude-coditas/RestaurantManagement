package com.example.RestaurantManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DishIngredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredients ingredient;


    @NotNull
    @Min(value = 0,message = "Required quantity cannot be negative")
    @Column(name = "required_quantity", nullable = false)
    private Double requiredQuantity;

}
