package com.example.kunuz.dto.profile;

import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

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
