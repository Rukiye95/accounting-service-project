package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Role;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {


    private UserService userService;
    private RoleService roleService;
    private SecurityService securityService;
    private CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, SecurityService securityService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.securityService = securityService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserDto> users = userService.listUsersForCurrentUser();
        model.addAttribute("users", users);
        return "user/user-list";
    }

    @GetMapping("/update/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findById(userId);
        model.addAttribute("user", userDto);
        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        userService.update(userDto);
        return "redirect:/users/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        UserDto currentLoggedInUser = securityService.getLoggedInUser();
        List<RoleDto> availableRoles = roleService.getAvailableRolesForUser(currentLoggedInUser);

        List<CompanyDto> availableCompanies;

        if ("Root User".equalsIgnoreCase(currentLoggedInUser.getRole().getDescription())) {
            availableCompanies = companyService.getAllCompany().stream()
                    .filter(companyDto -> !companyDto.getTitle().equalsIgnoreCase("CYDEO"))
                    .collect(Collectors.toList());
        } else {
            availableCompanies = List.of(companyService.findById(currentLoggedInUser.getCompany().getId()));
        }

        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new UserDto());
        }
        model.addAttribute("companies", availableCompanies);
        model.addAttribute("userRoles", availableRoles);
        log.info("Available companies : "+availableCompanies);
        log.info("available Roles: "+availableRoles);

        return "user/user-create";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("newUser") UserDto userDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.addError(new FieldError("newUser", "confirmPassword", "Passwords should match."));
        }
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", result);
            redirectAttributes.addFlashAttribute("newUser", userDto);
            return "redirect:/users/create";
        }
        userService.create(userDto);
        return "redirect:/users/list";
    }

}
