package com.TeachingManager.TeachingManager.controller.API.Student;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
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
class ApiStudentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String addStudentRequest;
    private String updateStudentRequest;

    @BeforeEach
    void setUp() {
        addStudentRequest = "{ " +
                "\"name\": \"김학생\", " +
                "\"age\": 20, " +
                "\"grade\": 3, " +
                "\"phoneNumber\": \"01033333333\", " +
                "\"parentName\": \"김부모\", " +
                "\"parentNumber\": \"01044444444\", " +
                "\"gender\": \"남\", " +
                "\"level\": \"상\" " +
                "}";
        updateStudentRequest = "{ " +
                "\"name\": \"이학생 수정\", " +
                "\"age\": 20, " +
                "\"grade\": 3, " +
                "\"phoneNumber\": \"01011111111\", " +
                "\"parentName\": \"이부모\", " +
                "\"parentNumber\": \"01022222222\", " +
                "\"gender\": \"남\", " +
                "\"level\": \"상\" " +
                "}";
        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("학원 계정의 학생 추가 테스트")
    void addStudentByI() throws Exception{
        mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStudentRequest))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학생 추가 실패 테스트")
    void addStudentByT() throws Exception{
        mockMvc.perform(post("/api/students")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStudentRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학생 조회 테스트")
    void findAllStudentsByI() throws Exception{
        mockMvc.perform(get("/api/students")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학생 조회 실패 테스트")
    void findAllStudentsByT() throws Exception{
        Assertions.assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/students")
                            .header("Authorization", "Bearer " + teacherAccessToken))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        });

    }

    @Test
    @DisplayName("학원 계정의 단일 학생 조회 테스트")
    void findStudentByI() throws Exception{
        mockMvc.perform(get("/api/students/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 단일 학생 조회 테스트")
    void findStudentByT() throws Exception{
        mockMvc.perform(get("/api/students/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학생 삭제 테스트")
    void deleteArticleByI() throws Exception{
        mockMvc.perform(put("/api/delete/students/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학생 삭제 실패 테스트")
    void deleteArticleByT() throws Exception{
        mockMvc.perform(put("/api/delete/students/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학생 수정 테스트")
    void updateStudentByI() throws Exception{
        mockMvc.perform(put("/api/students/1")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateStudentRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학생 수정 실패 테스트")
    void updateStudentByT() throws Exception{
        mockMvc.perform(put("/api/students/1")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateStudentRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}