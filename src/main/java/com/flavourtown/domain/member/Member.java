package com.flavourtown.domain.member;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.like.PostLike;
import com.flavourtown.domain.like.ReplyLike;
import com.flavourtown.domain.post.Post;
import com.flavourtown.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@Setter
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    // 주석 추가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date birth;

    private String gender;

    private String introduce;

    private LocalDateTime signUpDate;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberAge memberAge;

    //pwd를 위한
    @OneToOne(fetch=FetchType.LAZY, mappedBy = "member", orphanRemoval = true)
    private Account account;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favoriteList = new ArrayList<>();

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.insertMember(this);
    }

    public void addBasicInfo(Date birth, String gender, String introduce, MemberAge memberAge) {
        this.birth = birth;
        this.gender = gender;
        this.introduce = introduce;
        this.memberAge = memberAge;
    }

    public void changeBasicInfo(String introduce) {
        this.introduce = introduce;
    }

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private Set<PostLike> postLike;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private Set<ReplyLike> replyLike;

    public void addPostLike(PostLike postLike) {
        this.postLike.add(postLike);
        postLike.setMember(this);
    }

    public void addFavoriteCategory(Favorite favorite) {
        this.getFavoriteList().add(favorite);
        favorite.insertMember(this);
    }

    public void insertAccount(Account account) {
        this.account = account;
    }

}