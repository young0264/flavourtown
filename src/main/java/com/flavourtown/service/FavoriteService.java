package com.flavourtown.service;

import com.flavourtown.domain.favorite.Favorite;
import com.flavourtown.domain.favorite.FavoriteRepository;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.place.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<Favorite> findAllByMember(Member member) {
        return favoriteRepository.findAllByMember(member);
    }

    public Favorite findTopByMember(Member member) {
        return favoriteRepository.findTopByMember(member);
    }

    public Favorite findById(Long favoriteId) {
        return favoriteRepository.findById(favoriteId).orElse(null);
    }
    public void replaceExistPlace(Member member, Place place, Favorite currentFavorite) {
        // Member -> FavoriteList 속에서 해당 Place가 있는지 확인
        List<Favorite> favoriteList = findAllByMember(member);
        for (Favorite favorite : favoriteList) {
            if (favorite.getPlaceList().contains(place)){
                favorite.getPlaceList().remove(place);
                break;
            }
        }
        currentFavorite.getPlaceList().add(place);
        place.setFavorite(currentFavorite);
        favoriteRepository.save(currentFavorite);
    }

    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public Favorite findByIdAndMember(Long favoriteId, Member member) {
        return favoriteRepository.findByIdAndMember(favoriteId, member);
    }

    public void delete(Favorite favorite) {
        log.info("favorite.name in service ->{}", favorite.getSubject());
        favoriteRepository.delete(favorite);
    }
}
