package com.example.kunuz.controller;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.SavedArticleDTO;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.service.SavedArticleService;
import com.example.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody  SavedArticleDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(savedArticleService.create(dto, jwtDTO.getId()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        savedArticleService.delete(id,jwtDTO.getId());
        return ResponseEntity.ok(true);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(savedArticleService.getList(jwtDTO.getId()));
    }

}
