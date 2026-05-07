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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private String size;
    private String color;
    private String material;
    private String fabricType;
    private String careInstructions;
}
