package com.TeachingManager.TeachingManager.controller.API.Student;

import com.TeachingManager.TeachingManager.Service.Student.StudentService;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Student;
import com.TeachingManager.TeachingManager.DTO.Student.AddStudentRequest;
import com.TeachingManager.TeachingManager.DTO.Student.StudentResponse;
import com.TeachingManager.TeachingManager.DTO.Student.UpdateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiStudentController {
    private final StudentService studentService;

    // 학생 추가 api
    @PostMapping("/api/students")
    public ResponseEntity<Student> addStudent(@AuthenticationPrincipal CustomUser user,@RequestBody AddStudentRequest request){
        // 헤더에서 추출한 이메일 값으로 생성하기
        Student savedStudent = studentService.create_student(request, user.getPk());
        if (savedStudent != null){
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedStudent);
        }
        else{
            // 존재하지 않는 이메일로 들어왔을 경우.
            return (ResponseEntity<Student>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    // 전체 학생 조회
    @GetMapping("/api/students")
    public ResponseEntity<List<StudentResponse>> findAllStudents(@AuthenticationPrincipal CustomUser user) {
        List<StudentResponse> students = studentService.findAll(user)
                .stream()
                .map(StudentResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(students);
    }

    // 단일 학생 조회
    @GetMapping("/api/students/{id}")
    public ResponseEntity<StudentResponse> findStudent(@AuthenticationPrincipal CustomUser user, @PathVariable(name = "id") long id){
        Student student = studentService.findById(user, id);
        return ResponseEntity.ok()
                .body(new StudentResponse(student));
    }

    //  삭제. 삭제시 삭제된 학생 이름 전달.
    @PostMapping("/api/delete/students/{id}")
    public ResponseEntity<String> deleteArticle(@AuthenticationPrincipal CustomUser user,@PathVariable(name="id") long id){
        String student_name = studentService.delete(user, id);

        return ResponseEntity.ok()
                .body(student_name);
    }

    // 학생 업데이트
    @PutMapping("/api/students/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@AuthenticationPrincipal CustomUser user, @PathVariable(name = "id") long id, @RequestBody UpdateStudentRequest request) {
        Student updatedStudent = studentService.update(user, id, request);

        return ResponseEntity.ok()
                .body(new StudentResponse(updatedStudent));

    }

}
