package com.example.kunuz.controller;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleLikeService;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @PutMapping("/like/{a_id}")
    public ResponseEntity<?> like(@PathVariable("a_id")  String articleId,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(articleLikeService.like(articleId, jwtDTO.getId()));
    }
    @PutMapping("/dislike/{a_id}")
    public ResponseEntity<?> dislike(@PathVariable("a_id")  String articleId,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(articleLikeService.dislike(articleId, jwtDTO.getId()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String articleId,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(articleLikeService.delete(articleId, jwt.getId()));
    }
}
