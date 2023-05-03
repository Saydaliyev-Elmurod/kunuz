package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, Integer> {


    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String a_id, Integer p_id);

    @Query("select count (a) from ArticleLikeEntity as a where a.status='LIKE' and a.articleId=:a_id")
    int likeCount(String a_id);
}
