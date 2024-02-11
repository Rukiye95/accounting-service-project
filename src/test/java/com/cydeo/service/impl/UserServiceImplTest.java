package com.cydeo.service.impl;

import com.cydeo.TestHelper;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MapperUtil mapperUtil;
    @Mock
    private SecurityService securityService;


    @AfterEach
    public void tearDown() {
        Mockito.reset(userRepository, roleRepository, mapperUtil, securityService);
    }
    @Test
    void testListUsersForCurrentUser_RootUser() {
        setupRootUserScenario();

        List<UserDto> result = userService.listUsersForCurrentUser();
        assertNotNull(result);
    }

    @Test
    void testListUsersForCurrentUser_AdminUser() {
        setupAdminUserScenario();

        List<UserDto> result = userService.listUsersForCurrentUser();
        assertNotNull(result);
    }

    @Test
    void testListUsersForCurrentUser_NoneAdminUser() {
        setupNoneAdminOrRootUserScenario();

        List<UserDto> result = userService.listUsersForCurrentUser();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListUserWithNonExistentRole() {
        UserDto userDto = new UserDto();
        RoleDto roleDto = new RoleDto();
        roleDto.setDescription("NonExistentRole");
        userDto.setRole(roleDto);

        Mockito.when(securityService.getLoggedInUser()).thenReturn(userDto);
        List<UserDto> users = userService.listUsersForCurrentUser();
        assertTrue(users.isEmpty());
    }

    @Test
    void testListUsersWhenRoleNotFoundInRepository() {
        UserDto adminUserDto = new UserDto();
        RoleDto adminRoleDto = new RoleDto();
        adminRoleDto.setDescription("Admin");
        adminUserDto.setRole(adminRoleDto);

        Mockito.when(securityService.getLoggedInUser()).thenReturn(adminUserDto);
        List<UserDto> users = userService.listUsersForCurrentUser();
        assertTrue(users.isEmpty());
    }

    //Helper methods
    private void setupRootUserScenario() {
        UserDto rootUserDto = new UserDto();
        RoleDto rootRoleDto = new RoleDto();
        CompanyDto companyDto = new CompanyDto();
        rootRoleDto.setDescription("Root User");
        rootUserDto.setRole(rootRoleDto);

        User adminUser1 = new User("admin1@greentech.com", "test", "Mary", "Grant", "43242342", true, new Role("Admin"), mapperUtil.convert(companyDto, new Company()));
        User adminUser2 = new User("admin2@greentech.com", "test", "Garrison", "Short", "43242342", true, new Role("Admin"), mapperUtil.convert(companyDto, new Company()));
        User adminUser3 = new User("admin@bluetech.com", "test", "Chris", "Brown", "43242342", true, new Role("Admin"), mapperUtil.convert(companyDto, new Company()));
        User adminUser4 = new User("admin@redtech.com", "test", "John", "Doe", "43242342", true, new Role("Admin"), mapperUtil.convert(companyDto, new Company()));

        List<User> adminUsers = Arrays.asList(adminUser1, adminUser2, adminUser3, adminUser4);

        List<User> users  = List.of(TestHelper.getUserEntity( "Admin User"));

        Role adminRole = new Role("Admin");
        Mockito.when(roleRepository.findByDescription("Admin")).thenReturn(Optional.of(adminRole));

        Mockito.when(securityService.getLoggedInUser()).thenReturn(rootUserDto);
        Mockito.when(userRepository.findByRoleOrderByCompanyTitleAscRoleDescriptionAsc(any(Role.class))).thenReturn(adminUsers);
    }

    private void setupAdminUserScenario() {
        UserDto adminUserDto = new UserDto();
        RoleDto adminRoleDto = new RoleDto();
        CompanyDto greenTechCompanyDto = new CompanyDto();
        greenTechCompanyDto.setId(2L);
        adminRoleDto.setDescription("Admin");
        adminUserDto.setRole(adminRoleDto);
        adminUserDto.setCompany(greenTechCompanyDto);

        Role adminRoleEntity = new Role();
        adminRoleEntity.setDescription("Admin");
        Company greenTechCompanyEntity = mapperUtil.convert(greenTechCompanyDto, new Company());

        User adminUser1 = new User("admin1@greentech.com","test", "Josh", "Tyler", "43242342", true, adminRoleEntity, greenTechCompanyEntity);
        User adminUser2 = new User("admin2@greentech.com","test", "Tom", "Cruise", "43242342", true, adminRoleEntity, greenTechCompanyEntity);
        User managerUser = new User("manager@greentech.com","test", "Robert", "Noah", "43242342", true, new Role(), greenTechCompanyEntity);
        User employeeUser = new User("employee@greentech.com","test", "Mike", "Times", "43242342", true, new Role(), greenTechCompanyEntity);

        List<User> greenTechUsers = Arrays.asList(adminUser1, adminUser2, managerUser, employeeUser);

        Mockito.when(securityService.getLoggedInUser()).thenReturn(adminUserDto);
        Mockito.when(userRepository.findByCompanyOrderByCompanyTitleAscRoleDescriptionAsc(greenTechCompanyEntity)).thenReturn(greenTechUsers);
    }

    private void setupNoneAdminOrRootUserScenario() {
        UserDto managerUserDto = new UserDto();
        RoleDto managerRoleDto = new RoleDto();
        managerRoleDto.setDescription("Manager");
        managerUserDto.setRole(managerRoleDto);

        Mockito.when(securityService.getLoggedInUser()).thenReturn(managerUserDto);
    }



}