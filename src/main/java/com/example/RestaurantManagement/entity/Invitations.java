package com.example.RestaurantManagement.entity;

import com.example.RestaurantManagement.enums.InvitationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="invitations")
public class Invitations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false,updatable = false)
    private Long id;
    @Column(name = "token", nullable = false, unique = true, updatable = false, length = 20)
   private String token;
    @NotBlank
    @Email
    @Column(name = "invited_email , nullable = false, length = 255")
    private String invitedEmail;

    // Role being invited to
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private Role role;
    // Who sent the invitation (SUPER_ADMIN or OWNER)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invited_by", nullable = false)
    private User invitedBy;
    // Null for OWNER invitations (restaurant not created yet)
    // Set for MANAGER/WAITER/CHEF/CLEANER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurants restaurant;

    // Branch-level roles need this; null for OWNER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private RestaurantBranch branch;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private InvitationStatus status = InvitationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Invitation expires in 48 hours
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @PrePersist
    public void prePersist() {
        if (this.token == null) {
            this.token = UUID.randomUUID().toString();
        }
        if (this.expiresAt == null) {
            this.expiresAt = LocalDateTime.now().plusHours(24);
        }
}}
