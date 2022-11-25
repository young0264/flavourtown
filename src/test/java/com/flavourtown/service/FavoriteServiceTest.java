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

    @Test
    @DisplayName("북마크 다른 카테고리로 이동")
    void t3() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Place place = placeRepository.findById(26981291L).orElse(null);
        Favorite favorite1 = favoriteService.initFavorite(member, "test Favorite1");
        Favorite favorite2 = favoriteService.initFavorite(member, "test Favorite2");

        //카테고리 1,2 추가
        favoriteService.addCategory(member, favorite1);
        favoriteService.addCategory(member, favorite2);

        //카테고리 1에 북마크한 palce 등록
        favoriteService.replaceExistPlace(member, place, favorite1);

        String subject = member.getFavoriteList().get(0).getSubject();

        //favorite 카테고리가 정상적으로 저장되었는지 확인
        assertThat(favorite1.getSubject()).isEqualTo("test Favorite1");

        //favorite 카테고리를 추가 저장 후 member에 저장되있는 것과 같은지 확인
        assertThat(subject).isEqualTo("test Favorite1");

        //favorite 카테고리 1에있던걸 2로 옮김 -> 1에없고 2에 있는 검증해야함
        favoriteService.replaceExistPlace(member, place, favorite2);

        List<Favorite> favoriteList = member.getFavoriteList();
        for (Favorite favorite : favoriteList) {
            if (favorite.getSubject().equals("test Favorite2")) {
                assertThat(favorite.getPlaceList().get(0).getPlaceName()).isEqualTo(place.getPlaceName());
                break;
            }
        }

    }


    //    public void delete(Favorite favorite) {
    @Test
    @DisplayName("북마크 삭제")
    void t4() {
        Member member = memberRepository.findByNickname("testUser").orElse(null);
        Place place = placeRepository.findById(26981291L).orElse(null);
        Favorite favorite = favoriteService.initFavorite(member, "test Favorite1");
        //카테고리 생성
        favoriteService.addCategory(member, favorite);
        //카테고리에 북마크한 장소 추가
        favoriteService.replaceExistPlace(member, place, favorite);

        assertThat(favorite.getSubject()).isEqualTo("test Favorite1");
        favorite.getPlaceList().remove(place);
        favoriteRepository.save(favorite);

        //북마크한 장소를 삭제한 placeList의 길이는 0 : favorite에서 확인
        assertThat(favorite.getPlaceList().size()).isEqualTo(0);
        //북마크한 장소를 삭제한 placeList의 길이는 0 : repository에서 확인
        assertThat(favoriteRepository.findAllByMember(member).get(0).getPlaceList().size()).isEqualTo(0);
    }


}
