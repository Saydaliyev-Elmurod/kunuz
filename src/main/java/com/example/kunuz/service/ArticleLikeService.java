package com.example.kunuz.service;

import com.example.kunuz.dto.ArticleLikeDTO;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.ArticleLikeEntity;
import com.example.kunuz.enums.LikeStatus;
import com.example.kunuz.exps.ItemNotFoundException;
import com.example.kunuz.repository.ArticleLikeRepository;
import com.example.kunuz.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public int like(String articleId, Integer profileId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);
        ArticleLikeEntity entity = new ArticleLikeEntity();
        if (optional.isEmpty()) {
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.LIKE);
            articleLikeRepository.save(entity);
        } else {
            entity = optional.get();
            if (entity.getStatus().equals(LikeStatus.LIKE)) {
                articleLikeRepository.delete(entity);
            } else {
                entity.setStatus(LikeStatus.LIKE);
                articleLikeRepository.save(entity);
            }
        }
        return articleLikeRepository.likeCount(articleId);

    }

    private ArticleLikeDTO toDTO(ArticleLikeEntity entity) {
        ArticleLikeDTO dto = new ArticleLikeDTO();
        dto.setArticleId(entity.getArticleId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        return dto;
    }


    public int dislike(String articleId, Integer profileId) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);
        ArticleLikeEntity entity = new ArticleLikeEntity();
        if (optional.isEmpty()) {
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.DISLIKE);
            articleLikeRepository.save(entity);
        } else {
            entity = optional.get();
            if (entity.getStatus().equals(LikeStatus.DISLIKE)) {
                articleLikeRepository.delete(entity);
            } else {
                entity.setStatus(LikeStatus.DISLIKE);
                articleLikeRepository.save(entity);
            }
        }
        return articleLikeRepository.likeCount(articleId);
    }
}
