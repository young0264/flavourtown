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

    Long thisId;

    @Test
    @DisplayName("회원 가입")
    void testBefore() {
        Account account = new Account();
        account.setUsername("testUser2");
        account.setPassword("testPwd2");
        account.setEmail("test2@naver.com");

        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setIntroduce("테스트");
        memberInfoDto.setBirth(new Date());
        memberInfoDto.setGender("man");
        memberInfoDto.setMemberAge(MemberAge.MEMBER_AGE_20S);

        Member newMember = memberService.createNewMember(account, memberInfoDto);

        assertThat(newMember.getNickname()).isEqualTo("testUser2");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd2");
        assertThat(newMember.getGender()).isEqualTo("man");
    }

    //savePost(String userName, Member user, PostDto postDto)
    @BeforeEach
    @DisplayName("게시글 등록")
    void t1() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        PostDto postDto = new PostDto();
        postDto.setPlaceId(26981291L); //등록되어 있는 place Id 사용
        postDto.setTitle("testTitle");
        postDto.setContent("testContent");
        postDto.setPrivateStatus(true);

        Post post = postService.savePost(member, postDto);

        thisId = post.getId();
        assertThat(post.getTitle()).isEqualTo("testTitle");
        assertThat(post.getContent()).isEqualTo("testContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        assertThat(post.getPlace().getId()).isEqualTo(26981291L);
    }

    @Test
    @DisplayName("게시글 수정")
    void t2() {
        Post post = postRepository.findById(thisId).orElse(null);
        PostDto postDto = new PostDto();
        postDto.setTitle("testUpdateTitle");
        postDto.setContent("testUpdateContent");
        postDto.setPrivateStatus(true);
        postService.updatePost(post, postDto);
        assertThat(post.getTitle()).isEqualTo("testUpdateTitle");
        assertThat(post.getContent()).isEqualTo("testUpdateContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        System.out.println("update title : " + post.getTitle());
    }

    @Test
    @DisplayName("게시글 삭제")
    void t3() {
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            postRepository.findById(86L);
//        });
        System.out.println("this id 3 : " + thisId);
        postService.delete(thisId);
        assertThat(postService.findById(thisId)).isNull();
    }
}