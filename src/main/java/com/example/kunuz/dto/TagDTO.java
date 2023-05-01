package com.example.kunuz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDTO {
    private Integer id;
    @NotBlank(message = "Name have some value")
    private String name;
}

