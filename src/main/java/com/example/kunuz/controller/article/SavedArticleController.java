package com.example.kunuz.controller.article;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.article.SavedArticleDTO;
import com.example.kunuz.service.article.SavedArticleService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/private")
    public ResponseEntity<?> create(@RequestBody  SavedArticleDTO dto) {
        return ResponseEntity.ok(savedArticleService.create(dto, SpringSecurityUtil.getProfileId()));
    }
    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        savedArticleService.delete(id,SpringSecurityUtil.getProfileId());
        return ResponseEntity.ok(true);
    }
    @GetMapping("/private/list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(savedArticleService.getList(SpringSecurityUtil.getProfileId()));
    }

}
