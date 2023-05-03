package com.example.kunuz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "Name uz must have some value")
    private String nameUz;
    @NotBlank(message = "Name ru must have some value")
    private String nameRu;
    @NotBlank(message = "Name en must have some value")
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String name;


}
