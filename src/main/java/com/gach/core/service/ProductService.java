package com.gach.core.service;

import com.gach.core.dto.ProductDto;
import com.gach.core.dto.ProductSearchRequest;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto getProduct(Long id);
    org.springframework.data.domain.Page<ProductDto> listProducts(org.springframework.data.domain.Pageable pageable, String name, String type);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void softDeleteProduct(Long id);
    org.springframework.data.domain.Page<ProductDto> searchProducts(ProductSearchRequest searchRequest);
}
