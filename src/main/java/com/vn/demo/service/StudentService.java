package com.vn.demo.service;

import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final EntityManagerFactory entityManagerFactory;

    public Student save(Student student) {

        student.setCreatedDate(LocalDateTime.now());
        return repository.save(student);
    }

    public String saveAll() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setName("nguyen Van Dong " + i);
            student.setCreatedDate(LocalDateTime.now());
            studentList.add(student);
        }
        repository.saveAll(studentList);
        return "OK";
    }

    public Stream<Student> get() {


        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            TypedQuery<Student> query = em.createQuery("SELECT e FROM Student e", Student.class);


            return query.getResultStream();
        } catch (Exception e) {
            return Stream.<Student>builder().build();
        }

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
