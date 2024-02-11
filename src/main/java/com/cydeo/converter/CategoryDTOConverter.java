package com.cydeo.converter;

import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CategoryDTOConverter implements Converter<String, CategoryDto>{

    CategoryService categoryService;
    public CategoryDTOConverter(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }
        return categoryService.getCategoryById(Long.parseLong(source));
    }
}
