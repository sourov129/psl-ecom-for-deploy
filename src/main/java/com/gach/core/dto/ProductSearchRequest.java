package com.gach.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Product search request with multiple filter options")
public class ProductSearchRequest {

    @Schema(description = "Product name (partial match allowed)", example = "shirt")
    private String name;

    @Schema(description = "Product description (partial match allowed)", example = "cotton")
    private String description;

    @Schema(description = "Product type/category", example = "Shirt")
    private String productType;

    @Schema(description = "Minimum price in BDT", example = "500")
    private Double minPrice;

    @Schema(description = "Maximum price in BDT", example = "5000")
    private Double maxPrice;

    @Schema(description = "Minimum stock quantity", example = "5")
    private Integer minStock;

    @Schema(description = "Page number for pagination", example = "0")
    private Integer page = 0;

    @Schema(description = "Page size for pagination", example = "10")
    private Integer pageSize = 10;

    @Schema(description = "Sort field", example = "price")
    private String sortBy = "id";

    @Schema(description = "Sort direction (ASC or DESC)", example = "ASC")
    private String sortDirection = "ASC";
}
