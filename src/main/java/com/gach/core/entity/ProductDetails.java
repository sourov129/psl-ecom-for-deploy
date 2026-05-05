package com.gach.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_details_id_seq")
    @SequenceGenerator(name = "product_details_id_seq", sequenceName = "product_details_id_seq", allocationSize = 1)
    private Long id;

    private Long productId;
    private String size;
    private String color;
    private String material;
    private String fabricType;
    private String careInstructions;
}
