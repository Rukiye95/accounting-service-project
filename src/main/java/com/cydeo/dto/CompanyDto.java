package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size( min = 2, max = 100,
            message="Title should be 2-100 characters long.")
    private String title;


    @NotNull
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
            message = "Phone number is required field and may be in any valid phone number format.")
    private String phone;


    @NotBlank(message = "Website is a required field")
    @Pattern(regexp = "^https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Website should have a valid format.")
    private String website;

    @NotNull
    @Valid
    private AddressDto address;

    private CompanyStatus companyStatus;



}
