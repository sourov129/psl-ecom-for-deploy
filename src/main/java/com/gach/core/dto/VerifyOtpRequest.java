package com.gach.core.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for OTP verification")
public class VerifyOtpRequest {

    @Schema(description = "Email address where OTP was sent",
            example = "user@example.com",
            required = true)
    private String email;

    @Schema(description = "6-digit OTP code received via email",
            example = "123456",
            required = true,
            pattern = "\\d{6}")
    private String otp;
}