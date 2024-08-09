package com.TeachingManager.TeachingManager.controller.API.Teacher;

import com.TeachingManager.TeachingManager.DTO.Teacher.*;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherServiceImpl;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ApiTeacherController {
    private final TeacherServiceImpl teacherService;

    @PostMapping("/teacher")
    public String signup(AddTeacherRequest request) {
        // 여기에 일반적인 방법으롤 회원가입을 하는 경우
        teacherService.register(request);
     return "redirect:/login";
    }

    @PostMapping("/social/teacher")
    public String social_signup(AddSocialTeacherRequest request) {
        // 소셜로그인으로 추가적인 회원가입 정보를 제출 받았을 경우
        teacherService.social_register(request);
        return "redirect:/home";
    }

    // 요청한 선생님의 정보를 전달하는 api
    @GetMapping("/teacher/detail")
    public ResponseEntity<TeacherInfo> search_oneTeacher(@AuthenticationPrincipal CustomUser user, @RequestBody FindOneTeacherRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teacherService.search_teacher(request.getTeacher_id(), user.getPk()));
    }


    // 요청한 기관의 모든 선생님 정보 정달하는 api
    @GetMapping("/teacher")
    public ResponseEntity<FindAllTeacherResponse> search_allTeacher(@AuthenticationPrincipal CustomUser user, @RequestBody FindOneTeacherRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teacherService.search_allTeacher(user.getPk()));
    }


}
