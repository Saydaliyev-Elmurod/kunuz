package com.example.kunuz.controller.auth;

import com.example.kunuz.service.auth.EmailHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/email")
@AllArgsConstructor
public class EmailController {

    private final EmailHistoryService emailHistoryService;

    @GetMapping("/public/{email}")
    public ResponseEntity<?> getEmailByEmail(@PathVariable("email") String email,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email, page, size));
    }

    @GetMapping("/public/date/{date}")
    public ResponseEntity<?> getByDate(@PathVariable("date") LocalDate date,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return ResponseEntity.ok(emailHistoryService.getByDate(date, page, size));
    }

}
