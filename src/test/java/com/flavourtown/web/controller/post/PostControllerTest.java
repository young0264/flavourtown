package com.flavourtown.web.controller.post;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.service.AccountService;
import com.flavourtown.util.WithAuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.transaction.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@WebAppConfiguration
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
//    @Autowired
//    private WebApplicationContext context;
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    @Test
    @DisplayName("/post/{id}/detail, get, 게시글 상세조회")
    void showDetailPost() throws Exception {

        mockMvc.perform(get("/post/87/detail"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/post/list")
    void showAllPosts() throws Exception {
        mockMvc.perform(get("/post/list"))
                .andExpect(status().isOk());
    }

    /**
     * 로그인
     */
    @BeforeEach
    @DisplayName("로그인")
    void t0() throws Exception {


    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface With{

    }


    @Test
    @DisplayName("/post/new")
//    @WithAnonymousUser
//    @WithMockUser
    void newPost() throws Exception {
        Account account = accountRepository.findByUsername("22222").orElse(null);
        accountService.login(account);
        mockMvc.perform(get("/post/new"))
                .andExpect(status().isOk());
    }

    @Test
    void createPost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void testUpdatePost() {
    }

    @Test
    void deletePost() {
    }
}