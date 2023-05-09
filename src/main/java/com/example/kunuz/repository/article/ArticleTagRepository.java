package com.example.kunuz.repository.article;

import com.example.kunuz.entity.ArticleLikeEntity;
import com.example.kunuz.entity.ArticleTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, Integer> {
}
