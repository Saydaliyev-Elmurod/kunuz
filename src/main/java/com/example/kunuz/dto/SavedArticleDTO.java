package com.example.kunuz.dto;

import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedArticleDTO {
        private Integer id;
        private LocalDateTime createdDate;
        private Integer profileId;
        private String articleId;
}
