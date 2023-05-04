package com.example.kunuz.repository;

import com.example.kunuz.entity.ArticleLikeEntity;
import com.example.kunuz.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentLikeRepository  extends JpaRepository<CommentLikeEntity, Integer> {
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer c_id, Integer p_id);

    @Query("select count (a) from CommentLikeEntity as a where a.status='LIKE' and a.commentId=:commentId")
    int likeCount(Integer commentId);
}
