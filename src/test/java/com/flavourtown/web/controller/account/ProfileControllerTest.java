package com.flavourtown.web.controller.account;

import com.flavourtown.domain.post.Post;
import com.flavourtown.service.ProfileService;
import com.flavourtown.web.vo.MemberVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.transaction.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProfileService profileService;

    @Test
    @DisplayName("/profile/username, get, 프로필페이지 보여주기")
    @WithMockUser(username = "testUser",password = "1", roles = "User")
    void showProfilePage() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(Post.builder().title("title").content("content").userName("testUser").build());

        MemberVo memberVo = MemberVo.builder().nickname("testUser").postList(postList).build();
        given(profileService.findByUsernameOrNickname("testUser"))
                .willReturn(Optional.ofNullable(memberVo));

        mockMvc.perform(get("/profile/"+"testUser"))
                .andExpect(status().isOk());

    }

    @Test
    void showProfileSettingPage() {
    }

    @Test
    void overlappedID() {
    }

    @Test
    void changeNickname() {
    }

    @Test
    void changeIntroduce() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void showBookmark() {
    }

    @Test
    void showMyMap() {
    }

    @Test
    void subjectCheck() {
    }
}