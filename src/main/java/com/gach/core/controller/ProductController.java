package com.gach.core.controller;

import com.gach.core.dto.CreateProductRequest;
import com.gach.core.dto.ProductDto;
import com.gach.core.dto.ProductSearchRequest;
import com.gach.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "Product management API with advanced search and filtering")
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product", description = "Admin only - Create a product with images and details")
    @ApiResponse(responseCode = "200", description = "Product created successfully")
    public ResponseEntity<ProductDto> addProduct(@RequestBody CreateProductRequest request) {
        logger.info("Adding product: {}", request.getName());
        ProductDto productDto = new ProductDto();
        productDto.setName(request.getName());
        productDto.setPrice(request.getPrice());
        productDto.setDescription(request.getDescription());
        productDto.setCount(request.getQuantity());
        productDto.setProductType(request.getProductType());
        productDto.setMerchantId(request.getMerchantId());
        productDto.setPrimaryImageUrl(request.getPrimaryImageUrl());
        productDto.setImageUrls(request.getImageUrls());
        productDto.setDetails(request.getDetails());
        ProductDto created = productService.addProduct(productDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product with all images and details")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        logger.info("Fetching product with id: {}", id);
        ProductDto product = productService.getProduct(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "List products", description = "Get paginated list of products with basic filtering")
    public ResponseEntity<Page<ProductDto>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        logger.info("Listing products: page={}, size={}, sortBy={}, name={}, type={}", page, size, sortBy, name, type);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductDto> products = productService.listProducts(pageable, name, type);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search")
    @Operation(summary = "Advanced product search", description = "Search products by name, description, type, price range, and stock level")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully",
        content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<ProductDto>> searchProducts(@RequestBody ProductSearchRequest searchRequest) {
        logger.info("Advanced search: name={}, description={}, type={}, minPrice={}, maxPrice={}", 
            searchRequest.getName(), 
            searchRequest.getDescription(),
            searchRequest.getProductType(),
            searchRequest.getMinPrice(),
            searchRequest.getMaxPrice());
        
        Page<ProductDto> results = productService.searchProducts(searchRequest);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "Update product information, images, and details")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        logger.info("Updating product id: {}", id);
        ProductDto updated = productService.updateProduct(id, productDto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product", description = "Soft delete - marks product as deleted but keeps data in database")
    public ResponseEntity<Void> softDeleteProduct(@PathVariable Long id) {
        logger.info("Soft deleting product id: {}", id);
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}