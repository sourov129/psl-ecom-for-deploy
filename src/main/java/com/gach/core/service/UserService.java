package com.gach.core.service;

import com.gach.core.dto.UpdateUserProfileRequest;
import com.gach.core.dto.UserRegisterRequest;

public interface UserService {
    void registerUser(UserRegisterRequest request);
    boolean verifyOtp(String email, String otp);
    void resendOtp(String email);
    void updateProfile(String email, UpdateUserProfileRequest request);
}
