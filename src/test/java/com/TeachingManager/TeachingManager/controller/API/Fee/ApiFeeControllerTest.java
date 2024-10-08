package com.TeachingManager.TeachingManager.controller.API.Fee;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("/setUpTest.sql")
class ApiFeeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;

    private String instituteAccessToken;
    private String teacherAccessToken;

    @BeforeEach
    void setUp() {

        instituteAccessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();
        teacherAccessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

    }

    @Test
    @DisplayName("학원 계정의 이번달 수강료 조회 테스트")
    void findFeeList() throws Exception{
        mockMvc.perform(get("/api/fee")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 1년 수강료 조회 테스트")
    void findYearFee() throws Exception{
        mockMvc.perform(get("/api/fee/year")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("year", "2024")
                        .param("month", "8"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 수강료 납부 및 수정 테스트")
    void purchaseFee() throws Exception{
        mockMvc.perform(get("/api/fee")
                        .header("Authorization", "Bearer " + instituteAccessToken)
                        .param("year", "2024")
                        .param("month", "8")
                        .param("enroll_id", "1")
                        .param("paid_amount", "10000"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}