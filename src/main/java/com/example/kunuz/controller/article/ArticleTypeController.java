package com.example.kunuz.controller.article;

import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.article.ArticleTypeService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articleType")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;
//  create articleType only by admin
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.create(dto));
    }
//### update by id only by admin
    @PostMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody ArticleTypeDTO dto,
                                        @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);

        dto.setId(id);
        return ResponseEntity.ok(articleTypeService.updateById(dto));
    }
//### delete by id only admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id,
                                        @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);

        articleTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
//### get list only by admin with pagination
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);

        return ResponseEntity.ok(articleTypeService.getList(page, size));
    }
//### get list by name  only by id with pagination
    @GetMapping("/list/{name}")
    public ResponseEntity<?> getList(@PathVariable("name") LangEnum name,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.getList(name,page, size));
    }

}
