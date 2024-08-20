package com.TeachingManager.TeachingManager.controller.API.Student;

import com.TeachingManager.TeachingManager.Service.Student.StudentService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.DTO.Student.AddStudentRequest;
import com.TeachingManager.TeachingManager.DTO.Student.StudentResponse;
import com.TeachingManager.TeachingManager.DTO.Student.UpdateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiStudentController {
    private final StudentService studentService;

    // 학생 추가 api
    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@AuthenticationPrincipal CustomUser user,@RequestBody AddStudentRequest request){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                        .body(studentService.create_student(request, user));
        }
        return ResponseEntity.badRequest().build();
    }

    // 전체 학생 조회
    @GetMapping("/api/students")
    public ResponseEntity<List<StudentResponse>> findAllStudents(@AuthenticationPrincipal CustomUser user) {
        if(user != null) {
            return ResponseEntity.ok()
                    .body(studentService.findAll(user));
        }
        return ResponseEntity.badRequest().build();
    }

    // 단일 학생 조회
    @GetMapping("/api/students/{id}")
    public ResponseEntity<StudentResponse> findStudent(@AuthenticationPrincipal CustomUser user, @PathVariable(name = "id") long id){
        if(user != null) {
            Student student = studentService.findById(user, id);
            return ResponseEntity.ok()
                    .body(new StudentResponse(student));
        }
        return ResponseEntity.badRequest().build();
    }

    //  삭제. 삭제시 삭제된 학생 이름 전달.
    @PutMapping("/api/delete/students/{id}")
    public ResponseEntity<String> deleteArticle(@AuthenticationPrincipal CustomUser user,@PathVariable(name="id") long id){
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            String student_name = studentService.delete(user, id);
            return ResponseEntity.ok()
                    .body(student_name);
        }
        return ResponseEntity.badRequest().build();
    }

    // 학생 업데이트
    @PutMapping("/api/students/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@AuthenticationPrincipal CustomUser user, @PathVariable(name = "id") long id, @RequestBody UpdateStudentRequest request) {
        if(user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PRESIDENT"))) {
            Student updatedStudent = studentService.update(user, id, request);
            return ResponseEntity.ok()
                    .body(new StudentResponse(updatedStudent));
        }
        return ResponseEntity.badRequest().build();
    }

}
