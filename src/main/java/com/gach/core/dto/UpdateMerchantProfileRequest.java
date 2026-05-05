package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to update merchant profile")
public class UpdateMerchantProfileRequest {
    
    @Schema(description = "Updated merchant name", example = "Green Garden Nursery")
    private String name;
    
    @Schema(description = "Updated location", example = "Dhaka, Bangladesh")
    private String location;
    
    @Schema(description = "Updated phone number", example = "+8801712345678")
    private String phone;
}
