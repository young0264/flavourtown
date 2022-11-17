package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.post.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @DisplayName("회원 가입")
    void testBefore() {
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("testPwd");
        account.setEmail("test@naver.com");
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setIntroduce("테스트");
        memberInfoDto.setBirth(new Date());
        memberInfoDto.setGender("man");
        memberInfoDto.setMemberAge(MemberAge.MEMBER_AGE_20S);
        Member newMember = memberService.createNewMember(account, memberInfoDto);

        assertThat(newMember.getNickname()).isEqualTo("testUser");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd");
        assertThat(newMember.getGender()).isEqualTo("man");
    }

    //savePost(String userName, Member user, PostDto postDto)
    @Test
    @DisplayName("게시글 등록")
    void createPost() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        PostDto postDto = new PostDto();
        postDto.setPlaceId(1995242054L);
        postDto.setTitle("testTitle");
        postDto.setContent("testContent");
        postDto.setPrivateStatus(true);
        Post post = postService.savePost("testUser", member, postDto);

        assertThat(post.getTitle()).isEqualTo("testTitle");
        assertThat(post.getContent()).isEqualTo("testContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        assertThat(post.getPlace().getId()).isEqualTo(1995242054L);
    }
}