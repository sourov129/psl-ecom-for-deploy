package com.gach.core.controller;

import com.gach.core.dto.ApiResponse;
import com.gach.core.dto.UpdateUserProfileRequest;
import com.gach.core.dto.UserRegisterRequest;
import com.gach.core.service.UserService;
import com.gach.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserRegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok(ApiResponse.ok("OTP sent to email."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Registration failed", ex.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestBody Map<String, String> body) {
        try {
            boolean success = userService.verifyOtp(body.get("email"), body.get("otp"));
            if (success) {
                return ResponseEntity.ok(ApiResponse.ok("User verified successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Verification failed", "Invalid or expired OTP."));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Verification error", ex.getMessage()));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Void>> resend(@RequestBody Map<String, String> body) {
        try {
            userService.resendOtp(body.get("email"));
            return ResponseEntity.ok(ApiResponse.ok("OTP resent to email."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Resend failed", ex.getMessage()));
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            @RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            
            // Validation: Email is required
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Email is required"));
            }

            // Security: Verify JWT token email matches request email
            try {
                SecurityUtil.verifyAuthorization(email);
            } catch (IllegalAccessException ex) {
                logger.warn("Unauthorized profile update attempt for email: {}", email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Forbidden", "You can only update your own profile"));
            }

            // Validation: Ensure at least one field is being updated
            String name = (String) request.get("name");
            String phone = (String) request.get("phone");

            if ((name == null || name.trim().isEmpty()) &&
                (phone == null || phone.trim().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "At least one field (name or phone) must be provided"));
            }

            UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest();
            updateRequest.setName(name);
            updateRequest.setPhone(phone);

            userService.updateProfile(email, updateRequest);
            logger.info("User profile updated for email: {}", email);
            return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully."));
        } catch (Exception ex) {
            logger.error("Failed to update profile: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Update failed", ex.getMessage()));
        }
    }

    // Alternative endpoint that accepts email in request body
    @PostMapping("/update-profile-with-email")
    public ResponseEntity<ApiResponse<Void>> updateProfileWithEmail(
            @RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            
            // Validation: Email is required
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Email is required"));
            }

            // Security: Verify JWT token email matches request email
            try {
                SecurityUtil.verifyAuthorization(email);
            } catch (IllegalAccessException ex) {
                logger.warn("Unauthorized profile update attempt for email: {}", email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Forbidden", "You can only update your own profile"));
            }

            // Validation: Ensure at least one field is being updated
            String name = (String) request.get("name");
            String phone = (String) request.get("phone");

            if ((name == null || name.trim().isEmpty()) &&
                (phone == null || phone.trim().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "At least one field (name or phone) must be provided"));
            }

            UpdateUserProfileRequest updateRequest = new UpdateUserProfileRequest();
            updateRequest.setName(name);
            updateRequest.setPhone(phone);

            userService.updateProfile(email, updateRequest);
            logger.info("User profile updated for email: {}", email);
            return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully."));
        } catch (Exception ex) {
            logger.error("Failed to update profile: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Update failed", ex.getMessage()));
        }
    }

    private String extractEmailFromRequest(String authHeader) {
        // This is a placeholder. In a real implementation, you'd extract from JWT
        // For now, return null and use the alternative endpoint
        return null;
    }
}
