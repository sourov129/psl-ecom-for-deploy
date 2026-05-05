package com.gach.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reg_otp")
public class RegOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_otp_seq")
    @SequenceGenerator(name = "reg_otp_seq", sequenceName = "reg_otp_seq", allocationSize = 1)
    private Long id;

    private String email;
    private String otp;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
