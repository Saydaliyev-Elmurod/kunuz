package com.example.kunuz.controller;

import com.example.kunuz.dto.CategoryDTO;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.CategoryService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //  create category only by admin
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    //### update by id only by admin
    @PostMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody CategoryDTO dto,
                                        @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        dto.setId(id);
        return ResponseEntity.ok(categoryService.updateById(dto));
    }

    //### delete by id only admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id,
                                        @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //### get list only by admin with pagination
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getList(page, size));
    }

    //### get list by name  only by id with pagination
    @GetMapping("/list/{name}")
    public ResponseEntity<?> getList(@PathVariable("name") LangEnum name,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getList(name, page, size));
    }
}
