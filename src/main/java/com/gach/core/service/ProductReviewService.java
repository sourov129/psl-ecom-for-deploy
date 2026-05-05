package com.gach.core.service;

import com.gach.core.entity.ProductReview;
import java.util.List;

public interface ProductReviewService {
    ProductReview addReview(Long productId, ProductReview review);
    List<ProductReview> getReviews(Long productId);
    void softDeleteReview(Long reviewId);
}
