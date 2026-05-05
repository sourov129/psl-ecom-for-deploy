package com.gach.core.repository.specification;

import com.gach.core.entity.Product;
import com.gach.core.enums.ProductStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class MerchantProductSpec implements Specification<Product> {
    private String name;
    private Long merchantId;
    private String type;
    private ProductStatus status;
    private int page;
    private int pageSize;

    private Sort sort;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.hasText(name)) {
            predicateList.add(cb.equal(root.get("name"), name));
        }
        if (merchantId != null) {
            predicateList.add(cb.equal(root.get("merchantId"), merchantId));
        }
        if (StringUtils.hasText(type)) {
            predicateList.add(cb.equal(root.get("productType"), type));
        }
        if (status != null) {
            predicateList.add(cb.equal(root.get("status"), status));
        }

        return cb.and(predicateList.toArray(new Predicate[0]));
    }
}
