package com.flavourtown.web.controller.account;

import com.flavourtown.web.dto.account.AccountSignUpDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET method /login 로그인페이지 보여주기")
    void showLoginPage() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(get("/login"))
                .andExpect(status().isOk());

    }
//
//    @Test
//    void kakaoLogin() {
//    }

    @Test
    @DisplayName("GET method /signup 회원가입 페이지 보여주기")
    void showSignUpPage() throws Exception {
        mockMvc
                .perform(get("/signup"))
                .andExpect(status().isOk());

    }

    @Test
    void createNewMember() throws Exception {
        AccountSignUpDto accountSignUpDto = AccountSignUpDto.builder()
                .username("mocktest")
                .password("mocktest")
                .email("mocktest@naver.com")
                .build();
        Gson gson = new Gson();
        String content = gson.toJson(accountSignUpDto);
        mockMvc
                .perform(
                        post("/signup")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void overlappedID() {
    }

    @Test
    void memberInformationInit() {
    }

    @Test
    void testCreateNewMember() {
    }

    @Test
    void accountWithdrawal() {
    }
}