package com.flavourtown.service;

import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.domain.reply.Reply;
import com.flavourtown.domain.reply.ReplyRepository;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class ReplyServiceTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ReplyService replyService;

    Long postId;

    @BeforeEach
    @DisplayName("댓글 등록")
    void t1() {
        ReplyDto replyDto = ReplyDto.builder()
                .comment("test reply comment")
                .build();

        Post post = postRepository.findAll().get(0);
        postId = post.getId();

        Long replyId = replyService.saveReply(post.getId(), replyDto, post.getMember().getId());
        Reply reply = replyRepository.findById(replyId).orElse(null);

        assertThat(reply.getComment()).isEqualTo("test reply comment");
        assertThat(reply.getReplyLike().size()).isEqualTo(0);
        assertThat(reply.getModifyDate()).isNull();
        assertThat(reply.getNickname()).isEqualTo(post.getMember().getNickname());
        assertThat(reply.getWriter().getAccount().getUsername()).isEqualTo(post.getMember().getAccount().getUsername());
        assertThat(reply.getWriter().getNickname()).isEqualTo(post.getMember().getNickname());
        assertThat(reply.getPost().getUserName()).isEqualTo(post.getUserName());
        System.out.println();

    }

    @Test
    @DisplayName("댓글 수정")
    void t2() {
//        public void update(Reply reply, String content) {
        Post post = postRepository.findById(postId).orElse(null);
        Reply reply = post.getReplyList().get(0);
        replyService.update(reply, "test update reply");
        assertThat(reply.getComment()).isEqualTo("test update reply");
        assertThat(reply.getModifyDate()).isNotNull();
        assertThat(reply.getReplyLike().size()).isEqualTo(0);
        }





}