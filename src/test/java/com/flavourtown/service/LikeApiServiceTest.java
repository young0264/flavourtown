package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.like.PostLike;
import com.flavourtown.domain.like.PostLikeRepository;
import com.flavourtown.domain.like.ReplyLikeRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.post.PostDto;
import org.assertj.core.api.Assertions;
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
class LikeApiServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    ReplyLikeRepository replyLikeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    LikeApiService likeApiService;
    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;
    Long postId;
    Long memberId;

    @Test
    @DisplayName("회원 가입")
    void testBefore() {
        Account account = Account.builder()
                .username("testUser")
                .password("testPwd")
                .email("test@naver.com")
                .build();

        MemberInfoDto memberInfoDto = MemberInfoDto.builder()
                .introduce("테스트")
                .birth(new Date())
                .gender("man")
                .memberAge(MemberAge.MEMBER_AGE_20S)
                .build();
        accountRepository.save(account);
        Member newMember = memberService.createNewMember(account, memberInfoDto);
        assertThat(newMember.getNickname()).isEqualTo("testUser");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd");
        assertThat(newMember.getGender()).isEqualTo("man");
    }

    @BeforeEach
    @DisplayName("게시글 등록")
    void t0() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        System.out.println("member nick" + member.getNickname());
        PostDto postDto = PostDto.builder()
                .placeId(26981291L) //등록되어 있는 place Id 사용
                .title("testTitle")
                .content("testContent")
                .privateStatus(true)
                .build();
        Post post = postService.savePost(member, postDto);

        postId = post.getId();
        memberId = post.getMember().getId();
        assertThat(post.getTitle()).isEqualTo("testTitle");
        assertThat(post.getContent()).isEqualTo("testContent");
        assertThat(post.isPrivateStatus()).isEqualTo(true);
        assertThat(post.getPlace().getId()).isEqualTo(26981291L);
    }


    @Test
    @DisplayName("PostLike 생성")
    void t1() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        PostLike postLike = likeApiService.createNewPostLike(member, post);
        assertThat(member.getNickname()).isEqualTo(postLike.getMember().getNickname());
        assertThat(member.getBirth()).isEqualTo(postLike.getMember().getBirth());
        assertThat(post.getTitle()).isEqualTo(postLike.getPost().getTitle());
        assertThat(post.getContent()).isEqualTo(postLike.getPost().getContent());
    }


    @Test
    @DisplayName("좋아요 기능 상태 리턴")
    void t2() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        likeApiService.modifyLikeStatus(member, postId, "post");
        assertThat(post.getPostLike().size()).isEqualTo(1);
        assertThat(member.getPostLike().size()).isEqualTo(1);
        likeApiService.modifyLikeStatus(member, postId, "post");
        assertThat(post.getPostLike().size()).isEqualTo(0);
        assertThat(member.getPostLike().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시글 좋아요 추가 및 삭제")
    void t3() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        boolean isPostLike = likeApiService.existPostLikeFlag(member, post);
        Assertions.assertThat(isPostLike).isEqualTo(false);
    }
}