package com.gach.core.entity;

import com.gach.core.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admin")
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_seq")
    @SequenceGenerator(name = "merchant_seq", sequenceName = "merchant_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String location;
    private String password;

    @Enumerated(EnumType.STRING)
    private MerchantStatus status;
}
