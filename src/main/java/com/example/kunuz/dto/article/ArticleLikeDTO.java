package com.example.kunuz.dto.article;

import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.ProfileEntity;
import com.example.kunuz.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ArticleLikeDTO {
    private Integer id;
    private String articleId;
    private Integer profileId;
    private LikeStatus status;
}
