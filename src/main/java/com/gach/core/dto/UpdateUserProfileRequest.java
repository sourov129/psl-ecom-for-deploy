package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to update user profile")
public class UpdateUserProfileRequest {
    
    @Schema(description = "Updated user name", example = "John Doe")
    private String name;
    
    @Schema(description = "Updated phone number", example = "+8801712345678")
    private String phone;
}
