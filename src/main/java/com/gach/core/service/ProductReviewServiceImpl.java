package com.gach.core.service;

import com.gach.core.entity.ProductReview;
import com.gach.core.repository.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository reviewRepository;

    @Override
    public ProductReview addReview(Long productId, ProductReview review) {
        review.setProductId(productId);
        review.setActive(true);
        return reviewRepository.save(review);
    }

    @Override
    public List<ProductReview> getReviews(Long productId) {
        return reviewRepository.findByProductIdAndActiveTrue(productId);
    }

    @Override
    public void softDeleteReview(Long reviewId) {
        reviewRepository.findById(reviewId).ifPresent(review -> {
            review.setActive(false);
            reviewRepository.save(review);
        });
    }
}
