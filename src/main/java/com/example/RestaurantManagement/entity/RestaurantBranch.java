package com.example.RestaurantManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "restaurant_branches",
        uniqueConstraints = {
                // branch name must be unique per restaurant
                @UniqueConstraint(columnNames = {"restaurant_id", "branch_name"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Branch name is required")
    @Size(max = 200)
    @Column(name = "branch_name", nullable = false, length = 200)
    private String branchName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "phone", length = 20)
    private String phone;

    // Which restaurant this branch belongs to
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurants restaurant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", unique = true)
    private User manager;

    @Column(name = "is_head_branch", nullable = false)
    private boolean headBranch = false;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}