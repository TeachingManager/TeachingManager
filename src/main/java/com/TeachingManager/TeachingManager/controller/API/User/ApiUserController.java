package com.TeachingManager.TeachingManager.controller.API.User;

import com.TeachingManager.TeachingManager.DTO.User.EmailRequest;
import com.TeachingManager.TeachingManager.DTO.User.InviteRequest;
import com.TeachingManager.TeachingManager.DTO.User.NewPasswordUpdateRequest;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.Service.User.UserService;
import com.TeachingManager.TeachingManager.domain.CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ApiUserController {

    private final TokenService tokenService;
    private final UserService userService;

    // 비밀번호 분실시 -> 재발급 요청에 관한 아이디 정보를 입력받을 곳
    // 이메일을 입력받아, 해당 이메일이 회원에 존재하면, 새 토큰을 이메일과 IP 주소를 통해 발급받고,
    // 해당 토큰을
    @PostMapping("/api/password/change")
    public ResponseEntity<Map<String, String>> sendEmailToUserWhoLostPassword(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "setNewPassword"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }


    // 비밀번호 분실시 -> 비밀번호 재설정 사이트에서 들어오는 요청
    @PostMapping("/password/change")
    public ResponseEntity<Map<String, String>> changePasswordByToken(
            @RequestParam(value = "token") String token,
            @RequestBody NewPasswordUpdateRequest request,
            HttpServletRequest HttpRequest) throws Exception {

        Map<String, String> response = new HashMap<>();
        response.put("message", userService.changePassword(token, HttpRequest.getRemoteAddr(), request.getNewPassword()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // 계정 잠김시 해제 요청을 하는 api
    @PostMapping("/api/email/locked/prove")
    public ResponseEntity<Map<String, String>> sendEmailToLockedUser(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "unLockUser"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    //  비밀번호 5회 틀림 -> 잠김.... 이를 해제 하기 위한 이메일 인증
    @PostMapping("/email/locked/prove")
    public ResponseEntity<Map<String, String>> proveLockedUser(
            @RequestParam(value = "token") String token,
            HttpServletRequest HttpRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.unLockUser(token, HttpRequest.getRemoteAddr()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 초기 회원 가입시 인증 메일 발송되게 하는 api
    @PostMapping("/api/email/initial/prove")
    public ResponseEntity<Map<String, String>> sendEmailToSignUpUser(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "initialAuthentication"));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }


// 초기 회원가입시 유저 인증을 위해 사용
    @PostMapping("/email/initial/prove")
    public ResponseEntity<Map<String, String>> proveInitialUser(
            @RequestParam(value = "token") String token,
            HttpServletRequest HttpRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.enableUser(token, HttpRequest.getRemoteAddr()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 강사 초대 이메일이 발송되게 하는 api
    @PostMapping("/api/invite/teacher")
    public ResponseEntity<Map<String, String>> sendInviteEmailToTeacher(
            @RequestBody InviteRequest request,
            @AuthenticationPrincipal CustomUser user) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.sendEmailWithJoinToken(request, user));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    // 강사가 학원에 등록되는 api
    @PostMapping("/invite/teacher")
    public ResponseEntity<Map<String, String>> inviteTeacherToInstitute(@RequestParam(value = "token") String token) throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", userService.joinTeacherToInstitute(token));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
