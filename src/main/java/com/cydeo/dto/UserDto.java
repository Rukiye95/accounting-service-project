package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @NotBlank(message = "Email is required field.")
    @Email(message = "Invalid email format.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d@#$%^&+=]).{4,}$",
            message = "Password should be at least 4 characters long and needs to contain 1 capital letter, 1 small letter, and 1 special character or number.")
    @NotBlank(message = "This is a required field.")
    private String password;
    @NotBlank(message = "This is a required field.")
    private String confirmPassword;
    @NotBlank(message = "First Name is required field.")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters long.")
    private String firstname;
    @NotBlank(message = "Last Name is required field.")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters long.")
    private String lastname;
    @NotBlank(message = "Phone Number is required field.")
    @Pattern(regexp = "^\\+\\d{11,15}$", message = "Phone Number is a required field and may be in any valid phone number format.")
    private String phone;

    @NotNull(message = "Please select a Role")
    private RoleDto role;
    @NotNull(message = "Please select a Company")
    private CompanyDto company;
    private boolean isOnlyAdmin;
}
