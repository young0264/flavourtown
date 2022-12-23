package com.flavourtown.web.controller.account;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.ProfileService;
import com.flavourtown.web.vo.MemberVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    AccountService accountService;

    @Test
    @DisplayName("/profile/username, get, 프로필페이지 보여주기")
    @WithMockUser(username = "testUser",password = "1", roles = "User")
    void showProfilePage() throws Exception {
        List<Post> postList = new ArrayList<>();
        postList.add(Post.builder().title("testUser").content("content").userName("testUser").build());

        MemberVo memberVo = MemberVo.builder().nickname("testUser").postList(postList).build();
//        Member member = memberRepository.findByNickname("testUser").orElse(null);
//        MemberVo memberVo = accountService.getReadOnlyMember("testUser");

        given(profileService.findByUsernameOrNickname("testUser"))
                .willReturn(Optional.ofNullable(memberVo));

        mockMvc.perform(get("/profile/"+"testUser")
                        .with(SecurityMockMvcRequestPostProcessors.user("testUser")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/settings/profile, get, 프로필 셋팅 페이지")
    @WithMockUser(username = "test",password = "1",roles = "User")
    void showProfileSettingPage() throws Exception {

//        MemberVo memberVo = accountService.getReadOnlyMember("test");
//        Account account = accountService.findAccountByUsername("test");

//        given(accountService.getReadOnlyMember("test")).willReturn(new MemberVo("하이","test"));
//        mockMvc.perform(get("/settings/profile"))
//                .andExpect(status().isOk());
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