package com.cydeo.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String description;
    private CompanyDto companyDto;
    private boolean hasProduct;
}
