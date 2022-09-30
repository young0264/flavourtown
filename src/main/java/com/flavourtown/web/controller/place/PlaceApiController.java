package com.flavourtown.web.controller.place;

import com.flavourtown.domain.place.PlaceDto;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.FavoriteService;
import com.flavourtown.service.PlaceService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceApiController {

    private final AccountService accountService;
    private final PlaceService placeService;
    private final FavoriteService favoriteService;
    
    @PostMapping
    public ResponseEntity<String> savePlace(@RequestBody PlaceDto placeDto){
        log.info("placeDto = {}", placeDto);
        placeService.savePlace(placeDto);
        return ResponseEntity.ok().body("ok");
    }

    /*@PostMapping("/{placeId}/favorite")
    public ResponseEntity<FavoriteDto> doFavorite(Principal principal, @PathVariable("placeId") Long placeId) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();
        Place place = placeService.findPlace(placeId);

        placeService.doFavorite(member.getId(), placeId);

        int count = favoriteService.countByPlace(place);
        boolean isFavorite = favoriteService.existFavorite(member, place);

        return ResponseEntity.ok().body(new FavoriteDto(count, isFavorite));
    }*/

    @Data
    @AllArgsConstructor
    static class FavoriteDto{
        int favoriteCount;
        boolean isFavorite;
    }



}
