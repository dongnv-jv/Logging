package com.vn.demo.controller;

import com.vn.demo.aop.Log;
import com.vn.demo.constant.LogActionEnum;
import com.vn.demo.constant.LogFunctionEnum;
import com.vn.demo.entity.Student;
import com.vn.demo.factory.RequestInsert;
import com.vn.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
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

    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> exportDataToExcel() {
        StreamingResponseBody responseBody = outputStream -> {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Data");


                Stream<Student> entityStream = studentService.get();

                int rowNum = 0;
                for (Student entity : (Iterable<Student>) entityStream::iterator) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(entity.getAge());
                    row.createCell(1).setCellValue(entity.getName());
                    row.createCell(2).setCellValue(entity.getCreatedDate());
                }

                // Write workbook to output stream
                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=data.xlsx")
                .body(responseBody);
    }

    @GetMapping("/export-all")
    public ResponseEntity<StreamingResponseBody> exportDataToExcelAll() {
        StreamingResponseBody responseBody = outputStream -> {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Data");


                List<Student> entityLst = studentService.getAll();

                int rowNum = 0;
                for (Student entity : entityLst) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(entity.getAge());
                    row.createCell(1).setCellValue(entity.getName());
                    row.createCell(2).setCellValue(entity.getCreatedDate());
                }

                // Write workbook to output stream
                workbook.write(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=data.xlsx")
                .body(responseBody);
    }
}
