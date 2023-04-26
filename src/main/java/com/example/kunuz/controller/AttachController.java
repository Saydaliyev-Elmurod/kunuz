package com.example.kunuz.controller;

import com.example.kunuz.enums.ProfileRole;
import com.example.kunuz.service.AttachService;
import com.example.kunuz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.save(file);
        return ResponseEntity.ok().body(fileName);
    }


    @GetMapping(value = "/open/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return attachService.open(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestHeader("Authorization") String auth,
                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        JwtUtil.getJwtDTO(auth, ProfileRole.ADMIN);
        return  ResponseEntity.ok(attachService.pagination(page,size));
    }


}
