package com.TeachingManager.TeachingManager.controller.View.Student;

import com.TeachingManager.TeachingManager.Service.Student.StudentService;
import com.TeachingManager.TeachingManager.DTO.Student.StudentListViewResponse;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudentViewController {
    private final StudentService studentService;

//    @GetMapping("/Students")
//    public String getStudents(Model model, @AuthenticationPrincipal CustomUser user){
//        List<StudentListViewResponse> students = studentService.findAll(user).stream()
//                .map(StudentListViewResponse::new)
//                .toList();
//        model.addAttribute("students", students);
//
//        return "student/student_main";
//    }
//
//    @GetMapping("/student/create")
//    public String createStudents(Model model){
//
//        return "student/student_create";
//    }


}
