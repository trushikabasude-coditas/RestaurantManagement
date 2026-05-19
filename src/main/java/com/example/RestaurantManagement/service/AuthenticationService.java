package com.example.RestaurantManagement.service;
import com.example.RestaurantManagement.dto.*;
import com.example.RestaurantManagement.entity.Invitations;
import com.example.RestaurantManagement.entity.RestaurantBranch;
import com.example.RestaurantManagement.entity.Restaurants;
import com.example.RestaurantManagement.entity.User;
import com.example.RestaurantManagement.enums.InvitationStatus;
import com.example.RestaurantManagement.enums.Role;
import com.example.RestaurantManagement.exception.ResourceNotFoundException;
import com.example.RestaurantManagement.filter.JwtUtil;
import com.example.RestaurantManagement.repository.InvitationRepository;
import com.example.RestaurantManagement.repository.RestaurantRepository;
import com.example.RestaurantManagement.repository.UserRepository;
import com.example.RestaurantManagement.repository.branchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.RestaurantManagement.enums.Role.OWNER;
import static com.example.RestaurantManagement.enums.Role.SUPER_ADMIN;
@Service
@RequiredArgsConstructor
public class authService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;
    private  final InvitationRepository invitationRepository;
    private  final JwtUtil jwtUtil;
    private final EmailService emailService;
    @Transactional
    public InviteResponseDto sendInvitation(InviteRequestDto dto, String senderEmail) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        validateSenderCanInvite(sender, dto.getRole());
        if(invitationRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("User with this email already exists");

        }

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("User with this email already exists");
        }
        Restaurants restaurant =null;
        RestaurantBranch branch = null;
        if (dto.getRestaurantId() != null) {
            restaurant = restaurantRepository.findById(dto.getRestaurantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        }
        if(dto.getBranchId() != null) {
            branch= branchRepository.findById(dto.getBranchId())
                    .orElseThrow(()-> new ResourceNotFoundException("Branch not found")) ;

        }
        if (isBranchLevelRole(dto.getRole()) && branch == null) {
            throw new BadRequestException("Branch is required for role: " + dto.getRole());
        }
        Invitations invitation=new Invitations.builder()
                .invitedEmail(dto.getEmail())
                .role(dto.getRole())
                .invitedBy(sender)
                .restaurant(restaurant)
                .branch(branch)
                .status(InvitationStatus.PENDING)
                .build();
        invitationRepository.save(invitation);
        emailService.sendInvitation(dto.getEmail(),dto.getRole().name(),invitation.getToken());

        return new InviteResponseDto(
                "Invitation sent successfully to " + dto.getEmail(),
                dto.getEmail()
        );
        @Transactional
        public AuthResponseDto acceptInvitation(AcceptInviteRequestDto dto) {
            Invitations invitation = invitationRepository.findByToken(dto.getToken())
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid invitation token"));

            if (invitation.getStatus() != InvitationStatus.PENDING) {
                throw new BadRequestException("Invitation is no longer valid");
            }
            if (invitation.getExpiresAt().isBefore(LocalDateTime.now())) {
                invitation.setStatus(InvitationStatus.EXPIRED);
                invitationRepository.save(invitation);
                throw new BadRequestException("Invitation has expired");
            }

            if (userRepository.existsByEmail(invitation.getInvitedEmail())) {
                throw new BadRequestException("User already registered with this email");
            }

            User user=User.builder()
                    .name(dto.getName())
                    .email((invitation.getInvitedEmail()))
                    .passwordHash(passwordEncoder.encode(dto.getPassword()))
                    .phone(dto.getPhone())
                    .role(invitation.getRestaurant())
                    .branch(invitation.getBranch())
                    .active(true)
                    .build();
            userRepository.save(user);
            //if manage rassign to branch
            // if (invitation.getRole() == Role.MANAGER && invitation.getBranch() != null) {
            RestaurantBranch branch = invitation.getBranch();
            branch.setManager(user);
            branchRepository.save(branch);
        }
        if(invitation.getRole()== Role.MANAGER && invitation.getBranch() != null) {
            RestaurantBranch branch = invitation.getBranch();
            branch.setManager(user);
            branchRepository.save(branch);
        }
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepository.save(invitation);

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDto(accessToken, refreshToken,
                user.getRole().name(), user.getEmail(), user.getName());
    }

    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!user.isActive()) {
            throw new BadRequestException("Account is not activated yet");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDto(accessToken, refreshToken,
                user.getRole().name(), user.getEmail(), user.getName());
    }
    public AuthResponseDto refreshToken(RefreshTokenRequestDto dto) {
        String token = dto.getRefreshToken();

        if (!jwtUtil.isTokenValid(token) || !jwtUtil.isRefreshToken(token)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDto(newAccessToken, newRefreshToken,
                user.getRole().name(), user.getEmail(), user.getName());
    }

    private void validateSenderCanInvite(User sender, Role targetRole) {}

    switch(sender.getRole()){
        case SUPER_ADMIN -> {
            if (targetRole != Role.OWNER) {
                throw new BadRequestException("Super admin can only invite owners");
            }
        }
        case OWNER -> {
            if(targetRole  == Role.OWNER || targetRole == Role.SUPER_ADMIN){
                throw new BadRequestException("Owner cannot invite another owner or super admin");
            }
        }
        default -> throw new BadRequestException("You do not have permission to send invitations");
    }
}

private boolean isBranchLevelRole(Role role) {
    return role==Role.MANAGER || role==Role.WAITER || role==Role.CHEF || role=Role.CLEANER;

}
      }
              }

