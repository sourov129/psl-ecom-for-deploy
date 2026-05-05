package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request to update admin profile")
public class UpdateAdminProfileRequest {

    @Schema(description = "Updated admin name", example = "Gach Admin")
    private String name;

    @Schema(description = "Updated admin location", example = "Dhaka, Bangladesh")
    private String location;

    @Schema(description = "Updated admin phone number", example = "+8801712345678")
    private String phone;
}
