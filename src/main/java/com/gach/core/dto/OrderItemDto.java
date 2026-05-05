package com.gach.core.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Individual item in an order")
public class OrderItemDto {

    @Schema(description = "ID of the product to order",
            example = "1",
            required = true)
    private Long productId;

    @Schema(description = "Quantity of the product",
            example = "2",
            required = true,
            minimum = "1")
    private Integer quantity;
}