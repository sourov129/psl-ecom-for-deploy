package com.gach.core.service;

import com.gach.core.dto.*;
import com.gach.core.entity.Admin;
import com.gach.core.entity.Product;
import com.gach.core.entity.RegOtp;
import com.gach.core.enums.MerchantStatus;
import com.gach.core.enums.ProductStatus;
import com.gach.core.repository.AdminRepository;
import com.gach.core.repository.ProductRepository;
import com.gach.core.entity.ProductImage;
import com.gach.core.repository.RegOtpRepository;
import com.gach.core.util.OtpGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository merchantRepo;
    private final ProductRepository productRepository;
    private final RegOtpRepository otpRepo;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    // ========================================================================
    // EXISTING REGISTRATION METHODS
    // ========================================================================

    @Override
    @Transactional
    public void registerAdmin(RegisterRequest request) {
        if (merchantRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered.");
        }

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setStatus(MerchantStatus.PENDING);
        merchantRepo.save(admin);

        generateAndSendOtp(request.getEmail());
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        RegOtp AdminOtp = otpRepo.findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new RuntimeException("No OTP found"));

        if (!AdminOtp.getOtp().equals(otp)) return false;
        if (AdminOtp.getExpiresAt().isBefore(LocalDateTime.now())) return false;

        Admin admin = merchantRepo.findByEmail(email).orElseThrow();
        admin.setStatus(MerchantStatus.ACTIVE);
        merchantRepo.save(admin);

        return true;
    }

    @Override
    public void resendOtp(String email) {
        generateAndSendOtp(email);
    }

    // ========================================================================
    // NEW: Admin SEARCH AND RETRIEVAL METHODS
    // ========================================================================

    @Override
    public Page<AdminDto> getAllAdmins(Pageable pageable, String name, String location) {
//        logger.info("Getting all Admins with filters - name: {}, location: {}", name, location);
//
//        Page<Admin> merchants = merchantRepo.findMerchantsWithFilters(
//                name, location, MerchantStatus.ACTIVE, pageable);
//
//        return merchants.map(this::toDto);
        return null;
    }

    @Override
    public AdminDto getAdminById(Long AdminId) {
        logger.info("Getting Admin by ID: {}", AdminId);

        return merchantRepo.findByIdAndStatus(AdminId, MerchantStatus.ACTIVE)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + AdminId));
    }

    @Override
    public Page<AdminDto> searchAdmins(String query, Pageable pageable) {
        logger.info("Searching Admins with query: {}", query);
//
//        // Search in both name and location
//        Page<Admin> merchants = merchantRepo.findMerchantsWithFilters(
//                query, query, MerchantStatus.ACTIVE, pageable);
//
//        return merchants.map(this::toDto);

        return  null;
    }

    @Override
    public Page<ProductDto> getAdminProducts(Long AdminId, Pageable pageable, String name, String type) {
//        logger.info("Getting products for Admin {} with filters - name: {}, type: {}", AdminId, name, type);
//
//        // Verify admin exists and is active
//        merchantRepo.findByIdAndStatus(AdminId, MerchantStatus.ACTIVE)
//                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + AdminId));
//
//        Page<Product> products = productRepository.findMerchantProductsWithFilters(
//                AdminId, name, type, ProductStatus.ACTIVE, pageable);
//        return products.map(this::productToDto);
        return null;
    }

    @Override
    public Page<ProductDto> getAdminProductsByEmail(String email, Pageable pageable) {
//        Admin admin = merchantRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        return productRepository.findByMerchantIdAndStatus(admin.getId(), ProductStatus.ACTIVE, pageable)
//                .map(this::productToDto);

        return null;
    }

    // ========================================================================
    // NEW: PROFILE AND PRODUCT MANAGEMENT
    // ========================================================================

    @Override
    public void updateProfile(String email, UpdateAdminProfileRequest request) {
        Admin admin = merchantRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            admin.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            admin.setPhone(request.getPhone());
        }

        merchantRepo.save(admin);
        logger.info("Admin profile updated for email: {}", email);
    }

    @Override
    @Transactional
    public ProductDto createProductForAdmin(String email, CreateProductRequest request) {
        Admin admin = merchantRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getStatus() != MerchantStatus.ACTIVE) {
            throw new RuntimeException("Admin is not active");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCount(request.getQuantity());
        product.setProductType(request.getProductType() != null ? request.getProductType() : "General");
        product.setAdminId(admin.getId());
        product.setStatus(ProductStatus.ACTIVE);
        product.setSaleCount(0);

        Product savedProduct = productRepository.save(product);
        logger.info("Product created for Admin {}: {}", email, savedProduct.getId());

        return productToDto(savedProduct);
    }
    
    /**
     * Create a product with an uploaded image.
     * Called from controller after image is uploaded.
     */
    @Transactional
    public ProductDto createProductForAdminWithImage(String email, CreateProductRequest request, String imagePath) {
        ProductDto productDto = createProductForAdmin(email, request);
        
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            Product product = productRepository.findById(productDto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setPrimaryImageUrl(imagePath);
            productRepository.save(product);
            logger.info("Primary image saved for product {}: {}", productDto.getId(), imagePath);
            productDto.setPrimaryImageUrl(imagePath);
        }
        
        return productDto;
    }
    

    // ========================================================================
    // HELPER METHODS
    // ========================================================================

    private AdminDto toDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setPhone(admin.getPhone());
        dto.setStatus(admin.getStatus());
        dto.setVerified(admin.getStatus() == MerchantStatus.ACTIVE);
        dto.setActive(admin.getStatus() == MerchantStatus.ACTIVE);
        dto.setCreatedAt(admin.getCreatedDate());
        dto.setUpdatedAt(admin.getUpdatedDate());
        return dto;
    }

    private ProductDto productToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setMerchantId(product.getAdminId());
        dto.setPrimaryImageUrl(product.getPrimaryImageUrl());
        dto.setImageUrls(product.getImages() != null ? product.getImages().stream().map(img -> img.getFileName() != null ? img.getFileName() : "image").toList() : List.of());
        dto.setCount(product.getCount());
        dto.setAvailable(product.getCount() != null && product.getCount() > 0);
        dto.setDescription(product.getDescription());
        dto.setProductType(product.getProductType());
        dto.setSaleCount(product.getSaleCount());

        return dto;
    }

    private void generateAndSendOtp(String email) {
        String otp = OtpGenerator.generateOtp();

        RegOtp AdminOtp = new RegOtp();
        AdminOtp.setEmail(email);
        AdminOtp.setOtp(otp);
        AdminOtp.setCreatedAt(LocalDateTime.now());
        AdminOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(AdminOtp);

        sendOtpEmail(email, otp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your .com OTP");
        message.setText("Your OTP is: " + otp + " (valid for 5 minutes)");
        mailSender.send(message);
    }
}