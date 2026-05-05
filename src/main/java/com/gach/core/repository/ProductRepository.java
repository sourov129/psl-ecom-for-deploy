package com.gach.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gach.core.entity.Product;
import com.gach.core.enums.ProductStatus;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
    Page<Product> findByNameContainingAndStatus(String name, ProductStatus status, Pageable pageable);
    Page<Product> findByProductTypeContainingAndStatus(String type, ProductStatus status, Pageable pageable);
    Page<Product> findByNameContainingAndProductTypeContainingAndStatus(String name, String type, ProductStatus status, Pageable pageable);

    // Advanced search queries
    @Query("SELECT p FROM Product p WHERE p.status = :status " +
           "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
           "AND (:productType IS NULL OR LOWER(p.productType) LIKE LOWER(CONCAT('%', :productType, '%'))) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:minStock IS NULL OR p.count >= :minStock)")
    Page<Product> searchProducts(
            @Param("status") ProductStatus status,
            @Param("name") String name,
            @Param("description") String description,
            @Param("productType") String productType,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minStock") Integer minStock,
            Pageable pageable
    );

    // Find by name and description
    @Query("SELECT p FROM Product p WHERE p.status = :status " +
           "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> findByKeyword(
            @Param("keyword") String keyword,
            @Param("status") ProductStatus status,
            Pageable pageable
    );

    // Find available products
    @Query("SELECT p FROM Product p WHERE p.status = :status AND p.count > 0")
    Page<Product> findAvailableProducts(@Param("status") ProductStatus status, Pageable pageable);

    // Find products by merchant
    Page<Product> findByAdminIdAndStatus(Long adminId, ProductStatus status, Pageable pageable);
}