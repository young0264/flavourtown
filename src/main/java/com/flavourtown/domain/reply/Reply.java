package com.flavourtown.domain.reply;

import com.flavourtown.domain.like.ReplyLike;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Entity
@Builder
@Getter
@RequiredArgsConstructor
//계정마다 관리가능하게
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;
    @Column(nullable = false, length = 500)
    private String comment;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY) //
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    //cascade = CascadeType.ALL,
    private Set<ReplyLike> replyLike = new HashSet<>();

    /**
     * 연관관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Reply parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> child = new ArrayList<>();

    private String replyTime;

    //데이터 필드를 가지고 있는 단에서 비즈니스 로직내기
    public void updateComment(String comment) {
        this.comment = comment;
        this.modifyDate = LocalDateTime.now();
    }

    public void insertReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public void insertPost(Post post) {
        this.post = post;
    }

    public void insertMember(Member member) {
        this.writer = member;
    }


}
