package com.gach.core.service;

public interface UserOtpService {
    void sendOtp(String email);
    boolean verifyOtp(String email, String otp);
    void resendOtp(String email);
}
