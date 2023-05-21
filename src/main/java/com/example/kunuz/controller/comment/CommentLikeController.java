package com.example.kunuz.controller.comment;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.service.comment.CommentLikeService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment_like")
@AllArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PutMapping("/private/like/{a_id}")
    public ResponseEntity<?> like(@PathVariable("a_id") Integer commentId) {
        return ResponseEntity.ok(commentLikeService.like(commentId, SpringSecurityUtil.getProfileId()));
    }

    @PutMapping("/private/dislike/{a_id}")
    public ResponseEntity<?> dislike(@PathVariable("a_id") Integer commentId) {
        return ResponseEntity.ok(commentLikeService.dislike(commentId,  SpringSecurityUtil.getProfileId()));
    }
}
