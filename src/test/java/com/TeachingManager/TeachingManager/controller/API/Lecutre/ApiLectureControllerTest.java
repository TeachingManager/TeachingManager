package com.TeachingManager.TeachingManager.controller.API.Lecutre;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/setUpTest.sql")
class ApiLectureControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;
    private String addLectureRequest;
    private String updateLectureRequest;

    @BeforeEach
    void setUp() {
        addLectureRequest = "{ " +
                "\"name\": \"확통\", " +
                "\"category\": \"수학\", " +
                "\"grade\": \"중\", " +
                "\"fee\": 10000, " +
                "\"time\": \"MONDAY-12:30~13:20\", " +
                "\"teacherId\": \"0dda6019-71af-478c-bd93-0cc55517cbe0\" " +
                "}";
        updateLectureRequest = "{ " +
                "\"name\": \"확통\", " +
                "\"category\": \"수학\", " +
                "\"grade\": \"중\", " +
                "\"fee\": 10000, " +
                "\"time\": \"MONDAY-12:30~13:20\", " +
                "\"teacherId\": \"0dda6019-71af-478c-bd93-0cc55517cbe0\" " +
                "}";
        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("강의 생성 테스트")
//    @WithMockUser(roles = "PRESIDENT")
    void createLecture() throws Exception {
        mockMvc.perform(post("/api/lectures")
                .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addLectureRequest))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 전체 강의 조회 테스트")
    void findAllLectureByI() throws Exception {
        mockMvc.perform(get("/api/lectures")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 전체 강의 조회 테스트")
    void findAllLectureByT() throws Exception {
        mockMvc.perform(get("/api/lectures")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 단일 강의 조회 테스트")
    void findLectureByI() throws Exception {
        mockMvc.perform(get("/api/lectures/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 단일 강의 조회 테스트")
    void findLectureByT() throws Exception {
        mockMvc.perform(get("/api/lectures/1")
                        .header("Authorization", "Bearer " + teacherAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 강의 업데이트 테스트")
    void updateLectureByI() throws Exception {
        mockMvc.perform(put("/api/lectures/1")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateLectureRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 강의 업데이트 실패 테스트")
    void updateLectureByT() throws Exception {
        Assertions.assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/api/lectures/1")
                        .header("Authorization", "Bearer " + teacherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateLectureRequest))
                .andExpect(status().isForbidden())
                .andDo(print());
        });

    }

    @Test
    @DisplayName("학원 계정의 강의 업데이트 테스트")
    void deleteLectureByI() throws Exception {
        mockMvc.perform(put("/api/delete/lectures/1")
                        .header("Authorization", "Bearer " + instituteAccessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 강의 업데이트 실패 테스트")
    void deleteLectureByT() throws Exception {
        Assertions.assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/api/delete/lectures/1")
                            .header("Authorization", "Bearer " + teacherAccessToken))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        });

    }

}