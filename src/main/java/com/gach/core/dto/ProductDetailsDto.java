package com.gach.core.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Detailed information for clothing products")
public class ProductDetailsDto {

    @Schema(description = "Size of the clothing item",
            example = "M")
    private String size;

    @Schema(description = "Color of the clothing item",
            example = "Blue")
    private String color;

    @Schema(description = "Material composition",
            example = "Cotton")
    private String material;

    @Schema(description = "Type of fabric",
            example = "Denim")
    private String fabricType;

    @Schema(description = "Care instructions",
            example = "Machine wash cold, tumble dry low")
    private String careInstructions;
}