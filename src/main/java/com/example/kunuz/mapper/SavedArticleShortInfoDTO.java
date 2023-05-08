package com.example.kunuz.mapper;

import com.example.kunuz.dto.article.ArticleShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedArticleShortInfoDTO {
    private Integer id;
    private ArticleShortInfoDTO article;

}
