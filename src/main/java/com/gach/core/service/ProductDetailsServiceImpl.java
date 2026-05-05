package com.gach.core.service;

import com.gach.core.entity.ProductDetails;
import com.gach.core.repository.ProductDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {
    private final ProductDetailsRepository detailsRepository;

    @Override
    public ProductDetails getDetails(Long productId) {
        return detailsRepository.findByProductId(productId).orElse(null);
    }

    @Override
    public ProductDetails updateDetails(Long productId, ProductDetails details) {
        ProductDetails existing = detailsRepository.findByProductId(productId).orElse(null);
        if (existing != null) {
            existing.setSize(details.getSize());
            existing.setColor(details.getColor());
            existing.setMaterial(details.getMaterial());
            existing.setFabricType(details.getFabricType());
            existing.setCareInstructions(details.getCareInstructions());
            return detailsRepository.save(existing);
        } else {
            details.setProductId(productId);
            return detailsRepository.save(details);
        }
    }
}
