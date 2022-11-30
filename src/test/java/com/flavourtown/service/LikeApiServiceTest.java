package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.like.PostLike;
import com.flavourtown.domain.like.PostLikeRepository;
import com.flavourtown.domain.like.ReplyLike;
import com.flavourtown.domain.like.ReplyLikeRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.domain.reply.Reply;
import com.flavourtown.domain.reply.ReplyRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.post.PostDto;
import com.flavourtown.web.dto.reply.ReplyDto;
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
@TestPropertySource(locations = "/application-test.properties")
class LikeApiServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    ReplyRepository replyRepository;
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
    @Autowired
    ReplyService replyService;
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
    @DisplayName("ReplyLike 생성")
    void t1_2() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        ReplyDto replyDto = ReplyDto.builder()
                .comment("test reply")
                .replyTime("test now")
                .replyLikeCount(1)
                .nickname("testUser")
                .build();
        Long replyId = replyService.saveReply(post.getId(), replyDto, member.getId());
        Reply reply = replyRepository.findById(replyId).orElse(null);
        ReplyLike replyLike = likeApiService.createNewReplyLike(member, reply);
        assertThat(member.getNickname()).isEqualTo(replyLike.getMember().getNickname());
        assertThat(member.getBirth()).isEqualTo(replyLike.getMember().getBirth());
        assertThat(reply.getNickname()).isEqualTo(replyLike.getReply().getNickname());
        assertThat(reply.getComment()).isEqualTo(replyLike.getReply().getComment());

    }


    @Test
    @DisplayName("post 좋아요 상태 리턴")
    void t2() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        System.out.println("post title : " + post.getTitle());
        System.out.println("post title : " + post.getContent());
        System.out.println("post title : " + post.getPostLike().size());

        likeApiService.modifyLikeStatus(member, postId, "post");
        System.out.println("post title : " + post.getPostLike().size());
        System.out.println("member size : " +member.getPostLike().size());

        assertThat(post.getPostLike().size()).isEqualTo(1);
        assertThat(member.getPostLike().size()).isEqualTo(1);
        likeApiService.modifyLikeStatus(member, postId, "post");
        assertThat(post.getPostLike().size()).isEqualTo(0);
        assertThat(member.getPostLike().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("reply 좋아요 상태 리턴")
    void t2_2() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        ReplyDto replyDto = ReplyDto.builder()
                .comment("test reply")
                .replyTime("test now")
                .replyLikeCount(0)
                .nickname("testUser")
                .build();
        Long replyId = replyService.saveReply(post.getId(), replyDto, member.getId());
        Reply reply = replyRepository.findById(replyId).orElse(null);

        likeApiService.modifyLikeStatus(member, replyId, "reply");
        assertThat(reply.getReplyLike().size()).isEqualTo(1);
//        assertThat(member.getReplyLike().size()).isEqualTo(1);
        likeApiService.modifyLikeStatus(member, replyId, "reply");
        assertThat(reply.getReplyLike().size()).isEqualTo(0);
//        assertThat(member.getReplyLike().size()).isEqualTo(0);
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