package com.cydeo.converter;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleDTOConverter implements Converter<String, RoleDto> {

    RoleService roleService;
    public RoleDTOConverter(@Lazy RoleService roleService) {
        this.roleService=roleService;
    }

    @Override
    public RoleDto convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return roleService.findById(Long.parseLong(source));
    }
}
