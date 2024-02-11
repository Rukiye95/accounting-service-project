package com.cydeo.service.impl;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Role;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final MapperUtil mapperUtil;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(MapperUtil mapperUtil, RoleRepository roleRepository) {
        this.mapperUtil = mapperUtil;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAvailableRolesForUser(UserDto currentUserDto) {
        List<Role> roles = new ArrayList<>();

        if ("Root User".equalsIgnoreCase(currentUserDto.getRole().getDescription())) {
            Role adminRole = roleRepository.findByDescription("Admin").orElse(null);
            if (adminRole != null) {
                roles.add(adminRole);
            }
        } else if ("Admin".equalsIgnoreCase(currentUserDto.getRole().getDescription())) {
            roles = roleRepository.findAllByDescriptionIn(Arrays.asList("Admin", "Manager", "Employee"));
        }

        return roles.stream()
                .map(role -> mapperUtil.convert(role, new RoleDto()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id){
        Role role = roleRepository.findById(id).get();
       return mapperUtil.convert(role, new RoleDto());
    }

}
