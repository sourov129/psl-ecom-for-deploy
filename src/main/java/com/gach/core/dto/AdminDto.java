package com.gach.core.dto;

import com.gach.core.enums.MerchantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Schema(description = "Admin information")
public class AdminDto {

    @Schema(description = "Admin ID", example = "1")
    private Long id;

    @Schema(description = "Admin name", example = "Gach Admin", required = true)
    private String name;

    @Schema(description = "Email address", example = "admin@example.com", required = true)
    private String email;

    @Schema(description = "Phone number", example = "+8801712345678")
    private String phone;

    @Schema(description = "Business location", example = "Dhaka, Bangladesh")
    private String location;

    @Schema(description = "Admin status", example = "ACTIVE")
    private MerchantStatus status;

    @Schema(description = "Whether admin is verified")
    private boolean verified;

    @Schema(description = "Whether admin is active")
    private boolean active;

    @Schema(description = "Creation timestamp")
    private Date createdAt;

    @Schema(description = "Last update timestamp")
    private Date updatedAt;
}
