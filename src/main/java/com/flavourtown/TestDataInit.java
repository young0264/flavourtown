package com.flavourtown;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.AccountRole;
import com.flavourtown.domain.account.LoginType;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TestDataInit {
// 처음 프로젝트를 실행할 때만 작성되는 class

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct
    @Transactional
    public void memberDataInit() {
        if (accountRepository.findByUsername("member1").isEmpty()) {
            // 등록된 username 중 member1이 없다면 새로운 member 등록
            // 빌드 이용

            Account newAccount = Account.builder()
                    .username("member1")
                    .password(passwordEncoder.encode("member1!"))
                    .accountRole(AccountRole.ROLE_USER)
                    .loginType(LoginType.LOCAL)
                    .email("member1@test.com")
                    .build();
            Member member = memberRepository.save(Member.builder()
                    .introduce("hello world")
                    .gender("male")
                    .nickname(newAccount.getUsername())
                    .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .signUpDate(LocalDateTime.now())
                    .memberAge(MemberAge.MEMBER_AGE_20S)
                    .build());
            newAccount.insertMember(member);
            member.insertAccount(newAccount);
            accountRepository.save(newAccount);
        }
        if (accountRepository.findByUsername("admin").isEmpty()) {
            // 등록된 username 중 member1이 없다면 새로운 member 등록
            // 빌드 이용

            Account newAccount = Account.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin!"))
                    .accountRole(AccountRole.ROLE_ADMIN)
                    .loginType(LoginType.LOCAL)
                    .email("admin@test.com")
                    .build();
            Member member = memberRepository.save(Member.builder()
                    .introduce("hello world")
                    .gender("male")
                    .nickname(newAccount.getUsername())
                    .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .signUpDate(LocalDateTime.now())
                    .memberAge(MemberAge.MEMBER_AGE_20S)
                    .build());
            newAccount.insertMember(member);
            member.insertAccount(newAccount);
            accountRepository.save(newAccount);
        }
        if (accountRepository.findByUsername("1").isEmpty()) {
            // 등록된 username 중 member1이 없다면 새로운 member 등록
            // 빌드 이용

            Account newAccount = Account.builder()
                    .username("1")
                    .password(passwordEncoder.encode("1"))
                    .accountRole(AccountRole.ROLE_ADMIN)
                    .loginType(LoginType.LOCAL)
                    .email("1@test.com")
                    .build();
            Member member = memberRepository.save(Member.builder()
                    .introduce("hello world")
                    .gender("male")
                    .nickname(newAccount.getUsername())
                    .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .signUpDate(LocalDateTime.now())
                    .memberAge(MemberAge.MEMBER_AGE_20S)
                    .build());
            newAccount.insertMember(member);
            member.insertAccount(newAccount);
            accountRepository.save(newAccount);
        }
    }
}
