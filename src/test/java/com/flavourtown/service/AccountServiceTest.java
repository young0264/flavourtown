package com.flavourtown.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * account domain : 로그인, 회원가입, 인증 인가 관련
 */

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class AccountServiceTest {

    @Test
    @DisplayName("1")
    void t1() {

    }

    @Test
    @DisplayName("2")
    void t2() {

    }

    @Test
    void t3() {

    }
}