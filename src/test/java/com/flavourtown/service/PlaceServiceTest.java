package com.flavourtown.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.place.PlaceRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.place.PlaceDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "/application-test.properties")
class PlaceServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceService placeService;

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


    @BeforeEach
    @DisplayName("place 저장")
    void t1() {
        Long placeId = 1971337446L;    //1971337446L : 두수고방
        PlaceDto placeDto = PlaceDto.builder()
                .id(1971337446L)
                .placeName("두수고방")
                .placeUrl("flavourtown.site")
                .categoryName("음식점")
                .addressName("수원")
                .roadAddressName("수원")
                .phone("031")
                .x("127.06070790835648")
                .y("37.27485723753273")
                .build();

        placeService.savePlace(placeDto);
        Place place = placeRepository.findById(1971337446L).orElse(null);

        assertThat(place.getPlaceName()).isEqualTo("두수고방");
        assertThat(place.getId()).isEqualTo(1971337446L);
        assertThat(place.getCategoryName()).isEqualTo("음식점");
        System.out.println("place menu : " + place.getMenus());
    }

    @Test
    @DisplayName("place info 가져오기")
    void t2() {
        Long placeId = 1971337446L;    //1971337446L : 두수고방
        Place place = placeRepository.findById(placeId).orElse(null);

        placeService.pullInfo(place);

        //해당 place menu, 편의시설, mainphoto, photo url (부가 info)검증
        assertThat(place.getMenus()).isEqualTo("보성녹차(HOT)=6,000|두수고방에서 만든 식혜=5,000|두수고방에서 만든 수정과=5,000|오늘의 공양=18,000");
        assertThat(place.getFacilityInfo()).isEqualTo("");
        assertThat(place.getMainPhotoUrl()).isEqualTo("http://t1.daumcdn.net/place/59C0763DCACC4117A2E63ED7BBECCB52");
        assertThat(place.getPhotoUrls()).isEqualTo("http://t1.daumcdn.net/place/59C0763DCACC4117A2E63ED7BBECCB52,http://t1.daumcdn.net/local/kakaomapPhoto/review/5c438397bcb7cb9cc9564c8f9ee1ccc4e5b0838c?original,http://t1.daumcdn.net/local/kakaomapPhoto/review/c2a1b64df9f9054a57caceeffb0f1f3f68d4b884?original,http://t1.daumcdn.net/local/kakaomapPhoto/review/6e797c3dc7ed1c5f59e01dc2091c808283546943?original"

        );
    }
}