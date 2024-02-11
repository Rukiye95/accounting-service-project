package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MapperUtil mapperUtil;

    @Test
    void testShowCreateFormForRootUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUsername");
        User user = new User();

        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        lenient().when(mapperUtil.convert(any(UserDto.class), any(User.class))).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.create(userDto);

        verify(userRepository).save(user);
    }

    @Test
    void testCreateUserWhenUserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUsername");
        User user = new User();

        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        lenient().when(mapperUtil.convert(any(UserDto.class), any(User.class))).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.create(userDto);

        verify(userRepository).save(user);
    }
}