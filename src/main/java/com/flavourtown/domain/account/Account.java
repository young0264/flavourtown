package com.flavourtown.domain.account;

import com.flavourtown.domain.member.Member;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Account {

    // 주석 추가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    // 로그인 아이디
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void insertMember(Member member) {
        this.member = member;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
