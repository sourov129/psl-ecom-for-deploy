
// ============================================================================
// 8. API CONSTANTS UPDATE
// File: com/gach/core/constants/ApiConstants.java (CREATE THIS FILE)
// ============================================================================

package com.gach.core.util;

public class ApiConstants {

    // Base URLs
    public static final String API_BASE = "/api";

    // Admin endpoints
    public static final String ADMIN_BASE = API_BASE + "/admin";
    public static final String ADMIN_REGISTER = ADMIN_BASE + "/register";
    public static final String ADMIN_VERIFY_OTP = ADMIN_BASE + "/verify-otp";
    public static final String ADMIN_RESEND_OTP = ADMIN_BASE + "/resend-otp";
    public static final String ADMIN_SEARCH = ADMIN_BASE + "/search";

    // Product endpoints
    public static final String PRODUCTS_BASE = API_BASE + "/products";

    // Dynamic endpoints
    public static String getAdminById(Long id) {
        return ADMIN_BASE + "/" + id;
    }

    public static String getAdminProducts(Long adminId) {
        return ADMIN_BASE + "/" + adminId + "/products";
    }

    public static String getProductById(Long id) {
        return PRODUCTS_BASE + "/" + id;
    }
}