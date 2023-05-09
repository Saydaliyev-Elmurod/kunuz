package com.example.kunuz.controller;

import com.example.kunuz.dto.RegionDTO;
import com.example.kunuz.enums.LangEnum;
import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.RegionService;
import com.example.kunuz.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/region")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;
    //  create region only by admin
    @PostMapping("/private")
    public ResponseEntity<?> create(@RequestBody @Valid RegionDTO dto,
                                    HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }
    //### update by id only by admin
    @PostMapping("/private/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") Integer id,
                                        @RequestBody @Valid RegionDTO dto,
                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        dto.setId(id);
        return ResponseEntity.ok(regionService.updateById(dto));
    }
    //### delete by id only admin
    @DeleteMapping("/private/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id,
                                        HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        regionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    //### get list only by admin with pagination
    @GetMapping("/private/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getList(page, size));
    }
    //### get list by name  only by id with pagination
    @GetMapping("/private/list/{name}")
    public ResponseEntity<?> getList(@PathVariable("name") LangEnum name,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     HttpServletRequest request) {
        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getList(name,page, size));
    }

}
