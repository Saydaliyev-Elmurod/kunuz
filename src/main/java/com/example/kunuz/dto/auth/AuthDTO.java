package com.example.kunuz.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @Email(message = "Email must be valid")
    private String email;
    @Size(min = 6, message = "Password lenght must be greater than 6")
    private String password;
}
