package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressDto {

    private Long id;

    @NotBlank(message = "City is a required field")
    @Size(min = 2, max = 50, message = "City should have 2-100 characters long")
    private String addressLine1;

    @Size(max = 100, message = "Address should have maximum 100 characters long")
    private String addressLine2;

    @NotBlank(message = "City is a required field")
    @Size(min = 2, max = 50, message = "City should have 2-100 characters long")
    private String city;

    @NotBlank(message = "State is a required field")
    @Size(min = 2, max = 50, message = "State should have 2-100 characters long")
    private String state;

    private String country;

    @NotBlank(message = "Zipcode is a required field")
    @Size(min = 2, max = 50, message = "Address should have 2-100 characters long")
    @Pattern(regexp = "\\d{5}-\\d{4}",message = "Zipcode should have a valid form.")

    private String zipCode;
}
