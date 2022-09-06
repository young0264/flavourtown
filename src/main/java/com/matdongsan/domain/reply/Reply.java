package com.matdongsan.domain.reply;

import com.matdongsan.domain.likeuser.ReplyLike;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
//계정마다 관리가능하게
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 500)
    private String comment;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY) //댓글 작성자
    private Member writer;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReplyLike> replyLike;


    //데이터 필드를 가지고 있는 단에서 비즈니스 로직내기
    public void updateComment(String comment) {
        this.comment = comment;
        this.modifyDate = LocalDateTime.now();
    }


//    public void save(Posts post, Member member) {
//        this.posts = post;
//        this.writer = member;
//    }

}
