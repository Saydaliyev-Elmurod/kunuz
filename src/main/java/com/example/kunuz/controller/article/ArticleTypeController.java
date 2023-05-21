package com.example.kunuz.controller.article;

import com.example.kunuz.dto.article.ArticleTypeDTO;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.article.ArticleTypeService;
import com.example.kunuz.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articleType")
@AllArgsConstructor
public class ArticleTypeController {
    private final ArticleTypeService articleTypeService;
    //  create articleType only by admin
    @PostMapping("/private/admin")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.create(dto));
    }
// update by id only by admin
    @PostMapping("/private/adm/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid ArticleTypeDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(articleTypeService.updateById(dto));
    }
//delete by id only admin
    @DeleteMapping("/private/admin/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        articleTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
//### get list only by admin with pagination
    @GetMapping("/private/admin/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleTypeService.getList(page, size));
    }
//### get list by name  only by id with pagination
    @GetMapping("/private/admin/list/{name}")
    public ResponseEntity<?> getList(@PathVariable("name") LangEnum name,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleTypeService.getList(name,page, size));
    }

}
