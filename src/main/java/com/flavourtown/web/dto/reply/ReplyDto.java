package com.flavourtown.web.dto.reply;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    @NotEmpty(message = "댓글은 필수입니다.")
    @Size(min = 5, message = "댓글은 5글자이상 이내로 작성해주세요.")
    private String comment;

    private Long id;
    private String replyTime;
    private Integer replyLikeCount;
    private String nickname;
}
