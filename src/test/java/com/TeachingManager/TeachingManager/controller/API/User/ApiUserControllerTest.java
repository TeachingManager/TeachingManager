package com.TeachingManager.TeachingManager.controller.API.User;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import com.TeachingManager.TeachingManager.config.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/setUpTest.sql")
class ApiUserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenProvider tokenProvider;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String emailRequest;
    private String newPasswordUpdateRequest;
    private String inviteRequest;
    private String resetToken;
    private String joinToken;

    @BeforeEach
    void setUp() throws Exception{
        emailRequest = "{ " +
                "\"email\": \"asd123@gmail.com\" " +
                "}";
        newPasswordUpdateRequest = "{ " +
                "\"newPassword\": \"234\" " +
                "}";
        inviteRequest = "{ " +
                "\"institute_email\": \"asd123@gmail.com\", " +
                "\"teacher_email\": \"zxc123@gmail.com\" " +
                "}";

        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();
        resetToken = tokenProvider.createResetToken(Duration.ofMinutes(5), "asd123@gmail.com", "192.168.1.1");
        joinToken = tokenProvider.createJoinToken(Duration.ofMinutes(10), "zxc123@gmail.com", "asd123@gmail.com");

    }

    @Test
    @DisplayName("비밀번호 분실 계정 이메일 요청 테스트")
    void sendEmailToUserWhoLostPassword() throws Exception{
        mockMvc.perform(post("/api/password/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("사용자 정보 확인. 비밀번호 변경 메일이 보내졌습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 재설정 요청 테스트")
    void changePasswordByToken() throws Exception{
        mockMvc.perform(post("/password/change")
                        .with(request -> {request.setRemoteAddr("192.168.1.1"); return request;})
                        .param("token", resetToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPasswordUpdateRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("비밀번호 변경 완료!"))
                .andDo(print());
    }

    @Test
    @DisplayName("계정 잠금해제 이메일 요청 테스트")
    void sendEmailToLockedUser() throws Exception{
        mockMvc.perform(post("/api/email/locked/prove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("사용자 정보 확인. 잠금해제용 메일이 보내졌습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("계정 잠금해제 이메일 인증 테스트")
    void proveLockedUser() throws Exception{
        mockMvc.perform(post("/email/locked/prove")
                        .with(request -> {request.setRemoteAddr("192.168.1.1"); return request;})
                        .param("token", resetToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("계정 잠금 해제 완료!"))
                .andDo(print());
    }

    @Test
    @DisplayName("계정 잠금해제 이메일 요청 테스트")
    void sendEmailToSignUpUser() throws Exception{
        mockMvc.perform(post("/api/email/initial/prove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("사용자 정보 확인. 초기 인증용 메일이 보내졌습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("계정 잠금해제 이메일 인증 테스트")
    void proveInitialUser() throws Exception{
        mockMvc.perform(post("/email/initial/prove")
                        .with(request -> {request.setRemoteAddr("192.168.1.1"); return request;})
                        .param("token", resetToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("계정 잠금 해제 완료!"))
                .andDo(print());
    }

    @Test
    @DisplayName("강사 초대 이메일 요청 테스트")
    void sendInviteEmailToTeacher() throws Exception{
        mockMvc.perform(post("/api/invite/teacher")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("강사에게 초대 이메일을 전송했습니다.."))
                .andDo(print());
    }

    @Test
    @DisplayName("강사 등록 테스트")
    void inviteTeacherToInstitute() throws Exception{
        mockMvc.perform(post("/invite/teacher")
                        .param("token", joinToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteRequest))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("가입완료"))
                .andDo(print());
    }
}