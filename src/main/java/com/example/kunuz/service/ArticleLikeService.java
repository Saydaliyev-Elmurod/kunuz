package com.example.kunuz.service;

import com.example.kunuz.dto.ArticleLikeDTO;
import com.example.kunuz.entity.ArticleEntity;
import com.example.kunuz.entity.ArticleLikeEntity;
import com.example.kunuz.enums.ArticleStatus;
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
        makeEmotion(articleId, profileId, LikeStatus.LIKE);
        return articleLikeRepository.likeCount(articleId);
    }

    private void makeEmotion(String articleId, Integer profileId, LikeStatus status) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository
                .findByArticleIdAndProfileId(articleId, profileId);
        if (optional.isEmpty()) {
            ArticleLikeEntity entity = new ArticleLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            articleLikeRepository.save(entity);
            // article like count dislike count larni trigger orqali qilingan
        } else {
            articleLikeRepository.update(status, articleId, profileId);
        }
    }


    public int dislike(String articleId, Integer profileId) {
        makeEmotion(articleId, profileId, LikeStatus.DISLIKE);
        return articleLikeRepository.likeCount(articleId);
    }

    public boolean delete(String articleId, Integer profileId) {
        articleLikeRepository.delete(articleId, profileId);
        return true;
    }
}
