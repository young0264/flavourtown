package com.flavourtown.domain.post;

import com.flavourtown.domain.like.PostLike;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.reply.Reply;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 유저

    private String userName; //작성자

    @Column(length = 200 , nullable = false)
    private String title; // 제목

    @Column(nullable = false , columnDefinition = "TEXT")
    private String content; // 내용

    @Nullable
    private String imageUrls;

//    @Column(updatable = false) //
    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

//    @Column(nullable = false) 검증면에서 notnull어노테이션 추천
    @NotNull
    private boolean privateStatus; // 공개 / 비공개 여부  true => 비공개 , false => 공개

    @Builder.Default
    @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private Set<PostLike> postLike;

    // 추가 부분
    private String postTime;


    public void insertPostTime(String postTime) {
        this.postTime = postTime;
    }


    public void addPlace(Place place) {
        this.place = place;
        place.getPosts().add(this);
    }


    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.insertPost(this);
    }

    /**
     * 생성자를 통한 직접 수정
     */
    public void updatePost(String title, String content, boolean privateStatus) {
        this.title = title;
        this.content = content;
        this.privateStatus = privateStatus;
        this.modifiedTime = LocalDateTime.now();
    }



    public void addPostLike(PostLike postLike) {
        this.postLike.add(postLike);
        postLike.setPost(this);
    }

    public void deletePostLike(PostLike dpostLike) {
        this.postLike.remove(dpostLike);
        dpostLike.setPost(this);
    }


}
