package com.vn.demo.service;

import com.vn.demo.aop.Log;
import com.vn.demo.constant.LogActionEnum;
import com.vn.demo.constant.LogFunctionEnum;
import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;


    public Student save(Student student) {

        student.setCreatedDate(LocalDateTime.now());
        return repository.save(student);
    }


    public Student update(RequestInsert requestInsert) {
        Long id = requestInsert.getId();
        Student student = repository.findById(id).orElse(null);
        assert student != null;
        student.setName(requestInsert.getName());
        student.setAge(requestInsert.getAge());

        return repository.save(student);
    }

}
