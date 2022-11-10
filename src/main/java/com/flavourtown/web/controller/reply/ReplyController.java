package com.flavourtown.web.controller.reply;


import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.reply.Reply;
import com.flavourtown.service.MemberService;
import com.flavourtown.service.PostService;
import com.flavourtown.service.ReplyService;
import com.flavourtown.web.dto.reply.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final PostService postService;
    private final ReplyService replyService;
/**
 * 시간 테스트
 */
    @ResponseBody
    @GetMapping("test")
    public String home() {
        LocalDateTime now = LocalDateTime.now();
        log.info("test time : " + now.toString());
        return now.toString();
    }
    /**
     * 댓글등록 (버튼)
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @RequestMapping(value = "/post/{postId}/reply", method = {RequestMethod.POST})
    public ResponseEntity createReply(@Valid ReplyDto replyDto, BindingResult bindingResult,
                                      @PathVariable("postId") Long postId,
                                      @AuthUser Account account,
                                      Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Member member = account.getMember(); //securityuser . account . member . id가져오기
        Long memberId = member.getId(); //securityuser . account . member . id가져오기
        Long replyId = replyService.saveReply(postId, replyDto, memberId);
        Reply reply = replyService.getReply(replyId);
        ReplyDto newReplyDto = ReplyDto.builder()
                .id(reply.getId())
                .nickname(member.getNickname())
                .replyLikeCount(reply.getReplyLike().size())
                .replyTime(reply.getReplyTime())
                .comment(reply.getComment())
                .build();
        log.info("값이 들어갔습니다 = " + replyDto.getComment());
        return ResponseEntity.ok(newReplyDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/update")
    @ResponseBody
    public boolean updateReply(@RequestParam Map<String, String> params, Model model) {
        Reply currentReply = replyService.getReply(Long.valueOf(params.get("replyNum")));
        replyService.update(currentReply, params.get("replyComment"));
        return true;
    }

    /**
     *
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/delete")
    @ResponseBody
    public boolean removeReply(@RequestParam Map<String, String> params) {
        Reply currentReply = replyService.getReply(Long.valueOf(params.get("replyNum")));
        replyService.deleteReply(currentReply);
        return true;
    }
}
