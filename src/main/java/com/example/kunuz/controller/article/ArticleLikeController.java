package com.example.kunuz.controller.article;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.service.article.ArticleLikeService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article_like")
@AllArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    @PutMapping("private/like/{a_id}")
    public ResponseEntity<?> like(@PathVariable("a_id") String articleId) {
        return ResponseEntity.ok(articleLikeService.like(articleId, SpringSecurityUtil.getProfileId()));
    }

    @PutMapping("private/dislike/{a_id}")
    public ResponseEntity<?> dislike(@PathVariable("a_id") String articleId) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        return ResponseEntity.ok(articleLikeService.dislike(articleId, profileId));
    }

    @DeleteMapping("private/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleLikeService.delete(articleId, SpringSecurityUtil.getProfileId()));
    }
}
