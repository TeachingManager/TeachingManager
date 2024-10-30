package com.TeachingManager.TeachingManager.controller.API.Schedule;

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
class ApiScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String addScheduleRequest;
    private String monthScheduleRequest;
    private String updateScheduleRequest;

    @BeforeEach
    void setUp() {
        addScheduleRequest = "{ " +
                "\"title\": \"이세돌\", " +
                "\"start_date\": \"2024-08-08T14:30:15\", " +
                "\"end_date\": \"2024-08-08T14:30:15\", " +
                "\"memo\": \"메모입니당\" " +
                "}";
        monthScheduleRequest = "{ " +
                "\"date_info\": \"2024-08-08\" " +
                "}";
        updateScheduleRequest = "{ " +
                "\"title\": \"수정 이세돌\", " +
                "\"start_date\": \"2024-08-08T14:30:15\", " +
                "\"end_date\": \"2024-08-08T14:30:15\", " +
                "\"memo\": \"수정 메모입니당\" " +
                "}";
        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("학원 계정의 일정 추가 테스트")
    void createScheduleByI() throws Exception{
        mockMvc.perform(post("/api/Schedule")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addScheduleRequest))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 일정 추가 실패 테스트")
    void createScheduleByT() throws Exception{
        mockMvc.perform(post("/api/Schedule")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addScheduleRequest))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 일정 조회 테스트")
    void scheduleByI() throws Exception{
        mockMvc.perform(get("/api/Schedule")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("date_info", "2024-08-08"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 일정 조회 테스트")
    void scheduleByT() throws Exception{
        mockMvc.perform(get("/api/Schedule")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .param("date_info", "2024-08-08"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 일정 디테일 조회 테스트")
    void detailScheduleByI() throws Exception{
        mockMvc.perform(get("/api/Schedule/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 일정 디테일 조회 테스트")
    void detailScheduleByT() throws Exception{
        mockMvc.perform(get("/api/Schedule/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 일정 삭제 테스트")
    void deleteScheduleByI() throws Exception{
        mockMvc.perform(put("/api/delete/Schedule/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 일정 삭제 실패 테스트")
    void deleteScheduleByT() throws Exception{
        mockMvc.perform(put("/api/delete/Schedule/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 일정 수정 테스트")
    void reviseScheduleByI() throws Exception{
        mockMvc.perform(put("/api/Schedule/1")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateScheduleRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 일정 수정 실패 테스트")
    void reviseScheduleByT() throws Exception{
        mockMvc.perform(put("/api/Schedule/1")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateScheduleRequest))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}