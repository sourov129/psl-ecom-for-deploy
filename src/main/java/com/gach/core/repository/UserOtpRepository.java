package com.gach.core.repository;

import com.gach.core.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByEmail(String email);
    
    // Get the latest OTP for an email (ordered by ID descending, as newer records have higher IDs)
    Optional<UserOtp> findTopByEmailOrderByIdDesc(String email);
}
