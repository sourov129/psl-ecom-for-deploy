package com.gach.core.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Product information")
public class ProductDto {

    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Name of the product",
            example = "Cotton T-Shirt",
            required = true)
    private String name;

    @Schema(description = "ID of the merchant selling this product",
            example = "1",
            required = true)
    private Long merchantId;

    @Schema(description = "Price of the product in BDT",
            example = "1500.00",
            required = true,
            minimum = "0")
    private Double price;

    @Schema(description = "Primary image URL for the product",
            example = "/uploads/products/primary.jpg")
    private String primaryImageUrl;

    @Schema(description = "Additional image URLs for the product",
            example = "[/uploads/products/1.jpg, /uploads/products/2.jpg]")
    private List<String> imageUrls;

    @Schema(description = "Available quantity in stock",
            example = "25",
            minimum = "0")
    private Integer count;

    @Schema(description = "Whether the product is currently available for order")
    private Boolean available;

    @Schema(description = "Detailed description of the product",
            example = "Comfortable cotton t-shirt perfect for casual wear.")
    private String description;

    @Schema(description = "Type/category of the product",
            example = "Shirt",
            allowableValues = {"Shirt", "Pants", "Dress", "Jacket", "Shoes", "Accessories"})
    private String productType;

    @Schema(description = "Number of units sold",
            example = "150",
            minimum = "0")
    private Integer saleCount;

    @Schema(description = "Detailed information for the clothing product")
    private ProductDetailsDto details;
}