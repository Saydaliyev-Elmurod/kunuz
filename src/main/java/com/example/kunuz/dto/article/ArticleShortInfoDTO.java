package com.example.kunuz.dto.article;

import com.example.kunuz.dto.AttachDTO;
import com.example.kunuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDTO {
    private String id;
    private String title;
    private String description;
    private AttachDTO image;
    private LocalDateTime publishedDate;

    public ArticleShortInfoDTO(String id, String title, String description, AttachDTO image, LocalDateTime publishedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishedDate = publishedDate;
    }

    public ArticleShortInfoDTO() {

    }
}
