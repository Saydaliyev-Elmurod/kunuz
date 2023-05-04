package com.example.kunuz.controller;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.CommentDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.CommentService;
import com.example.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    @RequestHeader("Authorization") String auth,
                                    @PathVariable("id") Integer commentId) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentService.update(dto, commentId,jwtDTO.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.checkToAdminOrOwner(auth);
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id")String articleId) {
        return ResponseEntity.ok(commentService.list(articleId));
    }
}
