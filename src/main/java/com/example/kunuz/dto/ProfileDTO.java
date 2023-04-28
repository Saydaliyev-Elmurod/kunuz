package com.example.kunuz.dto;

import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.enums.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Integer id;
    @NotBlank(message = "Name must have some value")
    private String name;
    @NotBlank(message = "Name must have some value")
    private String surname;
    @Email(message = "Email must be valid")
    private String email;
    private String phone;
    @Size(min = 6,message = "Password lenght must be greater than 6")
    private String password;
    private ProfileRole role;
    private GeneralStatus status;
    private String photo_id;
}
