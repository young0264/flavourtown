package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
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
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class MemberServiceTest {
//    @Autowired(required = false)
    @Autowired
    private MemberService memberService;

    Long memberId;

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
    }


    @Test
    @DisplayName("소갯말 수정")
    void t3() {


    }

}