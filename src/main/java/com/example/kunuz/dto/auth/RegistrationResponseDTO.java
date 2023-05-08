package com.example.kunuz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RegistrationResponseDTO {
    private String message;

    public RegistrationResponseDTO(String message) {
        this.message = message;
    }
}
