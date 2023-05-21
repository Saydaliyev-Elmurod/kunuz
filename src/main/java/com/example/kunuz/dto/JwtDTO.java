package com.example.kunuz.dto;

import com.example.kunuz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private String email;
    private ProfileRole role;

    public JwtDTO(Integer id, ProfileRole profileRole) {
        this.id = id;
        this.role = profileRole;
    }

    public JwtDTO(String email, ProfileRole profileRole) {
        this.email = email;
        this.role = profileRole;
    }
}
