package com.gach.core.controller;

import com.gach.core.entity.ProductReview;
import com.gach.core.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/reviews")
public class ProductReviewController {
    private final ProductReviewService reviewService;

    @PostMapping
    public ResponseEntity<ProductReview> addReview(@PathVariable Long productId, @RequestBody ProductReview review) {
        return ResponseEntity.ok(reviewService.addReview(productId, review));
    }

    @GetMapping
    public ResponseEntity<List<ProductReview>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> softDeleteReview(@PathVariable Long productId, @PathVariable Long reviewId) {
        reviewService.softDeleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
