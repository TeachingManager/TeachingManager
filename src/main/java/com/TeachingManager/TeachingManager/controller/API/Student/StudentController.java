package com.TeachingManager.TeachingManager.controller.API.Student;

import com.TeachingManager.TeachingManager.Service.Student.StudentService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.DTO.Student.AddStudentRequest;
import com.TeachingManager.TeachingManager.DTO.Student.StudentResponse;
import com.TeachingManager.TeachingManager.DTO.Student.UpdateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentController {
    private final StudentService studentService;
    private final TokenService tokenService;

    // 학생 추가 api
    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@RequestHeader("Authorization") String authorizationHeader,@RequestBody AddStudentRequest request){
        // 이메일 체크
        String token = tokenService.extractedToken(authorizationHeader);
        String email = tokenService.findEmailInToken(token);

        Student savedStudent = studentService.save(request, email);

        if (savedStudent != null){
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStudent);
        }
        else{
            // 존재하지 않는 이메일로 들어왔을 경우.
            return (ResponseEntity<Student>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/api/students/{id}")
    public ResponseEntity<StudentResponse> findStudent(@PathVariable(name = "id") long id){
        Student student = studentService.findById(id);

        return ResponseEntity.ok()
                .body(new StudentResponse(student));
    }

    @PostMapping("/api/delete/students/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(name="id") long id){
        studentService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(name = "id") long id, @RequestBody UpdateStudentRequest request) {
        Student updatedStudent = studentService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedStudent);

    }

}
