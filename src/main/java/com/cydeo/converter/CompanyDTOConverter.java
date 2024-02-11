package com.cydeo.converter;

import com.cydeo.dto.CompanyDto;
import com.cydeo.service.CompanyService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CompanyDTOConverter implements Converter<String, CompanyDto> {

    CompanyService companyService;
    public CompanyDTOConverter(@Lazy CompanyService companyService) {
        this.companyService=companyService;
    }

    @Override
    public CompanyDto convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return companyService.findById(Long.parseLong(source));
    }
}
