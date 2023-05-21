package com.example.kunuz.controller.comment;

import com.example.kunuz.dto.comment.CommentDTO;
import com.example.kunuz.dto.comment.CommentFilterDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.comment.CommentService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/private/")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.create(dto, SpringSecurityUtil.getProfileId()));
    }

    @PostMapping("/private/update/{id}")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    @PathVariable("id") Integer commentId) {
        return ResponseEntity.ok(commentService.update(dto, commentId, SpringSecurityUtil.getProfileId()));
    }

    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
//        JwtUtil.checkToAdminOrOwner(auth);
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/public/list/{id}")
    public ResponseEntity<?> list(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(commentService.list(articleId));
    }

    @GetMapping("/private/list/admin/{id}")
    public ResponseEntity<?> listByAdmin(@PathVariable("id") String articleId,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(commentService.listByAdmin(articleId, page, size));
    }

    @PostMapping("/private/admin/filter")
    public ResponseEntity<?> filter(@RequestBody CommentFilterDTO dto,
                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(commentService.filter(dto, page, size));
    }

    @GetMapping("/public/list/reply/{id}")
    public ResponseEntity<?> replyCommentList(@PathVariable("id") Integer commentId) {
        return ResponseEntity.ok(commentService.replyCommentList(commentId));
    }
}
