package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private  Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "password_hash")
    private String password;

    @Size(max = 20, message = "Phone must be at most 20 characters")
    @Column(name = "phone", length = 20)
    private String phone;
    private String address;
    private String photo_id;

    @NotNull(message = "role is required field")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "is_active", nullable = false)
    private boolean active = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private RestaurantBranch branch;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
