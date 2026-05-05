package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for merchant registration")
public class RegisterRequest {

    @Schema(description = "Full name of the merchant",
            example = "John Smith",
            required = true)
    private String name;

    @Schema(description = "Email address of the merchant",
            example = "john.smith@example.com",
            required = true)
    private String email;

    @Schema(description = "Phone number with country code",
            example = "+8801712345678",
            required = true)
    private String phone;

    @Schema(description = "Password for the account (min 8 characters)",
            example = "SecurePass123!",
            required = true,
            minLength = 8)
    private String password;
}
