package com.vn.demo.service;

import com.vn.demo.aop.Log;
import com.vn.demo.constant.LogActionEnum;
import com.vn.demo.constant.LogFunctionEnum;
import com.vn.demo.dto.StudentDTO;
import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.helper.LogHelper;
import com.vn.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final LogHelper logHelper;
    private final ModelMapper modelMapper;


    public Student save(Student student) {
        return repository.save(student);
    }

    @Log(action = LogActionEnum.UPDATE,function = LogFunctionEnum.STUDENT_MANAGER)
    public Student update(RequestInsert requestInsert) {
        Long id = requestInsert.getId();
        Student student = repository.findById(id).orElse(null);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

        assert student != null;
        student.setName(requestInsert.getName());
        student.setAge(requestInsert.getAge());
        logHelper.compareObjects(studentDTO, student, id);

        return repository.save(student);
    }

}
