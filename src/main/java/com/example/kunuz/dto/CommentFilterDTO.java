package com.example.kunuz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentFilterDTO {
    private Integer id;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private Integer profileId;
    private String articleId;
}
