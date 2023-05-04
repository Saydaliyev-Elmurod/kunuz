package com.example.kunuz.controller;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.service.ArticleLikeService;
import com.example.kunuz.service.CommentLikeService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;
    @PutMapping("/like/{a_id}")
    public ResponseEntity<?> like(@PathVariable("a_id")  Integer commentId,
                                  @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentLikeService.like(commentId, jwtDTO.getId()));
    }
    @PutMapping("/dislike/{a_id}")
    public ResponseEntity<?> dislike(@PathVariable("a_id")  Integer commentId,
                                     @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentLikeService.dislike(commentId, jwtDTO.getId()));
    }
}
