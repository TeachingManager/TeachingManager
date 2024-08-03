package com.TeachingManager.TeachingManager.controller.API.Teacher;

import com.TeachingManager.TeachingManager.DTO.Teacher.AddSocialTeacherRequest;
import com.TeachingManager.TeachingManager.DTO.Teacher.AddTeacherRequest;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ApiTeacherController {
    private final TeacherServiceImpl teacherService;

    @PostMapping("/teacher")
    public String signup(AddTeacherRequest request) {
        // 여기에 일반적인 방법으롤 회원가입을 하는 경우
        teacherService.register(request);
     return "redirect:/login/teacher";
    }

    @PostMapping("/social/teacher")
    public String social_signup(AddSocialTeacherRequest request) {
        // 소셜로그인으로 추가적인 회원가입 정보를 제출 받았을 경우
        teacherService.social_register(request);
        return "redirect:/home";
    }


}
