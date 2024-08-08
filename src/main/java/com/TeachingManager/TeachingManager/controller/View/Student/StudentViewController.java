package com.TeachingManager.TeachingManager.controller.View.Student;

import com.TeachingManager.TeachingManager.Service.Student.StudentService;
import com.TeachingManager.TeachingManager.DTO.Student.StudentListViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudentViewController {
    private final StudentService studentService;

    @GetMapping("/Students")
    public String getStudents(Model model){
        List<StudentListViewResponse> students = studentService.findAll().stream()
                .map(StudentListViewResponse::new)
                .toList();
        model.addAttribute("students", students);

        return "student/student_main";
    }

    @GetMapping("/student/create")
    public String createStudents(Model model){

        return "student/student_create";
    }


}
