package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.LoginType;
import com.flavourtown.web.dto.account.AccountSignUpDto;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * account domain : 로그인, 회원가입, 인증 인가 관련
 */

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String username = "test121";
    private final String rowPwd = "test121!!";
    private final String userEmail = "test@naver.com";


//    public Account saveNewAccount(AccountSignUpDto accountSignUpDto, LoginType type) {
    @Test//saveNewAccount
    @DisplayName("account 회원가입")
    void t1() {
        AccountSignUpDto accountSignUpDto = AccountSignUpDto.builder()
                .username(username)
                .password(rowPwd)
                .email(userEmail)
                .build();
        Account account = accountService.saveNewAccount(accountSignUpDto, LoginType.LOCAL);

        //username, email, encoded password assert
        assertThat("test121").isEqualTo(account.getUsername());
        assertThat("test@naver.com").isEqualTo(account.getEmail());
        assertTrue(passwordEncoder.matches(rowPwd,account.getPassword()));
    }

    @Test
    @DisplayName("로그인")
    void t2() {

    }

    @Test//getReadOnlyMember
    @DisplayName("멤버 vo 가져오기")
    void t3() {

    }

    @Test//changeAccountPassword
    @DisplayName("비밀번호 변경")
    void t4() {

    }
}