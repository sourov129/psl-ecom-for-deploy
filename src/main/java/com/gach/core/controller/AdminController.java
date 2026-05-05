package com.gach.core.controller;

import com.gach.core.dto.*;
import com.gach.core.service.AdminService;
import com.gach.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    // ========================================================================
    // EXISTING REGISTRATION ENDPOINTS
    // ========================================================================

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        try {
            logger.info("Registering admin: {}", request.getEmail());
            adminService.registerAdmin(request);
            return ResponseEntity.ok(ApiResponse.ok("OTP sent to email."));
        } catch (IllegalArgumentException ex) {
            logger.error("Registration failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Registration failed", ex.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestBody VerifyOtpRequest request) {
        try {
            logger.info("Verifying OTP for admin: {}", request.getEmail());
            boolean success = adminService.verifyOtp(request.getEmail(), request.getOtp());
            if (success) {
                return ResponseEntity.ok(ApiResponse.ok("Admin verified successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Verification failed", "Invalid or expired OTP."));
            }
        } catch (Exception ex) {
            logger.error("OTP verification failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Verification error", ex.getMessage()));
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Void>> resend(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            logger.info("Resending OTP to: {}", email);
            adminService.resendOtp(email);
            return ResponseEntity.ok(ApiResponse.ok("OTP resent to email."));
        } catch (Exception ex) {
            logger.error("OTP resend failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Resend failed", ex.getMessage()));
        }
    }

    // ========================================================================
    // ADMIN SEARCH AND RETRIEVAL ENDPOINTS
    // ========================================================================

    @GetMapping
    public ResponseEntity<Page<AdminDto>> getAllAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location
    ) {
        try {
            logger.info("Getting all admins: page={}, size={}, sortBy={}, name={}, location={}",
                    page, size, sortBy, name, location);

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<AdminDto> admins = adminService.getAllAdmins(pageable, name, location);

            logger.info("Found {} admins", admins.getTotalElements());
            return ResponseEntity.ok(admins);
        } catch (Exception ex) {
            logger.error("Failed to get admins: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        try {
            logger.info("Getting admin by ID: {}", id);
            AdminDto admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException ex) {
            logger.error("Admin not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("Failed to get admin: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AdminDto>> searchAdmins(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        try {
            logger.info("Searching admins with query: {}", query);

            if (query == null || query.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<AdminDto> admins = adminService.searchAdmins(query.trim(), pageable);

            logger.info("Found {} admins for query: {}", admins.getTotalElements(), query);
            return ResponseEntity.ok(admins);
        } catch (Exception ex) {
            logger.error("Search failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========================================================================
    // ADMIN PRODUCTS ENDPOINTS
    // ========================================================================

    @GetMapping("/{adminId}/products")
    public ResponseEntity<Page<ProductDto>> getAdminProducts(
            @PathVariable Long adminId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        try {
            logger.info("Getting products for admin {}: page={}, size={}, name={}, type={}",
                    adminId, page, size, name, type);

            Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
            Page<ProductDto> products = adminService.getAdminProducts(adminId, pageable, name, type);

            logger.info("Found {} products for admin {}", products.getTotalElements(), adminId);
            return ResponseEntity.ok(products);
        } catch (RuntimeException ex) {
            logger.error("Admin not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("Failed to get admin products: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========================================================================
    // ADMIN PROFILE AND PRODUCT MANAGEMENT
    // ========================================================================

    @PostMapping("/update-profile-with-email")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Email is required"));
            }

            try {
                SecurityUtil.verifyAuthorization(email);
            } catch (IllegalAccessException ex) {
                logger.warn("Unauthorized profile update attempt for email: {}", email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Forbidden", "You can only update your own profile"));
            }

            String name = (String) request.get("name");
            String location = (String) request.get("location");
            String phone = (String) request.get("phone");

            if ((name == null || name.trim().isEmpty()) &&
                (location == null || location.trim().isEmpty()) &&
                (phone == null || phone.trim().isEmpty())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "At least one field (name, location, or phone) must be provided"));
            }

            UpdateAdminProfileRequest updateRequest = new UpdateAdminProfileRequest();
            updateRequest.setName(name);
            updateRequest.setLocation(location);
            updateRequest.setPhone(phone);

            adminService.updateProfile(email, updateRequest);
            logger.info("Admin profile updated for email: {}", email);
            return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully."));
        } catch (Exception ex) {
            logger.error("Failed to update profile: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Update failed", ex.getMessage()));
        }
    }

    @PostMapping(value = "/products", consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam int quantity,
            @RequestParam(required = false, defaultValue = "Plant") String productType,
            @RequestParam(required = false) org.springframework.web.multipart.MultipartFile image) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Email is required"));
            }

            try {
                SecurityUtil.verifyAuthorization(email);
            } catch (IllegalAccessException ex) {
                logger.warn("Unauthorized product creation attempt for email: {}", email);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error("Forbidden", "You can only create products for your own account"));
            }

            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Product name is required"));
            }

            if (price <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Product price must be greater than 0"));
            }

            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Product description is required"));
            }

            if (quantity < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Bad request", "Product quantity cannot be negative"));
            }

            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                try {
                    imagePath = com.gach.core.util.FileUploadUtil.saveUploadedFile(image);
                    logger.info("Image uploaded for product: {}", imagePath);
                } catch (Exception ex) {
                    logger.warn("Image upload failed: {}", ex.getMessage());
                    imagePath = null;
                }
            }

            CreateProductRequest createRequest = new CreateProductRequest();
            createRequest.setName(name.trim());
            createRequest.setPrice(price);
            createRequest.setDescription(description.trim());
            createRequest.setQuantity(quantity);
            createRequest.setProductType(productType != null ? productType.trim() : "Plant");

            ProductDto product = adminService.createProductForAdminWithImage(email, createRequest, imagePath);

            if (imagePath != null) {
                logger.info("Product created for admin {}: {} (ID: {}) with image: {}", 
                        email, product.getName(), product.getId(), imagePath);
            } else {
                logger.info("Product created for admin {}: {} (ID: {})", 
                        email, product.getName(), product.getId());
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.ok("Product created successfully.", product));
        } catch (Exception ex) {
            logger.error("Failed to create product: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create product", ex.getMessage()));
        }
    }

    @GetMapping("/my-products")
    public ResponseEntity<Page<ProductDto>> getMyProducts(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            logger.info("Getting products for admin: {}", email);

            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Page<ProductDto> products = adminService.getAdminProductsByEmail(email, pageable);

            logger.info("Found {} products for admin: {}", products.getTotalElements(), email);
            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            logger.error("Failed to get admin products: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
