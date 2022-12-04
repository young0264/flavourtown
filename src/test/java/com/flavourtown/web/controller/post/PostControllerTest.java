package com.flavourtown.web.controller.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

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

    @Test
    void newPost() {
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