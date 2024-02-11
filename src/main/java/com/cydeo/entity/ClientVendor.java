package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "clients_vendors")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Where(clause = "is_deleted = false")
public class ClientVendor extends BaseEntity {

    private String clientVendorName;

    private String phone;

    private String website;

    @Enumerated(EnumType.STRING)
    ClientVendorType clientVendorType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    Company company;
}
