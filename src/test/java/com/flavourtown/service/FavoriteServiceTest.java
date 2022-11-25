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

    @Test
    @DisplayName("장소 북마크에 등록")
    void t2() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Place place = placeRepository.findById(26981291L).orElse(null);
        Favorite favorite = favoriteService.initFavorite(member, "test Favorite1");
        favoriteService.addCategory(member, favorite);
        favoriteService.replaceExistPlace(member, place, favorite);
        String subject = member.getFavoriteList().get(0).getSubject();

        //favorite 카테고리가 정상적으로 저장되었는지 확인
        assertThat(favorite.getSubject()).isEqualTo("test Favorite1");

        //favorite 카테고리를 추가 저장 후 member에 저장되있는 것과 같은지 확인
        assertThat(subject).isEqualTo("test Favorite1");

    }


}
