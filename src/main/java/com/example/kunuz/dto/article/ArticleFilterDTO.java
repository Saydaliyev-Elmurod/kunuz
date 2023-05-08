package com.example.kunuz.dto.article;

import com.example.kunuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ArticleFilterDTO {
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private Integer typeId;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;

}

