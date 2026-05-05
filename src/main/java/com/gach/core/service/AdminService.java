package com.gach.core.service;

import com.gach.core.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    // Registration methods
    void registerAdmin(RegisterRequest request);
    boolean verifyOtp(String email, String otp);
    void resendOtp(String email);

    // Profile management
    void updateProfile(String email, UpdateAdminProfileRequest request);

    // Admin search and retrieval methods
    Page<AdminDto> getAllAdmins(Pageable pageable, String name, String location);
    AdminDto getAdminById(Long adminId);
    Page<AdminDto> searchAdmins(String query, Pageable pageable);

    // Admin products
    Page<ProductDto> getAdminProducts(Long adminId, Pageable pageable, String name, String type);
    ProductDto createProductForAdmin(String email, CreateProductRequest request);
    ProductDto createProductForAdminWithImage(String email, CreateProductRequest request, String imagePath);
    Page<ProductDto> getAdminProductsByEmail(String email, Pageable pageable);
}