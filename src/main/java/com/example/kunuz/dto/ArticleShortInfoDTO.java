package com.example.kunuz.dto;

import com.example.kunuz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private Integer imageId;
    private LocalDateTime publishedDate;
}
