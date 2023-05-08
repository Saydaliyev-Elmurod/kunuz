package com.example.kunuz.controller.article;

import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.dto.article.SavedArticleDTO;
import com.example.kunuz.service.article.SavedArticleService;
import com.example.kunuz.util.JwtUtil;
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
