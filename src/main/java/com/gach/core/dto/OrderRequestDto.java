package com.gach.core.dto;

import lombok.Data;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Request payload for creating an order")
public class OrderRequestDto {

    @Schema(description = "Email address of the customer",
            example = "customer@example.com",
            required = true)
    private String userMail;

    @Schema(description = "Email address of the admin",
            example = "admin@example.com",
            required = true)
    private String adminMail;

    @Schema(description = "List of items to order",
            required = true)
    private List<OrderItemDto> items;
}