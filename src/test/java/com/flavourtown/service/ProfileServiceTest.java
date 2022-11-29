package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.vo.MemberVo;
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
class ProfileServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MemberRepository memberRepository;

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

    /**
     * profile page를 읽기위한 member VO 가져오기
     */
    @Test
    @DisplayName("member Vo 찾기")
    void t1() {
        Member member = memberRepository.findById(memberId).orElse(null);
        MemberVo memberVo = profileService.findByUsernameOrNickname(member.getNickname()).orElse(null);

        assertThat(memberVo.getNickname()).isEqualTo(member.getNickname());
    }
}