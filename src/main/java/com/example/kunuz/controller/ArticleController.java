package com.example.kunuz.controller;

import com.example.kunuz.dto.ArticleDTO;
import com.example.kunuz.dto.ArticleTypeDTO;
import com.example.kunuz.dto.JwtDTO;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.ArticleService;
import com.example.kunuz.service.ArticleTypeService;
import com.example.kunuz.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    @RequestHeader("Authorization") String auth,
                                    @PathVariable("id") String articleId) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam ArticleStatus status,
                                          @RequestHeader("Authorization") String auth) {
        JwtDTO jwtDTO = JwtUtil.getJwtDTO(auth, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatusToPublish(id, status, jwtDTO.getId()));
    }

    @GetMapping("/publish/5/{id}")
    public ResponseEntity<?> getByTypeTop5(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop5ByTypeId(typeId));
    }

    @GetMapping("/publish/3/{id}")
    public ResponseEntity<?> getByTypeTop3(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop3ByTypeId(typeId));
    }

    @GetMapping("/publish/8/{id}")
    public ResponseEntity<?> getByTypeTop8(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop8ByTypeId(typeId));
    }

    @GetMapping("/publish/{id}")
    public ResponseEntity<?> getByIdAndLang(@PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.getByIdAndLang(articleId));
    }

    @GetMapping("/publish/4")
    public ResponseEntity<?> getByTypeWithoutId(@RequestParam("articleId") String articleId,
                                                @RequestParam("typeId") Integer typeId) {
        return ResponseEntity.ok(articleService.getByTypeWithoutId(articleId, typeId));
    }

    @GetMapping("/publish/top/4")
    public ResponseEntity<?> getByTop4Read() {
        return ResponseEntity.ok(articleService.getByTop4Read());
    }

    @GetMapping("/publish/region/5")
    public ResponseEntity<?> getByTop5TypeAndRegion(@RequestParam("regionId") Integer regionId,
                                                    @RequestParam("typeId") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop5TypeAndRegion(regionId, typeId));
    }

    @GetMapping("/publish/region")
    public ResponseEntity<?> getArticleByRegion(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                @RequestParam("regionId") Integer regionId) {
        return ResponseEntity.ok(articleService.getArticleByRegion(regionId, page, size));
    }

    @GetMapping("/publish/category")
    public ResponseEntity<?> getArticleByCategory(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                  @RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getArticleByCategory(categoryId, page, size));
    }

    @GetMapping("/publish/category/5/{id}")
    public ResponseEntity<?> getTop5ArticleByCategory(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(articleService.getArticleByCategory(categoryId, 1, 5));
    }

}
