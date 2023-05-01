package com.example.kunuz.controller;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.TagDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.TagService;
import com.example.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody TagDTO dto,
                                    @RequestHeader("Authorization") String auth,
                                    @PathVariable("id") Integer tagId) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.update(tagId, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        tagService.delete(id);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getLIst(@RequestHeader("Authorization") String auth,
                                     @RequestParam(value = "page",defaultValue = "1")Integer page,
                                     @RequestParam(value = "size",defaultValue = "10")Integer size) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.getList(page,size));
    }
}
