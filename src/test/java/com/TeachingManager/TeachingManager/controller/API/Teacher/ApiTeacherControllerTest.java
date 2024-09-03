package com.TeachingManager.TeachingManager.controller.API.Teacher;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/setUpTest.sql")
class ApiTeacherControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String addTeacherRequest;
    private String addSocialTeacherRequest;
    private String findOneTeacherRequest;
    private String updateTeacherRequest;

    @BeforeEach
    void setUp() {
        addTeacherRequest = "{ " +
                "\"email\": \"test123@gmail.com\", " +
                "\"password\": \"123\", " +
                "\"teacherName\": \"김선생\", " +
                "\"age\": 23, " +
                "\"birth\": \"2024-09-09\", " +
                "\"phoneNum\": \"01011111111\", " +
                "\"gender\": \"남\", " +
                "\"bank_account\": \"234\", " +
                "\"nickname\": \"닉네임\" " +
                "}";
        updateTeacherRequest = "{ " +
                "\"teacherName\": \"김선생\", " +
                "\"age\": 23, " +
                "\"birth\": \"2024-09-09\", " +
                "\"phoneNum\": \"01011111111\", " +
                "\"gender\": \"남\", " +
                "\"bank_account\": \"234\", " +
                "\"salary\": 200, " +
                "\"nickname\": \"닉네임 수정\" " +
                "}";
        findOneTeacherRequest = "{ " +
                "\"teacher_id\": \"0dda6019-71af-478c-bd93-0cc55517cbe0\" " +
                "}";
        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();
    }
    @Test
    @DisplayName("선생 회원가입 테스트")
    void signup() throws Exception{
        mockMvc.perform(post("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addTeacherRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    어떻게 할지 보류
//    @Test
//    void social_signup() {
//    }

    @Test
    @DisplayName("학원 계정의 단일 선생 조회 테스트")
    void searchOneTeacherByI() throws Exception{
        mockMvc.perform(get("/api/teacher/one")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findOneTeacherRequest))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 본인 선생 조회 테스트")
    void searchOneTeacherByT() throws Exception{
        mockMvc.perform(get("/api/teacher/one")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findOneTeacherRequest))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

//    @Test
//    @DisplayName("선생 계정의 다른 선생 조회 실패 테스트")
//    void searchOneTeacherByOtherT() throws Exception{
//        mockMvc.perform(post("/api/teacher/one")
//                        .header("Authorization", "Bearer " + teacherAccessToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(findOneTeacherRequest))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }

    @Test
    @DisplayName("학원 계정의 전체 선생 조회 테스트")
    void searchAllTeacherByI() throws Exception{
        mockMvc.perform(get("/api/teacher")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findOneTeacherRequest))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 선생 수정 테스트")
    void update_Teacher() throws Exception{
        mockMvc.perform(put("/api/teacher")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTeacherRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 선생 탈퇴 테스트")
    void delete_Teacher() throws Exception{
        mockMvc.perform(put("/api/delete/teacher")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }
}