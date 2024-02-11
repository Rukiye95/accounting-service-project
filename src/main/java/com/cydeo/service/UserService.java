package com.cydeo.service;

import com.cydeo.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findByUsername(String username);
    UserDto findById(Long Id);
    List<UserDto> listUsersForCurrentUser();
    void update(UserDto userDto);
    void create(UserDto userDto);
}
