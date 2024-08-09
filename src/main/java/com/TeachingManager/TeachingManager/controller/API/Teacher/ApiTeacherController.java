package com.TeachingManager.TeachingManager.controller.API.Teacher;

import com.TeachingManager.TeachingManager.DTO.Schedule.ScheduleResponse;
import com.TeachingManager.TeachingManager.DTO.Schedule.UpdateScheduleRequest;
import com.TeachingManager.TeachingManager.DTO.Teacher.*;
import com.TeachingManager.TeachingManager.Service.User.Teacher.TeacherServiceImpl;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import com.TeachingManager.TeachingManager.domain.Schedule;
import com.TeachingManager.TeachingManager.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ApiTeacherController {
    private final TeacherServiceImpl teacherService;

    // 선생님 회원가입
    @PostMapping("/api/teacher")
    public String signup(AddTeacherRequest request) {
        // 여기에 일반적인 방법으롤 회원가입을 하는 경우
        teacherService.register(request);
     return "redirect:/login";
    }

    @PostMapping("/api/social/teacher")
    public String social_signup(AddSocialTeacherRequest request) {
        // 소셜로그인으로 추가적인 회원가입 정보를 제출 받았을 경우
        teacherService.social_register(request);
        return "redirect:/home";
    }

    // 요청한 선생님의 정보를 전달하는 api
    @GetMapping("/api/teacher/{pk}")
    public ResponseEntity<TeacherInfo> search_oneTeacher(@AuthenticationPrincipal CustomUser user, @RequestBody FindOneTeacherRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teacherService.search_teacher(request.getTeacher_id(), user.getPk()));
    }


    // 요청한 기관의 모든 선생님 정보 정달하는 api
    @GetMapping("/api/teacher")
    public ResponseEntity<FindAllTeacherResponse> search_allTeacher(@AuthenticationPrincipal CustomUser user, @RequestBody FindOneTeacherRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(teacherService.search_allTeacher(user.getPk()));
    }

    // 선생님 정보 수정 api
    @PutMapping("/api/teacher") ResponseEntity<TeacherInfo> update_Teacher(@RequestBody UpdateTeacherRequest request, @AuthenticationPrincipal CustomUser user){
        Teacher sc = teacherService.update_Teacher(user, request);
        return ResponseEntity.ok()
                .body(new TeacherInfo(sc));

    }

    // 회원탈퇴 api
    @PutMapping("/api/delete/teacher") ResponseEntity<String> delete_Teacher(@AuthenticationPrincipal CustomUser user){
        return ResponseEntity.ok()
                .body(teacherService.delete_Teacher(user));
    }

}
