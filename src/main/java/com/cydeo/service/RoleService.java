package com.cydeo.service;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Role;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAvailableRolesForUser(UserDto currentUserDto);

    RoleDto findById(Long id);
}
