package com.example.kunuz.dto.profile;

import com.example.kunuz.enums.GeneralStatus;
import com.example.kunuz.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private ProfileRole role;
    private GeneralStatus status;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
