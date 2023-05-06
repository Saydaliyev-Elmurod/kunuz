package com.example.kunuz.repository;

import com.example.kunuz.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update CommentEntity  set content =:content where id=:id")
    void updateContent(@Param("content") String content, @Param("id") Integer commentId);

    @Modifying
    @Transactional
    @Query("update CommentEntity set visible=:visible where id=:id")
    Integer updateVisible(@Param("visible") boolean visible, @Param("id") Integer id);

    List<CommentEntity> getByVisibleAndArticleId(Boolean visible,String articleId);
    @Modifying
    @Transactional
    @Query("update CommentEntity set visible=false where replyId=?1")
    void deleteReplyIdComment(Integer id);
    Page<CommentEntity> getByArticleId(String articleId, Pageable pageable);
    @Query("from CommentEntity  where replyId=?1")
    List<CommentEntity> replyCommentList(Integer commentId);


}
