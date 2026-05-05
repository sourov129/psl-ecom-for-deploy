package com.gach.core.dto;

import lombok.Data;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request payload for user registration")
public class UserRegisterRequest {

    @Schema(description = "Full name of the user",
            example = "Sarah Johnson",
            required = true)
    private String name;

    @Schema(description = "Email address of the user",
            example = "sarah.johnson@gmail.com",
            required = true)
    private String email;

    @Schema(description = "Phone number with country code",
            example = "+8801587654321",
            required = true)
    private String phone;

    @Schema(description = "Password for the account (min 8 characters)",
            example = "MyPassword456!",
            required = true,
            minLength = 8)
    private String password;
}