package com.flavourtown.web.controller.bookmark;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.FavoriteService;
import com.flavourtown.service.MemberService;
import com.flavourtown.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final PlaceService placeService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final MemberService memberService;

    /**
     * 북마크 카테고리 추가
     */
    @PostMapping("/favorite/addFavorite")
    public String addFavorite(@AuthUser Account account, @PathParam("subject") String subject, Principal principal) {
//        String username = account.getUserna게me(); // 이건 나오는데
//        String nickname2 = account.getMember().getNickname(); //되네?
//        System.out.println("나오나이게 123 " + username);
//        System.out.println("나오나이게 222 " + nickname2);

        //영속성에 안담긴건 줄 알았으나 AuthUser로 찾는 account가 그냥 null인것.?

        //Member 객체 Repository에서 찾기
        // //transactional 넣음
//        Account account1 = accountRepository.findByUsername(username).orElse(null);
//        Member member = account1.getMember();

        //안되는것
//        Member member = account.getMember();

        //principal로 찾기
        String name = principal.getName();
        Member member = memberService.findMemberByUsername(name);
//        log.info("member 나오나 : " + member.getNickname());
        Favorite favorite = favoriteService.initFavorite(member, subject);
        System.out.println("1231212 : " + member.getFavoriteList().size());// no session 이것도 에러남
        favoriteService.addCategory(member,favorite);

        favoriteService.addCategory(member, favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     *장소에서 북마크추가하면서, 카테고리 위치 변경 가능
     */
    @GetMapping("/favorite/changeFavorite/{favoriteId}/{placeId}")
    public String changeFavorite(@AuthUser Account account,@PathVariable("favoriteId") Long favoriteId, @PathVariable("placeId") long placeId) {
        Member member = account.getMember(); //여기 영속성은 정상작동
        Place place = placeService.findPlace(placeId);
        Favorite favorite = favoriteService.findById(favoriteId);
        favoriteService.replaceExistPlace(member, place, favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     * 북마크 카테고리 이름 변경
     */
    @PostMapping("/favorite/modifyFavorite/{favoriteId}")
    public String modifyFavorite(@AuthUser Account account, @PathVariable Long favoriteId, @PathParam("subject") String subject) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favorite.insertSubject(subject);
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     *북마크 카테고리 삭제
     */
    @GetMapping("/favorite/deleteFavorite/{favoriteId}")
    public String deleteFavorite(@AuthUser Account account, @PathVariable Long favoriteId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favoriteService.delete(member, favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     * 북마크 장소 삭제
     */
    @GetMapping("/favorite/{favoriteId}/deletePlace/{placeId}")
    public String deletePlace(@AuthUser Account account, @PathVariable Long favoriteId, @PathVariable Long placeId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favorite.getPlaceList().remove(placeService.findPlace(placeId));
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark/view";
    }
}
