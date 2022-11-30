package com.flavourtown.web.controller.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;
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
    void createNewMember() {
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