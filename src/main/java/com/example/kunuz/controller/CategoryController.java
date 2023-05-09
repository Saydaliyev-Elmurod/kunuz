package com.example.kunuz.controller;

import com.example.kunuz.dto.CategoryDTO;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.CategoryService;
import com.example.kunuz.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    //  create category only by admin
    @PostMapping("/private")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    //### update by id only by admin
    @PostMapping("/private/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid CategoryDTO dto,
                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        dto.setId(id);
        return ResponseEntity.ok(categoryService.updateById(dto));
    }

    //### delete by id only admin
    @DeleteMapping("/private/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id,
                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //### get list only by admin with pagination
    @GetMapping("/private/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getList(page, size));
    }

    //### get list by name  only by id with pagination
    @GetMapping("/private/list/{name}")
    public ResponseEntity<?> getList(@PathVariable("name") LangEnum name,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getList(name, page, size));
    }
}
