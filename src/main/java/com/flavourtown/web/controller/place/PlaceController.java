package com.flavourtown.web.controller.place;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import com.flavourtown.domain.post.Post;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.FavoriteService;
import com.flavourtown.service.PlaceService;
import com.flavourtown.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;
    private final FavoriteService favoriteService;
    private final AccountService accountService;
    private final PostService postService;

    @GetMapping
    public String showList(){
        return "place/place-list";
    }

    @GetMapping("/map")
    public String showMap(){
        return "place/place-map";
    }

    @GetMapping("/{placeId}/detail")
    public String showPlaceDetail(Principal principal,@PathVariable("placeId") long placeId, Model model) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();
        Place place = placeService.findPlace(placeId);
        List<Post> posts = postService.findAllByPlaceAndPrivateStatus(place);

        Optional<Favorite> optionalFavorite = Optional.ofNullable(favoriteService.findTopByMember(member));
        if (optionalFavorite.isPresent()) {
            List<Favorite> favoriteList = favoriteService.findAllByMember(member);
            model.addAttribute("favorites", favoriteList);
        } else {
            Favorite favorite = new Favorite(member, "나만의 맛집");
            favoriteService.save(favorite);
            model.addAttribute("favorites", favorite);
        }
        model.addAttribute("place", place);
        model.addAttribute("posts", posts);

        return "place/place-detail";
    }





}
