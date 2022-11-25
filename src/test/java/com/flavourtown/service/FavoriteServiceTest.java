package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.favorite.FavoriteRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.place.PlaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class FavoriteServiceTest {


    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("카테고리 등록")
    void t1() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Account account = accountRepository.findByUsername("testUser").orElse(null);
        assertThat(account.getUsername()).isEqualTo("testUser");
        Favorite favorite = favoriteService.initFavorite(account.getMember(), "test category subject");

        favoriteService.addCategory(member, favorite);
        assertThat(member.getFavoriteList().size()).isEqualTo(1);
        Assertions.assertThat(member.getFavoriteList().get(0).getSubject()).isEqualTo("test category subject");
    }

}