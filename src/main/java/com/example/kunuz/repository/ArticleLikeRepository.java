package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleLikeEntity;
import com.example.kunuz.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, Integer> {
    @Modifying
    @Transactional
    @Query("delete from ArticleLikeEntity where articleId=:articleId and profileId=:profileId")
    int delete(String articleId, Integer profileId);
    @Modifying
    @Transactional
    @Query("update ArticleLikeEntity  set status =:status where articleId=:articleId and profileId=:profileId")
    int update(@Param("status") LikeStatus status,
               @Param("articleId") String articleId,
               @Param("profileId") Integer profileId);

    Optional<ArticleLikeEntity> findByArticleIdAndProfileId(String a_id, Integer p_id);

    @Query("select count (a) from ArticleLikeEntity as a where a.status='LIKE' and a.articleId=:a_id")
    int likeCount(String a_id);
}
