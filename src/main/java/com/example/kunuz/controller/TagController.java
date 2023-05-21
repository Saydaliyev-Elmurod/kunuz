package com.example.kunuz.controller;

import com.example.kunuz.dto.TagDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.TagService;
import com.example.kunuz.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PostMapping("/private/")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PostMapping("/private/update/{id}")
    public ResponseEntity<?> update(@RequestBody TagDTO dto,
                                    @PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(tagService.update(tagId, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        tagService.delete(id);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @GetMapping("/private/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(tagService.getList(page, size));
    }

    @PostMapping("/private/moder/tag")
    public ResponseEntity<?> addTagToArticle(@RequestParam("a_id") String articleId,
                                             @RequestParam("t_id") Integer tagId) {
        tagService.addTagToArticle(articleId, tagId);
        return ResponseEntity.ok().build();
    }
}
