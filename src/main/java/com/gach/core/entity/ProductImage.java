package com.gach.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "product_image")
@JsonIgnoreProperties({"product", "imageData"})
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_seq")
    @SequenceGenerator(name = "product_image_id_seq", sequenceName = "product_image_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    @Lob
    @Column(name = "image_data", columnDefinition = "BYTEA")
    private byte[] imageData;

    private String fileName;
    private String mimeType = "image/jpeg";
    private Long fileSize; // Size in bytes
    private Boolean primaryImage = false;
}
