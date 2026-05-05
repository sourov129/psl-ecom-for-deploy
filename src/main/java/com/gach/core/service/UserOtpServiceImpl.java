package com.gach.core.service;

import com.gach.core.entity.UserOtp;
import com.gach.core.repository.UserOtpRepository;
import com.gach.core.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOtpServiceImpl implements UserOtpService {
    private final UserOtpRepository userOtpRepository;
    private final MailService mailService;
    // No need for OtpGenerator instance, use static method

    @Override
    public void sendOtp(String email) {
        String otp = OtpGenerator.generateOtp();
        UserOtp userOtp = new UserOtp();
        userOtp.setEmail(email);
        userOtp.setOtp(otp);
        userOtp.setExpiry(System.currentTimeMillis() + 5 * 60 * 1000); // 5 min
        userOtp.setVerified(false);
        log.info("otp sent: {}", otp);
        userOtpRepository.save(userOtp);
        mailService.sendOtpEmail(email, userOtp.getOtp());
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return userOtpRepository.findTopByEmailOrderByIdDesc(email)
                .filter(u -> !u.isVerified() && u.getOtp().equals(otp) && u.getExpiry() > System.currentTimeMillis())
                .map(u -> {
                    u.setVerified(true);
                    userOtpRepository.save(u);
                    return true;
                }).orElse(false);
    }

    @Override
    public void resendOtp(String email) {
        sendOtp(email);
    }
}
