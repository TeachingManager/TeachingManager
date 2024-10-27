package com.TeachingManager.TeachingManager.controller.API.Enroll;

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
class ApiEnrollControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String enrollLectureRequest;

    @BeforeEach
    void setUp() {
        enrollLectureRequest = "{ " +
                "\"studentIdList\": " +
                "[1] " +
                "}";

        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("학원 계정의 특정 달 수강한 학생 조회 테스트")
    void findEnrolledStudentsByI() throws Exception{
        mockMvc.perform(get("/api/enroll/students")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("lecture_id", "1")
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 특정 달 수강한 학생 조회 실패 테스트")
    void findEnrolledStudentsByT() throws Exception{
        mockMvc.perform(get("/api/enroll/students")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .param("lecture_id", "1")
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 특정 달 개설된 강의 조회 테스트")
    void findEnrolledLecturesByI() throws Exception{
        mockMvc.perform(get("/api/enroll/lectures")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 특정 달 개설된 강의 조회 테스트")
    void findEnrolledLecturesByT() throws Exception{
        mockMvc.perform(get("/api/enroll/lectures")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 특정 달 개설되지 않은 강의 조회 테스트")
    void findNotEnrolledLecturesByI() throws Exception{
        mockMvc.perform(get("/api/notEnroll/lectures")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 강의 개설 테스트")
    void enrollLectures() throws Exception{
        mockMvc.perform(post("/api/enroll")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("lecture_id", "2")
                        .param("year", "2024")
                        .param("month", "8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enrollLectureRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학생 수강등록 테스트")
    void enrollStudentToLecture() throws Exception{
//        강의 개설하여 스케쥴에 테이블 등록 후 테스트 하기 위해서는 아래 주석 실행 및 lecture_id 2로 변경
//
//        mockMvc.perform(post("/api/enroll")
//                .header("Authorization", "Bearer " + instituteAccessToken)
//                .param("lecture_id", "2")
//                .param("year", "2024")
//                .param("month", "8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(enrollLectureRequest))
//                .andDo(print());
        mockMvc.perform(post("/api/enroll/student")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("lecture_id", "1")    //value 2로 변경
                        .param("student_id", "2")
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 수강취소 테스트")
    void cancelStudentEnroll() throws Exception{
        mockMvc.perform(put("/api/delete/enroll/student")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("enroll_id", "1")
                        .param("lecture_id", "1")
                        .param("student_id", "1")
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}