package com.example.kunuz.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private String attachId;
    private Integer regionId;
    private Integer categoryId;
    private Integer typeId;
}
