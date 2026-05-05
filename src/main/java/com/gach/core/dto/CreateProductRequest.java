package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Request to create a new product")
public class CreateProductRequest {
    
    @Schema(description = "Product name", example = "Cotton T-Shirt", required = true)
    private String name;
    
    @Schema(description = "Product price in BDT", example = "1500.00", required = true, minimum = "0")
    private Double price;
    
    @Schema(description = "Product description", example = "Comfortable cotton t-shirt", required = true)
    private String description;
    
    @Schema(description = "Available quantity", example = "25", required = true, minimum = "0")
    private Integer quantity;
    
    @Schema(description = "Product type/category", example = "Shirt", allowableValues = {"Shirt", "Pants", "Dress", "Jacket", "Shoes", "Accessories"})
    private String productType;

    @Schema(description = "Merchant ID who owns the product", example = "1")
    private Long merchantId;

    @Schema(description = "Primary image URL for the product",
            example = "/uploads/products/primary.jpg")
    private String primaryImageUrl;

    @Schema(description = "Additional image URLs for the product",
            example = "[/uploads/products/1.jpg, /uploads/products/2.jpg]")
    private List<String> imageUrls;

    @Schema(description = "Detailed clothing information")
    private com.gach.core.dto.ProductDetailsDto details;
}
