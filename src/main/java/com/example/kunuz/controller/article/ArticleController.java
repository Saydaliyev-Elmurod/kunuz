package com.example.kunuz.controller.article;

import com.example.kunuz.dto.article.ArticleDTO;
import com.example.kunuz.dto.article.ArticleFilterDTO;
import com.example.kunuz.dto.article.ArticleGetByTypeRequestDTO;
import com.example.kunuz.enums.ArticleStatus;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.article.ArticleService;
import com.example.kunuz.util.JwtUtil;
import com.example.kunuz.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/private/moder/")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleDTO dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PostMapping("/private/moder/update/{id}")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                    @PathVariable("id") String articleId) {
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/private/moder/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/private/pub/publish/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam ArticleStatus status) {
        return ResponseEntity.ok(articleService.changeStatusToPublish(id, status, SpringSecurityUtil.getProfileId()));
    }

    @GetMapping("/public/publish/5/{id}")
    public ResponseEntity<?> getByTypeTop5(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop5ByTypeId(typeId));
    }

    @GetMapping("/public/publish/3/{id}")
    public ResponseEntity<?> getByTypeTop3(@PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop3ByTypeId(typeId));
    }

    @PostMapping("/public/publish/8")
    public ResponseEntity<?> getByTypeTop8(@RequestBody ArticleGetByTypeRequestDTO dto) {
        return ResponseEntity.ok(articleService.getTop8ByTypeId(dto.getIdList()));
    }

    @GetMapping("/public/publish/lang")
    public ResponseEntity<?> getByIdAndLang(@RequestParam("id") String articleId,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "uz", required = false) LangEnum lang) {
        return ResponseEntity.ok(articleService.getByIdAndLang(articleId, lang));
    }

    @GetMapping("/public/publish/4")
    public ResponseEntity<?> getByTypeWithoutId(@RequestParam("articleId") String articleId,
                                                @RequestParam("typeId") Integer typeId) {
        return ResponseEntity.ok(articleService.getByTypeWithoutId(articleId, typeId));
    }

    @GetMapping("/public/publish/top/4")
    public ResponseEntity<?> getByTop4Read() {
        return ResponseEntity.ok(articleService.getByTop4Read());
    }

    @GetMapping("/public/publish/tag/{tag}")
    public ResponseEntity<?> getByTop4ByTagName(@PathVariable("tag") String tag) {
        return ResponseEntity.ok(articleService.getByTop4ByTagName(tag));
    }
    @GetMapping("/public/publish/region/5")
    public ResponseEntity<?> getByTop5TypeAndRegion(@RequestParam("regionId") Integer regionId,
                                                    @RequestParam("typeId") Integer typeId) {
        return ResponseEntity.ok(articleService.getTop5TypeAndRegion(regionId, typeId));
    }

    @GetMapping("/public/publish/region")
    public ResponseEntity<?> getArticleByRegion(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                @RequestParam("regionId") Integer regionId) {
        return ResponseEntity.ok(articleService.getArticleByRegion(regionId, page, size));
    }

    @GetMapping("/public/publish/category")
    public ResponseEntity<?> getArticleByCategory(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                  @RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getArticleByCategory(categoryId, page, size));
    }

    @GetMapping("/public/publish/category/5/{id}")
    public ResponseEntity<?> getTop5ArticleByCategory(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(articleService.getArticleByCategory(categoryId, 1, 5));
    }

    @PostMapping("/public/publish/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestBody ArticleFilterDTO dto) {
        return ResponseEntity.ok(articleService.filter(dto, page, size));
    }

    @PutMapping("/public/view_count/{id}")
    public ResponseEntity<?> increaseViewCount(@PathVariable("id") String articleId){
      return ResponseEntity.ok(  articleService.increaseViewCount(articleId));
    }
    @PutMapping("/public/share_count/{id}")
    public ResponseEntity<?> increaseShareCount(@PathVariable("id") String articleId){
        return ResponseEntity.ok(  articleService.increaseShareCount(articleId));
    }
}
