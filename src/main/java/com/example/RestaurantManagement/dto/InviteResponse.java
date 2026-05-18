package com.example.RestaurantManagement.dto;

import com.example.RestaurantManagement.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class InviteResponse {

private  String message;
   private String invitedEmail;
}
