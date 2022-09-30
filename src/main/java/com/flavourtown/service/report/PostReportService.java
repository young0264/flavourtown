package com.flavourtown.service.report;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.post.PostRepository;
import com.flavourtown.domain.report.PostReport;
import com.flavourtown.domain.report.PostReportRepository;
import com.flavourtown.domain.report.ReportCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;

    public PostReport savePostReport(long memberId, long postId,
                                     ReportCategory reportCategory,String content){
        Account account = accountRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 없음"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없음"));
        return postReportRepository.save(
                PostReport.createReport(account.getMember(), post, reportCategory, content)
        );
    }



}
