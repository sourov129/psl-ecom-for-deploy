package com.gach.core.repository;

import com.gach.core.entity.Admin;
import com.gach.core.enums.MerchantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByIdAndStatus(Long id, MerchantStatus status);

    // Custom query for complex search
//    @Query("SELECT m FROM Admin m WHERE m.status = :status ")
//    Page<Admin> findMerchantsWithFilters(
//            @Param("name") String name,
//            @Param("location") String location,
//            @Param("status") MerchantStatus status,
//            Pageable pageable);
}
