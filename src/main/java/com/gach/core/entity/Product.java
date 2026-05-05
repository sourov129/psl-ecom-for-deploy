package com.gach.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import com.gach.core.enums.ProductStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private Long adminId;
    private Double price;
    private String primaryImageUrl;
    private Integer count;
    private String description;
    private String productType;
    private Integer saleCount = 0;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ProductImage> images = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;
}
