package com.flavourtown.web.vo;

import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberVo {
    private final String introduce;
    private final String nickname;
    private final Date birth;
    private final LocalDateTime signUpDate;
    private final String gender;
    private final List<Post> postList;
    private final List<Reply> replyList;

}
