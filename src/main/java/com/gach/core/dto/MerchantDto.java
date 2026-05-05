package com.gach.core.dto;

import com.gach.core.enums.MerchantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Schema(description = "Admin information")
public class MerchantDto {
    
    @Schema(description = "Admin ID", example = "1")
    private Long id;
    
    @Schema(description = "Admin name", example = "Green Garden Nursery", required = true)
    private String name;
    
    @Schema(description = "Email address", example = "contact@greengarden.com", required = true)
    private String email;
    
    @Schema(description = "Phone number", example = "+8801712345678")
    private String phone;
    
    @Schema(description = "Business location", example = "Dhaka, Bangladesh", required = true)
    private String location;
    
    @Schema(description = "Admin status", example = "ACTIVE")
    private MerchantStatus status;
    
    @Schema(description = "Average rating", example = "4.5")
    private double rating;
    
    @Schema(description = "Whether merchant is verified")
    private boolean isVerified;
    
    @Schema(description = "Whether merchant is active")
    private boolean isActive;
    
    @Schema(description = "Creation timestamp")
    private Date createdAt;
    
    @Schema(description = "Last update timestamp")
    private Date updatedAt;
}
