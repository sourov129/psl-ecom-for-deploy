package com.gach.core.service;

import com.gach.core.dto.ProductDto;
import com.gach.core.dto.ProductDetailsDto;
import com.gach.core.dto.ProductSearchRequest;
import com.gach.core.entity.Product;
import com.gach.core.entity.ProductDetails;
import com.gach.core.entity.ProductImage;
import com.gach.core.enums.ProductStatus;
import com.gach.core.repository.ProductDetailsRepository;
import com.gach.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setMerchantId(product.getAdminId());
        dto.setPrice(product.getPrice());
        dto.setPrimaryImageUrl(product.getPrimaryImageUrl());
        dto.setImageUrls(product.getImages() != null ? product.getImages().stream()
            .map(img -> img.getFileName() != null ? img.getFileName() : "image")
            .toList() : Collections.emptyList());
        dto.setCount(product.getCount());
        dto.setAvailable(product.getCount() != null && product.getCount() > 0);
        dto.setDescription(product.getDescription());
        dto.setProductType(product.getProductType());
        dto.setSaleCount(product.getSaleCount());

        ProductDetailsDto detailsDto = new ProductDetailsDto();
        productDetailsRepository.findByProductId(product.getId()).ifPresent(existing -> {
            detailsDto.setSize(existing.getSize());
            detailsDto.setColor(existing.getColor());
            detailsDto.setMaterial(existing.getMaterial());
            detailsDto.setFabricType(existing.getFabricType());
            detailsDto.setCareInstructions(existing.getCareInstructions());
        });
        dto.setDetails(detailsDto);
        return dto;
    }

    @Override
    public ProductDto addProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setAdminId(dto.getMerchantId());
        product.setPrice(dto.getPrice());
        product.setPrimaryImageUrl(dto.getPrimaryImageUrl());
        product.setCount(dto.getCount());
        product.setDescription(dto.getDescription());
        product.setProductType(dto.getProductType());
        product.setSaleCount(dto.getSaleCount());
        product.setStatus(ProductStatus.ACTIVE);

        if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            List<ProductImage> images = new ArrayList<>();
            for (String fileName : dto.getImageUrls()) {
                ProductImage image = new ProductImage();
                image.setProduct(product);
                image.setFileName(fileName);
                image.setMimeType("image/jpeg");
                image.setPrimaryImage(fileName.equals(dto.getPrimaryImageUrl()));
                images.add(image);
            }
            product.getImages().addAll(images);
        }

        product = productRepository.save(product);

        ProductDetailsDto details = dto.getDetails();
        if (details != null) {
            ProductDetails productDetails = new ProductDetails();
            productDetails.setProductId(product.getId());
            productDetails.setSize(details.getSize());
            productDetails.setColor(details.getColor());
            productDetails.setMaterial(details.getMaterial());
            productDetails.setFabricType(details.getFabricType());
            productDetails.setCareInstructions(details.getCareInstructions());
            productDetailsRepository.save(productDetails);
        }

        return toDto(product);
    }

    @Override
    public ProductDto getProduct(Long id) {
        return productRepository.findById(id)
            .filter(product -> product.getStatus() == ProductStatus.ACTIVE)
            .map(this::toDto)
            .orElse(null);
    }

    @Override
    public Page<ProductDto> listProducts(Pageable pageable, String name, String type) {
        logger.info("Listing products with filters - name: {}, type: {}", name, type);
        Page<Product> products;
        if (name != null && type != null) {
            products = productRepository.findByNameContainingAndProductTypeContainingAndStatus(name, type, ProductStatus.ACTIVE, pageable);
        } else if (name != null) {
            products = productRepository.findByNameContainingAndStatus(name, ProductStatus.ACTIVE, pageable);
        } else if (type != null) {
            products = productRepository.findByProductTypeContainingAndStatus(type, ProductStatus.ACTIVE, pageable);
        } else {
            products = productRepository.findByStatus(ProductStatus.ACTIVE, pageable);
        }
        return products.map(this::toDto);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        return productRepository.findById(id).map(product -> {
            product.setName(dto.getName());
            product.setAdminId(dto.getMerchantId());
            product.setPrice(dto.getPrice());
            product.setPrimaryImageUrl(dto.getPrimaryImageUrl());
            product.setCount(dto.getCount());
            product.setDescription(dto.getDescription());
            product.setProductType(dto.getProductType());
            product.setSaleCount(dto.getSaleCount());

            if (dto.getImageUrls() != null) {
                product.getImages().clear();
                for (String fileName : dto.getImageUrls()) {
                    ProductImage image = new ProductImage();
                    image.setProduct(product);
                    image.setFileName(fileName);
                    image.setMimeType("image/jpeg");
                    image.setPrimaryImage(fileName.equals(dto.getPrimaryImageUrl()));
                    product.getImages().add(image);
                }
            }

            ProductDetailsDto details = dto.getDetails();
            if (details != null) {
                ProductDetails productDetails = productDetailsRepository.findByProductId(id).orElse(new ProductDetails());
                productDetails.setProductId(product.getId());
                productDetails.setSize(details.getSize());
                productDetails.setColor(details.getColor());
                productDetails.setMaterial(details.getMaterial());
                productDetails.setFabricType(details.getFabricType());
                productDetails.setCareInstructions(details.getCareInstructions());
                productDetailsRepository.save(productDetails);
            }

            productRepository.save(product);
            return toDto(product);
        }).orElse(null);
    }

    @Override
    public void softDeleteProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setStatus(ProductStatus.DELETED);
            productRepository.save(product);
        });
    }

    @Override
    public Page<ProductDto> searchProducts(ProductSearchRequest searchRequest) {
        logger.info("Searching products with criteria - name: {}, description: {}, type: {}", 
            searchRequest.getName(), searchRequest.getDescription(), searchRequest.getProductType());

        Sort sort = Sort.by(Sort.Direction.fromString(searchRequest.getSortDirection()), 
            searchRequest.getSortBy());
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getPageSize(), sort);

        return productRepository.searchProducts(
            ProductStatus.ACTIVE,
            searchRequest.getName(),
            searchRequest.getDescription(),
            searchRequest.getProductType(),
            searchRequest.getMinPrice(),
            searchRequest.getMaxPrice(),
            searchRequest.getMinStock(),
            pageable
        ).map(this::toDto);
    }
}
