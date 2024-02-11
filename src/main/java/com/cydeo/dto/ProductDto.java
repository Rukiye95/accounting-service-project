package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;

    private Integer quantityInStock;
    private Integer lowLimitAlert;
    private ProductUnit productUnit;

    private CategoryDto category;
    private boolean hasProduct;

    public ProductDto( String name, Integer quantityInStock, Integer lowLimitAlert, ProductUnit productUnit, CategoryDto category, boolean hasProduct) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.lowLimitAlert = lowLimitAlert;
        this.productUnit = productUnit;
        this.category = category;
        this.hasProduct = hasProduct;
    }
}
