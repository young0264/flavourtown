package com.flavourtown.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.place.PlaceRepository;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.place.PlaceDto;
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
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class PlaceServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    PlaceService placeService;
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

    //    public void savePlace(PlaceDto placeDto) {
    //PlaceDto
//    private long id;
//    private String placeName;
//    private String placeUrl;
//    private String categoryName;
//    private String addressName;
//    private String roadAddressName;
//    private String phone;
//    private String x;
//    private String y;
//
//    @JsonProperty("category_group_code")
//    private String categoryGroupCode;
//    @JsonProperty("category_group_name")
//    private String categoryGroupName;
//    private String distance;

    @Test
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
    }
}