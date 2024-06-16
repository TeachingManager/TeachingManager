package com.TeachingManager.TeachingManager.controller.View.Teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherController {

    @GetMapping("/login/teacher")
    public String login() {
        return "Teacher/login-teacher";
    }

    @GetMapping("/signup/teacher")
    public String signup() {
        return "Teacher/signup-teacher";
    }

    @GetMapping("/signup/social/teacher")
    public String social_additional_info() {
        // OAuth 가 실행되는 부분.

        // 추가적으로 입력받는 창을 열어준다.
        return "Teacher/signup-social-teacher";
    }

}
