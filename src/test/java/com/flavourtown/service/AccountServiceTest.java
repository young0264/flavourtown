package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.LoginType;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.web.dto.account.AccountSignUpDto;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.vo.MemberVo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * account domain : 로그인, 회원가입, 인증 인가 관련
 */

@SpringBootTest
@Transactional
@TestPropertySource(locations = "/application-test.properties")
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String username = "test121";
    private final String rowPwd = "test121!!";
    private final String userEmail = "test@naver.com";

    Long memberId;

    @BeforeEach
    @DisplayName("회원 가입")
    void t0() {
        Account account = new Account();
        account.setUsername("testUser11");
        account.setPassword("testPwd11");
        account.setEmail("test11@naver.com");
        System.out.println(1);
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setIntroduce("테스트 intro");
        memberInfoDto.setBirth(new Date());
        memberInfoDto.setGender("man");
        memberInfoDto.setMemberAge(MemberAge.MEMBER_AGE_20S);
        Member newMember = memberService.createNewMember(account, memberInfoDto);
        memberId = newMember.getId();

        assertThat(newMember.getNickname()).isEqualTo("testUser11");
        assertThat(newMember.getAccount().getPassword()).isEqualTo("testPwd11");
        assertThat(newMember.getGender()).isEqualTo("man");
    }
    @Test//saveNewAccount
    @DisplayName("account 회원가입")
    void t1() {
        AccountSignUpDto accountSignUpDto = AccountSignUpDto.builder()
                .username(username)
                .password(rowPwd)
                .email(userEmail)
                .build();
        Account account = accountService.saveNewAccount(accountSignUpDto, LoginType.LOCAL);

        //username, email, encoded password 검증
        assertThat("test121").isEqualTo(account.getUsername());
        assertThat("test@naver.com").isEqualTo(account.getEmail());
        assertTrue(passwordEncoder.matches(rowPwd,account.getPassword()));
    }

    @Test//getReadOnlyMember
    @DisplayName("멤버vo 가져오기")
    void t3() {
        Member member = memberRepository.findById(memberId).orElse(null);
        MemberVo memberVo = accountService.getReadOnlyMember(member.getNickname());

        assertThat(memberVo.getNickname()).isEqualTo("testUser11");
        assertThat(memberVo.getIntroduce()).isEqualTo("테스트 intro");
    }

    @Test//changeAccountPassword
    @DisplayName("비밀번호 변경")
    void t4() {
        Member member = memberRepository.findById(memberId).orElse(null);
        Account account = member.getAccount();
        String newPwd = "changedPwd";

        accountService.changeAccountPassword(newPwd, account);

        assertTrue(passwordEncoder.matches(newPwd, account.getPassword()));
    }
}