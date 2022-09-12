package com.matdongsan.web.vo;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.reply.Reply;
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
    private final List<Posts> postsList;
    private final List<Reply> replyList;

}
