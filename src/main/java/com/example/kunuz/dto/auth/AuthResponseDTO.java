package com.example.kunuz.dto.auth;

import com.example.kunuz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String name;
    private String surname;
    private ProfileRole role;
    private String jwt;
}
