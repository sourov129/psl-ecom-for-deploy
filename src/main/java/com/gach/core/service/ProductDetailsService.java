package com.gach.core.service;

import com.gach.core.entity.ProductDetails;

public interface ProductDetailsService {
    ProductDetails getDetails(Long productId);
    ProductDetails updateDetails(Long productId, ProductDetails details);
}
