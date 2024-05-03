package com.TeachingManager.TeachingManager.controller;

import com.TeachingManager.TeachingManager.Service.student.StudentService;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.dto.AddStudentRequest;
import com.TeachingManager.TeachingManager.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@RequestBody AddStudentRequest request){
        Student savedStudent = studentService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    @GetMapping("/api/students")
    public ResponseEntity<List<StudentResponse>> findAllArticles() {
        List<StudentResponse> students = studentService.findAll()
                .stream()
                .map(StudentResponse::new)
                .toList();


        return ResponseEntity.ok()
                .body(students);
    }
}
