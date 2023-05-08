package com.example.kunuz.service.comment;

import com.example.kunuz.entity.CommentLikeEntity;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public int like(Integer commentId, Integer profileId) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(commentId, profileId);
        CommentLikeEntity entity = new CommentLikeEntity();
        if (optional.isEmpty()) {
            entity.setCommentId(commentId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.LIKE);
            commentLikeRepository.save(entity);
        } else {
            entity = optional.get();
            if (entity.getStatus().equals(LikeStatus.LIKE)) {
                commentLikeRepository.delete(entity);
            } else {
                entity.setStatus(LikeStatus.LIKE);
                commentLikeRepository.save(entity);
            }
        }
        return commentLikeRepository.likeCount(commentId);

    }
    public int dislike(Integer commentId, Integer profileId) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(commentId, profileId);
        CommentLikeEntity entity = new CommentLikeEntity();
        if (optional.isEmpty()) {
            entity.setCommentId(commentId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.DISLIKE);
            commentLikeRepository.save(entity);
        } else {
            entity = optional.get();
            if (entity.getStatus().equals(LikeStatus.DISLIKE)) {
                commentLikeRepository.delete(entity);
            } else {
                entity.setStatus(LikeStatus.DISLIKE);
                commentLikeRepository.save(entity);
            }
        }
        return commentLikeRepository.likeCount(commentId);
    }
}
