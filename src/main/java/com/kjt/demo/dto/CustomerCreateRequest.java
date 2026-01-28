package com.kjt.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {
    @NotBlank(message = "validation.customer.name.required")
    private String name;

    @NotBlank(message = "validation.customer.email.required")
    @Email(message = "validation.customer.email.invalid")
    private String email;

    @Pattern(regexp = "^(0|\\+84|84)(3|5|7|8|9)[0-9]{8}$\n", message = "validation.customer.phone.invalid")
    private String phone;

    private String address;
}
