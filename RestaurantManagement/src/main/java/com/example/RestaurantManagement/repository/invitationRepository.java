package com.example.RestaurantManagement.repository;

import com.example.RestaurantManagement.entity.Invitations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface invitationRepository extends JpaRepository<Invitations, Long> {
}
