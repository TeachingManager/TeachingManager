package com.TeachingManager.TeachingManager.controller.API.User;

import com.TeachingManager.TeachingManager.DTO.User.EmailRequest;
import com.TeachingManager.TeachingManager.DTO.User.NewPasswordUpdateRequest;
import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.Service.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ApiUserController {

    private final TokenService tokenService;
    private final UserService userService;

    // 비밀번호 분실시 -> 재발급 요청에 관한 아이디 정보를 입력받을 곳
    // 이메일을 입력받아, 해당 이메일이 회원에 존재하면, 새 토큰을 이메일과 IP 주소를 통해 발급받고,
    // 해당 토큰을
    @PostMapping("/api/password/change")
    public ResponseEntity<String> sendEmailToUserWhoLostPassword(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "setNewPassword"));
    }


    // 비밀번호 분실시 -> 비밀번호 재설정 사이트에서 들어오는 요청
    @PostMapping("/password/change")
    public ResponseEntity<String> changePasswordByToken(
            @RequestParam(value = "token") String token,
            @RequestBody NewPasswordUpdateRequest request,
            HttpServletRequest HttpRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.changePassword(token, HttpRequest.getRemoteAddr(), request.getNewPassword()));
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // 계정 잠김시 해제 요청을 하는 api
    @PostMapping("/api/email/locked/prove")
    public ResponseEntity<String> sendEmailToLockedUser(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "unLockUser"));
    }

    //  비밀번호 5회 틀림 -> 잠김.... 이를 해제 하기 위한 이메일 인증
    @PostMapping("/email/locked/prove")
    public ResponseEntity<String> proveLockedUser(
            @RequestParam(value = "token") String token,
            HttpServletRequest HttpRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.unLockUser(token, HttpRequest.getRemoteAddr()));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 초기 회원 가입시 인증 메일 발송되게 하는 api
    @PostMapping("/api/email/initial/prove")
    public ResponseEntity<String> sendEmailToSignUpUser(
            @RequestBody EmailRequest request,
            HttpServletRequest HttpRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.sendEmailWithResetToken(request.getEmail(), HttpRequest.getRemoteAddr(), "initialAuthentication"));
    }


// 초기 회원가입시 유저 인증을 위해 사용
    @PostMapping("/email/initial/prove")
    public ResponseEntity<String> proveInitialUser(
            @RequestParam(value = "token") String token,
            HttpServletRequest HttpRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.enableUser(token, HttpRequest.getRemoteAddr()));
    }

}
