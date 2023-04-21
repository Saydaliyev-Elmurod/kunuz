package com.example.kunuz.controller;

import com.example.kunuz.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/email")
public class EmailController {
    @Autowired
    private EmailHistoryService emailHistoryService;
    @GetMapping("/{email}")
    public ResponseEntity<?> getEmailByEmail(@PathVariable("email") String email,
                                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                                             @RequestParam(value = "page",defaultValue = "1") Integer page){
        return ResponseEntity.ok(emailHistoryService.getByEmail(email,page,size));
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") LocalDate date,
                                       @RequestParam(value = "size",defaultValue = "10") Integer size,
                                       @RequestParam(value = "page",defaultValue = "1") Integer page){
        return ResponseEntity.ok(emailHistoryService.getByDate(date,page,size));
    }

}
