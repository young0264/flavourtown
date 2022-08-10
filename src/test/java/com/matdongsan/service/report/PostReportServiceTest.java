package com.matdongsan.service.report;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.report.PostReport;
import com.matdongsan.domain.report.PostReportRepository;
import com.matdongsan.domain.report.ReportCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostReportServiceTest {

    @Autowired
    private PostReportService postReportService;
    @Autowired
    private PostReportRepository postReportRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostsRepository postsRepository;

    @Test
    @Transactional
    @Rollback
    @DisplayName("PostReport 생성")
    void createPostReport(){
        Member member = new Member("memberA", "123",
                "email", "gender", LocalDateTime.now(), MemberRole.ROLE_USER);
        Posts posts = new Posts(null, "title", "content");

        Member savedMember = memberRepository.save(member);
        Posts savedPost = postsRepository.save(posts);

        PostReport savedReport = postReportService.savePostReport(savedMember.getId(), savedPost.getId(), ReportCategory.TYPE1, "content");

        PostReport findReport = postReportRepository.findById(savedReport.getId()).get();

        Assertions.assertThat(savedReport).isEqualTo(findReport);
    }
}