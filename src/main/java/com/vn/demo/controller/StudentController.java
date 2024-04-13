package com.vn.demo.controller;

import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody RequestInsert student) {

        return ResponseEntity.ok(studentService.update(student));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Student student) {

        return ResponseEntity.ok(studentService.save(student));
    }

}
