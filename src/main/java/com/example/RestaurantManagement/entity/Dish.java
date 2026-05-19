package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.DishCategory;
import com.example.RestaurantManagement.enums.FoodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // Dish belongs to a branch's menu
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private RestaurantBranch branch;

    @NotBlank(message = "Dish name is required")
    @Size(max = 200)
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "special_note", columnDefinition = "TEXT")
    private String specialNote;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "food_type", nullable = false, length = 20)
    private FoodType foodType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private DishCategory category;

    @NotNull
    @Min(value = 0, message = "Price cannot be negative")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "is_available", nullable = false)
    private boolean available = true;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishIngredients> dishIngredients = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}