package com.flavourtown.web.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.service.AccountService;
import com.flavourtown.util.WithAuthUser;
import com.flavourtown.web.dto.post.PostDto;
import net.minidev.json.JSONUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.transaction.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
//    Account account = accountRepository.findByUsername("22222").orElse(null);

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

    @Test
    @DisplayName("/post/new, get, 로그인 페이지보여주기")
    void newPost() throws Exception {
        Account account = accountRepository.findByUsername("22222").orElse(null);
        accountService.login(account);
        mockMvc.perform(get("/post/new"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("/post/new, post, 로그인하기")
    void createPost() throws Exception {
        Account account = accountRepository.findByUsername("22222").orElse(null);
        Member member = memberRepository.findByNickname("22222").orElse(null);
        accountService.login(account);
        PostDto postDto = PostDto.builder()
                .id(account.getId())
                .title("testTitle2")
                .content("testContent2")
                .placeId(8114166L)
                .privateStatus(Boolean.FALSE)
                .modifiedTime(null)
                .postTime("now")
                .placeName("가온")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(postDto);
        System.out.println("json 1: "+ content);
        //when
        mockMvc.perform(post("/post/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(jsonPath("$.title").value("testTitle2"))
        ;

        Post testTitle = postRepository.findByTitle("testTitle2");
        Assertions.assertThat(testTitle.getContent()).isEqualTo("testContent2");
        System.out.println();
//                .andExpect(jsonPath("id").value(440L));
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