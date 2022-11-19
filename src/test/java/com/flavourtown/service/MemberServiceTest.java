package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * member domain : 회원 유저의 프로필
 */
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class MemberServiceTest {
//    @Autowired(required = false)
    Long memberId;
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * spring security 로직 제외한 메서드
     */
    public void changeMemberNickname(String nickname, Account account) {
        Member currentMember = account.getMember();
        currentMember.setNickname(nickname);
        memberRepository.save(currentMember);
//        forceAuthentication(account);
    }

    @BeforeEach
    @DisplayName("회원 가입")
    void t1() {
        Account account = new Account();
        account.setUsername("testUser");
        account.setPassword("testPwd");
        account.setEmail("test@naver.com");
        System.out.println(1);
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setIntroduce("테스트");
        memberInfoDto.setBirth(new Date());
        memberInfoDto.setGender("man");
        memberInfoDto.setMemberAge(MemberAge.MEMBER_AGE_20S);
        Member newMember = memberService.createNewMember(account, memberInfoDto);
        memberId = newMember.getId();

        assertThat(newMember.getNickname()).isEqualTo("testUser");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd");
        assertThat(newMember.getGender()).isEqualTo("man");
    }

    @Test
    @DisplayName("회원탈퇴")
    void t2() {
        memberService.withdrawalMember(memberId);
        Member member = memberRepository.findById(memberId).orElse(null);
        assertThat(member).isNull();
    }

    @BeforeEach
    @DisplayName("멤버 프로필 등록")
    void t3() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Date now = new Date();
        MemberInfoDto memberInfo = MemberInfoDto.builder()
                .gender("man")
                .birth(now)
                .memberAge(MemberAge.MEMBER_AGE_20S)
                .introduce("테스트 인삿말입니다.")
                .build();

        memberService.updateCurrentMember(member, memberInfo);
        assertThat(memberInfo.getMemberAge()).isEqualTo(MemberAge.MEMBER_AGE_20S);
        assertThat(memberInfo.getGender()).isEqualTo("man");
        assertThat(memberInfo.getBirth()).isEqualTo(now);
        assertThat(memberInfo.getIntroduce()).isEqualTo("테스트 인삿말입니다.");
    }

    @Test
    @DisplayName("멤버 프로필 닉네임변경")
    void t4() {
        Member member = memberRepository.findById(memberId).orElse(null);
        changeMemberNickname("testNewNickname", member.getAccount());
        assertThat(member.getNickname()).isEqualTo("testNewNickname");
    }


}