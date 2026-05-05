package com.gach.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_otp")
public class UserOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_otp_seq")
    @SequenceGenerator(name = "user_otp_seq", sequenceName = "user_otp_seq", allocationSize = 1)
    private Long id;

    private String email;
    private String otp;
    private boolean verified = false;
    private long expiry;
}
