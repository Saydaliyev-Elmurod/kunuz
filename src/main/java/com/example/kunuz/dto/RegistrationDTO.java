package com.example.kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RegistrationDTO {
    @NotBlank(message = "Name must have some value")
    private String name;
    @NotBlank(message = "surname must have some value")
    private String surname;
    @Email(message = "Email must be valid")
    private String email;
    private String phone;
    @Size(min = 6,message = "Password lenght must be greater than 6")
    private String password;

}
