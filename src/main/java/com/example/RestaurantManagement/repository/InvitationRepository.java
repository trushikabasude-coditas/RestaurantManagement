package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Invitations;
import com.example.RestaurantManagement.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InvitationRepository extends JpaRepository<Invitations, Long> {
    Optional<Invitations> findByToken(String token);
    Optional<Invitations> findByInvitedEmailAndStatus(String email, InvitationStatus status);
    boolean existsByInvitedEmailAndStatus(String email, InvitationStatus status);

}
