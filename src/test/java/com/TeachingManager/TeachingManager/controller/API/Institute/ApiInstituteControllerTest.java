package com.TeachingManager.TeachingManager.controller.API.Institute;

import com.TeachingManager.TeachingManager.Service.User.TokenService;
import org.junit.jupiter.api.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/setUpTest.sql")
class ApiInstituteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    @DisplayName("학원 회원가입 성공")
//    @Order(1)
//    @Rollback(value = false)
    void signup() throws Exception {

        String request = "{ " +
                "\"email\": \"test123@gmail.com\", " +
                "\"password\": \"123\", " +
                "\"insName\": \"test\", " +
                "\"address\": \"서울시\", " +
                "\"phoneNum\": \"01011111111\" " +
                "}";

        mockMvc.perform(post("/api/institute")
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andExpect(content().string("생성됨!"))
                .andDo(print());
    }

    @Test
    @DisplayName("중복 학원 회원가입 실패")
    void duplicateSignup() throws Exception {

        String request = "{ " +
                "\"email\": \"asd123@gmail.com\", " +
                "\"password\": \"123\", " +
                "\"insName\": \"asd\", " +
                "\"address\": \"서울시\", " +
                "\"phoneNum\": \"01011111111\" " +
                "}";

        mockMvc.perform(post("/api/institute")
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학원 정보 조회 성공")
    void searchInstituteByI() throws Exception {
        String accessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();

        mockMvc.perform(get("/api/institute")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학원 정보 조회 성공")
    void searchInstituteByT() throws Exception {
        String accessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

        mockMvc.perform(get("/api/institute")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 계정의 학원 정보 수정 성공")
    void updateInstituteByI() throws Exception {
        String request = "{ " +
                "\"institute_name\": \"sdf\", " +
                "\"address\": \"수정\", " +
                "\"phoneNum\": \"01022222222\" " +
                "}";

        String accessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();

        mockMvc.perform(put("/api/institute")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선생 계정의 학원 정보 수정 실패")
    void updateInstituteByT() throws Exception {
        String request = "{ " +
                "\"institute_name\": \"sdf\", " +
                "\"address\": \"수정\", " +
                "\"phoneNum\": \"01022222222\" " +
                "}";

        String accessToken = tokenService.LoginTokenCreate("qwe123@gmail.com", "123").getAccessToken();

        mockMvc.perform(put("/api/institute")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("학원 회원 탈퇴 성공")
    void deleteInstitute() throws Exception {
        String accessToken = tokenService.LoginTokenCreate("asd123@gmail.com", "123").getAccessToken();

        mockMvc.perform(put("/api/delete/institute")
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andDo(print());
    }
}