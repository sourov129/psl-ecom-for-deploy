package com.gach.core.service;

import com.gach.core.dto.UpdateUserProfileRequest;
import com.gach.core.dto.UserRegisterRequest;
import com.gach.core.entity.User;
import com.gach.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserOtpService userOtpService;

    @Override
    public void registerUser(UserRegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new RuntimeException("User exists with this email");
        } else {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            userOtpService.sendOtp(user.getEmail());
        }
    }

    public boolean verifyOtp(String email, String otp) {
        boolean response = userOtpService.verifyOtp(email, otp);
        log.info("otp verify response: {}", response);
        return response;
    }

    public void resendOtp(String email) {
        userOtpService.resendOtp(email);
    }

    @Override
    public void updateProfile(String email, UpdateUserProfileRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        
        userRepository.save(user);
        log.info("User profile updated for email: {}", email);
    }
}
