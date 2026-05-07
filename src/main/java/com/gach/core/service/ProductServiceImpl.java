package com.gach.core.service;

import com.gach.core.dto.ProductDto;
import com.gach.core.dto.ProductDetailsDto;
import com.gach.core.dto.ProductImageDto;
import com.gach.core.dto.ProductSearchRequest;
import com.gach.core.entity.Product;
import com.gach.core.entity.ProductDetails;
import com.gach.core.entity.ProductImage;
import com.gach.core.enums.ProductStatus;
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

        dto.setImageUrls(product.getImages() != null ? product.getImages().stream()
                .map(img -> img.getFileName() != null ? img.getFileName() : "image")
                .toList() : Collections.emptyList());

        dto.setImages(product.getImages() != null ? product.getImages().stream()
                .map(img -> {
                    ProductImageDto imageDto = new ProductImageDto();
                    imageDto.setId(img.getId());
                    imageDto.setFileName(img.getFileName());
                    imageDto.setMimeType(img.getMimeType());
                    imageDto.setFileSize(img.getFileSize());
                    imageDto.setPrimaryImage(Boolean.TRUE.equals(img.getPrimaryImage()));
                    return imageDto;
                }).toList() : Collections.emptyList());

        ProductDetailsDto detailsDto = new ProductDetailsDto();
        if (product.getDetails() != null) {
            detailsDto.setSize(product.getDetails().getSize());
            detailsDto.setColor(product.getDetails().getColor());
            detailsDto.setMaterial(product.getDetails().getMaterial());
            detailsDto.setFabricType(product.getDetails().getFabricType());
            detailsDto.setCareInstructions(product.getDetails().getCareInstructions());
        }
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

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            for (var imageDto : dto.getImages()) {
                ProductImage image = new ProductImage();
                image.setProduct(product);
                image.setFileName(imageDto.getFileName());
                image.setMimeType(imageDto.getMimeType() != null ? imageDto.getMimeType() : "image/jpeg");
                image.setFileSize(imageDto.getFileSize());
                image.setImageData(imageDto.getImageData());
                image.setPrimaryImage(Boolean.TRUE.equals(imageDto.getPrimaryImage()));
                product.getImages().add(image);
            }
        } else if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            for (String fileName : dto.getImageUrls()) {
                ProductImage image = new ProductImage();
                image.setProduct(product);
                image.setFileName(fileName);
                image.setMimeType("image/jpeg");
                image.setPrimaryImage(fileName.equals(dto.getPrimaryImageUrl()));
                product.getImages().add(image);
            }
        }

        if (product.getPrimaryImageUrl() == null && product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                if (Boolean.TRUE.equals(image.getPrimaryImage())) {
                    product.setPrimaryImageUrl(image.getFileName());
                    break;
                }
            }
        }

        if (dto.getDetails() != null) {
            ProductDetails productDetails = new ProductDetails();
            productDetails.setProduct(product);
            productDetails.setSize(dto.getDetails().getSize());
            productDetails.setColor(dto.getDetails().getColor());
            productDetails.setMaterial(dto.getDetails().getMaterial());
            productDetails.setFabricType(dto.getDetails().getFabricType());
            productDetails.setCareInstructions(dto.getDetails().getCareInstructions());
            product.setDetails(productDetails);
        }

        product = productRepository.save(product);

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

            if (dto.getImages() != null) {
                product.getImages().clear();
                for (var imageDto : dto.getImages()) {
                    ProductImage image = new ProductImage();
                    image.setProduct(product);
                    image.setFileName(imageDto.getFileName());
                    image.setMimeType(imageDto.getMimeType() != null ? imageDto.getMimeType() : "image/jpeg");
                    image.setFileSize(imageDto.getFileSize());
                    image.setImageData(imageDto.getImageData());
                    image.setPrimaryImage(Boolean.TRUE.equals(imageDto.getPrimaryImage()));
                    product.getImages().add(image);
                }
            } else if (dto.getImageUrls() != null) {
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

            if (product.getPrimaryImageUrl() == null && product.getImages() != null) {
                product.getImages().stream()
                        .filter(ProductImage::getPrimaryImage)
                        .findFirst()
                        .ifPresent(primary -> product.setPrimaryImageUrl(primary.getFileName()));
            }

            ProductDetailsDto details = dto.getDetails();
            if (details != null) {
                ProductDetails productDetails = product.getDetails() != null ? product.getDetails() : new ProductDetails();
                productDetails.setProduct(product);
                productDetails.setSize(details.getSize());
                productDetails.setColor(details.getColor());
                productDetails.setMaterial(details.getMaterial());
                productDetails.setFabricType(details.getFabricType());
                productDetails.setCareInstructions(details.getCareInstructions());
                product.setDetails(productDetails);
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
