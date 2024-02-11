package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "addresses")
@Where(clause = "is_deleted=false")
public class Address extends BaseEntity {


    private String addressLine1;

    private String addressLine2;


    private String city;


    private String state;


    private String country;


    private String zipCode;

}
