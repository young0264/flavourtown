package com.flavourtown.web.controller.bookmark;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import com.flavourtown.service.FavoriteService;
import com.flavourtown.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final PlaceService placeService;

    /**
     * 북마크 카테고리 추가
     */
    @PostMapping("/favorite/addFavorite")
    public String addFavorite(@AuthUser Account account, @PathParam("subject") String subject) {
        Member member = account.getMember();
        Favorite favorite = new Favorite(member, subject);
        member.getFavoriteList().add(favorite); //연관관계
        favorite.setMember(member);
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     *장소에서 북마크추가하면서, 카테고리 위치 변경 가능
     */
    @GetMapping("/favorite/changeFavorite/{favoriteId}/{placeId}")
    public String changeFavorite(@AuthUser Account account,@PathVariable("favoriteId") Long favoriteId, @PathVariable("placeId") long placeId) {
        Member member = account.getMember();
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
        favorite.setSubject(subject);
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark/view";
    }

    /**
     *
     */
    @GetMapping("/favorite/deleteFavorite/{favoriteId}")
    public String deleteFavorite(@AuthUser Account account, @PathVariable Long favoriteId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        log.info("favorite.name -> {}",favorite.getSubject());

        favoriteService.delete(favorite);
        return "redirect:/profile/bookmark/view";
    }

    @GetMapping("/favorite/{favoriteId}/deletePlace/{placeId}")
    public String deletePlace(@AuthUser Account account, @PathVariable Long favoriteId, @PathVariable Long placeId) {
        Member member = account.getMember();
        Favorite favorite = favoriteService.findByIdAndMember(favoriteId, member);
        favorite.getPlaceList().remove(placeService.findPlace(placeId));
        favoriteService.save(favorite);
        return "redirect:/profile/bookmark/view";
    }
}