package com.gach.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_review_id_seq")
    @SequenceGenerator(name = "product_review_id_seq", sequenceName = "product_review_id_seq", allocationSize = 1)
    private Long id;

    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean active = true;
}
