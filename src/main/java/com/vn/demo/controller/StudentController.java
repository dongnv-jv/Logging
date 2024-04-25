package com.vn.demo.controller;

import com.vn.demo.aop.Log;
import com.vn.demo.constant.LogActionEnum;
import com.vn.demo.constant.LogFunctionEnum;
import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/update")
    @Log(action = LogActionEnum.UPDATE, function = LogFunctionEnum.STUDENT_MANAGER)
    public ResponseEntity<?> update(@RequestBody RequestInsert student) {

        return ResponseEntity.ok(studentService.update(student));
    }

    @PostMapping("/save")
    @Log(action = LogActionEnum.SAVE, function = LogFunctionEnum.STUDENT_MANAGER)
    public ResponseEntity<?> save(@RequestBody Student student) {

        return ResponseEntity.ok(studentService.save(student));
    }
    @GetMapping("/saveAll")
    @Log(action = LogActionEnum.SAVE, function = LogFunctionEnum.STUDENT_MANAGER)
    public ResponseEntity<?> saveAll() {

        return ResponseEntity.ok(studentService.saveAll());
    }

    @GetMapping("/get")
    @Log(action = LogActionEnum.SAVE, function = LogFunctionEnum.STUDENT_MANAGER)
    public ResponseEntity<?> get() {
        List<Student> studentList = new ArrayList<>();
        Stream<Student> studentStream = studentService.get();
        studentList = studentStream.collect(Collectors.toList());

        return ResponseEntity.ok(studentList);
    }
}
