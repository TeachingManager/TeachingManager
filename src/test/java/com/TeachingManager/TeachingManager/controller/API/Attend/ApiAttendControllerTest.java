package com.TeachingManager.TeachingManager.controller.API.Attend;

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
class ApiAttendControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String updateAttendListRequest;

    @BeforeEach
    void setUp() {
        updateAttendListRequest = "{ " +
                "\"attendList\": " +
                "{ " +
                "\"1\": 1 " +
                "} " +
                "}";
        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("학원 계정의 단일 출석 조회 테스트")
    void detailAttendByI() throws Exception{
        mockMvc.perform(get("/api/attend/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 단일 출석 조회 테스트")
    void detailAttendByT() throws Exception{
        mockMvc.perform(get("/api/attend/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 특정 달 특정 강의 출석 리스트 조회 테스트")
    void findMonthlyLectureAttendanceByI() throws Exception{
        mockMvc.perform(get("/api/attend/lecture")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("lecture_id", "1")
                        .param("date_info", "2024-08-08"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 특정 달 특정 강의 출석 리스트 조회 테스트")
    void findMonthlyLectureAttendanceByT() throws Exception{
        mockMvc.perform(get("/api/attend/lecture")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .param("lecture_id", "1")
                        .param("date_info", "2024-08-08"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 특정 달 특정 학생 출석 리스트 조회 테스트")
    void findMonthlyStudentAttendance() throws Exception{
        mockMvc.perform(get("/api/attend/student")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("student_id", "1")
                        .param("date_info", "2024-08-08"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 특정 일정의 출석 등록 테스트")
    void createAttend() throws Exception{
        mockMvc.perform(post("/api/attend")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("schedule_id", "1")
                        .param("student_id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 출석 수정 테스트")
    void updateAttend() throws Exception{
        mockMvc.perform(put("/api/attend")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateAttendListRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 출석 삭제 테스트")
    void deleteAttend() throws Exception{
        mockMvc.perform(put("/api/delete/attend")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("attend_id", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}