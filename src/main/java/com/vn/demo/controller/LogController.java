package com.vn.demo.controller;

import com.vn.demo.service.LogDetailService;
import com.vn.demo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/log")
@RequiredArgsConstructor
@CrossOrigin
public class LogController {

    private final LogService logService;
    private final LogDetailService logDetailService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {

        return ResponseEntity.ok(logService.get(id));
    }

    @GetMapping("/get-detail/{id}")
    public ResponseEntity<?> save(@PathVariable Long id) {

        return ResponseEntity.ok(logDetailService.get(id));
    }

}
