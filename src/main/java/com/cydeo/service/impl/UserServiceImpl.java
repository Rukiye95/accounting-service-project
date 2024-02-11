package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.exceptions.UserAlreadyExistsException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, MapperUtil mapperUtil, @Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public List<UserDto> listUsersForCurrentUser() {
        UserDto currentUserDto = securityService.getLoggedInUser();
        log.info("Current user role: " + currentUserDto.getRole().getDescription());

        if ("Root User".equalsIgnoreCase(currentUserDto.getRole().getDescription())) {
            Role adminRole = roleRepository.findByDescription("Admin")
                    .orElseThrow(() -> new IllegalArgumentException("Admin role not found"));

            List<UserDto> userList = userRepository.findByRoleOrderByCompanyTitleAscRoleDescriptionAsc(adminRole)
                    .stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .collect(Collectors.toList());

            log.info("User List of Admins: " + userList);
            return userList;
        } else if ("Admin".equalsIgnoreCase(currentUserDto.getRole().getDescription())) {
            Company currentCompany = mapperUtil.convert(currentUserDto.getCompany(), new Company());

            List<UserDto> userList = userRepository.findByCompanyOrderByCompanyTitleAscRoleDescriptionAsc(currentCompany)
                    .stream()
                    .map(user -> mapperUtil.convert(user, new UserDto()))
                    .collect(Collectors.toList());

            log.info("Current Company " + currentCompany + " User List: " + userList);
            return userList;
        } else {
            log.info("Empty List is executed");
            return Collections.emptyList();
        }
    }

    @Override
    public void update(UserDto userDto) {
        User user = mapperUtil.convert(userDto, new User());
        userRepository.save(user);
    }

    @Override
    public UserDto findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return mapperUtil.convert(user, new UserDto());
    }

    @Override
    public void create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        User user = mapperUtil.convert(userDto, new User());
        log.info("User created: "+user);
        User storedUser = userRepository.save(user);
        log.info("Stored user is "+storedUser);
    }


}
