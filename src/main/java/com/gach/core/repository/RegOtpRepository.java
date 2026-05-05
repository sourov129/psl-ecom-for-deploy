package com.gach.core.repository;

import com.gach.core.entity.RegOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RegOtpRepository extends JpaRepository<RegOtp, Long> {
    Optional<RegOtp> findTopByEmailOrderByCreatedAtDesc(String email);
}
