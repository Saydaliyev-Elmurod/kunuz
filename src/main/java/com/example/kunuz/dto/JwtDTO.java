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
    private ProfileRole role;
}
